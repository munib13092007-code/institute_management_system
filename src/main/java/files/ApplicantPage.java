package files;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ApplicantPage extends rootNode{
     private Applicant applicant;
     private Label name=new Label();
     private Label percentage=new Label();
     private Label meritPosition= new Label();
     private Label admissionUpdate= new Label();
     private GridPane center=new GridPane();
    public ApplicantPage(){
        center.setBackground(background2);
       
        center.add(name,0,0,2,1);
        center.add(percentage,0,1,2,1);
        center.add(new Text(""),0,2,2,1);
        center.add(meritPosition,0,3,2,1);
        center.add(new Text(""),0,4,2,1);
        center.add(admissionUpdate,0,5,2,1);

        addContent(center);
    
   }

    public void setUser(Applicant applicant){
        this.applicant=applicant;
        name.setText("Name: "+applicant.getName());
        percentage.setText("Last Grade Percentage:"+applicant.getlastGradePercentage());
        meritPosition.setText("Merit Position:"+applicant.getMeritPosition());
        if(applicant.getAdmissionStatus()){
            admissionUpdate.setText("You have been admitted\n You can login using following Id and password:\n"+applicant.getAllotedID());
        }
        else{
           admissionUpdate.setText("You have not been admitted yet.\nKeep checking for further update");
        }
    }
}




