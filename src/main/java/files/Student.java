package files;

import javafx.collections.*;

public class Student extends Person {

    private Institute institute;
    private double totalCreditHoursEnrolled;
    private ObservableList<Course> courses = FXCollections.observableArrayList();
    private boolean transportStatus;
    private String seatNumber;
    private boolean hostelStatus;
    private String roomNumber;
    private double dueFee;
    private ObservableList<FeeEntity> fee = FXCollections.observableArrayList();

    public Student(String Name, String Id, String Password) {
        super(Name, Id, Password);
        totalCreditHoursEnrolled = 0;
        hostelStatus = false;
        transportStatus = false;
        seatNumber = "";
        roomNumber = "";
        dueFee=0;
    }

    public double getTotalCreditHoursEnrolled() {
        return totalCreditHoursEnrolled;
    }

    public ObservableList<String> getCoursesNames() {
        ObservableList<String> coursesNames = FXCollections.observableArrayList();
        for (int i = 0; i < courses.size(); i++)
            coursesNames.add(courses.get(i).getName());
        return coursesNames;
    }

    public ObservableList<Course> getEnrolledCourses() {
        return courses;
    }

    public ObservableList<Course> getAvailableCourses() {
        return institute.getEducationDivision().getCourses();
    }

    public double getDueFee(){
        dueFee=0;
        for(int i=0;i<fee.size();i++){
            dueFee+=fee.get(i).getAmount();
        }
        return dueFee;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }
    public Institute getInstitute(){return institute;}

    /** Normal enroll — adds fee entry. */
    public void enrollCourse(Course course) {
        if(!courses.contains(course)){
                courses.add(course);
                course.enrollStudent(this);
                this.addFeeEntity(new FeeEntity(course.getName()+" Enrollment", institute.getEducationDivision().getCreditHourFee()*course.getCreditHours()));
        }
    }

    /** Restore enroll from DB — no fee side-effect. */
    public void enrollCourseRestore(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
            course.enrollStudent(this);
        }
    }

    public String getHostelStatus() {
        if (hostelStatus)
            return roomNumber;
        else
            return "Not accommodating hostel";
    }

    public String getTranportStatus() {
        if (transportStatus)
            return seatNumber;
        else
            return "Not availing transport";
    }

    public boolean availTransport(Route route) {
        for (int i = 0; i < institute.getTransportDivision().getRoutes().size(); i++) {
            if (institute.getTransportDivision().getRoutes().get(i).equals(route)) {
                if (institute.getTransportDivision().getRoutes().get(i).getAvailableSeats() > 0) {
                    institute.getTransportDivision().getRoutes().get(i).addStudent(this);
                    seatNumber = "" + route.getCurrentSeat() + " in bus number " + route.getBusNumber();
                    this.addFeeEntity(new FeeEntity("Transport Fee", institute.getTransportDivision().getFeePerSeat()));
                    transportStatus = true;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean getTranportValue(){return transportStatus;}
    public boolean getHostelValue(){return hostelStatus;}
    public String getSeatNumber(){return seatNumber;}
    public String getRoomNumber(){return roomNumber;}

    /** Restore transport status from DB without fee side-effect. */
    public void restoreTransportStatus(String seat) {
        transportStatus = true;
        seatNumber = seat == null ? "" : seat;
    }

    /** Restore hostel status from DB without fee side-effect. */
    public void restoreHostelStatus(String room) {
        hostelStatus = true;
        roomNumber = room == null ? "" : room;
    }

    public boolean getHostelAccomodation() {
        for (int i = 0; i < institute.getHostelDivision().getHostels().size(); i++) {
            if (institute.getHostelDivision().getHostels().get(i).getAvailableAccomodations() > 0) {
                institute.getHostelDivision().getHostels().get(i).addStudent(this);
                fee.add(new FeeEntity("Hostel Fee", institute.getHostelDivision().getHostelFee()));
                hostelStatus = true;
                return true;
            }
        }
        return false;
    }

    public void addFeeEntity(FeeEntity feeEntity) {
        fee.add(feeEntity);
    }
    public ObservableList<FeeEntity> getFeeDetails(){
        return fee;
    }

    public void setRoomNumber(String roomNumber){
        this.roomNumber=roomNumber;
    }
}
