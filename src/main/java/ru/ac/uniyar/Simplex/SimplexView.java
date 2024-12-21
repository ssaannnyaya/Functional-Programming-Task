package ru.ac.uniyar.Simplex;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ru.ac.uniyar.Simplex.Utils.Fraction;
import ru.ac.uniyar.Simplex.Utils.SimplexTable;
import java.util.ArrayList;

public class SimplexView {
    private BorderPane root;
    private ArrayList<SimplexTable> simplexSteps;
    private int curStep;
    private int curRow;
    private int curCol;
    private boolean isDecimal;

    public SimplexView(SimplexTable task, boolean isDecimal){
        root = new BorderPane();
        simplexSteps = new ArrayList<>();
        simplexSteps.add(task.clone());
        curStep = 0;
        this.isDecimal = isDecimal;
        curCol = -1;
        curRow = -1;

        createCenter();
        createSolvingButtons();
    }

    public BorderPane getPane() {
        return root;
    }

    public void createCenter() {
        root.setCenter(new ScrollPane(getSolvingSteps()));
    }

    public void createSolvingButtons(){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10));
        root.setBottom(hBox);

        Button prevStepButton = new Button("Предыдущий шаг");
        Button nextStepButton = new Button("Следующий шаг");
        Button solveButton = new Button("Решить");

        prevStepButton.setDisable(isFirstStep());
        nextStepButton.setDisable(isLastStep() && !isTimeToDoMainTask());
        solveButton.setDisable(isLastStep() && !isTimeToDoMainTask());

        prevStepButton.setOnAction(event -> {
            prevStep();
            prevStepButton.setDisable(isFirstStep());
            nextStepButton.setDisable(isLastStep() && !isTimeToDoMainTask());
            solveButton.setDisable(isLastStep() && !isTimeToDoMainTask());
        });

        nextStepButton.setOnAction(event -> {
            nextStep();
            prevStepButton.setDisable(isFirstStep());
            nextStepButton.setDisable(isLastStep() && !isTimeToDoMainTask());
            solveButton.setDisable(isLastStep() && !isTimeToDoMainTask());
        });

        solveButton.setOnAction(event -> {
            while (!isLastStep() || isTimeToDoMainTask()) {
                nextStep();
            }
            prevStepButton.setDisable(isFirstStep());
            nextStepButton.setDisable(isLastStep() && !isTimeToDoMainTask());
            solveButton.setDisable(isLastStep() && !isTimeToDoMainTask());
        });

        hBox.getChildren().addAll(prevStepButton, nextStepButton, solveButton);
    }

    public SimplexTable getProblem(){
        return simplexSteps.get(0);
    }

    public Text getFunction() {
        if (isEmpty()) {
            return new Text("");
        }
        SimplexTable task = simplexSteps.get(0);
        Text function = new Text(SimplexTable.getFuncAsString(
                task.getFunc(),
                task.isMinimisation(),
                isDecimal));
        function.setFont(new Font(16));
        return function;
    }

    public GridPane getTable(int step) {
        if (isEmpty() || step < 0 || step >= simplexSteps.size()) {
            return new GridPane();
        }

        int high = 30;
        int width = 60;

        SimplexTable task = simplexSteps.get(step);

        GridPane pane = new GridPane();

        pane.getColumnConstraints().add(new ColumnConstraints(width));
        pane.getRowConstraints().add(new RowConstraints(high));

        Rectangle rectangle00 = new Rectangle(width, high);
        rectangle00.setFill(Color.LIGHTGRAY);

        Label cell00 = new Label("X");
        GridPane.setHalignment(cell00, HPos.CENTER);
        GridPane.setValignment(cell00, VPos.CENTER);

        StackPane stack00 = new StackPane();
        stack00.getChildren().addAll(rectangle00, cell00);
        pane.add(stack00, 0, 0);

        for (int j = 0; j < task.getN(); j++) {
            pane.getColumnConstraints().add(new ColumnConstraints(width));

            Rectangle rectangleHigh = new Rectangle(width, high);
            rectangleHigh.setFill(Color.LIGHTGRAY);
            int x = Math.abs(task.getColX()[j]);
            Label cellInHigh = new Label(SimplexTable.getIndexedX(x));
            cellInHigh.setFont(new Font(16));

            GridPane.setHalignment(cellInHigh, HPos.CENTER);
            GridPane.setValignment(cellInHigh, VPos.CENTER);

            StackPane stackHigh = new StackPane();
            stackHigh.getChildren().addAll(rectangleHigh, cellInHigh);
            pane.add(stackHigh, j + 1, 0);
        }


        pane.getColumnConstraints().add(new ColumnConstraints(width));

        Rectangle rectangleHigh = new Rectangle(width, high);
        rectangleHigh.setFill(Color.LIGHTGRAY);

        Label cellInHigh = new Label("b");
        GridPane.setHalignment(cellInHigh, HPos.CENTER);
        GridPane.setValignment(cellInHigh, VPos.CENTER);

        StackPane stackHigh = new StackPane();
        stackHigh.getChildren().addAll(rectangleHigh, cellInHigh);
        pane.add(stackHigh, task.getN() + 1, 0);


        for (int i = 0; i < task.getM(); i++) {
            pane.getRowConstraints().add(new RowConstraints(high));

            Rectangle rectangleLeft = new Rectangle(width, high);
            rectangleLeft.setFill(Color.LIGHTGRAY);

            int x = Math.abs(task.getRowX()[i]);
            Label cellInLeft = new Label(SimplexTable.getIndexedX(x));
            cellInLeft.setFont(new Font(16));

            GridPane.setHalignment(cellInLeft, HPos.CENTER);
            GridPane.setValignment(cellInLeft, VPos.CENTER);

            StackPane stackLeft = new StackPane();
            stackLeft.getChildren().addAll(rectangleLeft, cellInLeft);
            pane.add(stackLeft, 0, i + 1);
        }


        pane.getRowConstraints().add(new RowConstraints(high));

        Rectangle rectangleLeft = new Rectangle(width, high);
        rectangleLeft.setFill(Color.LIGHTGRAY);

        Label cellInLeft = new Label("f(x)");
        GridPane.setHalignment(cellInLeft, HPos.CENTER);
        GridPane.setValignment(cellInLeft, VPos.CENTER);

        StackPane stackLeft = new StackPane();
        stackLeft.getChildren().addAll(rectangleLeft, cellInLeft);
        pane.add(stackLeft, 0, task.getM() + 1);


        for (int j = 0; j <= task.getN(); j++) {
            for (int i = 0; i <= task.getM(); i++) {
                Label cell = new Label(Fraction.getFrString(task.getTable()[i][j], isDecimal));
                GridPane.setHalignment(cell, HPos.CENTER);
                GridPane.setValignment(cell, VPos.CENTER);

                pane.add(cell, j + 1, i + 1);
            }
        }

        if (step == curStep && SimplexTable.hasSolution(task.getTable(), task.getN(), task.getM())
                && !SimplexTable.isSolved(task.getTable(), task.getN(), task.getM())) {
            int[][] elementsForStep = SimplexTable.getPossibleElementsForStep(task.getN(), task.getM(), task.getTable());
            for (int[] element : elementsForStep) {
                int rowForStep = element[0];
                int colForStep = element[1];
                Rectangle rectangle = new Rectangle(width, high);
                rectangle.setFill(Color.LIGHTBLUE);
                rectangle.setOnMouseClicked(event -> {
                    curRow = element[0];
                    curCol = element[1];
                    createCenter();
                });

                Label cellForStep = new Label(Fraction.getFrString(task.getTable()[rowForStep][colForStep], isDecimal));
                cellForStep.setOnMouseClicked(event -> {
                    curRow = element[0];
                    curCol = element[1];
                    createCenter();
                });
                GridPane.setHalignment(cellForStep, HPos.CENTER);
                GridPane.setValignment(cellForStep, VPos.CENTER);

                StackPane stackForStep = new StackPane();
                stackForStep.getChildren().addAll(rectangle, cellForStep);
                pane.add(stackForStep, colForStep + 1, rowForStep + 1);
            }

            int colForStep;
            int rowForStep;
            if (curCol == -1 && curRow == -1) {
                colForStep = SimplexTable.colForSimplexStep(
                        task.getTable(),
                        task.getN(),
                        task.getM());
                rowForStep = SimplexTable.rowForSimplexStep(
                        colForStep,
                        task.getTable(),
                        task.getN(),
                        task.getM());
            } else {
                colForStep = curCol;
                rowForStep = curRow;
            }
            Rectangle rectangle = new Rectangle(width, high);
            rectangle.setFill(Color.GREEN);

            Label cellForStep = new Label(Fraction.getFrString(task.getTable()[rowForStep][colForStep], isDecimal));
            GridPane.setHalignment(cellForStep, HPos.CENTER);
            GridPane.setValignment(cellForStep, VPos.CENTER);

            StackPane stackSelectedElement = new StackPane();
            stackSelectedElement.getChildren().addAll(rectangle, cellForStep);
            pane.add(stackSelectedElement, colForStep + 1, rowForStep + 1);

        } else {
            int colForStep = getColForStep(step);
            int rowForStep = getRowForStep(step);
            if (colForStep != -1 && rowForStep != -1) {
                Rectangle rectangle = new Rectangle(width, high);
                rectangle.setFill(Color.CYAN);

                Label cellForStep = new Label(Fraction.getFrString(task.getTable()[rowForStep][colForStep], isDecimal));
                GridPane.setHalignment(cellForStep, HPos.CENTER);
                GridPane.setValignment(cellForStep, VPos.CENTER);

                StackPane stackElementWasSelected = new StackPane();
                stackElementWasSelected.getChildren().addAll(rectangle, cellForStep);
                pane.add(stackElementWasSelected, colForStep + 1, rowForStep + 1);
            }
        }

        pane.setGridLinesVisible(true);
        pane.setPadding(new Insets(10));
        return pane;
    }

    public VBox getSolvingSteps() {
        VBox solvingSteps = new VBox();
        solvingSteps.setPadding(new Insets(20));
        solvingSteps.getChildren().clear();
        solvingSteps.getChildren().add(getFunction());
        for (int i = 0; i <= curStep; i++) {
            if (isTimeToDoMainTask(i - 1)) {
                solvingSteps.getChildren().add(new Text("Базис найден, переходим к основной задаче"));
            }
            solvingSteps.getChildren().add(getTable(i));
        }
        if (!isEmpty() && !isTimeToDoMainTask()) {
            SimplexTable curTable = simplexSteps.get(curStep);
            solvingSteps.getChildren().add(new Text(
                    SimplexTable.getAnswerAsString(
                            curTable.getN(),
                            curTable.getM(),
                            curTable.getTable(),
                            curTable.getColX(),
                            curTable.getRowX(),
                            curTable.isMinimisation(),
                            isDecimal)
                    )
            );
        }
        return solvingSteps;
    }

    public boolean isFirstStep(){
        return curStep == 0;
    }

    public boolean isLastStep(){
        SimplexTable table = simplexSteps.get(curStep);
        return SimplexTable.isSolved(table.getTable(), table.getN(), table.getM()) || !SimplexTable.hasSolution(table.getTable(), table.getN(), table.getM());
    }

    public boolean isTimeToDoMainTask() {
        return isTimeToDoMainTask(curStep);
    }

    public boolean isTimeToDoMainTask(int step) {
        if (step <= 0) {
            return false;
        }
        SimplexTable prevTable = simplexSteps.get(step - 1);
        SimplexTable stepTable = simplexSteps.get(step);
        return curStep != 0 && SimplexTable.hasAdditionalVars(prevTable.getColX(), prevTable.getRowX())
                && !SimplexTable.hasAdditionalVars(stepTable.getColX(), stepTable.getRowX());
    }

    public int getColForStep(int step) {
        if (step < 0 || step >= curStep || isTimeToDoMainTask(step)) {
            return -1;
        }
        int[] colX = simplexSteps.get(step).getColX();
        int[] nextColX = simplexSteps.get(step + 1).getColX();
        int col = colX.length - 1;
        for (int j = 0; j < nextColX.length; j++) {
            if (colX[j] != nextColX[j]) {
                return j;
            }
        }
        return col;
    }

    public int getRowForStep(int step) {
        if (step < 0 || step >= curStep) {
            return -1;
        }
        int[] rowX = simplexSteps.get(step).getRowX();
        int[] nextRowX = simplexSteps.get(step + 1).getRowX();
        int row = rowX.length - 1;
        for (int i = 0; i < nextRowX.length; i++) {
            if (rowX[i] != nextRowX[i]) {
                return i;
            }
        }
        return row;
    }

    public void nextStep() {
        SimplexTable simplexTable = simplexSteps.get(curStep).clone();
        if (curStep != 0) {
            if (isTimeToDoMainTask()) {
                simplexSteps.add(
                        SimplexTable.toMainTask(
                                simplexTable.getN(),
                                simplexTable.getM(),
                                simplexTable.getFunc(),
                                simplexTable.getTable(),
                                simplexTable.getColX(),
                                simplexTable.getRowX(),
                                simplexTable.isMinimisation())
                );
                curStep++;
                curCol = -1;
                curRow = -1;
                createCenter();
                return;
            }
        }
        if (isLastStep()) {
            return;
        }
        if (SimplexTable.hasAdditionalVars(simplexTable.getColX(), simplexTable.getRowX())) {
            if (curCol != -1 && curRow != -1) {
                simplexTable = SimplexTable.simplexStep(
                        curRow,
                        curCol,
                        simplexTable.getN(),
                        simplexTable.getM(),
                        simplexTable.getFunc(),
                        simplexTable.getTable(),
                        simplexTable.getColX(),
                        simplexTable.getRowX(),
                        simplexTable.isMinimisation());
            } else {
                simplexTable = SimplexTable.simplexStep(
                        simplexTable.getN(),
                        simplexTable.getM(),
                        simplexTable.getFunc(),
                        simplexTable.getTable(),
                        simplexTable.getColX(),
                        simplexTable.getRowX(),
                        simplexTable.isMinimisation());
            }
            int additionalVarColumn = SimplexTable.findAdditionalVarColumn(simplexTable.getColX(), simplexTable.getN());
            if (additionalVarColumn != -1) {
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
            simplexSteps.add(simplexTable);
            curStep++;
            curCol = -1;
            curRow = -1;
            createCenter();
            return;
        }
        if (curCol != -1 && curRow != -1) {
            simplexTable = SimplexTable.simplexStep(
                    curRow,
                    curCol,
                    simplexTable.getN(),
                    simplexTable.getM(),
                    simplexTable.getFunc(),
                    simplexTable.getTable(),
                    simplexTable.getColX(),
                    simplexTable.getRowX(),
                    simplexTable.isMinimisation());
        } else {
            simplexTable = SimplexTable.simplexStep(
                    simplexTable.getN(),
                    simplexTable.getM(),
                    simplexTable.getFunc(),
                    simplexTable.getTable(),
                    simplexTable.getColX(),
                    simplexTable.getRowX(),
                    simplexTable.isMinimisation());
        }
        simplexSteps.add(simplexTable);
        curStep++;
        curCol = -1;
        curRow = -1;
        createCenter();
    }

    public void prevStep(){
        if (curStep != 0) {
            simplexSteps.remove(curStep);
            curStep--;
            curCol = -1;
            curRow = -1;
            createCenter();
        }
    }

    public void changeDecimal(){
        isDecimal = !isDecimal;
        createCenter();
    }

    public boolean isDecimal() {
        return isDecimal;
    }

    public boolean isEmpty(){
        return simplexSteps == null || simplexSteps.isEmpty();
    }
}