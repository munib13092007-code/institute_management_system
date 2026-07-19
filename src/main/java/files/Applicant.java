package files;

public class Applicant extends Person{
    private int lastGradeObtainedMarks;
    private int lastGradeTotalMarks;
    private double lastGradePercentage;
    private int meritPosition;
    private boolean admissionStatus;
    private String allotedID;
    public String studentPassword;
    public Applicant(String Name,String Password,int ObtMarks,int TotalMarks){
        super(Name,Name,Password);
        lastGradeObtainedMarks=ObtMarks;
        lastGradeTotalMarks=TotalMarks;
        lastGradePercentage=(double) lastGradeObtainedMarks * 100/lastGradeTotalMarks;
        meritPosition=1;
        admissionStatus=false;
        studentPassword=Password;
    }
    public double getlastGradePercentage(){
        return lastGradePercentage;
    }
    public int getObtainedMarks(){
        return lastGradeObtainedMarks;
    }
    public int getTotalMarks(){
        return lastGradeTotalMarks;
    }
    public String getAllotedID(){
        return allotedID;
    }
    public String getPassword(){
        return studentPassword;
    }
    public int getMeritPosition(){
        return meritPosition;
    }
    public void setMeritPosition(int meritPosition){
        this.meritPosition=meritPosition;
    }
    public boolean getAdmissionStatus(){
        return admissionStatus;
    }

    public void admit(String Id){
        admissionStatus=true;
        allotedID=Id;
    }
}