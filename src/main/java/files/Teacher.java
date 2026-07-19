package files;


 import javafx.collections.*;

public class Teacher extends Person{
    
    private double totalCreditHoursAssigned;
    private ObservableList<Course> courses = FXCollections.observableArrayList();
    public Teacher(String Name, String Id,String Password) {
        super(Name,Id,Password);
        totalCreditHoursAssigned=0;
    }

    public double getTotalCreditHoursAssigned() {
        return totalCreditHoursAssigned;
    }

    public ObservableList<String> getCoursesNames() {
        ObservableList<String> coursesNames = FXCollections.observableArrayList();
        for (int i = 0; i < courses.size(); i++)
            coursesNames.add(courses.get(i).getName());
        return coursesNames;
    }

    public ObservableList<Course> getAssignedCourses(){return courses;}

    public void teachCourse(Course course) {
        this.totalCreditHoursAssigned += course.getCreditHours();
        courses.add(course);
    }
}
