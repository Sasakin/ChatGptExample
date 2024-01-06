const express = require('express');
const app = express();
const ejs = require('ejs');

app.set('view engine', 'ejs');
app.set('views', __dirname + '/views');

app.get('/', (req, res) => {
    const labels = ['Янв', 'Фев', 'Мар', 'Апр', 'Май'];
    const data = [12, 19, 3, 5, 2];
    const chartLabel = 'Продажи';

    res.render('index1', { labels, data, chartLabel });
});

app.listen(3000, () => {
    console.log('Сервер запущен на порту 3000');
});