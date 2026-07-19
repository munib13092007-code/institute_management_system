package files;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class StudentDashboard extends rootNode {
    private Student student;
    private ObservableList<Institute> institutes;

    private Label name           = new Label();
    private Label transportStatus = new Label();
    private Label hostelStatus    = new Label();
    private Label statusLabel     = new Label();

    private Button transport = new Button("Apply for Transport");
    private Button hostel    = new Button("Apply for Hostel");

    private TableView<Course> coursesTable = new TableView<>();

    @SuppressWarnings("unchecked")
    public StudentDashboard(ObservableList<Institute> institutes) {
        this.institutes = institutes;

        // Profile info area
        VBox infoBox = new VBox(8, name, transportStatus, hostelStatus, statusLabel);
        infoBox.setPadding(new Insets(10));

        // Enrolled courses table
        TableColumn<Course, String> cNameCol = new TableColumn<>("Course");
        cNameCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getName()));
        cNameCol.setPrefWidth(200);

        TableColumn<Course, String> cHrsCol = new TableColumn<>("Credit Hours");
        cHrsCol.setCellValueFactory(d ->
            new SimpleStringProperty(String.valueOf((int) d.getValue().getCreditHours())));
        cHrsCol.setPrefWidth(120);

        TableColumn<Course, String> cTeacherCol = new TableColumn<>("Teacher");
        cTeacherCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTeacherName()));
        cTeacherCol.setPrefWidth(200);

        coursesTable.getColumns().addAll(cNameCol, cHrsCol, cTeacherCol);
        coursesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        coursesTable.setPrefHeight(280);
        coursesTable.setPlaceholder(new Label("No courses enrolled yet."));

        Label coursesHeader = new Label("ENROLLED COURSES");
        coursesHeader.setStyle("-fx-font-size:14px; -fx-font-weight:bold; -fx-text-fill:#4f46e5;");

        addContent(infoBox, coursesHeader, coursesTable);
        addBottom(transport, hostel);

        hostel.setOnAction(e -> {
            boolean success = student.getHostelAccomodation();
            statusLabel.setText(success
                ? "Hostel accommodation allotted successfully!"
                : "Failed to allot hostel. No vacancy or no hostels available.");
            if (success && institutes != null) DatabaseManager.saveAll(institutes);
            this.setUser(student);
        });

        transport.setOnAction(e -> {
            VBox rightSide = (VBox) getRight();
            rightSide.getChildren().clear();
            rightSide.getChildren().add(new Label("CHOOSE A ROUTE"));
            if (student.getInstitute().getTransportDivision().getRoutes().isEmpty()) {
                statusLabel.setText("No transport routes available.");
                return;
            }
            for (Route r : student.getInstitute().getTransportDivision().getRoutes()) {
                Button btn = new Button(r.getStopsString() + " (" + r.getAvailableSeats() + " seats)");
                btn.setWrapText(true);
                btn.setMaxWidth(250);
                rightSide.getChildren().add(btn);
                btn.setOnAction(f -> {
                    boolean success = student.availTransport(r);
                    statusLabel.setText(success
                        ? "Transport seat availed successfully!"
                        : "Failed to avail transport. Seats full.");
                    if (success && institutes != null) DatabaseManager.saveAll(institutes);
                    rightSide.getChildren().clear();
                    this.setUser(student);
                });
            }
        });
    }

    // Legacy no-arg constructor
    public StudentDashboard() { this(null); }

    public void setUser(Student student) {
        this.student = student;
        name.setText("Name: " + student.getName() + "   |   ID: " + student.getID());
        transportStatus.setText("Transport: " + student.getTranportStatus());
        hostelStatus.setText("Hostel: " + student.getHostelStatus());
        coursesTable.setItems(student.getEnrolledCourses());

        VBox rightSide = (VBox) getRight();
        rightSide.getChildren().clear();

        transport.setVisible(!student.getTranportValue());
        hostel.setVisible(!student.getHostelValue());
    }
}
