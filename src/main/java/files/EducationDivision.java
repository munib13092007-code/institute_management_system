package files;



import javafx.collections.*;

public class EducationDivision {
    private ObservableList<Student> students=FXCollections.observableArrayList();
    private ObservableList<Teacher> teachers=FXCollections.observableArrayList();
    private ObservableList<Applicant> applicants=FXCollections.observableArrayList();
    private ObservableList<Course> courses=FXCollections.observableArrayList();
    private double creditHourFee=0;
    private double totalFeeCollected;

    public EducationDivision(){
        creditHourFee=0;
        totalFeeCollected=0;
    }

    public double getCreditHourFee(){return creditHourFee;}
    public void setCreditHourFee(double creditHourFee){this.creditHourFee=creditHourFee;}

    public double getTotalFeeCollected(){return totalFeeCollected;}
    public void addTotalFeeCollected(double amount){totalFeeCollected+=amount;}

    public void addEducationalFee(double amount){totalFeeCollected+=amount;};

    public void addStudent(Student student){students.add(student);}
    public ObservableList<Student>  getStudents(){return students;}

    public void addTeacher(Teacher teacher){teachers.add(teacher);}
    public ObservableList<Teacher> getTeachers(){return teachers;}


    public void addCourse(Course course){courses.add(course);}
    public ObservableList<Course> getCourses(){return courses;}


    public void addApplicant(Applicant applicant){applicants.add(applicant);    }
    public ObservableList<Applicant> getApplicants(){return applicants;}

    

    public static void setMeritPositions(ObservableList<Applicant> applicants){
         for(int i=0;i<applicants.size();i++){
            for(int j=i;j<applicants.size();j++){
                if(applicants.get(j).getlastGradePercentage()>applicants.get(i).getlastGradePercentage()){
                    Applicant temp=applicants.get(i);
                    applicants.set(i,applicants.get(j));
                    applicants.set(j,temp);
                }
            }
        }
        for(int i=0;i<applicants.size();i++){
            applicants.get(i).setMeritPosition(i+1);
        }
    }

    


}
