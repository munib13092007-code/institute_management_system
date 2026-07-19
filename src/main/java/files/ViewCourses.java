package files;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ViewCourses extends rootNode {
    private Administrator administrator;
    private ObservableList<Course> courses;
    private ObservableList<Teacher> teachers;
    private ObservableList<Institute> institutes;
    private TableView<Course> table = new TableView<>();

    private HBox newCourseForm = new HBox(10);
    private Button addNewCourse    = new Button("Add Course");
    private TextField newCourseName  = new TextField();
    private TextField newCourseCrHrs = new TextField();

    @SuppressWarnings("unchecked")
    public ViewCourses(ObservableList<Institute> institutes) {
        this.institutes = institutes;

        newCourseName.setPromptText("Course Name");
        newCourseCrHrs.setPromptText("Credit Hours");

        // Name column
        TableColumn<Course, String> nameCol = new TableColumn<>("Course Name");
        nameCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getName()));
        nameCol.setPrefWidth(180);

        // Credit hours
        TableColumn<Course, String> crHrCol = new TableColumn<>("Credit Hrs");
        crHrCol.setCellValueFactory(d ->
            new SimpleStringProperty(String.valueOf((int) d.getValue().getCreditHours())));
        crHrCol.setPrefWidth(100);

        // Enrolled students count
        TableColumn<Course, String> studentsCol = new TableColumn<>("Enrolled Students");
        studentsCol.setCellValueFactory(d ->
            new SimpleStringProperty(String.valueOf(d.getValue().getEnrolledStudents().size())));
        studentsCol.setPrefWidth(140);

        // Teacher
        TableColumn<Course, String> teacherCol = new TableColumn<>("Teacher");
        teacherCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTeacherName()));
        teacherCol.setPrefWidth(200);

        // Action column — Assign Teacher button
        TableColumn<Course, Void> actionCol = new TableColumn<>("Action");
        actionCol.setPrefWidth(150);
        actionCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Assign Teacher");
            {
                btn.setOnAction(e -> {
                    Course c = getTableView().getItems().get(getIndex());
                    if (c.getTeacherName().equals("Not Assigned yet") && teachers != null) {
                        VBox rightSide = (VBox) getRight();
                        rightSide.getChildren().clear();
                        rightSide.getChildren().add(new Label("Select Teacher for: " + c.getName()));
                        for (Teacher t : teachers) {
                            Button tb = new Button(t.getName());
                            tb.setOnAction(f -> {
                                c.assignTeacher(t);
                                if (institutes != null) DatabaseManager.saveAll(institutes);
                                table.refresh();
                                rightSide.getChildren().clear();
                            });
                            rightSide.getChildren().add(tb);
                        }
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
                    setGraphic(c.getTeacherName().equals("Not Assigned yet") ? btn : null);
                }
            }
        });

        table.getColumns().addAll(nameCol, crHrCol, studentsCol, teacherCol, actionCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefHeight(400);
        table.setPlaceholder(new Label("No courses added yet."));

        addContent(table);

        newCourseForm.getChildren().addAll(
            new Label("Name:"), newCourseName,
            new Label("Credit Hours:"), newCourseCrHrs,
            addNewCourse);
        newCourseForm.setAlignment(Pos.CENTER);
        newCourseForm.setPadding(new Insets(10, 0, 0, 0));
        addBottom(new Label("ADD A NEW COURSE"));
        addBottom(newCourseForm);

        addNewCourse.setOnAction(e -> {
            try {
                String name = newCourseName.getText().trim();
                double crHrs = Double.parseDouble(newCourseCrHrs.getText().trim());
                if (name.isEmpty()) return;
                courses.add(new Course(name, crHrs));
                newCourseName.clear();
                newCourseCrHrs.clear();
                if (institutes != null) DatabaseManager.saveAll(institutes);
            } catch (NumberFormatException ignored) {}
        });
    }

    // Legacy no-arg constructor
    public ViewCourses() { this(null); }

    public void setUser(Administrator administrator) {
        this.administrator = administrator;
        courses  = administrator.getInstitute().getEducationDivision().getCourses();
        teachers = administrator.getInstitute().getEducationDivision().getTeachers();
        table.setItems(courses);
    }
}
