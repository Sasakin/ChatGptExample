const express = require('express');
const app = express();
const path = require('path');
const chartController = require('./controllers/chartController');
const { getData} = require('./utils/chartUtils');
const { aptGetUpdate, installPython} = require('./utils/pythonUtils');

app.set('view engine', 'ejs');
app.set('views', __dirname + '/views');
app.use(express.static(path.join(__dirname, 'public')));

app.get('/', async (req, res) => {
    await getData(res);
});

// Обработка GET-запроса на страницу чата
app.get('/chat', (req, res) => {
    res.render('chat'); // отображение страницы chat.ejs
});

app.get('/stock', (req, res) => {
    res.render('stock');
});

app.get('/generate-chart', chartController.generateChart);

app.use((req, res) => {
    res.status(404).send('404 Not Found');
});

app.listen(3000, () => {
    aptGetUpdate()
        .then(() => {
            console.log('apt-get update completed successfully');
            installPython();
        })
        .catch((error) => {
            console.error('apt-get update failed:', error);
        });
    console.log('Сервер запущен на порту 3000');
});