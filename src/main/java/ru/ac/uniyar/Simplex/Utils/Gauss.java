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

    static public Fraction[][] calculateByVars(Fraction[][] table, int n, int m, int[] vars) {
        Fraction[][] newTable = table.clone();
        for (int j = 0; j < vars.length; j++) {
            int row = j;
            while (row < m - 1 && Fraction.equals(newTable[row][vars[j]], Fraction.zero())) {
                row++;
            }
            if (!Fraction.equals(newTable[row][vars[j]], Fraction.zero())) {
                newTable = divideRow(newTable, n, row, newTable[row][vars[j]]);
                for (int i = 0; i < row; i++) {
                    newTable = subtractionRowsWithMultiplier(newTable, n, row, i, newTable[i][vars[j]]);
                }
                for (int i = row + 1; i < m; i++) {
                    newTable = subtractionRowsWithMultiplier(newTable, n, row, i, newTable[i][vars[j]]);
                }
                newTable = switchRows(newTable, n, row, j);
            }
        }
        return newTable;
    }

    static public Fraction[][] transformTable(Fraction[][] table, int n, int m, int[] vars) {
        Fraction[][] newTable = table.clone();
        newTable = calculateByVars(newTable, n, m, vars);
        Fraction[][] transformedTable = new Fraction[m + 1][n - vars.length + 1];
        int offset = 0;
        for (int j = 0; j <= n; j++) {
            int J = j;
            if (Arrays.stream(vars).filter(it -> it == J).toArray().length != 0) {
                offset++;
            } else {
                for (int i = 0; i < m; i++) {
                    transformedTable[i][j - offset] = newTable[i][j];
                }
            }
        }
        return transformedTable;
    }
}