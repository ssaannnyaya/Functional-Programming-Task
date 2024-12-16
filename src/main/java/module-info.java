module ru.ac.uniyar.Simplex {
    requires javafx.controls;
    requires javafx.fxml;

    opens ru.ac.uniyar.Simplex to javafx.fxml;
    exports ru.ac.uniyar.Simplex;
}