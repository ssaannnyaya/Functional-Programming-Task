package ru.ac.uniyar.Simplex.Utils;

import java.util.Objects;

/**
 * Класс для работы с обыкновенными дробями и дробной арифметикой
 */
public class Fraction {
    private transient long num;
    private transient long denom;

    /**
     * Конструктор дроби из двух целых чисел
     * @param num целочисленный числитель дроби
     * @param denom целочисленный знаменатель дроби
     * @throws NumberFormatException если в знаменателе ноль, возникает исключение "Dividing by zero"
     */
    public Fraction(long num, long denom){
        if (denom == 0)
            throw new NumberFormatException("Dividing by zero");
        this.num = num;
        this.denom = denom;
        cut();
    }

    /**
     * Конструктор дроби из строки
     * @param str Строка в формате "NUMERATOR/DENOMINATOR" или "INTEGER_NUMBER" или "NUM.BER"
     * @throws NumberFormatException если в знаменателе ноль или если неверный формат строки
     * @throws ArrayIndexOutOfBoundsException если неверный формат
     */
    public Fraction(String str) {
        if (str.contains("/")) {
            String[] data = str.split("/");
            if (str.indexOf("/") != str.lastIndexOf("/")) {
                throw new ArrayIndexOutOfBoundsException("Wrong fraction format");
            }
            this.num = Long.parseLong(data[0]);
            this.denom = Long.parseLong(data[1]);
        } else {
            if (str.contains(".")) {
                String[] data = str.split("\\.");
                if (str.indexOf(".") != str.lastIndexOf(".")) {
                    throw new ArrayIndexOutOfBoundsException("Wrong fraction format");
                }
                this.num = Long.parseLong(data[0] + data[1]);
                this.denom = (long) Math.pow(10, data[1].length());
            } else {
                this.num = Long.parseLong(str);
                this.denom = 1;
            }
        }
        if (this.denom == 0)
            throw new NumberFormatException("Dividing by zero");
        cut();
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
     */
    public final void cut(){
        long d = gcd(num, denom);
        this.num /= d;
        this.denom /= d;
        if (this.denom < 0) {
            this.num *= -1;
            this.denom *= -1;
        }
    }

    /**
     * Домножает дробь на -1
     * @return -FRACTION
     */
    public Fraction negative(){
        return new Fraction(-num, denom);
    }

    /**
     * Переворачивает дробь
     * @return 1/(изначальная дробь)
     */
    public Fraction flip(){
        return new Fraction(denom, num);
    }

    /**
     * Прибавляет к текущей дроби другую
     * @param a слагаемое
     * @return сумма текущей дроби и дроби a
     */
    public Fraction plus(Fraction a){
        Fraction sum = new Fraction(this.num * a.denom + this.denom * a.num, this.denom * a.denom);
        sum.cut();
        return sum;
    }

    /**
     * Вычитает из текущей дроби другую
     * @param a вычитаемое
     * @return разность текущей дроби и дроби a
     */
    public Fraction minus(Fraction a){
        return plus(a.negative());
    }

    /**
     * Перемножает текущую дробь с другой
     * @param a множитель
     * @return произведение текущей дроби и a
     */
    public Fraction multiply(Fraction a){
        Fraction prod = new Fraction(this.num * a.num, this.denom * a.denom);
        prod.cut();
        return prod;
    }

    /**
     * Делит текущую дробь на другую
     * @param a делитель
     * @return частное текущей дроби и a
     */
    public Fraction divide(Fraction a){
        return multiply(a.flip());
    }

    /**
     * Сравнивает текущую дробь с другой
     * @param a другая дробь
     * @return true, если дробь a больше, чем текущая дробь, false - иначе
     */
    public boolean moreThen(Fraction a){
        return (double) this.num / this.denom > (double) a.num / a.denom;
    }

    /**
     * Сравнивает текущую дробь с другой
     * @param a другая дробь
     * @return true, если дробь a меньше, чем текущая дробь, false - иначе
     */
    public boolean lessThen(Fraction a){
        return (double) this.num / this.denom < (double) a.num / a.denom;
    }

    /**
     * Переводит обычную дробь в десятичную
     * @return десятичная дробь, равная текущей с учётом погрешности
     */
    public double toDecimal() {
        return ((double) num) / denom;
    }

    /**
     * Получить дробь в строковом виде
     * @param isDecimal если true - результат в десятичном виде, false - в виде обыкновенной дроби
     * @return строка, содержащая дробь
     */
    public String getFrString(boolean isDecimal) {

        if (isDecimal) {
            if (this.denom == 1) {
                return String.format("%d", num);
            } else {
                return String.format("%.6s", toDecimal());
            }
        } else {
            return toString();
        }
    }

    @Override
    public String toString(){
        if (denom ==1) {
            return String.valueOf(num);
        } else {
            return num + "/" + denom;
        }
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