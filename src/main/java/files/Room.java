package files;

import javafx.collections.*;

public class Room {
    private int roomNumber;
    private int numberOfBeds;
    private ObservableList<Student> accomodatingStudents=FXCollections.observableArrayList();

    public Room(int roomNumber,int numberOfBeds){
        this.roomNumber=roomNumber;
        this.numberOfBeds=numberOfBeds;
    }

    public void allotToStudent(Student student, String hostelName){
        if(accomodatingStudents.size()<numberOfBeds){
            accomodatingStudents.add(student);
            student.setRoomNumber("Room " + this.roomNumber + " in " + hostelName);
        }
    }

    public int getRoomNumber(){return roomNumber;}
    public int getAvailability(){return numberOfBeds-accomodatingStudents.size();}
    public ObservableList<Student> getAccomodatingStudents(){return accomodatingStudents;}

}
