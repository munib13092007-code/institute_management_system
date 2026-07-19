package files;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginPage extends rootNode{
  private ObservableList<Institute> institutes;
  private TextField username = new TextField();
  private PasswordField password = new PasswordField();
  Button loginButton = new Button("Login");
  Button createApplication = new Button("Create a new application");
  private CreateApplicationPage createApplicationPage;
  Button createInstitute = new Button("Register an institute");
  private ObservableList<String> instituteNames = FXCollections.observableArrayList();
  private ComboBox<String> selectInstitute = new ComboBox<>(instituteNames);
  private ObservableList<String> role = FXCollections.observableArrayList("Administrator", "Student", "Teacher",
      "Applicant");
  private ComboBox<String> selectRole = new ComboBox<>(role);
  private Label loginStatus = new Label();

  public LoginPage(ObservableList<Institute> institutes) {
    this.institutes=institutes;
    for (Institute i : institutes) {
      this.instituteNames.add(i.getName());
    }
    createApplicationPage=new CreateApplicationPage(this);
    selectInstitute.setPromptText("Select your Institute");
    selectRole.setPromptText("Login as:");
    selectInstitute.setEditable(false);

    addContent(selectInstitute);
    addContent(new Text(""));
    addContent(selectRole);
    addContent(new Text(""));
    addContent(new Text("Login ID:"));
    addContent(username);
    addContent(new Text(""));
    addContent(new Text("Password:"));
    addContent(password);
    addContent(new Text(""));
    addContent(loginButton);
    addContent(loginStatus);
    addContent(new Text(""));
    addContent(createApplication);

    createApplication.setOnAction(e->{
      if (selectInstitute.getValue() == null) {
        loginStatus.setText("Please select your Institute first.");
        return;
      }
      createApplicationPressed(institutes);
      this.getScene().setRoot(createApplicationPage);
    });

    
    Text message = new Text("This is login page");
    addTop(message);
    addBottom(createInstitute);
  }

  public Person loginPressed(ObservableList<Institute> institutes) {
    Institute selectedInstitute = null;
    for (Institute i : institutes) {
      if (i.getName().equals(selectInstitute.getValue()))
        selectedInstitute = i;
    }
    if (selectedInstitute != null) {
      if (selectRole.getValue() != null) {
        boolean userExists = false;
        if (selectRole.getValue().equals("Administrator")) {
          for (Administrator j : selectedInstitute.getAdministrators()) {
            if (j.getID().equals(username.getText())) {
              userExists = true;
              if (j.matchPassword(password.getText())) {
                return j;
              } else {
                loginStatus.setText("Wrong Password");
              }
            }
          }
        } else if (selectRole.getValue().equals("Teacher")) {
          for (Teacher j : selectedInstitute.getEducationDivision().getTeachers()) {
            if (j.getID().equals(username.getText())) {
              userExists = true;
              if (j.matchPassword(password.getText())) {
                return j;
              } else {
                loginStatus.setText("Wrong Password");
              }
            }
          }
        } else if (selectRole.getValue().equals("Student")) {
          for (Student j : selectedInstitute.getEducationDivision().getStudents()) {
            if (j.getID().equals(username.getText())) {
              userExists = true;
              if (j.matchPassword(password.getText())) {
                j.setInstitute(selectedInstitute);
                return j;
              } else {
                loginStatus.setText("Wrong Password");
              }
            }
          }
        } else if (selectRole.getValue().equals("Applicant")) {
          for (Applicant j : selectedInstitute.getEducationDivision().getApplicants()) {
            if (j.getID().equals(username.getText())) {
              userExists = true;
              if (j.matchPassword(password.getText())) {
                return j;
              } else {
                loginStatus.setText("Wrong Password");
              }
            }
          }
        }
        if (!userExists) {
          loginStatus.setText("User not found");
        }
      } else {
        loginStatus.setText("Please select a role");
      }
    } else {
      loginStatus.setText("Please select an institute");
    }
    return null;
  }


  public void clearLogin(){username.setText(null);
    password.setText(null);
    loginStatus.setText(null);
    selectInstitute.getSelectionModel().clearSelection();
    selectInstitute.setValue(null);
    selectInstitute.setPromptText("Select your institute:");
    selectRole.getSelectionModel().clearSelection();
    selectRole.setValue(null);
    selectRole.setPromptText("Login as:");
  }

  public void createApplicationPressed(ObservableList<Institute> institutes){
    Institute selectedInstitute = null;
    for (Institute i : institutes) {
      if (i.getName().equals(selectInstitute.getValue()))
        selectedInstitute = i;
    }    if (selectedInstitute == null) return;
    createApplicationPage.setApplicants(selectedInstitute.getEducationDivision().getApplicants());
    
  
}


  public void addInstitutename(){
    instituteNames.add(institutes.get(institutes.size()-1).getName());
  }


}