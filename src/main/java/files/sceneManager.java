package files;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class sceneManager extends rootNode {
    private ObservableList<Institute> institutes;
    private Scene scene;
    LoginPage loginPage;
    AdministratorPage administratorPage;
    TeacherPage teacherPage;
    StudentPage studentPage;
    ApplicantPage applicantPage;
    RegisterInstitutePage registerInstitutePage;

    public sceneManager(ObservableList<Institute> institutes) {
        this.institutes = institutes;
        loginPage = new LoginPage(institutes);
        scene = new Scene(loginPage);
        scene.getStylesheets().add(getClass().getResource("/files/style.css").toExternalForm());

        administratorPage = new AdministratorPage(institutes);
        teacherPage       = new TeacherPage();
        studentPage       = new StudentPage(institutes);
        applicantPage     = new ApplicantPage();

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                loginPage.clearLogin();
                scene.setRoot(loginPage);
            }
        });

        registerInstitutePage = new RegisterInstitutePage(institutes, loginPage);
        loginPage.createInstitute.setOnAction(e -> registerInstitutePage.setOnScene(scene));

        loginPage.loginButton.setOnAction(e -> loginPerson(loginPage.loginPressed(institutes)));
    }

    public Scene getTheScene() { return scene; }

    public void loginPerson(Person person) {
        if (person instanceof Administrator) {
            administratorPage.setUser((Administrator) person);
            scene.setRoot(administratorPage);
        } else if (person instanceof Teacher) {
            teacherPage.setUser((Teacher) person);
            teacherPage.setOnScene(scene);
        } else if (person instanceof Student) {
            studentPage.setUser((Student) person);
            studentPage.setOnScene(scene);
        } else if (person instanceof Applicant) {
            applicantPage.setUser((Applicant) person);
            applicantPage.setOnScene(scene);
        }
    }
}
