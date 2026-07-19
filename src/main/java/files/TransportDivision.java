package files;

import javafx.collections.*;

public class TransportDivision{
    private ObservableList<Route> availableRoutes=FXCollections.observableArrayList();

    private double feePerSeat=0;

    public void addRoute(Route route){
        availableRoutes.add(route);
    }
    public ObservableList<Route> getRoutes(){
        return availableRoutes;
    }

    public double getFeePerSeat(){return feePerSeat;}
    public void setFeePerSeat(double feePerSeat){this.feePerSeat=feePerSeat;}
}
