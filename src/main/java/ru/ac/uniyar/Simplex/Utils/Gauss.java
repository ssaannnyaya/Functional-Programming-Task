package ru.ac.uniyar.Simplex.Utils;

import java.util.Arrays;

public class Gauss {
    static public Fraction[][] divideRow(Fraction[][] table, int n, int row, Fraction divider) {
        Fraction[][] newTable = table.clone();
        for (int j = 0; j <= n; j++) {
            newTable[row][j] = Fraction.divide(newTable[row][j], divider);
        }
        return newTable;
    }

    static public Fraction[][] subtractionRowsWithMultiplier(Fraction[][] table, int n, int source, int dest, Fraction multiplier) {
        Fraction[][] newTable = table.clone();
        for (int j = 0; j <= n; j++) {
            newTable[dest][j] = Fraction.subtract(newTable[dest][j], Fraction.multiply(newTable[source][j], multiplier));
        }
        return newTable;
    }

    static public Fraction[][] switchRows(Fraction[][] table, int n, int row1, int row2) {
        Fraction[][] newTable = table.clone();
        for (int j = 0; j <= n; j++) {
            Fraction box = newTable[row1][j];
            newTable[row1][j] = newTable[row2][j];
            newTable[row2][j] = box;
        }
        return newTable;
    }

    static public int getNonZeroRow(Fraction[][] table, int m, int startRow, int j) {
        int row = startRow;
        while (row < m - 1 && Fraction.equals(table[row][j], Fraction.zero())) {
            row++;
        }
        return row;
    }

    static public Fraction[][] calculateByVars(Fraction[][] table, int n, int m, int[] vars) {
        Fraction[][] newTable = table.clone();
        for (int j = 0; j < vars.length; j++) {
            int row = getNonZeroRow(table, m, j, vars[j]);
            if (!Fraction.equals(newTable[row][vars[j]], Fraction.zero())) {
                newTable = divideRow(newTable, n, row, newTable[row][vars[j]]);
                for (int i = 0; i < m; i++) {
                    if (i == row) continue;
                    newTable = subtractionRowsWithMultiplier(newTable, n, row, i, newTable[i][vars[j]]);
                }
                newTable = switchRows(newTable, n, row, j);
            }
        }
        return newTable;
    }

    static public Fraction[][] getTableWithoutVars(Fraction[][] table, int n, int m, int[] vars) {
        Fraction[][] newTable = new Fraction[m + 1][n - vars.length + 1];
        int offset = 0;
        for (int j = 0; j <= n; j++) {
            int J = j;
            if (Arrays.stream(vars).anyMatch(it -> it == J)) {
                offset++;
            } else {
                for (int i = 0; i < m; i++) {
                    newTable[i][j - offset] = table[i][j];
                }
            }
        }
        return newTable;
    }

    static public Fraction[][] transformTable(Fraction[][] table, int n, int m, int[] vars) {
        Fraction[][] newTable = table.clone();
        newTable = calculateByVars(newTable, n, m, vars);
        return getTableWithoutVars(newTable, n, m, vars);
    }
}