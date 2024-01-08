import sys
import io
import base64
import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
import prophet as Prophet
import requests

symbol = sys.argv[1]

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

def matplotPlot(symbol):

    s = candlesusdt(symbol)
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
    # Convert plot to PNG image
    buffer = io.BytesIO()
    plt.savefig(buffer, format='png')
    img_base64 = base64.b64encode(buffer.getvalue()).decode('utf-8')
    print(img_base64)

matplotPlot(symbol)