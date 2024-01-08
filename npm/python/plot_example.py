import matplotlib.pyplot as plt

# Создание данных для гистограммы
data = [1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5]

# Создание гистограммы
plt.hist(data, bins=5)

# Отображение гистограммы
plt.show()