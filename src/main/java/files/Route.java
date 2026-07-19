package files;

import javafx.collections.*;

public class Route {
    private String driverName;
    private String busNumber;
    private int seats;
    private ObservableList<String> stops= FXCollections.observableArrayList();
    private ObservableList<Student> availingStudents= FXCollections.observableArrayList();

    public Route(String driver,String busNum,int availableSeats,String stops){
        driverName=driver;
        busNumber=busNum;
        this.stops.add(stops);
        seats=availableSeats;
    }

    public void addStops(String... stops){
        this.stops.addAll(stops);
    }
    public void changeDriver(String newDriver){
        driverName=newDriver;
    }
    public void changeBus(String newBusNumber){
        busNumber=newBusNumber;
    }
    public void addStudent(Student student){
        if(availingStudents.size()<seats){
            availingStudents.add(student);
        }
    }
    public String getDriver(){return driverName;}
    public String getBusNumber(){return busNumber;}
    public ObservableList<String> getStops(){return stops;}
    public String getStopsString() {
        return String.join(" -> ", stops);
    }
    public int getTotalSeats(){return seats;}
    public int getAvailableSeats(){return seats-availingStudents.size();}
    public int getCurrentSeat(){return availingStudents.size();}

    

}
