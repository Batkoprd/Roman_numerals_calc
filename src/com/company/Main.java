package com.company;

/*
Создай консольное приложение “Калькулятор”. Приложение должно читать из консоли введенные пользователем строки, числа,
арифметические операции проводимые между ними и выводить в консоль результат их выполнения.Реализуй класс Main с методом
public static String calc(String input). Метод должен принимать строку с арифметическим выражением между двумя числами
и возвращать строку с результатом их выполнения. Ты можешь добавлять свои импорты, классы и методы.
Добавленные классы не должны иметь модификаторы доступа (public или другие)

+ 1) Калькулятор умеет выполнять операции сложения, вычитания, умножения и деления с двумя числами:
a + b, a - b, a * b, a / b. Данные передаются в одну строку (смотри пример)!
Решения, в которых каждое число и арифмитеческая операция передаются с новой строки считаются неверными.

+ 2) Калькулятор умеет работать как с арабскими (1,2,3,4,5…), так и с римскими (I,II,III,IV,V…) числами.

+ 3) Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более.
На выходе числа не ограничиваются по величине и могут быть любыми.

+ 4) Калькулятор умеет работать только с целыми числами.

+ 5) Калькулятор умеет работать только с арабскими или римскими цифрами одновременно,
при вводе пользователем строки вроде 3 + II калькулятор должен выбросить исключение и прекратить свою работу.

+ 6) При вводе римских чисел, ответ должен быть выведен римскими цифрами, соответственно,
при вводе арабских - ответ ожидается арабскими.

+ 7) При вводе пользователем неподходящих чисел приложение выбрасывает исключение и завершает свою работу.

+ 8) При вводе пользователем строки, не соответствующей одной из вышеописанных арифметических операций,
приложение выбрасывает исключение и завершает свою работу.

+ 9) Результатом операции деления является целое число, остаток отбрасывается.

+ 10)Результатом работы калькулятора с арабскими числами могут быть отрицательные числа и ноль.
Результатом работы калькулятора с римскими числами могут быть только положительные числа,
если результат работы меньше единицы, выбрасывается исключение
*/

import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {

    private static final String[] ROMAN = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    private static final int[] ARABIC = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    //специально такая проверка римских, чтобы isValidRoman имел смысл
    public static final String regex_roman_input =  "([MDCLXVI]+)\s([+*/\\-])\s([MDCLXVI]+)";
    public static final String regex_arabic_input = "(\\d+)\s([+*/\\-])\s(\\d+)";
    public static final String regex_input = "([MDCLXVI]+)\s([+*/\\-])\s([MDCLXVI]+)|(\\d+)\s([+*/\\-])\s(\\d+)";


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение вида \"x + y\": ");
        String user_input = scanner.nextLine();
        myCalc(user_input);

        System.out.println();

        scanner.close();

        String[] test_list = {"XX + CM", "ZXC + zxC", "X * V", "100 + 200",
                "90-10", "1 - 1000", "X - D", "10+ D", "X+1", "MMMCD + DCC"};

        IntStream.range(0, test_list.length).forEach(i -> {
            System.out.println(test_list[i]);
            myCalc(test_list[i]);
            System.out.println();
        });

    }

    public static String arabicToRoman(String inputNum) {

        int int_inputNum = Integer.parseInt(inputNum);
        StringBuilder num_in_roman = new StringBuilder();
        int size = ROMAN.length;

        if (int_inputNum > 3999) {
            return inputNum;
        } else {

            for (int i = 0; i < size; i++) {
                int exp = int_inputNum / ARABIC[i]; // получаем количесство тысяч/сотен/десятков
                int_inputNum -= ARABIC[i] * (int_inputNum / ARABIC[i]); // уменьшаем вводимое число
                while (exp != 0) {
                    num_in_roman.append(ROMAN[i]); // добавляем в строку тысячи/сотни/десятки
                    exp--; // уменьшаем кол-во тысяч/сотен/десятков до 0, пока все не добавятся в строку
                }
            }
            return num_in_roman.toString();
        }
    }

    public static String romanToArabic (String inputNum){

        String regex_romans = "[MDCLXVI]+";

        int len = ROMAN.length;
        int roman_int = 0;
        int j = 0;
        while (j < inputNum.length()) {
            if (!inputNum.matches(regex_romans)){
                break;
            }
            else {
                for (int i = 0; i < len; i++) {
                    if (inputNum.startsWith(ROMAN[i], j)) { //Проверяем, что слайсы ввода соответствуют римским числам
                        roman_int += ARABIC[i]; //Прибавляем соответсвеющие значения арабских чисел в результат
                        j += ROMAN[i].length(); //Двигаем слайс по строке на длину прибавленного римского числа 1/2
                    }
                }
            }
        }   return Integer.toString(roman_int);
    }

    public static boolean isValidRoman (String inputNum){
        return inputNum.equals(arabicToRoman(romanToArabic(inputNum)));
    }


    public static void myCalc (String user_input) {
        boolean calc_is_running = true;
        int x = 0;
        int y = 0;
        int result = 0;

        while (calc_is_running) {

            if (!user_input.matches(regex_input)) {
                System.out.println("НЕВЕРНЫЙ ВВОД!!!");
                calc_is_running = false;
            } else {
                String num1 = user_input.substring(0, user_input.indexOf(" ")).toUpperCase();
                String num2 = user_input.substring(user_input.lastIndexOf(" ") + 1).toUpperCase();
                String operation = user_input.substring(user_input.indexOf(" ") + 1, user_input.lastIndexOf(" "));
                boolean both_roman = user_input.matches(regex_roman_input) && (isValidRoman(num1) && isValidRoman(num2));
                boolean both_arabic = user_input.matches(regex_arabic_input);

                if (both_arabic) {
                    x = Integer.parseInt(num1);
                    y = Integer.parseInt(num2);
                } else if (both_roman) {
                    x = Integer.parseInt(romanToArabic(num1));
                    y = Integer.parseInt(romanToArabic(num2));
                } else {
                    System.out.println("НЕВЕРНЫЙ ВВОД!!!");
                    calc_is_running = false;
                }

                switch (operation) {
                    case "+" -> result = x + y;
                    case "-" -> result = x - y;
                    case "*" -> result = y * x;
                    case "/" -> result = x / y;
                }

                if (both_arabic) {
                    System.out.println(user_input + " = " + result);
                    calc_is_running = false;
                }
                if (both_roman) {
                    if (result < 0) {
                        System.out.println("РИМСКОЕ ЧИСЛО НЕ МОЖЕТ БЫТЬ ОТРИЦАТЕЛЬНЫМ!");
                        calc_is_running = false;
                    } else if (result > 3999) {
                        System.out.println("РИМСКОЕ ЧИСЛО НЕ МОЖЕТ БЫТЬ БОЛЬШЕ 3999!");
                        calc_is_running = false;
                    } else {
                        System.out.println(user_input + " = " + arabicToRoman(Integer.toString(result)));
                        calc_is_running = false;
                    }
                }
            }
        }
    }
}
