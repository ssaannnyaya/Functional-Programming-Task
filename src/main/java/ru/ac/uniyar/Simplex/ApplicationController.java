package ru.ac.uniyar.Simplex;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.ac.uniyar.Simplex.Utils.SimplexTable;
import java.io.File;

public class ApplicationController {
    private transient SimplexView simplexView;
    private transient SimplexCreatingView simplexCreatingView;
    private final BorderPane root;

    public ApplicationController(){
        root = new BorderPane();
        createMenus();
    }

    public void clearPane() {
        root.setBottom(null);
        root.setCenter(null);
        root.setRight(null);
    }

    public BorderPane getRoot() {
        return root;
    }

    public void showSimplex() {
        if (root.getCenter() == null) {
            clearPane();
            if (simplexView == null || simplexView.isEmpty()) {
                return;
            }
            root.setCenter(simplexView.getPane());
        }
        createDecimalButton();
    }

    public void createDecimalButton() {
        Button decimalButton = new Button();
        if (simplexView.isDecimal()){
            decimalButton.setText("Обыкновенные дроби");
        } else {
            decimalButton.setText("Десятичные дроби");
        }
        decimalButton.setOnAction(event -> {
            simplexView.changeDecimal();
            if (simplexView.isDecimal()){
                decimalButton.setText("Обыкновенные дроби");
            } else {
                decimalButton.setText("Десятичные дроби");
            }
            showSimplex();
        });
        root.setRight(decimalButton);
    }

    public void createMenus() {
        MenuBar menuBar = new MenuBar();
        Menu mainMenu = new Menu("Файл");
        menuBar.getMenus().add(mainMenu);
        mainMenu.getItems().addAll(createFileReadingMenu(), createFileSavingMenu(), createNewTaskMenu());
        root.setTop(menuBar);
    }

    public MenuItem createFileReadingMenu(){
        MenuItem menuItem = new MenuItem("Загрузить");
        menuItem.setOnAction((ActionEvent t) -> {
            loadFromFile();
        });
        return menuItem;
    }

    public MenuItem createFileSavingMenu(){
        MenuItem menuItem = new MenuItem("Сохранить");
        menuItem.setOnAction((ActionEvent t) -> {
            saveToFile();
        });
        return menuItem;
    }

    public MenuItem createNewTaskMenu() {
        MenuItem menuItem = new MenuItem("Новая задача");
        menuItem.setOnAction((ActionEvent t) -> {
            newTask();
        });
        return menuItem;
    }

    public void newTask() {
        clearPane();
        simplexCreatingView = new SimplexCreatingView();
        root.setCenter(simplexCreatingView.getPane());
        createNewTaskButtons();
    }

    public void createNewTaskButtons(){
        Button accept = new Button("Принять");
        accept.setOnAction(event -> {
            newTaskAccept();
        });

        Button cancel = new Button("Отмена");
        cancel.setOnAction(event -> {
            newTaskCancel();
        });

        HBox hBox = new HBox();
        hBox.getChildren().addAll(cancel, accept);
        hBox.setPadding(new Insets(10));

        root.setBottom(hBox);
    }

    public void newTaskAccept() {
        if (simplexCreatingView.isTableOk()) {
            clearPane();
            simplexView = new SimplexView(simplexCreatingView.getTask(), false);
            showSimplex();
        }
    }

    public void newTaskCancel() {
        clearPane();
        showSimplex();
    }

    public void loadFromFile(){
        FileChooser fileChooser = new FileChooser();
        Stage fileStage = new Stage();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        File file = fileChooser.showOpenDialog(fileStage);
        if (file != null) {
            if (SimplexTable.isOkFile(file.getPath())) {
                SimplexTable simplexTable = new SimplexTable(file.getPath());
                simplexView = new SimplexView(simplexTable, false);
                showSimplex();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid file");
                alert.setHeaderText("Неверный файл");
                alert.setContentText("Файл не существует или имеет неверный формат");
                alert.showAndWait();
            }
        }
    }

    public void saveToFile(){
        if (simplexView == null || simplexView.isEmpty()) {
            return;
        }
        FileChooser fileChooser = new FileChooser();
        Stage fileStage = new Stage();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        File file = fileChooser.showSaveDialog(fileStage);
        if (file != null) {
            simplexView.getProblem().writeToFile(file.getPath());
        }
    }
}