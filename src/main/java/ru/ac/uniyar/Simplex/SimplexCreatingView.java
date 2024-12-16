package ru.ac.uniyar.Simplex;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ru.ac.uniyar.Simplex.Utils.Fraction;
import ru.ac.uniyar.Simplex.Utils.SimplexTable;

import java.util.ArrayList;

public class SimplexCreatingView {
    private BorderPane root;
    private Spinner<Integer> nSpinner;
    private Spinner<Integer> mSpinner;
    private TextField[] func;
    private TextField[][] table;
    private GridPane topPane;
    private GridPane centerPane;
    private Text kindOfTask;

    private ToggleGroup minChooser;
    private RadioButton minButton;
    private RadioButton maxButton;

    private ToggleGroup basisChooser;
    private RadioButton artificialBasisButton;
    private RadioButton givenBasisButton;

    private ArrayList<CheckBox> varsCheckBoxes;
    private VBox varsCheckBoxesBox;

    private boolean isMinimisation;
    private boolean isGivenBasis;

    public SimplexCreatingView(){
        root = new BorderPane();

        nSpinner = new Spinner<>(1, 16, 4);
        nSpinner.setEditable(true);

        mSpinner = new Spinner<>(1, 16, 2);
        mSpinner.setEditable(true);

        isMinimisation = true;
        isGivenBasis = false;

        createCenter();
        createTop();
        nSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            createCenter();
            createTop();
        });
        mSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            createCenter();
            createTop();
        });
    }

    public void createTop() {
        topPane = new GridPane();
        topPane.setPadding(new Insets(10));
        topPane.getRowConstraints().add(new RowConstraints(30));
        topPane.getRowConstraints().add(new RowConstraints(30));
        topPane.getColumnConstraints().add(new ColumnConstraints(160));
        topPane.getColumnConstraints().add(new ColumnConstraints(60));
        topPane.getColumnConstraints().add(new ColumnConstraints(35));
        topPane.getColumnConstraints().add(new ColumnConstraints(160));
        topPane.getColumnConstraints().add(new ColumnConstraints(35));
        topPane.getColumnConstraints().add(new ColumnConstraints(200));
        Text nText = new Text("Количество переменных");
        Text mText = new Text("Количество ограничений");

        topPane.add(nText, 0, 0);
        topPane.add(nSpinner, 1, 0);
        topPane.add(mText, 0, 1);
        topPane.add(mSpinner, 1, 1);

        minChooser = new ToggleGroup();

        minButton = new RadioButton("Задача минимизации");
        minButton.setOnAction(event -> {
            isMinimisation = true;
            centerPane.getChildren().remove(kindOfTask);
            kindOfTask.setText("-> min");
            centerPane.add(kindOfTask, nSpinner.getValue() + 2, 1);

        });
        minButton.setSelected(isMinimisation);

        maxButton = new RadioButton("Задача максимизации");
        maxButton.setOnAction(event -> {
            isMinimisation = false;
            centerPane.getChildren().remove(kindOfTask);
            kindOfTask.setText("-> max");
            centerPane.add(kindOfTask, nSpinner.getValue() + 2, 1);

        });
        maxButton.setSelected(!isMinimisation);

        minButton.setToggleGroup(minChooser);
        maxButton.setToggleGroup(minChooser);

        topPane.add(minButton, 3, 0);
        topPane.add(maxButton, 3, 1);

        basisChooser = new ToggleGroup();

        artificialBasisButton = new RadioButton("Метод искусственного базиса");
        artificialBasisButton.setOnAction(event -> {
            isGivenBasis = false;
            root.setRight(null);
        });
        artificialBasisButton.setSelected(!isGivenBasis);

        givenBasisButton = new RadioButton("Введённый базис");
        givenBasisButton.setOnAction(event -> {
            isGivenBasis = true;
            createRight();
        });
        givenBasisButton.setSelected(isGivenBasis);

        artificialBasisButton.setToggleGroup(basisChooser);
        givenBasisButton.setToggleGroup(basisChooser);

        topPane.add(artificialBasisButton, 5, 0);
        topPane.add(givenBasisButton, 5, 1);

        root.setTop(topPane);
    }

    public void createCenter() {
        centerPane = new GridPane();

        func = new TextField[nSpinner.getValue() + 1];
        for (int i = 0; i <= nSpinner.getValue(); i++) {
            func[i] = new TextField();
            int I = i;
            func[i].textProperty().addListener((observable, oldValue, newValue) -> {
                validate(func[I]);
            });
        }

        table = new TextField[mSpinner.getValue() + 1][nSpinner.getValue() + 1];
        for (int j = 0; j <= nSpinner.getValue(); j++) {
            for (int i = 0; i <= mSpinner.getValue(); i++) {
                table[i][j] = new TextField();
                int I = i;
                int J = j;
                table[i][j].textProperty().addListener((observable, oldValue, newValue) -> {
                    validate(table[I][J]);
                });
            }
        }

        int high = 30;
        int width = 60;

        for (int j = 0; j < nSpinner.getValue(); j++) {
            Text text;
            if (j + 1 > 9) {
                text = new Text("x" + "\u2081" + ((char) ('\u2080' + ((j + 1) % 10))));
            } else {
                text = new Text("x" + "" + ((char) ('\u2080' + ((j + 1) % 10))));
            }
            text.setFont(new Font(16));
            centerPane.add(text, j + 1, 0);
            GridPane.setHalignment(text, HPos.CENTER);
            GridPane.setValignment(text, VPos.BOTTOM);

            centerPane.add(func[j], j + 1, 1);
            centerPane.getColumnConstraints().add(new ColumnConstraints(width));
        }

        Text textB = new Text("b");
        textB.setFont(new Font(16));
        centerPane.add(textB, nSpinner.getValue() + 1, 0);
        GridPane.setHalignment(textB, HPos.CENTER);
        GridPane.setValignment(textB, VPos.BOTTOM);

        centerPane.add(func[nSpinner.getValue()], nSpinner.getValue() + 1, 1);
        centerPane.getColumnConstraints().add(new ColumnConstraints(width));

        Text textFun = new Text("f(x)");
        textFun.setFont(new Font(16));
        centerPane.add(textFun, 0, 1);
        GridPane.setHalignment(textFun, HPos.RIGHT);
        GridPane.setValignment(textFun, VPos.CENTER);
        centerPane.getColumnConstraints().add(new ColumnConstraints(width));

        if (isMinimisation) {
            kindOfTask = new Text("--> " + "min");
        } else {
            kindOfTask = new Text("--> " + "max");
        }
        kindOfTask.setFont(new Font(16));
        centerPane.add(kindOfTask, nSpinner.getValue() + 2, 1);
        GridPane.setHalignment(kindOfTask, HPos.CENTER);
        GridPane.setValignment(kindOfTask, VPos.CENTER);
        centerPane.getColumnConstraints().add(new ColumnConstraints(width));

        for (int i = 0; i < mSpinner.getValue(); i++) {
            centerPane.getRowConstraints().add(new RowConstraints(high));
            for (int j = 0; j <= nSpinner.getValue(); j++) {
                centerPane.add(table[i][j], j + 1, i + 2);
            }
        }
        root.setCenter(centerPane);
    }

    public void createRight() {
        varsCheckBoxes = new ArrayList<>();
        varsCheckBoxesBox = new VBox();
        varsCheckBoxesBox.setPadding(new Insets(15));
        varsCheckBoxesBox.setOnMouseEntered(event -> {
            varsCheckBoxesBox.setBorder(Border.EMPTY);
        });
        for (int j = 0; j < nSpinner.getValue(); j++) {
            CheckBox checkBox;
            if (j+1>9){
                checkBox = new CheckBox("x" + "\u2081" + ((char) ('\u2080' + ((j + 1) % 10))));
            }
            else {
                checkBox = new CheckBox("x" + "" + ((char) ('\u2080' + ((j + 1) % 10))));
            }
            checkBox.setFont(new Font(16));
            checkBox.setOnAction(event -> {
                for (int i = 0; i < nSpinner.getValue(); i++) {
                    if (!varsCheckBoxes.get(i).isSelected()) {
                        varsCheckBoxes.get(i).setDisable(isAllVarsChosen());
                    }
                }
            });
            varsCheckBoxes.add(checkBox);
            varsCheckBoxesBox.getChildren().add(checkBox);
        }
        root.setRight(varsCheckBoxesBox);
    }

    public boolean isAllVarsChosen() {
        if (nSpinner.getValue() < 3) {
            return true;
        }
        int count = 0;
        for (int j = 0; j < nSpinner.getValue(); j++) {
            if (varsCheckBoxes.get(j).isSelected()) {
                count++;
            }
        }
        return count == mSpinner.getValue();
    }

    public boolean isTableOk() {
        boolean isOk = true;

        for (int j = 0; j < func.length; j++) {
            try {
                new Fraction(func[j].getText());
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                isOk = false;
                func[j].setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(2), BorderWidths.DEFAULT)));
            }
        }

        for (int j = 0; j <= nSpinner.getValue(); j++) {
            for (int i = 0; i < mSpinner.getValue(); i++) {
                try {
                    new Fraction(table[i][j].getText());
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    isOk = false;
                    table[i][j].setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(2), BorderWidths.DEFAULT)));
                }
            }
        }

        if (isGivenBasis && !isAllVarsChosen()) {
            isOk = false;
            varsCheckBoxesBox.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(2), BorderWidths.DEFAULT)));
        }

        return isOk;
    }

    public BorderPane getPane() {
        return root;
    }

    public SimplexTable getTask() {
        int n = nSpinner.getValue();
        int m = mSpinner.getValue();
        Fraction[] fractionFunc = new Fraction[n + 1];
        Fraction[][] fractionTable = new Fraction[m + 1][n + 1];
        for (int j = 0; j < func.length; j++) {
            fractionFunc[j] = new Fraction(func[j].getText());
        }

        for (int j = 0; j <= nSpinner.getValue(); j++) {
            for (int i = 0; i < mSpinner.getValue(); i++) {
                fractionTable[i][j] = new Fraction(table[i][j].getText());
            }
        }
        SimplexTable simplexTable;
        if (isGivenBasis) {
            int[] vars = new int[mSpinner.getValue()];
            int var = 0;
            for (int j = 0; j < nSpinner.getValue(); j++) {
                if (varsCheckBoxes.get(j).isSelected()) {
                    vars[var] = j;
                    var++;
                }
            }
            simplexTable = new SimplexTable(n, m, fractionFunc, fractionTable, vars, isMinimisation);
        } else {
            simplexTable = new SimplexTable(n, m, fractionFunc, fractionTable, isMinimisation);
        }
        return simplexTable;
    }

    public void validate(TextField textField) {
        boolean isOk = true;
        try {
            new Fraction(textField.getText());
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            textField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(2), BorderWidths.DEFAULT)));
            isOk = false;
        }
        if (isOk) {
            textField.setBorder(Border.EMPTY);
        }
    }

}