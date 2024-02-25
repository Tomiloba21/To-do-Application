module com.computerscience333.todo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.computerscience333.todo to javafx.fxml;
    exports com.computerscience333.todo;
}