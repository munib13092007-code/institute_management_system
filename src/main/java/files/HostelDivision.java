package files;

import javafx.collections.*;

public class HostelDivision {
    private double hostelFee;
    private double totalFeeCollected;
    private ObservableList<Hostel> hostels=FXCollections.observableArrayList();


    public HostelDivision(){
        hostelFee=0;
        totalFeeCollected=0;
    }

    public void allotHostelAccomodation(Student student){
        for(int i=0;i<hostels.size();i++){
            if(hostels.get(i).getAvailableAccomodations()>0){
                hostels.get(i).addStudent(student);
                totalFeeCollected+=hostelFee;
                break;
            }
        }

    }


    public void addHostel(Hostel hostel){hostels.add(hostel);}
    public ObservableList<Hostel> getHostels(){return hostels;}

    public void setHostelFee(double newHostelFee){hostelFee=newHostelFee;}
    public double getHostelFee(){return hostelFee;}

    public double getTotalFeeCollected(){return totalFeeCollected;}
    public void addTotalFeeCollected(double amount){totalFeeCollected+=amount;}
}
