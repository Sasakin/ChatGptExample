// Создаем элементы для шапки сайта
const header = document.createElement('header');
const logo = document.createElement('img');
const nav = document.createElement('nav');

// Устанавливаем атрибуты для логотипа
logo.src = 'path/to/logo.png';
logo.alt = 'Logo';

// Добавляем элементы на страницу
header.appendChild(logo);
header.appendChild(nav);
document.body.prepend(header);

// Добавляем элементы навигации
const navLinks = [
    { text: 'Главная', href: '/' },
    { text: 'О нас', href: '/about' },
    { text: 'Контакты', href: '/contact' }
];

navLinks.forEach(link => {
    const a = document.createElement('a');
    a.textContent = link.text;
    a.href = link.href;
    nav.appendChild(a);
});

// Стилизуем шапку сайта
header.style.display = 'flex';
header.style.justifyContent = 'space-between';
header.style.alignItems = 'center';
header.style.padding = '1rem';
header.style.backgroundColor = '#fff';

logo.style.maxHeight = '50px';

nav.style.display = 'flex';
nav.style.gap = '1rem';

nav.querySelectorAll('a').forEach(a => {
    a.style.color = '#333';
    a.style.textDecoration = 'none';
});