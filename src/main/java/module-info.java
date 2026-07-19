module files {
    requires javafx.controls;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens files to javafx.fxml;
    exports files;
}
