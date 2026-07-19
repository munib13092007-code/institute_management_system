package files;

import javafx.collections.*;

public class Hostel {
    private String name;
    private ObservableList<Room> rooms= FXCollections.observableArrayList();
    private ObservableList<Student> students= FXCollections.observableArrayList();
    private int totalOccupancy;
    private int occupancyPerRoom;
    private double hostelFee;
    public Hostel(String name,int numberOfRooms,int occupancyInEachRoom,double feePerAccomodation){
        this.name=name;
        this.occupancyPerRoom=occupancyInEachRoom;
        totalOccupancy=numberOfRooms*occupancyInEachRoom;
        while(rooms.size()<numberOfRooms)
        rooms.add(new Room(rooms.size()+1, occupancyInEachRoom));
        hostelFee=feePerAccomodation;
    }
    public void addStudent(Student student){
        int index=0;
        while(index<rooms.size()){
            if(rooms.get(index).getAvailability()>0){
                rooms.get(index).allotToStudent(student, this.name);
                students.add(student);
                break;
            }
            index++;
        }
    }
    public String getName(){return name;}
    public double getFee(){return hostelFee;}
    public int getOccupancyPerRoom(){return occupancyPerRoom;}
    public int getAvailableAccomodations(){return totalOccupancy-students.size();}
    public int getTotalAccomodations(){return students.size();}
    public int getNumberOfRooms(){return rooms.size();}


    public void setFee(double feePerAccomodation){
        this.hostelFee=feePerAccomodation;
    }
}
