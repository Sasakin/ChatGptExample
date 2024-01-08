import pandas as pd
from datetime import timedelta
import numpy as np
import prophet as Prophet
import time

import requests
from requests.adapters import HTTPAdapter
from urllib3.util.retry import Retry

import requests

def parefut():
  url = 'https://api.bybit.com/v5/market/instruments-info?category=spot'
  r = requests.get(url)
  df = pd.DataFrame(r.json()["result"]["list"])
  df = df[df['symbol'].str.contains('USDT')]
  df = df[df['status'] == 'Trading']
  pare = df['symbol']
  return pare

#print(parefut())

def candlesusdt(symb):
  try:
    url = "https://api.bybit.com/v5/market/kline"
    params = {
      "symbol": symb,
      "interval": "D",
      "limit": 300 , # Количество котировок, которые вы хотите получить
      "category": "spot"
    }

    response = requests.get(url, params=params)
    df = pd.DataFrame(response.json()["result"]["list"])
    m = pd.DataFrame()
    m['ds'] = df.iloc[:,0]
    m['ds'] = pd.to_datetime(np.int64(m['ds']), unit='ms')
    m['y'] = df.iloc[:,4].astype(float)
    return m
  except IndexError:
    # Код, который будет выполнен, если возникнет исключение IndexError
    print("Ошибка функции: candlesusdt: " + symb)

#print(candlesusdt("BTCUSDT"))

def nedo(limit):
  sp = []
  name = parefut()
  name = name[:limit]
  for i in name:
    s = candlesusdt(i)
    m = Prophet.Prophet()
    m.fit(s)
    future = m.make_future_dataframe(periods=0)
    forecast = m.predict(future)
    forecast = forecast[['ds', 'yhat', 'yhat_lower', 'yhat_upper']]
    if s.iloc[-1,-1] < forecast.iloc[-1, -2]:
      sp.append(i)

  if len(sp) == 0:
    print('Недооцененных монет нет')
  if len(sp) > 0:
    print(f'Недооцененныe монеты {sp}')

#nedo(100)

def pere(limit):
  sp = []
  name = parefut()[:limit]
  for i in name:
    s = candlesusdt(i)
    m = Prophet.Prophet()
    m.fit(s)
    future = m.make_future_dataframe(periods=0)
    forecast = m.predict(future)
    forecast = forecast[['ds', 'yhat', 'yhat_lower', 'yhat_upper']]
    if s.iloc[-1,-1] > forecast.iloc[-1, -1]:
      sp.append(i)

  if len(sp) == 0:
    print('Переоцененных монет нет')
  if len(sp) > 0:
    print(f'Переоцененныe монеты {sp}')

#pere(20)

def prophetPlot():
  from prophet.plot import plot_plotly, plot_components_plotly

  s = candlesusdt('BTCUSDT')
  m = Prophet.Prophet() # 'EOSUSDT', 'LTCUSDT', 'CHZUSDT', 'MANAUSDT'
  m.fit(s)
  future = m.make_future_dataframe(periods=10)
  forecast = m.predict(future)
  forecast = forecast[['ds', 'yhat', 'yhat_lower', 'yhat_upper']]

  plot_plotly(m, forecast)

def matplotPlot():
   import matplotlib.pyplot as plt
   from prophet.plot import plot_plotly, plot_components_plotly
   from prophet.plot import add_changepoints_to_plot
   from prophet.plot import plot_forecast_component

   s = candlesusdt('BTCUSDT')
   m = Prophet.Prophet() # 'EOSUSDT', 'LTCUSDT', 'CHZUSDT', 'MANAUSDT'
   m.fit(s)
   future = m.make_future_dataframe(periods=10)
   forecast = m.predict(future)
   forecast = forecast[['ds', 'yhat', 'yhat_lower', 'yhat_upper']]

   # Создание графика временных рядов
   fig, ax = plt.subplots()
   ax.plot(forecast['ds'], forecast['yhat'], label='Forecast')
   ax.fill_between(forecast['ds'], forecast['yhat_lower'], forecast['yhat_upper'], alpha=0.3, label='Confidence Interval')
   ax.scatter(s['ds'], s['y'], label='Actual')
   ax.legend()
   ax.set_title('BTCUSDT Price Forecast')
   ax.set_xlabel('Date')
   ax.set_ylabel('Price')

   # Отображение графика
   plt.show()

   # Создание графиков компонентов
   fig = m.plot_components(forecast)
   plt.show()

matplotPlot()
#prophetPlot()