package files;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class CreateApplicationPage extends rootNode{
    private ObservableList<Applicant> applicants;
    private Button submit = new Button("Submit");
    private Button cancel = new Button("Cancel");
    private TextField name = new TextField();
    private TextField password = new TextField();
    private TextField ObtMarks = new TextField();
    private TextField TotalMarks = new TextField();
    private GridPane grid = new GridPane();
    private Label confirmation=new Label("Your name is your LOGIN ID");

    public CreateApplicationPage(BorderPane previousPage) {
        grid.add(new Text("Name:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Text("Password:"), 0, 1);
        grid.add(password, 1, 1);
        grid.add(new Text("Obtained marks:"), 0, 2);
        grid.add(ObtMarks, 1, 2);
        grid.add(new Text("Total Marks:"), 0, 3);
        grid.add(TotalMarks, 1, 3);
        grid.add(submit, 0, 5, 2, 1);
        grid.add(cancel, 0, 6, 2, 1);
        submit.setMaxWidth(Double.MAX_VALUE);
        cancel.setMaxWidth(Double.MAX_VALUE);
        grid.setAlignment(Pos.CENTER);
        addContent(grid);
        addBottom(confirmation);

        submit.setOnAction(e -> {
            try {
                String nameText = name.getText();
                String passText = password.getText();
                String obtText = ObtMarks.getText();
                String totText = TotalMarks.getText();
                if (nameText.isEmpty() || passText.isEmpty() || obtText.isEmpty() || totText.isEmpty()) {
                    confirmation.setText("Fields cannot be empty.");
                    return;
                }
                int obt = Integer.parseInt(obtText);
                int tot = Integer.parseInt(totText);
                if (tot <= 0 || obt < 0 || obt > tot) {
                    confirmation.setText("Invalid marks values.");
                    return;
                }
                applicants.add(new Applicant(nameText, passText, obt, tot));
                EducationDivision.setMeritPositions(applicants);
                clear();
                confirmation.setText("Your name is your LOGIN ID");
                this.getScene().setRoot(previousPage);
            } catch (NumberFormatException ex) {
                confirmation.setText("Marks must be valid integers.");
            }
        });

        cancel.setOnAction(e->{
            clear();
            confirmation.setText("Your name is your LOGIN ID");
            this.getScene().setRoot(previousPage);
        });

    }
    public void setApplicants(ObservableList<Applicant> applicants){
        this.applicants=applicants;
    }

    public void clear(){
        name.clear();
        password.clear();
        ObtMarks.clear();
        TotalMarks.clear();
    }
}
