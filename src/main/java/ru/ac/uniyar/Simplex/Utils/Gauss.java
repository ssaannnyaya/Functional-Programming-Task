package ru.ac.uniyar.Simplex.Utils;

import java.util.Arrays;

public class Gauss {
    public int n;
    public int m;
    public Fraction[][] table;

    public Gauss(int n, int m, Fraction[][] table) {
        this.n = n;
        this.m = m;
        this.table = table;
    }

    public void divideRow(int row, Fraction divider) {
        for (int j = 0; j <= n; j++) {
            table[row][j] = Fraction.divide(table[row][j], divider);
        }
    }

    public void subtractionRowsWithMultiplier(int source, int dest, Fraction multiplier) {
        for (int j = 0; j <= n; j++) {
            table[dest][j] = Fraction.subtract(table[dest][j], Fraction.multiply(table[source][j], multiplier));
        }
    }

    public void switchRows(int row1, int row2) {
        for (int j = 0; j <= n; j++) {
            Fraction box = table[row1][j];
            table[row1][j] = table[row2][j];
            table[row2][j] = box;
        }
    }

    public void calculateByVars(int[] vars) {
        for (int j = 0; j < vars.length; j++) {
            int row = j;
            while (row < m - 1 && Fraction.equals(table[row][vars[j]], Fraction.zero())) {
                row++;
            }
            if (!Fraction.equals(table[row][vars[j]], Fraction.zero())) {
                divideRow(row, table[row][vars[j]]);
                for (int i = 0; i < row; i++) {
                    subtractionRowsWithMultiplier(row, i, table[i][vars[j]]);
                }
                for (int i = row + 1; i < m; i++) {
                    subtractionRowsWithMultiplier(row, i, table[i][vars[j]]);
                }
                switchRows(row, j);
            }
        }
    }

    public Fraction[][] getTableWithoutVars(int[] vars) {
        Fraction[][] newTable = new Fraction[m + 1][n - vars.length + 1];
        int offset = 0;
        for (int j = 0; j <= n; j++) {
            int J = j;
            if (Arrays.stream(vars).filter(it -> it == J).toArray().length != 0) {
                offset++;
            } else {
                for (int i = 0; i < m; i++) {
                    newTable[i][j-offset] = table[i][j];
                }
            }
        }
        return newTable;
    }
}