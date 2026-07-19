package files;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class AdministratorDashboard extends rootNode {
    private Administrator administrator;
    private ObservableList<Institute> institutes;

    private Label nameLabel          = new Label();
    private Label instituteNameLabel = new Label();
    private Label totalStudents      = new Label();
    private Label totalTeachers      = new Label();
    private Label totalCourses       = new Label();
    private Label totalApplicants    = new Label();
    private GridPane center = new GridPane();

    private HBox addNewAdministrator = new HBox(8);
    private Button addAdministrator  = new Button("Add Administrator");
    private TextField newAdminName     = new TextField();
    private TextField newAdminID       = new TextField();
    private TextField newAdminPassword = new TextField();

    private HBox manageFees    = new HBox(8);
    private Button updateFees  = new Button("Update Fees");
    private TextField creditHourFee = new TextField();
    private TextField hostelFee     = new TextField();
    private TextField transportFee  = new TextField();

    public AdministratorDashboard(ObservableList<Institute> institutes) {
        this.institutes = institutes;

        center.setBackground(background2);
        center.setHgap(18);
        center.setVgap(12);
        center.setAlignment(Pos.CENTER_LEFT);
        center.setPadding(new Insets(18));

        Label dashTitle = new Label("INSTITUTE OVERVIEW");
        dashTitle.setStyle("-fx-font-size:16px; -fx-font-weight:bold; -fx-text-fill:#4f46e5;");

        center.add(dashTitle,         0, 0, 2, 1);
        center.add(new Label("Administrator:"),  0, 1); center.add(nameLabel, 1, 1);
        center.add(new Label("Institute:"),      0, 2); center.add(instituteNameLabel, 1, 2);
        center.add(new Label("Students:"),       0, 3); center.add(totalStudents, 1, 3);
        center.add(new Label("Teachers:"),       0, 4); center.add(totalTeachers, 1, 4);
        center.add(new Label("Courses:"),        0, 5); center.add(totalCourses, 1, 5);
        center.add(new Label("Applicants:"),     0, 6); center.add(totalApplicants, 1, 6);

        addContent(center);

        // Add administrator form
        newAdminName.setPromptText("Full Name");
        newAdminID.setPromptText("Login ID");
        newAdminPassword.setPromptText("Password");
        addNewAdministrator.getChildren().addAll(
            new Label("Name:"), newAdminName,
            new Label("Login ID:"), newAdminID,
            new Label("Password:"), newAdminPassword,
            addAdministrator);
        addNewAdministrator.setAlignment(Pos.CENTER);
        addBottom(new Label("ADD A NEW ADMINISTRATOR"));
        addBottom(addNewAdministrator);
        addBottom(new Label(""));

        // Fee management form
        creditHourFee.setPromptText("Credit Hr Fee");
        hostelFee.setPromptText("Hostel Fee");
        transportFee.setPromptText("Transport Fee");
        manageFees.getChildren().addAll(
            new Label("Credit Hr Fee (PKR):"), creditHourFee,
            new Label("Hostel Fee (PKR):"), hostelFee,
            new Label("Transport Fee (PKR):"), transportFee,
            updateFees);
        manageFees.setAlignment(Pos.CENTER);
        addBottom(new Label("MANAGE FEES"));
        addBottom(manageFees);
        addBottom(new Label(""));

        addAdministrator.setOnAction(e -> {
            String n = newAdminName.getText().trim();
            String id = newAdminID.getText().trim();
            String pw = newAdminPassword.getText().trim();
            if (n.isEmpty() || id.isEmpty() || pw.isEmpty()) return;
            administrator.getInstitute().addAdministrator(
                new Administrator(n, id, pw, administrator.getInstitute()));
            newAdminName.clear(); newAdminID.clear(); newAdminPassword.clear();
            if (institutes != null) DatabaseManager.saveAll(institutes);
        });

        updateFees.setOnAction(e -> {
            try {
                double crFee = Double.parseDouble(creditHourFee.getText());
                double hFee  = Double.parseDouble(hostelFee.getText());
                double tFee  = Double.parseDouble(transportFee.getText());
                administrator.getInstitute().getEducationDivision().setCreditHourFee(crFee);
                administrator.getInstitute().getHostelDivision().setHostelFee(hFee);
                administrator.getInstitute().getTransportDivision().setFeePerSeat(tFee);
                if (institutes != null) DatabaseManager.saveAll(institutes);
            } catch (NumberFormatException ignored) {}
        });
    }

    // Legacy no-arg constructor
    public AdministratorDashboard() { this(null); }

    public void setUser(Administrator administrator) {
        this.administrator = administrator;
        nameLabel.setText(administrator.getName());
        instituteNameLabel.setText(administrator.getInstitute().getName());
        totalStudents.setText(String.valueOf(
            administrator.getInstitute().getEducationDivision().getStudents().size()));
        totalTeachers.setText(String.valueOf(
            administrator.getInstitute().getEducationDivision().getTeachers().size()));
        totalCourses.setText(String.valueOf(
            administrator.getInstitute().getEducationDivision().getCourses().size()));
        totalApplicants.setText(String.valueOf(
            administrator.getInstitute().getEducationDivision().getApplicants().size()));

        creditHourFee.setText(String.valueOf(
            administrator.getInstitute().getEducationDivision().getCreditHourFee()));
        hostelFee.setText(String.valueOf(
            administrator.getInstitute().getHostelDivision().getHostelFee()));
        transportFee.setText(String.valueOf(
            administrator.getInstitute().getTransportDivision().getFeePerSeat()));
    }
}
