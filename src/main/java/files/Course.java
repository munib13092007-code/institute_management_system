package files;



import javafx.collections.*;

public class Course {
    private String name;
    private Teacher teacher;
    private double creditHourPerWeek;
    private ObservableList<Student> enrolled = FXCollections.observableArrayList();
    public Course(String Name, double hours) {
        name = Name;
        creditHourPerWeek = hours;
        teacher=(new Teacher("Not Assigned yet","------","------"));
    }

    public String getName() {
        return name;
    }
    public double getCreditHours() {
        return creditHourPerWeek;
    }
    public String getTeacherName() {
        return teacher.getName();
    }
    /** Returns teacher's login ID, or empty string if not assigned. */
    public String getTeacherID() {
        if (teacher.getID().equals("------")) return "";
        return teacher.getID();
    }

    public void enrollStudent(Student student) {
        enrolled.add(student);
    }

    public ObservableList<Student> getEnrolledStudents(){return enrolled;}

    public void assignTeacher(Teacher teacher) {
        this.teacher = teacher;
        teacher.teachCourse(this);
    }

}
