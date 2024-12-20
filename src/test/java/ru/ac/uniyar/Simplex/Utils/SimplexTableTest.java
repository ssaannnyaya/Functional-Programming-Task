package ru.ac.uniyar.Simplex.Utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.*;

public class SimplexTableTest {

    @Test
    public void normaliseTest(){
        int n1 = 2;
        int m1 = 2;
        Fraction[] func1 = new Fraction[n1 + 1];
        func1[0] = new Fraction("1/2");
        func1[1] = new Fraction("1");
        func1[2] = new Fraction("0");
        Fraction[][] table1 = new Fraction[m1 + 1][n1 + 1];
        table1[0][0] = new Fraction("-1");
        table1[0][1] = new Fraction("2");
        table1[0][2] = new Fraction("1");
        table1[1][0] = new Fraction("-1/2");
        table1[1][1] = new Fraction("2");
        table1[1][2] = new Fraction("-1");
        table1[2][0] = new Fraction("3/2");
        table1[2][1] = new Fraction("-4");
        table1[2][2] = new Fraction("0");
        int[] colX1 = new int[2];
        colX1[0] = 0;
        colX1[1] = 1;
        int[] rowX1 = new int[2];
        rowX1[0] = 2;
        rowX1[1] = 3;
        SimplexTable simplexTable1 = new SimplexTable(n1, m1, func1, table1, colX1, rowX1, true);

        int n2 = 2;
        int m2 = 2;
        Fraction[] func2 = new Fraction[n2 + 1];
        func2[0] = new Fraction("1/2");
        func2[1] = new Fraction("1");
        func2[2] = new Fraction("0");
        Fraction[][] table2 = new Fraction[m2 + 1][n2 + 1];
        table2[0][0] = new Fraction("-1");
        table2[0][1] = new Fraction("2");
        table2[0][2] = new Fraction("1");
        table2[1][0] = new Fraction("1/2");
        table2[1][1] = new Fraction("-2");
        table2[1][2] = new Fraction("1");
        table2[2][0] = new Fraction("1/2");
        table2[2][1] = new Fraction("0");
        table2[2][2] = new Fraction("-2");
        int[] colX2 = new int[2];
        colX2[0] = 0;
        colX2[1] = 1;
        int[] rowX2 = new int[2];
        rowX2[0] = 2;
        rowX2[1] = 3;
        SimplexTable simplexTable2 = new SimplexTable(n2, m2, func2, table2, colX2, rowX2, true);

        simplexTable1 = new SimplexTable(
                simplexTable1.getN(),
                simplexTable1.getM(),
                simplexTable1.getFunc(),
                SimplexTable.normalize(
                        simplexTable1.getN(),
                        simplexTable1.getM(),
                        simplexTable1.getTable()),
                simplexTable1.getColX(),
                simplexTable1.getRowX(),
                simplexTable1.isMinimisation());
        assertThat(simplexTable1).isEqualTo(simplexTable2);
    }

    @Test
    public void yesSolved(){
        int n = 2;
        int m = 2;
        Fraction[] func = new Fraction[n + 1];
        func[0] = new Fraction("1/2");
        func[1] = new Fraction("1");
        func[2] = new Fraction("0");
        Fraction[][] table = new Fraction[m + 1][n + 1];
        table[0][0] = new Fraction("-1");
        table[0][1] = new Fraction("-2");
        table[0][2] = new Fraction("1");
        table[1][0] = new Fraction("1/2");
        table[1][1] = new Fraction("-2");
        table[1][2] = new Fraction("1");
        table[2][0] = new Fraction("3/2");
        table[2][1] = new Fraction("4");
        table[2][2] = new Fraction("0");
        int[] colX = new int[2];
        colX[0] = 0;
        colX[1] = 1;
        int[] rowX = new int[2];
        rowX[0] = 2;
        rowX[1] = 3;
        SimplexTable simplexTable = new SimplexTable(n, m, func, table, colX, rowX, true);
        assertThat(SimplexTable.isSolved(simplexTable.getTable(), simplexTable.getN(), simplexTable.getM())).isTrue();
    }

    @Test
    public void yesSolvedNegativeAnswer(){
        int n = 2;
        int m = 2;
        Fraction[] func = new Fraction[n + 1];
        func[0] = new Fraction("1/2");
        func[1] = new Fraction("1");
        func[2] = new Fraction("0");
        Fraction[][] table = new Fraction[m + 1][n + 1];
        table[0][0] = new Fraction("-1");
        table[0][1] = new Fraction("-2");
        table[0][2] = new Fraction("1");
        table[1][0] = new Fraction("1/2");
        table[1][1] = new Fraction("-2");
        table[1][2] = new Fraction("1");
        table[2][0] = new Fraction("3/2");
        table[2][1] = new Fraction("4");
        table[2][2] = new Fraction("-8");
        int[] colX = new int[2];
        colX[0] = 0;
        colX[1] = 1;
        int[] rowX = new int[2];
        rowX[0] = 2;
        rowX[1] = 3;
        SimplexTable simplexTable = new SimplexTable(n, m, func, table, colX, rowX, true);
        assertThat(SimplexTable.isSolved(simplexTable.getTable(), simplexTable.getN(), simplexTable.getM())).isTrue();
    }

    @Test
    public void noSolved(){
        int n = 2;
        int m = 2;
        Fraction[] func = new Fraction[n + 1];
        func[0] = new Fraction("1/2");
        func[1] = new Fraction("1");
        func[2] = new Fraction("0");
        Fraction[][] table = new Fraction[m + 1][n + 1];
        table[0][0] = new Fraction("-1");
        table[0][1] = new Fraction("2");
        table[0][2] = new Fraction("1");
        table[1][0] = new Fraction("-1/2");
        table[1][1] = new Fraction("2");
        table[1][2] = new Fraction("1");
        table[2][0] = new Fraction("-3/2");
        table[2][1] = new Fraction("-4");
        table[2][2] = new Fraction("0");
        int[] colX = new int[2];
        colX[0] = 0;
        colX[1] = 1;
        int[] rowX = new int[2];
        rowX[0] = 2;
        rowX[1] = 3;
        SimplexTable simplexTable = new SimplexTable(n, m, func, table, colX, rowX, true);
        assertThat(SimplexTable.isSolved(simplexTable.getTable(), simplexTable.getN(), simplexTable.getM())).isFalse();
    }

    @Test
    public void yesSolution(){
        int n = 2;
        int m = 2;
        Fraction[] func = new Fraction[n + 1];
        func[0] = new Fraction("1/2");
        func[1] = new Fraction("1");
        func[2] = new Fraction("0");
        Fraction[][] table = new Fraction[m + 1][n + 1];
        table[0][0] = new Fraction("1");
        table[0][1] = new Fraction("2");
        table[0][2] = new Fraction("1");
        table[1][0] = new Fraction("1/2");
        table[1][1] = new Fraction("2");
        table[1][2] = new Fraction("1");
        table[2][0] = new Fraction("-3/2");
        table[2][1] = new Fraction("-4");
        table[2][2] = new Fraction("0");
        int[] colX = new int[2];
        colX[0] = 0;
        colX[1] = 1;
        int[] rowX = new int[2];
        rowX[0] = 2;
        rowX[1] = 3;
        SimplexTable simplexTable = new SimplexTable(n, m, func, table, colX, rowX, true);
        assertThat(SimplexTable.hasSolution(simplexTable.getTable(), simplexTable.getN(), simplexTable.getM())).isTrue();
    }

    @Test
    public void noSolution(){
        int n = 2;
        int m = 2;
        Fraction[] func = new Fraction[n + 1];
        func[0] = new Fraction("1/2");
        func[1] = new Fraction("1");
        func[2] = new Fraction("0");
        Fraction[][] table = new Fraction[m + 1][n + 1];
        table[0][0] = new Fraction("-1");
        table[0][1] = new Fraction("2");
        table[0][2] = new Fraction("-1");
        table[1][0] = new Fraction("-1/2");
        table[1][1] = new Fraction("2");
        table[1][2] = new Fraction("1");
        table[2][0] = new Fraction("-3/2");
        table[2][1] = new Fraction("-4");
        table[2][2] = new Fraction("0");
        int[] colX = new int[2];
        colX[0] = 0;
        colX[1] = 1;
        int[] rowX = new int[2];
        rowX[0] = 2;
        rowX[1] = 3;
        SimplexTable simplexTable = new SimplexTable(n, m, func, table, colX, rowX, true);
        assertThat(SimplexTable.hasSolution(simplexTable.getTable(), simplexTable.getN(), simplexTable.getM())).isFalse();
    }

    @Test
    public void equalTest(){
        int n1 = 2;
        int m1 = 2;
        Fraction[] func1 = new Fraction[n1 + 1];
        func1[0] = new Fraction("1/2");
        func1[1] = new Fraction("1");
        func1[2] = new Fraction("0");
        Fraction[][] table1 = new Fraction[m1 + 1][n1 + 1];
        table1[0][0] = new Fraction("-1");
        table1[0][1] = new Fraction("2");
        table1[0][2] = new Fraction("1");
        table1[1][0] = new Fraction("-1/2");
        table1[1][1] = new Fraction("2");
        table1[1][2] = new Fraction("1");
        table1[2][0] = new Fraction("-3/2");
        table1[2][1] = new Fraction("-4");
        table1[2][2] = new Fraction("0");
        int[] colX1 = new int[2];
        colX1[0] = 0;
        colX1[1] = 1;
        int[] rowX1 = new int[2];
        rowX1[0] = 2;
        rowX1[1] = 3;
        SimplexTable simplexTable1 = new SimplexTable(n1, m1, func1, table1, colX1, rowX1, true);

        int n2 = 2;
        int m2 = 2;
        Fraction[] func2 = new Fraction[n2 + 1];
        func2[0] = new Fraction("1/2");
        func2[1] = new Fraction("1");
        func2[2] = new Fraction("0");
        Fraction[][] table2 = new Fraction[m2 + 1][n2 + 1];
        table2[0][0] = new Fraction("-1");
        table2[0][1] = new Fraction("2");
        table2[0][2] = new Fraction("1");
        table2[1][0] = new Fraction("-1/2");
        table2[1][1] = new Fraction("2");
        table2[1][2] = new Fraction("1");
        table2[2][0] = new Fraction("-3/2");
        table2[2][1] = new Fraction("-4");
        table2[2][2] = new Fraction("0");
        int[] colX2 = new int[2];
        colX2[0] = 0;
        colX2[1] = 1;
        int[] rowX2 = new int[2];
        rowX2[0] = 2;
        rowX2[1] = 3;
        SimplexTable simplexTable2 = new SimplexTable(n2, m2, func2, table2, colX2, rowX2, true);
        assertThat(simplexTable1.equals(simplexTable2)).isTrue();
    }

    @Test
    public void simplexStepTest(){
        int n = 2;
        int m = 2;
        Fraction[] func = new Fraction[n + 1];
        func[0] = new Fraction("1/2");
        func[1] = new Fraction("1");
        func[2] = new Fraction("0");
        Fraction[][] table1 = new Fraction[m + 1][n + 1];
        table1[0][0] = new Fraction("1/3");
        table1[0][1] = new Fraction("1/3");
        table1[0][2] = new Fraction("1");
        table1[1][0] = new Fraction("2/3");
        table1[1][1] = new Fraction("-1/3");
        table1[1][2] = new Fraction("1");
        table1[2][0] = new Fraction("-1/3");
        table1[2][1] = new Fraction("-1/3");
        table1[2][2] = new Fraction("4");
        int[] colX1 = new int[2];
        colX1[0] = 0;
        colX1[1] = 1;
        int[] rowX1 = new int[2];
        rowX1[0] = 2;
        rowX1[1] = 3;
        SimplexTable simplexTable1 = new SimplexTable(n, m, func, table1, colX1, rowX1, true);

        Fraction[][] table2 = new Fraction[m + 1][n + 1];
        table2[0][0] = new Fraction("-1/2");
        table2[0][1] = new Fraction("1/2");
        table2[0][2] = new Fraction("1/2");
        table2[1][0] = new Fraction("3/2");
        table2[1][1] = new Fraction("-1/2");
        table2[1][2] = new Fraction("3/2");
        table2[2][0] = new Fraction("1/2");
        table2[2][1] = new Fraction("-1/2");
        table2[2][2] = new Fraction("9/2");
        int[] colX2 = new int[2];
        colX2[0] = 3;
        colX2[1] = 1;
        int[] rowX2 = new int[2];
        rowX2[0] = 2;
        rowX2[1] = 0;
        SimplexTable simplexTable2 = new SimplexTable(n, m, func, table2, colX2, rowX2, true);

        simplexTable1 = SimplexTable.simplexStep(1, 0,
                simplexTable1.getN(),
                simplexTable1.getM(),
                simplexTable1.getFunc(),
                simplexTable1.getTable(),
                simplexTable1.getColX(),
                simplexTable1.getRowX(),
                simplexTable1.isMinimisation());
        assertThat(simplexTable1.equals(simplexTable2)).isTrue();
    }

    @Test
    public void columnForSimplexStep(){
        int n = 2;
        int m = 2;
        Fraction[] func = new Fraction[n + 1];
        func[0] = new Fraction("1/2");
        func[1] = new Fraction("1");
        func[2] = new Fraction("0");
        Fraction[][] table = new Fraction[m + 1][n + 1];
        table[0][0] = new Fraction("1/3");
        table[0][1] = new Fraction("1/3");
        table[0][2] = new Fraction("1");
        table[1][0] = new Fraction("2/3");
        table[1][1] = new Fraction("-1/3");
        table[1][2] = new Fraction("1");
        table[2][0] = new Fraction("-1/3");
        table[2][1] = new Fraction("-1/3");
        table[2][2] = new Fraction("4");
        int[] colX = new int[2];
        colX[0] = 0;
        colX[1] = 1;
        int[] rowX = new int[2];
        rowX[0] = 2;
        rowX[1] = 3;
        SimplexTable simplexTable = new SimplexTable(n, m, func, table, colX, rowX, true);
        int col = SimplexTable.colForSimplexStep(
                simplexTable.getTable(),
                simplexTable.getN(),
                simplexTable.getM());
        assertThat(col).isEqualTo(0);
    }

    @Test
    public void elementForSimplexStep(){
        int n = 2;
        int m = 2;
        Fraction[] func = new Fraction[n + 1];
        func[0] = new Fraction("1/2");
        func[1] = new Fraction("1");
        func[2] = new Fraction("0");
        Fraction[][] table = new Fraction[m + 1][n + 1];
        table[0][0] = new Fraction("1/3");
        table[0][1] = new Fraction("1/3");
        table[0][2] = new Fraction("1");
        table[1][0] = new Fraction("2/3");
        table[1][1] = new Fraction("-1/3");
        table[1][2] = new Fraction("1");
        table[2][0] = new Fraction("-1/3");
        table[2][1] = new Fraction("-1/3");
        table[2][2] = new Fraction("4");
        int[] colX = new int[2];
        colX[0] = 0;
        colX[1] = 1;
        int[] rowX = new int[2];
        rowX[0] = 2;
        rowX[1] = 3;
        SimplexTable simplexTable = new SimplexTable(n, m, func, table, colX, rowX, true);
        int col = SimplexTable.colForSimplexStep(
                simplexTable.getTable(),
                simplexTable.getN(),
                simplexTable.getM());
        int row = SimplexTable.rowForSimplexStep(
                col,
                simplexTable.getTable(),
                simplexTable.getN(),
                simplexTable.getM());
        assertThat(row).isEqualTo(1);
    }

    @Test
    public void nonParametrisedSimplexStepTest(){
        int n = 2;
        int m = 2;
        Fraction[] func = new Fraction[n + 1];
        func[0] = new Fraction("1/2");
        func[1] = new Fraction("1");
        func[2] = new Fraction("0");
        Fraction[][] table1 = new Fraction[m + 1][n + 1];
        table1[0][0] = new Fraction("1/3");
        table1[0][1] = new Fraction("1/3");
        table1[0][2] = new Fraction("1");
        table1[1][0] = new Fraction("2/3");
        table1[1][1] = new Fraction("-1/3");
        table1[1][2] = new Fraction("1");
        table1[2][0] = new Fraction("-1/3");
        table1[2][1] = new Fraction("-1/3");
        table1[2][2] = new Fraction("4");
        int[] colX1 = new int[2];
        colX1[0] = 0;
        colX1[1] = 1;
        int[] rowX1 = new int[2];
        rowX1[0] = 2;
        rowX1[1] = 3;
        SimplexTable simplexTable1 = new SimplexTable(n, m, func, table1, colX1, rowX1, true);

        Fraction[][] table2 = new Fraction[m + 1][n + 1];
        table2[0][0] = new Fraction("-1/2");
        table2[0][1] = new Fraction("1/2");
        table2[0][2] = new Fraction("1/2");
        table2[1][0] = new Fraction("3/2");
        table2[1][1] = new Fraction("-1/2");
        table2[1][2] = new Fraction("3/2");
        table2[2][0] = new Fraction("1/2");
        table2[2][1] = new Fraction("-1/2");
        table2[2][2] = new Fraction("9/2");
        int[] colX2 = new int[2];
        colX2[0] = 3;
        colX2[1] = 1;
        int[] rowX2 = new int[2];
        rowX2[0] = 2;
        rowX2[1] = 0;
        SimplexTable simplexTable2 = new SimplexTable(n, m, func, table2, colX2, rowX2, true);

        simplexTable1 = SimplexTable.simplexStep(
                simplexTable1.getN(),
                simplexTable1.getM(),
                simplexTable1.getFunc(),
                simplexTable1.getTable(),
                simplexTable1.getColX(),
                simplexTable1.getRowX(),
                simplexTable1.isMinimisation());
        assertThat(simplexTable1.equals(simplexTable2)).isTrue();
    }

    @Test
    public void anotherCreatingTest(){
        int n1 = 2;
        int m1 = 2;
        Fraction[] func1 = new Fraction[n1 + 1];
        func1[0] = new Fraction("1/2");
        func1[1] = new Fraction("1");
        func1[2] = new Fraction("0");
        Fraction[][] table1 = new Fraction[m1 + 1][n1 + 1];
        table1[0][0] = new Fraction("-1");
        table1[0][1] = new Fraction("2");
        table1[0][2] = new Fraction("-1");
        table1[1][0] = new Fraction("-1/2");
        table1[1][1] = new Fraction("2");
        table1[1][2] = new Fraction("1");
        SimplexTable simplexTable1 = new SimplexTable(n1, m1, func1, table1, true);

        int n2 = 2;
        int m2 = 2;
        Fraction[] func2 = new Fraction[n2 + 1];
        func2[0] = new Fraction("1/2");
        func2[1] = new Fraction("1");
        func2[2] = new Fraction("0");
        Fraction[][] table2 = new Fraction[m2 + 1][n2 + 1];
        table2[0][0] = new Fraction("1");
        table2[0][1] = new Fraction("-2");
        table2[0][2] = new Fraction("1");
        table2[1][0] = new Fraction("-1/2");
        table2[1][1] = new Fraction("2");
        table2[1][2] = new Fraction("1");
        table2[2][0] = new Fraction("-1/2");
        table2[2][1] = new Fraction("0");
        table2[2][2] = new Fraction("-2");
        int[] colX2 = new int[2];
        colX2[0] = 0;
        colX2[1] = 1;
        int[] rowX2 = new int[2];
        rowX2[0] = -2;
        rowX2[1] = -3;
        SimplexTable simplexTable2 = new SimplexTable(n2, m2, func2, table2, colX2, rowX2, true);
        assertThat(simplexTable1.equals(simplexTable2)).isTrue();
    }

    @Test
    public void yesAdditionalVars(){
        int n = 2;
        int m = 2;
        Fraction[] func = new Fraction[n + 1];
        func[0] = new Fraction("1/2");
        func[1] = new Fraction("1");
        func[2] = new Fraction("0");
        Fraction[][] table = new Fraction[m + 1][n + 1];
        table[0][0] = new Fraction("1/3");
        table[0][1] = new Fraction("1/3");
        table[0][2] = new Fraction("1");
        table[1][0] = new Fraction("2/3");
        table[1][1] = new Fraction("-1/3");
        table[1][2] = new Fraction("1");
        table[2][0] = new Fraction("-1/3");
        table[2][1] = new Fraction("-1/3");
        table[2][2] = new Fraction("4");
        int[] colX = new int[2];
        colX[0] = 0;
        colX[1] = 2;
        int[] rowX = new int[2];
        rowX[0] = 1;
        rowX[1] = -3;
        SimplexTable simplexTable = new SimplexTable(n, m, func, table, colX, rowX, true);
        assertThat(SimplexTable.hasAdditionalVars(colX, rowX)).isTrue();
    }

    @Test
    public void noAdditionalVars(){
        int n = 2;
        int m = 2;
        Fraction[] func = new Fraction[n + 1];
        func[0] = new Fraction("1/2");
        func[1] = new Fraction("1");
        func[2] = new Fraction("0");
        Fraction[][] table = new Fraction[m + 1][n + 1];
        table[0][0] = new Fraction("1/3");
        table[0][1] = new Fraction("1/3");
        table[0][2] = new Fraction("1");
        table[1][0] = new Fraction("2/3");
        table[1][1] = new Fraction("-1/3");
        table[1][2] = new Fraction("1");
        table[2][0] = new Fraction("-1/3");
        table[2][1] = new Fraction("-1/3");
        table[2][2] = new Fraction("4");
        int[] colX = new int[2];
        colX[0] = 0;
        colX[1] = 2;
        int[] rowX = new int[2];
        rowX[0] = 1;
        rowX[1] = 3;
        SimplexTable simplexTable = new SimplexTable(n, m, func, table, colX, rowX, true);
        assertThat(SimplexTable.hasAdditionalVars(colX, rowX)).isFalse();
    }

    @Test
    public void findAdditionalColumn(){
        int n = 2;
        int m = 2;
        Fraction[] func = new Fraction[n + 1];
        func[0] = new Fraction("1/2");
        func[1] = new Fraction("1");
        func[2] = new Fraction("0");
        Fraction[][] table = new Fraction[m + 1][n + 1];
        table[0][0] = new Fraction("1/3");
        table[0][1] = new Fraction("1/3");
        table[0][2] = new Fraction("1");
        table[1][0] = new Fraction("2/3");
        table[1][1] = new Fraction("-1/3");
        table[1][2] = new Fraction("1");
        table[2][0] = new Fraction("-1/3");
        table[2][1] = new Fraction("-1/3");
        table[2][2] = new Fraction("4");
        int[] colX = new int[2];
        colX[0] = 0;
        colX[1] = -3;
        int[] rowX = new int[2];
        rowX[0] = 1;
        rowX[1] = 2;
        SimplexTable simplexTable = new SimplexTable(n, m, func, table, colX, rowX, true);
        assertThat(SimplexTable.findAdditionalVarColumn(simplexTable.getColX(), simplexTable.getN())).isEqualTo(1);
    }

    @Test
    public void cantFindAdditionalColumn(){
        int n = 2;
        int m = 2;
        Fraction[] func = new Fraction[n + 1];
        func[0] = new Fraction("1/2");
        func[1] = new Fraction("1");
        func[2] = new Fraction("0");
        Fraction[][] table = new Fraction[m + 1][n + 1];
        table[0][0] = new Fraction("1/3");
        table[0][1] = new Fraction("1/3");
        table[0][2] = new Fraction("1");
        table[1][0] = new Fraction("2/3");
        table[1][1] = new Fraction("-1/3");
        table[1][2] = new Fraction("1");
        table[2][0] = new Fraction("-1/3");
        table[2][1] = new Fraction("-1/3");
        table[2][2] = new Fraction("4");
        int[] colX = new int[2];
        colX[0] = 0;
        colX[1] = 3;
        int[] rowX = new int[2];
        rowX[0] = 1;
        rowX[1] = 2;
        SimplexTable simplexTable = new SimplexTable(n, m, func, table, colX, rowX, true);
        assertThat(SimplexTable.findAdditionalVarColumn(simplexTable.getColX(), simplexTable.getN())).isEqualTo(-1);
    }

    @Test
    public void toMainTest(){
        int n = 2;
        int m = 2;
        Fraction[] func = new Fraction[n + m + 1];
        func[0] = new Fraction("-1");
        func[1] = new Fraction("5");
        func[2] = new Fraction("1");
        func[3] = new Fraction("-1");
        func[4] = new Fraction("0");
        Fraction[][] table1 = new Fraction[m + 1][n + 1];
        table1[0][0] = new Fraction("1/2");
        table1[0][1] = new Fraction("1/2");
        table1[0][2] = new Fraction("1/3");
        table1[1][0] = new Fraction("3/2");
        table1[1][1] = new Fraction("-1/2");
        table1[1][2] = new Fraction("2");
        table1[2][0] = new Fraction("0");
        table1[2][1] = new Fraction("0");
        table1[2][2] = new Fraction("0");
        int[] colX1 = new int[2];
        colX1[0] = 2;
        colX1[1] = 3;
        int[] rowX1 = new int[2];
        rowX1[0] = 1;
        rowX1[1] = 0;
        SimplexTable simplexTable1 = new SimplexTable(n, m, func, table1, colX1, rowX1, true);

        Fraction[][] table2 = new Fraction[m + 1][n + 1];
        table2[0][0] = new Fraction("1/2");
        table2[0][1] = new Fraction("1/2");
        table2[0][2] = new Fraction("1/3");
        table2[1][0] = new Fraction("3/2");
        table2[1][1] = new Fraction("-1/2");
        table2[1][2] = new Fraction("2");
        table2[2][0] = new Fraction("0");
        table2[2][1] = new Fraction("-4");
        table2[2][2] = new Fraction("1/3");
        int[] colX2 = new int[2];
        colX2[0] = 2;
        colX2[1] = 3;
        int[] rowX2 = new int[2];
        rowX2[0] = 1;
        rowX2[1] = 0;
        SimplexTable simplexTable2 = new SimplexTable(n, m, func, table2, colX2, rowX2, true);

        simplexTable1 = SimplexTable.toMainTask(
                simplexTable1.getN(),
                simplexTable1.getM(),
                simplexTable1.getFunc(),
                simplexTable1.getTable(),
                simplexTable1.getColX(),
                simplexTable1.getRowX(),
                simplexTable1.isMinimisation()
        );
        assertThat(simplexTable1).isEqualTo(simplexTable2);
    }

    @Test
    public void removeColumnTest(){
        int n1 = 2;
        int m1 = 2;
        Fraction[] func1 = new Fraction[n1 + 1];
        func1[0] = new Fraction("1/2");
        func1[1] = new Fraction("1");
        func1[2] = new Fraction("0");
        Fraction[][] table1 = new Fraction[m1 + 1][n1 + 1];
        table1[0][0] = new Fraction("-1");
        table1[0][1] = new Fraction("2");
        table1[0][2] = new Fraction("1");
        table1[1][0] = new Fraction("-1/2");
        table1[1][1] = new Fraction("2");
        table1[1][2] = new Fraction("1");
        table1[2][0] = new Fraction("-3/2");
        table1[2][1] = new Fraction("-4");
        table1[2][2] = new Fraction("0");
        int[] colX1 = new int[2];
        colX1[0] = 0;
        colX1[1] = 1;
        int[] rowX1 = new int[2];
        rowX1[0] = 2;
        rowX1[1] = 3;
        SimplexTable simplexTable1 = new SimplexTable(n1, m1, func1, table1, colX1, rowX1, true);

        int n2 = 2;
        int m2 = 2;
        Fraction[] func2 = new Fraction[n2 + 1];
        func2[0] = new Fraction("1/2");
        func2[1] = new Fraction("1");
        func2[2] = new Fraction("0");
        Fraction[][] table2 = new Fraction[m2 + 1][n2];
        table2[0][0] = new Fraction("-1");
        table2[0][1] = new Fraction("1");
        table2[1][0] = new Fraction("-1/2");
        table2[1][1] = new Fraction("1");
        table2[2][0] = new Fraction("-3/2");
        table2[2][1] = new Fraction("0");
        int[] colX2 = new int[1];
        colX2[0] = 0;
        int[] rowX2 = new int[2];
        rowX2[0] = 2;
        rowX2[1] = 3;
        SimplexTable simplexTable2 = new SimplexTable(n2 - 1, m2, func2, table2, colX2, rowX2, true);

        simplexTable1 = SimplexTable.removeCol(
                simplexTable1.getN(),
                simplexTable1.getM(),
                simplexTable1.getFunc(),
                simplexTable1.getTable(),
                simplexTable1.getColX(),
                simplexTable1.getRowX(),
                simplexTable1.isMinimisation(),
                1);
        assertThat(simplexTable1).isEqualTo(simplexTable2);
    }

    @Test
    public void solvingTest(){
        int n1 = 4;
        int m1 = 2;
        Fraction[] func1 = new Fraction[n1 + 1];
        func1[0] = new Fraction("1");
        func1[1] = new Fraction("-5");
        func1[2] = new Fraction("-1");
        func1[3] = new Fraction("1");
        func1[4] = new Fraction("0");
        Fraction[][] table1 = new Fraction[m1 + 1][n1 + 1];
        table1[0][0] = new Fraction("1");
        table1[0][1] = new Fraction("3");
        table1[0][2] = new Fraction("3");
        table1[0][3] = new Fraction("1");
        table1[0][4] = new Fraction("3");
        table1[1][0] = new Fraction("2");
        table1[1][1] = new Fraction("0");
        table1[1][2] = new Fraction("3");
        table1[1][3] = new Fraction("-1");
        table1[1][4] = new Fraction("4");
        SimplexTable simplexTable1 = new SimplexTable(n1, m1, func1, table1, true);

        while (!SimplexTable.isSolved(simplexTable1.getTable(), simplexTable1.getN(), simplexTable1.getM())
                && SimplexTable.hasSolution(simplexTable1.getTable(), simplexTable1.getN(), simplexTable1.getM())
                && SimplexTable.hasAdditionalVars(simplexTable1.getColX(), simplexTable1.getRowX())){
            simplexTable1 = SimplexTable.simplexStep(
                    simplexTable1.getN(),
                    simplexTable1.getM(),
                    simplexTable1.getFunc(),
                    simplexTable1.getTable(),
                    simplexTable1.getColX(),
                    simplexTable1.getRowX(),
                    simplexTable1.isMinimisation());
            int additionalVarColumn = SimplexTable.findAdditionalVarColumn(simplexTable1.getColX(), simplexTable1.getN());
            if (additionalVarColumn != -1){
                simplexTable1 = SimplexTable.removeCol(
                        simplexTable1.getN(),
                        simplexTable1.getM(),
                        simplexTable1.getFunc(),
                        simplexTable1.getTable(),
                        simplexTable1.getColX(),
                        simplexTable1.getRowX(),
                        simplexTable1.isMinimisation(),
                        additionalVarColumn);
            }
        }
        simplexTable1 = SimplexTable.toMainTask(
                simplexTable1.getN(),
                simplexTable1.getM(),
                simplexTable1.getFunc(),
                simplexTable1.getTable(),
                simplexTable1.getColX(),
                simplexTable1.getRowX(),
                simplexTable1.isMinimisation());
        while (!SimplexTable.isSolved(simplexTable1.getTable(), simplexTable1.getN(), simplexTable1.getM())
                && SimplexTable.hasSolution(simplexTable1.getTable(), simplexTable1.getN(), simplexTable1.getM())){
            simplexTable1 = SimplexTable.simplexStep(
                    simplexTable1.getN(),
                    simplexTable1.getM(),
                    simplexTable1.getFunc(),
                    simplexTable1.getTable(),
                    simplexTable1.getColX(),
                    simplexTable1.getRowX(),
                    simplexTable1.isMinimisation());
        }
        assertThat(SimplexTable.getAnswer(
                simplexTable1.getN(),
                simplexTable1.getM(),
                simplexTable1.getTable(),
                simplexTable1.isMinimisation()
        )).isEqualTo(new Fraction("1/3"));
    }

    @Test
    public void solvingTest2(){
        int n = 5;
        int m = 3;
        Fraction[] func = new Fraction[n + 1];
        func[0] = new Fraction("-1");
        func[1] = new Fraction("-1");
        func[2] = new Fraction("-1");
        func[3] = new Fraction("-1");
        func[4] = new Fraction("-1");
        func[5] = new Fraction("0");
        Fraction[][] table = new Fraction[m + 1][n + 1];
        table[0][0] = new Fraction("1");
        table[0][1] = new Fraction("1");
        table[0][2] = new Fraction("2");
        table[0][3] = new Fraction("0");
        table[0][4] = new Fraction("0");
        table[0][5] = new Fraction("6");
        table[1][0] = new Fraction("0");
        table[1][1] = new Fraction("2");
        table[1][2] = new Fraction("2");
        table[1][3] = new Fraction("-1");
        table[1][4] = new Fraction("1");
        table[1][5] = new Fraction("6");
        table[2][0] = new Fraction("1");
        table[2][1] = new Fraction("-1");
        table[2][2] = new Fraction("6");
        table[2][3] = new Fraction("1");
        table[2][4] = new Fraction("1");
        table[2][5] = new Fraction("12");
        SimplexTable simplexTable = new SimplexTable(n, m, func, table, true);

        while (!SimplexTable.isSolved(simplexTable.getTable(), simplexTable.getN(), simplexTable.getM())
                && SimplexTable.hasSolution(simplexTable.getTable(), simplexTable.getN(), simplexTable.getM())
                && SimplexTable.hasAdditionalVars(simplexTable.getColX(), simplexTable.getRowX())){
            simplexTable = SimplexTable.simplexStep(
                    simplexTable.getN(),
                    simplexTable.getM(),
                    simplexTable.getFunc(),
                    simplexTable.getTable(),
                    simplexTable.getColX(),
                    simplexTable.getRowX(),
                    simplexTable.isMinimisation());
            int additionalVarColumn = SimplexTable.findAdditionalVarColumn(simplexTable.getColX(), simplexTable.getN());
            if (additionalVarColumn != -1){
                simplexTable = SimplexTable.removeCol(
                        simplexTable.getN(),
                        simplexTable.getM(),
                        simplexTable.getFunc(),
                        simplexTable.getTable(),
                        simplexTable.getColX(),
                        simplexTable.getRowX(),
                        simplexTable.isMinimisation(),
                        additionalVarColumn);
            }
        }
        simplexTable = SimplexTable.toMainTask(
                simplexTable.getN(),
                simplexTable.getM(),
                simplexTable.getFunc(),
                simplexTable.getTable(),
                simplexTable.getColX(),
                simplexTable.getRowX(),
                simplexTable.isMinimisation());
        while (!SimplexTable.isSolved(simplexTable.getTable(), simplexTable.getN(), simplexTable.getM())
                && SimplexTable.hasSolution(simplexTable.getTable(), simplexTable.getN(), simplexTable.getM())){
            simplexTable = SimplexTable.simplexStep(
                    simplexTable.getN(),
                    simplexTable.getM(),
                    simplexTable.getFunc(),
                    simplexTable.getTable(),
                    simplexTable.getColX(),
                    simplexTable.getRowX(),
                    simplexTable.isMinimisation());
        }
        assertThat(SimplexTable.getAnswer(
                simplexTable.getN(),
                simplexTable.getM(),
                simplexTable.getTable(),
                simplexTable.isMinimisation()
        )).isEqualTo(new Fraction("-24"));
    }

    @Test
    public void fileTest(){
        int n = 2;
        int m = 2;
        Fraction[] func = new Fraction[n + 1];
        func[0] = new Fraction("1/2");
        func[1] = new Fraction("1");
        func[2] = new Fraction("0");
        Fraction[][] table = new Fraction[m + 1][n + 1];
        table[0][0] = new Fraction("1/3");
        table[0][1] = new Fraction("1/3");
        table[0][2] = new Fraction("1");
        table[1][0] = new Fraction("2/3");
        table[1][1] = new Fraction("-1/3");
        table[1][2] = new Fraction("1");
        table[2][0] = new Fraction("-1/3");
        table[2][1] = new Fraction("-1/3");
        table[2][2] = new Fraction("4");
        int[] colX = new int[2];
        colX[0] = 0;
        colX[1] = 3;
        int[] rowX = new int[2];
        rowX[0] = 1;
        rowX[1] = 2;
        SimplexTable simplexTable = new SimplexTable(n, m, func, table, colX, rowX, true);
        String file = "src/test/resources/test.txt";

        simplexTable.writeToFile(file);
        SimplexTable newSimplexTable = new SimplexTable(file);
        assertThat(newSimplexTable).isEqualTo(simplexTable);
        try{
            Files.delete(Path.of(file));
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}