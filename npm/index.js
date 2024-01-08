const axios  = require('axios');
const express = require('express');
const app = express();
/*const { createCanvas } = require('canvas');*/
const Chart = require('chart.js');
const Decimal = require('decimal.js');
const parser = require('really-relaxed-json').createParser();
const path = require('path');

const ejs = require('ejs');
app.set('view engine', 'ejs');
app.set('views', __dirname + '/views');
app.use(express.static(path.join(__dirname, 'public')));

async function getData(res) {
    try {
        const startDate = new Date('2021-09-01'); // задаем начальную дату
        const endDate = new Date(); // получаем текущую дату и время

        const startTimestamp = startDate.getTime(); // получаем timestamp начальной даты
        const endTimestamp = endDate.getTime(); // получаем timestamp текущей даты

        const apiUrl = `https://api.coincap.io/v2/assets/bitcoin/history?interval=d1&start=${startTimestamp}&end=${endTimestamp}`;

        const response = await axios.get(apiUrl); // отправляем запрос с параметризированными датами

        const data = response.data.data;

        // Extract the required data for the chart
        const dates= data.map(date => {
            const currentDate = new Date(date.time);
            const year = currentDate.getFullYear().toString().slice(2);
            const month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
            const day = currentDate.getDate().toString().padStart(2, '0');
            return `${year}-${month}-${day}`;
        });
        const prices = data.map(item => item.priceUsd);
        res.render('index', { dates, prices });
        console.log('График успешно создан');
    } catch (error) {
        console.error(error);
    }
}

app.get('/', async (req, res) => {
    getData(res);
});

// Обработка GET-запроса на страницу чата
app.get('/chat', (req, res) => {
    res.render('chat'); // отображение страницы chat.ejs
});

app.get('/stock', (req, res) => {
    res.render('stock'); // отображение страницы stock.ejs
});

app.use((req, res) => {
    res.status(404).send('404 Not Found');
});

app.listen(3000, () => {
    console.log('Сервер запущен на порту 3000');
});