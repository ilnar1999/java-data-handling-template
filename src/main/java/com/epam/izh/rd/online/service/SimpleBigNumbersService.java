package com.epam.izh.rd.online.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class SimpleBigNumbersService implements BigNumbersService {

    /**
     * Метод делит первое число на второе с заданной точностью
     * Например 1/3 с точностью 2 = 0.33
     * @param range точность
     * @return результат
     */
    @Override
    public BigDecimal getPrecisionNumber(int a, int b, int range) {
        return new BigDecimal(a).divide(new BigDecimal(b),2, RoundingMode.HALF_UP);
    }

    /**
     * Метод находит простое число по номеру
     *
     * @param range номер числа, считая с числа 2
     * @return простое число
     */
    @Override
    public BigInteger getPrimaryNumber(int range) {
        BigInteger[] primeNumbers = new BigInteger[range+1]; // создание массива для хранения простых чисел
        int id = 0; // текущая позиция в массиве для добавления нового занчения
        BigInteger number = BigInteger.valueOf(2); // текущее число (не обязятельно простое)
        while (primeNumbers[range] == null){ // выполнять, пока последнее значение равно нулю
            boolean isPrime = true; // переменная для проверки числа на простоту
            for (BigInteger value: primeNumbers) { // прохождение по массиву с простыми числами
                if (value != null) { // проверка текущего элемента на наличие какого-либо значения
                    if (number.remainder(value).equals(new BigInteger(String.valueOf(0)))) { // если число делится на текущий элемент массива без остатка...
                        isPrime = false; // ... то оно не является простым
                    }
                    if (!isPrime) {continue;} // пропуск текущего числа
                } else {continue;} // пропуск, если текущий элемент равен null, т.к. остальные элементы тоже null
            }
            if (isPrime) { // если число прошло все проверки...
                primeNumbers[id++] = number; // ...оно записывается в массив,а id увеличивается на 1
            }
            number = number.add(BigInteger.valueOf(1)); // увеличение текущего числа на 1
        }
        return primeNumbers[range]; // вывод последнего элемента массива
    }
}
