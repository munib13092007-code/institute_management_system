package files;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AdministratorPage extends rootNode {
    Administrator administrator;
    private ObservableList<Institute> institutes;

    private Button dashboard       = new Button("Dashboard");
    private Button viewapplicants  = new Button("View Applicants");
    private Button viewteachers    = new Button("View Teachers");
    private Button viewstudents    = new Button("View Students");
    private Button viewcourses     = new Button("View Courses");
    private Button viewhostels     = new Button("View Hostels");
    private Button viewroutes      = new Button("View Transport Routes");

    private AdministratorDashboard Dashboard;
    private ViewApplicants viewApplicants;
    private ViewTeachers   viewTeachers;
    private ViewStudents   viewStudents;
    private ViewCourses    viewCourses;
    private ViewHostels    viewHostels;
    private ViewTransportRoutes viewTransportRoutes;

    private Label nameLabel = new Label("");

    public AdministratorPage(ObservableList<Institute> institutes) {
        this.institutes = institutes;

        Dashboard           = new AdministratorDashboard(institutes);
        viewApplicants      = new ViewApplicants(institutes);
        viewTeachers        = new ViewTeachers(institutes);
        viewStudents        = new ViewStudents(institutes);
        viewCourses         = new ViewCourses(institutes);
        viewHostels         = new ViewHostels(institutes);
        viewTransportRoutes = new ViewTransportRoutes(institutes);

        addBottom(nameLabel);
        addTop(dashboard, viewapplicants, viewteachers, viewstudents, viewcourses, viewhostels, viewroutes);

        dashboard.setOnAction(e      -> { Dashboard.setUser(administrator);           setCenter(Dashboard); });
        viewapplicants.setOnAction(e -> { viewApplicants.setUser(administrator);       setCenter(viewApplicants); });
        viewteachers.setOnAction(e   -> { viewTeachers.setUser(administrator);         setCenter(viewTeachers); });
        viewstudents.setOnAction(e   -> { viewStudents.setUser(administrator);         setCenter(viewStudents); });
        viewcourses.setOnAction(e    -> { viewCourses.setUser(administrator);          setCenter(viewCourses); });
        viewhostels.setOnAction(e    -> { viewHostels.setUser(administrator);          setCenter(viewHostels); });
        viewroutes.setOnAction(e     -> { viewTransportRoutes.setUser(administrator);  setCenter(viewTransportRoutes); });
    }

    // Legacy no-arg constructor
    public AdministratorPage() { this(null); }

    public void setUser(Administrator administrator) {
        this.administrator = administrator;
        nameLabel.setText("Logged in as: " + administrator.getName()
            + "   |   Institute: " + administrator.getInstitute().getName());
        Dashboard.setUser(administrator);
        viewApplicants.setUser(administrator);
        viewTeachers.setUser(administrator);
        viewStudents.setUser(administrator);
        viewCourses.setUser(administrator);
        viewHostels.setUser(administrator);
        viewTransportRoutes.setUser(administrator);
        setCenter(Dashboard);
    }
}
