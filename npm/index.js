const axios  = require('axios');
const express = require('express');
const app = express();
/*const { createCanvas } = require('canvas');*/
const Chart = require('chart.js');
const Decimal = require('decimal.js');
const parser = require('really-relaxed-json').createParser();

const ejs = require('ejs');
app.set('view engine', 'ejs');
app.set('views', __dirname + '/views');

async function getData(res) {
    try {
        const response = await axios.get('https://api.coincap.io/v2/assets/bitcoin/history?interval=d1&start=1630435200000&end=1633027200000');
        const data = response.data.data;

        // Extract the required data for the chart
        const dates = data.map(item => new Date(item.time));
        const prices = data.map(item => item.priceUsd);
        app.set('view engine', 'ejs'); // Установка EJS в качестве движка шаблонов
        res.render = ejs.render
        res.render('index.ejs', { dates, prices });
        console.log('График успешно создан');
    } catch (error) {
        console.error(error);
    }
}
/*
function getData(res) {
    // Ваша логика получения данных
    const dates = ['01.01.2024', '02.01.2024', '03.01.2024'];
    const prices = [10, 20, 30];

    res.render('index.ejs', { dates:dates, prices: prices });
}*/

app.get('/', async (req, res) => {
    const response = await axios.get('https://api.coincap.io/v2/assets/bitcoin/history?interval=d1&start=1630435200000&end=1633027200000');
    const data = response.data.data;
    res.setHeader('Content-Type', 'text/html; charset=utf-8');

    // Extract the required data for the chart
    const dates = data.map(item => item.time);
    const prices = data.map(item => new Decimal(item.priceUsd));
    res.render('index', {dates, prices});
});

app.use((req, res) => {
    res.status(404).send('404 Not Found');
});

app.listen(3000, () => {
    console.log('Сервер запущен на порту 3000');
});