# Проект по объекто-ориентированному программированию


# Игра шахматы
Позволяет сыграть в [шахматы](https://ru.wikipedia.org/wiki/%D0%A8%D0%B0%D1%85%D0%BC%D0%B0%D1%82%D1%8B) на 2 человек. Реализации времени в игре не предусмотрено.

# Как собирать?
Проект собирается с помощью maven, команды:
```sh
$ mvn package
```
В Intelij IDEA можно создать maven конфиг и передать туда параметр package.

# Как запускать?
```sh
$ java -jar target\chessGame-1.0-SNAPSHOT.jar
```

# Описание проекта
Состоит из следующих pachages:
1. chess, отвечает за всю отрисовку.
2. chessboard, информация о расстановке фигур на доске.
3. piece, логика свойств фигур.
4. constants, содержит различные константы в коде

Общая структура проекта выглядит следующим образом:
![alt text](https://image.ibb.co/dtxK77/Untitled_Diagram_4.png)

1. Main - запуск игры
2. Game - сама игра, состоящая из GameController и GameView
3. GameController - контроллер за игрой, проверяет наличие шаха\мата\пата, нажатия на кнопки, начало новой игры, захват фигуры в руку, передвижение фигур.
4. GameView - рисует GUI, состоящий из ChessboardView и MenuView. Последние два отвечают за отрисовку шахматной доски и интерфейсное меню справа.
5. ChessBoard доска, хранящая черные и белые фигуры и обеспечивающая доступ к ним. Генерирует стандартную расстановку в начале игры.
6. Классы фигур, унаследованные от абстрактного класса Piece, который хранит информацию о позиции фигуры, ее цвете. В классах фигур реализована абстрактная функция класса Piece, которая по текущей позиции выдает все возможные координаты для перемещения.
7. Классы хелперы Constants и Coordinate. Второй описывает положение на доске 8x8 для фигуры.
