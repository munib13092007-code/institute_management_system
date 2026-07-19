package files;

public class Administrator extends Person{
    private Institute institute;

    public Administrator(String Name,String Id, String Password,Institute institute) {
        super(Name,Id,Password);
        this.institute=institute;
    }

    public void admitApplicant(Applicant applicant,String allotId){
        applicant.admit(allotId);
        institute.getEducationDivision().addStudent(new Student(applicant.getName(), allotId, applicant.getPassword()));
    }

    public void addTeacher(String Name, String Id,String Password){
        institute.getEducationDivision().addTeacher(new Teacher(Name,Id,Password));
    }

    public void addCourse(String name,double crHrs){
        institute.getEducationDivision().addCourse(new Course(name,crHrs));
    }

    public Institute getInstitute(){return institute;}

    public void setInstitute(Institute institute){
        this.institute=institute;
    }

}
