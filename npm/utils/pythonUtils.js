const { spawn } = require("child_process");

function aptGetUpdate() {
    return new Promise((resolve, reject) => {
        const updateProcess = spawn('apt-get', ['update']);

        updateProcess.stdout.on('data', (data) => {
            console.log(`stdout: ${data}`);
        });

        updateProcess.stderr.on('data', (data) => {
            console.error(`stderr: ${data}`);
            reject(new Error(`stderr: ${data}`));
        });

        updateProcess.on('close', (code) => {
            console.log(`Child process exited with code ${code}`);
            if (code === 0) {
                resolve();
            } else {
                reject(new Error(`Child process exited with code ${code}`));
            }
        });
    });
}

async function installPython() {
    console.log('install pipx completed successfully');
    installLibProphet()
    await installLib('python3-pandas')
    await installLib('python3-numpy')
    await installLib('python3-requests')
    await installLib('python3-matplotlib')
}

function installLibProphet() {
    // Запуск команды установки Python
    const installPython = spawn('python3', ['-m', 'pip', 'install', 'prophet', '--break-system-packages']);

    // Обработка событий вывода данных
    installPython.stdout.on('data', (data) => {
        console.log(`stdout: ${data}`);
    });

    // Обработка событий вывода ошибок
    installPython.stderr.on('data', (data) => {
        console.error(`stderr: ${data}`);
    });

    // Обработка события завершения команды
    installPython.on('close', (code) => {
        console.log(`Child process exited with code ${code}`);
    });
}


async function installLib(lib) {
    return new Promise((resolve, reject) => {
        // Запуск команды установки Python
        const installPython = spawn('apt', ['install', '-y', lib]);

        // Обработка событий вывода данных
        installPython.stdout.on('data', (data) => {
            console.log(`stdout: ${data}`);
        });

        // Обработка событий вывода ошибок
        installPython.stderr.on('data', (data) => {
            console.error(`stderr: ${data} lib: ${lib}`);
        });

        // Обработка события завершения команды
        installPython.on('close', (code) => {
            console.log(`Child process exited with code ${code}`);
            if (code === 0) {
                resolve();
            } else {
                reject(new Error(`Child process exited with code ${code}`));
            }
        });
    });
}

module.exports = {
    aptGetUpdate,
    installPython
};