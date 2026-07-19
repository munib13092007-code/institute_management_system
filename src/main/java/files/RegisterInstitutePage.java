package files;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegisterInstitutePage extends rootNode{
    Button registerInstitute = new Button("Register");
    Button cancel = new Button("Cancel");
    private TextField instituteName=new TextField();
    private TextField adminName=new TextField();
    private TextField adminId=new TextField();
    private TextField adminPassword=new TextField();
    private Label nameLabel=new Label("Name");
    private Label adminNameLabel=new Label("Admin Name");
    private Label adminIDLabel=new Label("Admin ID");
    private Label passwordLabel=new Label("Admin Password");
    public RegisterInstitutePage(ObservableList<Institute> institutes,LoginPage loginPage){
        addContent(nameLabel,instituteName,adminNameLabel,adminName,adminIDLabel,adminId,passwordLabel,adminPassword,registerInstitute,cancel);

        registerInstitute.setOnAction(e->{
            institutes.add(new Institute(instituteName.getText(),institutes.size(),adminName.getText(),adminId.getText(),adminPassword.getText()));
            DatabaseManager.saveAll(institutes);
            loginPage.addInstitutename();
            this.getScene().setRoot(loginPage);
        });
        cancel.setOnAction(e->{this.getScene().setRoot(loginPage);});
    }
}
