package files;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ViewStudents extends rootNode {
    private Administrator administrator;
    private ObservableList<Institute> institutes;
    private TableView<Student> table = new TableView<>();

    @SuppressWarnings("unchecked")
    public ViewStudents(ObservableList<Institute> institutes) {
        this.institutes = institutes;

        // Name column
        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getName()));
        nameCol.setPrefWidth(160);

        // Login ID column
        TableColumn<Student, String> idCol = new TableColumn<>("Login ID");
        idCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getID()));
        idCol.setPrefWidth(180);

        // Enrolled courses
        TableColumn<Student, String> coursesCol = new TableColumn<>("Enrolled Courses");
        coursesCol.setCellValueFactory(d ->
            new SimpleStringProperty(String.join(", ", d.getValue().getCoursesNames())));
        coursesCol.setPrefWidth(220);

        // Hostel status
        TableColumn<Student, String> hostelCol = new TableColumn<>("Hostel Status");
        hostelCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getHostelStatus()));
        hostelCol.setPrefWidth(180);

        // Transport status
        TableColumn<Student, String> transportCol = new TableColumn<>("Transport Status");
        transportCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTranportStatus()));
        transportCol.setPrefWidth(200);

        // Due fee
        TableColumn<Student, String> feeCol = new TableColumn<>("Due Fee (PKR)");
        feeCol.setCellValueFactory(d ->
            new SimpleStringProperty(String.format("%.0f", d.getValue().getDueFee())));
        feeCol.setPrefWidth(120);

        table.getColumns().addAll(nameCol, idCol, coursesCol, hostelCol, transportCol, feeCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefHeight(400);
        table.setPlaceholder(new Label("No students enrolled yet."));

        addContent(table);
    }

    // Legacy constructor (no institutes list)
    public ViewStudents() {
        this(null);
    }

    public void setUser(Administrator administrator) {
        this.administrator = administrator;
        table.setItems(administrator.getInstitute().getEducationDivision().getStudents());
    }
}
