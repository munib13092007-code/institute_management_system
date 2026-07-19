package files;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class StudentPage extends rootNode {
    private Student student;
    private ObservableList<Institute> institutes;
    private StudentDashboard dashBoard;
    private EnrollCoursePage enrollCourse;
    private ViewFee viewFee;

    private Button dashboard    = new Button("Dashboard");
    private Button enrollcourse = new Button("Enroll a Course");
    private Button viewfee      = new Button("View Fee");
    private Label nameLabel     = new Label("");

    public StudentPage(ObservableList<Institute> institutes) {
        this.institutes = institutes;
        dashBoard    = new StudentDashboard(institutes);
        enrollCourse = new EnrollCoursePage(institutes);
        viewFee      = new ViewFee();

        addTop(dashboard, enrollcourse, viewfee);
        this.setCenter(dashBoard);

        dashboard.setOnAction(e    -> { dashBoard.setUser(student);    this.setCenter(dashBoard); });
        enrollcourse.setOnAction(e -> { enrollCourse.setUser(student); this.setCenter(enrollCourse); });
        viewfee.setOnAction(e      -> { viewFee.setUser(student);      this.setCenter(viewFee); });
    }

    // Legacy no-arg constructor
    public StudentPage() { this(null); }

    public void setUser(Student student) {
        this.student = student;
        nameLabel.setText("Logged In As: " + student.getName());
        dashBoard.setUser(student);
        enrollCourse.setUser(student);
        viewFee.setUser(student);
    }
}
