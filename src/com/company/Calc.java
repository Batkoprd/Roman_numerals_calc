package com.company;

import com.company.Exceptions.IncorrectOperationException;
import com.company.Exceptions.IncorrectRomanNumException;
import com.company.Exceptions.RomanNumberOutOfRangeException;
import com.company.Exceptions.UserInputException;

import java.util.*;

public class Calc {

    private static final String[] ROMAN =     {"MMM", "MM", "M", "CM", "DCCC", "DCC", "DC", "D", "CD", "CCC", "CC", "C",
            "XC", "LXXX", "LXX", "LX", "L", "XL", "XXX", "XX", "X", "IX",
            "VIII", "VII", "VI", "V", "IV", "III", "II", "I"};
    private static final int[] ARABIC = {3000, 2000, 1000, 900, 800, 700, 600, 500, 400, 300, 200, 100, 90, 80, 70, 60,
            50, 40, 30, 20, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    public static final String REGEX_ROMAN =  "[MDCLXVI]+";
    public static final String REGEX_ARABIC = "-?\\d+";
    public static final String REGEX_OPERATION = "[+*/\\-]";
    public static final String[] OPERATORS = {"+", "*", "-", "/"};


    public static void main(String[] args)  {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение вида \"x + y\": ");
        String calcInput = scanner.nextLine().toUpperCase();

        try {
            myCalc(calcInput);
        } catch (UserInputException e) {
            System.err.println("UserInputException: " + e);
        } catch (IncorrectRomanNumException e) {
            System.err.println("IncorrectRomanNumException: " + e);
        } catch (RomanNumberOutOfRangeException e) {
            System.err.println("RomanNumberOutOfRangeException" + e);
        } catch (IncorrectOperationException e) {
            System.err.println("IncorrectOperationException: " + e);
        }

        scanner.close();
    }

    public static String[] test() throws RomanNumberOutOfRangeException {
        Random random = new Random();
        String[] testArray = new String[3000];

        for (int i = 0; i < testArray.length; i++){
            testArray[i] = arabicToRoman(Integer.toString(random.nextInt(1, 100))) + " "
                    + OPERATORS[random.nextInt(0, OPERATORS.length -1)] +
                    " " + arabicToRoman(Integer.toString(random.nextInt(1, 100)));
        }
        return testArray;
    }

    public static String arabicToRoman(String inputNum) throws RomanNumberOutOfRangeException {

        int intInputNum = Integer.parseInt(inputNum);
        StringBuilder numInRoman = new StringBuilder();
        int size = ROMAN.length;

        if (intInputNum > 100 || intInputNum < 1) {
            throw new RomanNumberOutOfRangeException("Калькулятор не принимает на вход римские числа меньше 1 и больше 100.");
        } else {
            for (int i = 0; i < size; i++) {
                if (intInputNum >= ARABIC[i]) {
                    numInRoman.append(ROMAN[i]);
                    intInputNum -= ARABIC[i];
                }
            }
            return numInRoman.toString();
        }
    }

    public static String romanToArabic (String inputNum) throws IncorrectRomanNumException {

        int len = ROMAN.length;
        int roman_int = 0;
        int j = 0;
        while (j < inputNum.length()) {
            if (!inputNum.matches(REGEX_ROMAN)){
                throw new IncorrectRomanNumException("Некорректный ввод римского числа.");
            }
            else {
                for (int i = 0; i < len; i++) {
                    if (inputNum.startsWith(ROMAN[i], j)) { //Проверяем, что слайсы ввода соответствуют римским числам
                        roman_int += ARABIC[i]; //Прибавляем соответсвующее значения арабских чисел в результат
                        j += ROMAN[i].length(); //Двигаем слайс по строке на длину прибавленного римского числа 1/2
                    }
                }
            }
        }
        return Integer.toString(roman_int);
    }

    public static boolean isValidRoman (String inputNum) {
        try {
            return inputNum.equals(arabicToRoman(romanToArabic(inputNum)));
        } catch (IncorrectRomanNumException e) {
            throw new IncorrectRomanNumException("IncorrectRomanNumException: некорректное римское число.");
        }
    }

    public static String readInput(String userInput) throws UserInputException{
        userInput= userInput.replaceFirst("^(-?\\w+)\\s*((?:(?!-\\b)[/*+-])*)\\s*(-?\\w+)$", "$1 $2 $3");

        if (userInput.chars().filter(ch -> ch == ' ').count() != 2) {
            throw new UserInputException("Калькулятор принимает только два операнда.");
        }

        return userInput;
    }

    public static void myCalc (String userInput) throws UserInputException, IncorrectRomanNumException,
            IncorrectOperationException, RomanNumberOutOfRangeException{

        int x;
        int y;
        int result = 0;

        userInput = readInput(userInput);
        List<String> userInputAsList = List.of(userInput.split(" "));

        String num1 = userInputAsList.get(0);
        String operation = userInputAsList.get(1);
        String num2 = userInputAsList.get(2);

        boolean bothRoman = (num1.matches(REGEX_ROMAN) && num2.matches(REGEX_ROMAN)) && (isValidRoman(num1) && isValidRoman(num2));
        boolean bothArabic = num1.matches(REGEX_ARABIC) && num2.matches(REGEX_ARABIC);
        boolean correctOperation = operation.matches(REGEX_OPERATION);
        boolean switcher = true; // true = римский ввод/ false = арабский

        if (!correctOperation) {
            throw new IncorrectOperationException("Неверная операция.");
        } else {
            if (bothArabic) {
                x = Integer.parseInt(num1);
                y = Integer.parseInt(num2);
                switcher = false;
            } else if (bothRoman) {
                x = Integer.parseInt(romanToArabic(num1));
                y = Integer.parseInt(romanToArabic(num2));
            } else {
                throw new UserInputException("Некорректный " +
                        "ввод, калькулятор принимает на вход либо два римских числа, либо два арабских");
            }
        }

        if (x < 1 || x > 10 || y < 1 || y > 10) {
            throw new UserInputException("Калькулятор работает только с числами от 1 до 10.");
        }

        switch (operation) {
            case "+" -> result = x + y;
            case "-" -> result = x - y;
            case "*" -> result = y * x;
            case "/" -> result = x / y;
        }

        if (!switcher) {
            System.out.println(userInput + " = " + result);
        } else {
            System.out.println(userInput + " = " + arabicToRoman(Integer.toString(result)));
        }

    }
}
