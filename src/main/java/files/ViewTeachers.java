package files;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class ViewTeachers extends rootNode {
    private Administrator administrator;
    private ObservableList<Institute> institutes;
    private ObservableList<Teacher> teachers;
    private TableView<Teacher> table = new TableView<>();

    private HBox addTeacherForm = new HBox(10);
    private Button addNewTeacher = new Button("Add Teacher");
    private TextField newTeacherName     = new TextField();
    private TextField newTeacherID       = new TextField();
    private TextField newTeacherPassword = new TextField();

    @SuppressWarnings("unchecked")
    public ViewTeachers(ObservableList<Institute> institutes) {
        this.institutes = institutes;
        newTeacherName.setPromptText("Full Name");
        newTeacherID.setPromptText("Login ID");
        newTeacherPassword.setPromptText("Password");

        // Name column
        TableColumn<Teacher, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getName()));
        nameCol.setPrefWidth(200);

        // Login ID column
        TableColumn<Teacher, String> idCol = new TableColumn<>("Login ID");
        idCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getID()));
        idCol.setPrefWidth(180);

        // Assigned courses
        TableColumn<Teacher, String> coursesCol = new TableColumn<>("Assigned Courses");
        coursesCol.setCellValueFactory(d ->
            new SimpleStringProperty(String.join(", ", d.getValue().getCoursesNames())));
        coursesCol.setPrefWidth(300);

        table.getColumns().addAll(nameCol, idCol, coursesCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefHeight(400);
        table.setPlaceholder(new Label("No teachers added yet."));

        addContent(table);

        addTeacherForm.getChildren().addAll(
            new Label("Name:"), newTeacherName,
            new Label("Login ID:"), newTeacherID,
            new Label("Password:"), newTeacherPassword,
            addNewTeacher);
        addTeacherForm.setAlignment(Pos.CENTER);
        addTeacherForm.setPadding(new Insets(10, 0, 0, 0));

        addBottom(new Label("ADD A NEW TEACHER"));
        addBottom(addTeacherForm);

        addNewTeacher.setOnAction(e -> {
            String name = newTeacherName.getText().trim();
            String id   = newTeacherID.getText().trim();
            String pwd  = newTeacherPassword.getText().trim();
            if (name.isEmpty() || id.isEmpty() || pwd.isEmpty()) return;
            teachers.add(new Teacher(name, id, pwd));
            if (institutes != null) DatabaseManager.saveAll(institutes);
            newTeacherName.clear();
            newTeacherID.clear();
            newTeacherPassword.clear();
        });
    }

    public void setUser(Administrator administrator) {
        this.administrator = administrator;
        teachers = administrator.getInstitute().getEducationDivision().getTeachers();
        table.setItems(teachers);
    }
}
