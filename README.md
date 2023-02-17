# paradigmas
This repository contains paradigma's homework's
# Тесты к курсу «Парадигмы программирования»

[Условия домашних заданий](https://www.kgeorgiy.info/courses/paradigms/homeworks.html)


## Домашнее задание 3. Очередь на массиве

Модификации
 * *Базовая*
    * Классы должны находиться в пакете `queue`
    * [Исходный код тестов](java/queue/ArrayQueueTest.java)
    * [Откомпилированные тесты](artifacts/queue/ArrayQueueTest.jar)


## Домашнее задание 2. Бинарный поиск

Модификации
 * *Базовая*
    * Класс `BinarySearch` должен находиться в пакете `search`
    * [Исходный код тестов](java/search/BinarySearchTest.java)
    * [Откомпилированные тесты](artifacts/search/BinarySearchTest.jar)
 * *Oddity* (32 - 37)
    * Если сумма всех чисел во входе чётная, то должна быть использоваться
      рекурсивная версия, иначе — итеративная.
 * *Shift* (32, 34)
    * На вход подается отсортированный (строго) по убыванию массив,
      циклически сдвинутый на `k` элементов. Требуется найти `k`.
      Все числа в массиве различны.
    * Класс должен иметь имя `BinarySearchShift`
 * *Max* (33, 35)
    * На вход подается циклический сдвиг
      отсортированного (строго) по возрастанию массива.
      Требуется найти в нём максимальное значение.
    * Класс должен иметь имя `BinarySearchMax`
 * *Uni* (36, 37)
    * На вход подается массив полученный приписыванием
      в конец массива отсортированного (строго) по возрастанию,
      массива отсортированного (строго) по убыванию.
      Требуется найти минимальную возможную длину первого массива.
    * Класс должен иметь имя `BinarySearchUni`
 * *Span* (38, 39)
    * На вход подаётся число `x` и массив, отсортированный по неубыванию.
      Требуется вывести два числа: начало и длину диапазона элементов, равных `x`.
      Если таких элементов нет, то следует вывести
      пустой диапазон, у которого левая граница совпадает с местом
      вставки элемента `x`.
    * Не допускается использование типов `long` и `BigInteger`.
    * Класс должен иметь имя `BinarySearchSpan`


## Домашнее задание 1. Обработка ошибок

Модификации
 * *Base*
    * Класс `ExpressionParser` должен реализовывать интерфейс
        [TripleParser](java/expression/exceptions/TripleParser.java)
    * Классы `CheckedAdd`, `CheckedSubtract`, `CheckedMultiply`,
        `CheckedDivide` и `CheckedNegate` должны реализовывать интерфейс
        [TripleExpression](java/expression/TripleExpression.java)
    * Нельзя использовать типы `long` и `double`
    * Нельзя использовать методы классов `Math` и `StrictMath`
    * [Исходный код тестов](java/expression/exceptions/ExceptionsTest.java)
        * Первый аргумент: `easy` или `hard`
        * Последующие аргументы: модификации
 * *SetClear* (32-37)
    * Дополнительно реализуйте бинарные операции (минимальный приоритет):
        * `set` – установка бита, `2 set 3` равно 10;
        * `clear` – сброс бита, `10 clear 3` равно 2.
 * *Count* (32-37)
    * Дополнительно реализуйте унарную операцию
      `count` – число установленных битов, `count -5` равно 31.
 * *GcdLcm* (38, 39)
    * Дополнительно реализуйте бинарные операции (минимальный приоритет):
        * `gcd` – НОД, `2 gcd -3` равно 1;
        * `lcm` – НОК, `2 lcm -3` равно -6.
 * *Reverse* (38, 39)
    * Дополнительно реализуйте унарную операцию
      `reverse` – число с переставленными цифрами, `reverse -12345` равно `-54321`.
 * *PowLog10* (36-39)
    * Дополнительно реализуйте унарные операции:
        * `log10` – логарифм по уснованию 10, `log10 1000` равно 3;
        * `pow10` – 10 в степени, `pow10 4` равно 10000.
