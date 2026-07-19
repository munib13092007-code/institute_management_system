package files;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class TeacherPage extends rootNode{
     private Teacher teacher;
    private Label name=new Label("");
    private GridPane coursesInfo=new GridPane();
    public TeacherPage(){
        coursesInfo.setBackground(background2);
        coursesInfo.setHgap(10);
        coursesInfo.setVgap(10);
        coursesInfo.setPadding(new Insets(5));
        coursesInfo.setGridLinesVisible(true);
        name.setBackground(background2);
        addContent(name);
        Label ab=new Label("COUSES TEACHING");
        ab.setBackground(background2);
        addContent(ab);
        addContent(coursesInfo);
    }

    public void setUser(Teacher teacher){
        this.teacher=teacher;
        name.setText("Name: "+teacher.getName());
        coursesInfo.getChildren().clear();
        coursesInfo.add(new Label("COURSE"),0,0);
        coursesInfo.add(new Label("CREDIT HOURS"),1,0);
        for(int i=0;i< teacher.getCoursesNames().size();i++){
            coursesInfo.add(new Label(teacher.getAssignedCourses().get(i).getName()),0,i+1);
            coursesInfo.add(new Label(""+teacher.getAssignedCourses().get(i).getCreditHours()),1,i+1);
        }
    }
}
