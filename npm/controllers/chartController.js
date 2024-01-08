const { spawn } = require('child_process');

function generateChart(req, res) {
    const symbol = req.query.symbol;

    // Запуск Python-скрипта
    const python = spawn('python3', ['python/generate_chart.py', symbol]);

    // Получение вывода из Python-скрипта
    let img_base64 = '';
    python.stdout.on('data', (data) => {
        img_base64 += data.toString();
    });

    // Обработка ошибок из Python-скрипта
    python.stderr.on('data', (data) => {
        console.error(data.toString());
    });

    // Обработка завершения Python-скрипта
    python.on('close', (code) => {
        if (code !== 0) {
            console.error(`Python-скрипт завершился с кодом ${code}`);
            res.status(500).send('Ошибка генерации графика');
            return;
        }

        // Возвращение данных изображения в качестве ответа
        res.send(img_base64);
    });
}

module.exports = {
    generateChart
};