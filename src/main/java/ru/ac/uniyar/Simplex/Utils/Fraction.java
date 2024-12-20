package ru.ac.uniyar.Simplex.Utils;

import java.util.Objects;
import javafx.util.Pair;

/**
 * Класс для работы с обыкновенными дробями и дробной арифметикой
 */
public class Fraction {
    private final long num;
    private final long denom;

    /**
     * Конструктор дроби из двух целых чисел
     * @param num целочисленный числитель дроби
     * @param denom целочисленный знаменатель дроби
     * @throws NumberFormatException если в знаменателе ноль, возникает исключение "Dividing by zero"
     */
    public Fraction(long num, long denom){
        if (denom == 0)
            throw new NumberFormatException("Dividing by zero");
        Pair<Long, Long> cutFraction = cut(num, denom);
        this.num = cutFraction.getKey();
        this.denom = cutFraction.getValue();
    }

    /**
     * Конструктор дроби из строки
     * @param str Строка в формате "NUMERATOR/DENOMINATOR" или "INTEGER_NUMBER" или "NUM.BER"
     * @throws NumberFormatException если в знаменателе ноль или если неверный формат строки
     * @throws ArrayIndexOutOfBoundsException если неверный формат
     */
    public Fraction(String str) {
        long num;
        long denom;
        if (str.contains("/")) {
            String[] data = str.split("/");
            if (str.indexOf("/") != str.lastIndexOf("/")) {
                throw new ArrayIndexOutOfBoundsException("Wrong fraction format");
            }
            num = Long.parseLong(data[0]);
            denom = Long.parseLong(data[1]);
        } else {
            if (str.contains(".")) {
                String[] data = str.split("\\.");
                if (str.indexOf(".") != str.lastIndexOf(".")) {
                    throw new ArrayIndexOutOfBoundsException("Wrong fraction format");
                }
                num = Long.parseLong(data[0] + data[1]);
                denom = (long) Math.pow(10, data[1].length());
            } else {
                num = Long.parseLong(str);
                denom = 1;
            }
        }
        if (denom == 0)
            throw new NumberFormatException("Dividing by zero");
        Pair<Long, Long> cutFraction = cut(num, denom);
        this.num = cutFraction.getKey();
        this.denom = cutFraction.getValue();
    }

    /**
     * Создаёт дробь, равную нулю
     * @return дробь 0/1
     */
    public static Fraction zero(){
        return new Fraction(0,1);
    }

    /**
     * Создаёт дробь, равную единице
     * @return дробь 1/1
     */
    public static Fraction one(){
        return new Fraction(1,1);
    }

    /**
     * Наибольший общий делитель чисел a и b
     * @param a первое число
     * @param b второе число
     * @return НОД(a,b)
     */
    public long gcd(long a, long b){
        if (b==0) {
            return a;
        } else {
            return gcd(b, a % b);
        }
    }

    /**
     * Сокращает дробь, если минус стоит в знаменателе, переносит его в числитель
     * @param num целочисленный числитель дроби
     * @param denom целочисленный знаменатель дроби
     * @return Пара чисел, ключ - числитель, значение - знаменатель
     */
    public Pair<Long, Long> cut(long num, long denom){
        long d = gcd(num, denom);
        long newNum = num / d;
        long newDenom = denom / d;
        if (newDenom < 0) {
            newNum *= -1;
            newDenom *= -1;
        }
        return new Pair<>(newNum, newDenom);
    }

    /**
     * Домножает дробь на -1
     * @param a дробь, знак которой нужно изменить
     * @return -a
     */
    static public Fraction negative(Fraction a){
        return new Fraction(-a.num, a.denom);
    }

    /**
     * Переворачивает дробь
     * @param a дробь, которую нужно перевернуть
     * @return 1/(a)
     */
    static public Fraction flip(Fraction a){
        return new Fraction(a.denom, a.num);
    }

    /**
     * Складывает дроби
     * @param a первое слагаемое
     * @param b второе слагаемое
     * @return сумма дробей a и b
     */
    static public Fraction add(Fraction a, Fraction b){
        return new Fraction(a.num * b.denom + a.denom * b.num, a.denom * b.denom);
    }

    /**
     * Вычитает одну дробь из другой
     * @param a уменьшаемое
     * @param b вычитаемое
     * @return разность дробей a и b
     */
    static public Fraction subtract(Fraction a, Fraction b){
        return Fraction.add(a, Fraction.negative(b));
    }

    /**
     * Перемножает дроби
     * @param a первый множитель
     * @param b второй множитель
     * @return произведение дробей a и b
     */
    static public Fraction multiply(Fraction a, Fraction b){
        return new Fraction(a.num * b.num, a.denom * b.denom);
    }

    /**
     * Делит одну дробь на другую
     * @param a делимое
     * @param b делитель
     * @return частное дробей a и b
     */
    static public Fraction divide(Fraction a, Fraction b){
        return multiply(a, Fraction.flip(b));
    }

    /**
     * Сравнивает дроби
     * @param a дробь с которой сравнивают
     * @param b другая дробь
     * @return true, если дробь a больше, чем текущая b, false - иначе
     */
    static public boolean firstIsMore(Fraction a, Fraction b){
        return (double) a.num / a.denom > (double) b.num / b.denom;
    }

    /**
     * Сравнивает дроби
     * @param a дробь с которой сравнивают
     * @param b другая дробь
     * @return true, если дробь a меньше, чем b, false - иначе
     */
    static public boolean firstIsLess(Fraction a, Fraction b){
        return (double) a.num / a.denom < (double) b.num / b.denom;
    }

    /**
     * Переводит обычную дробь в десятичную
     * @param a дробь, которую нужно перевести в десятичную
     * @return десятичная дробь, равная текущей с учётом погрешности
     */
    static public double toDecimal(Fraction a) {
        return ((double) a.num) / a.denom;
    }

    /**
     * Получить дробь в строковом виде
     * @param isDecimal если true - результат в десятичном виде, false - в виде обыкновенной дроби
     * @return строка, содержащая дробь
     */
    static public String getFrString(Fraction a, boolean isDecimal) {

        if (isDecimal) {
            if (a.denom == 1) {
                return String.format("%d", a.num);
            } else {
                return String.format("%.6s", Fraction.toDecimal(a));
            }
        } else {
            return Fraction.toString(a);
        }
    }

    static public String toString(Fraction a){
        if (a.denom == 1) {
            return String.valueOf(a.num);
        } else {
            return a.num + "/" + a.denom;
        }
    }

    /**
     * Сравнивает дроби
     * @param a первая дробь
     * @param b вторая дробь
     * @return true, если объект o является дробью и равен текущей дроби, false - иначе
     */
    static public boolean equals(Fraction a, Fraction b) {
        return a.num == b.num && a.denom == b.denom;
    }

    /**
     * Сравнивает текущую дробь с другой
     * @param o другая дробь или иной объект
     * @return true, если объект o является дробью и равен текущей дроби, false - иначе
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Fraction fraction = (Fraction) o;
        return num == fraction.num && denom == fraction.denom;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, denom);
    }
}