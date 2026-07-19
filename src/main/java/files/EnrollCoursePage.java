package files;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

public class EnrollCoursePage extends rootNode {
    private Student student;
    private ObservableList<Institute> institutes;
    private TableView<Course> table = new TableView<>();

    @SuppressWarnings("unchecked")
    public EnrollCoursePage(ObservableList<Institute> institutes) {
        this.institutes = institutes;

        TableColumn<Course, String> nameCol = new TableColumn<>("Course Name");
        nameCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getName()));
        nameCol.setPrefWidth(200);

        TableColumn<Course, String> crHrCol = new TableColumn<>("Credit Hours");
        crHrCol.setCellValueFactory(d ->
            new SimpleStringProperty(String.valueOf((int) d.getValue().getCreditHours())));
        crHrCol.setPrefWidth(120);

        TableColumn<Course, String> teacherCol = new TableColumn<>("Teacher");
        teacherCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTeacherName()));
        teacherCol.setPrefWidth(200);

        TableColumn<Course, Void> enrollCol = new TableColumn<>("Action");
        enrollCol.setPrefWidth(120);
        enrollCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Enroll");
            {
                btn.setOnAction(e -> {
                    Course c = getTableView().getItems().get(getIndex());
                    if (student != null) {
                        student.enrollCourse(c);
                        if (institutes != null) DatabaseManager.saveAll(institutes);
                        table.refresh();
                    }
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Course c = getTableView().getItems().get(getIndex());
                    boolean alreadyEnrolled = student != null && student.getEnrolledCourses().contains(c);
                    if (alreadyEnrolled) {
                        Label enrolled = new Label("✓ Enrolled");
                        enrolled.setStyle("-fx-text-fill: #16a34a; -fx-font-weight: bold;");
                        setGraphic(enrolled);
                    } else {
                        setGraphic(btn);
                    }
                }
            }
        });

        table.getColumns().addAll(nameCol, crHrCol, teacherCol, enrollCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefHeight(400);
        table.setPlaceholder(new Label("No courses available."));

        Label header = new Label("AVAILABLE COURSES — Click Enroll to register");
        header.setStyle("-fx-font-size:14px; -fx-font-weight:bold; -fx-text-fill:#4f46e5;");

        addContent(header, table);
    }

    // Legacy no-arg constructor
    public EnrollCoursePage() { this(null); }

    public void setUser(Student student) {
        this.student = student;
        table.setItems(student.getInstitute().getEducationDivision().getCourses());
        table.refresh();
    }
}