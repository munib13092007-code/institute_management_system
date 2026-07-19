package files;

import javafx.collections.*;

public class Institute {
    private String name;
    private String instituteCode;
    private ObservableList<Administrator> administrators=FXCollections.observableArrayList();
    private EducationDivision educationDivision=new EducationDivision();
    private HostelDivision hostelDivision=new HostelDivision();
    private TransportDivision transportDivision=new TransportDivision();

    public Institute(String name,int instituteCode,String adminName,String adminId,String adminPassword){
        this.name=name;
        this.instituteCode=String.format("%03d",instituteCode+1);
        administrators.add(new Administrator(adminName, adminId, adminPassword,this));
    }

    public ObservableList<Administrator> getAdministrators(){return administrators;}
    public void addAdministrator(Administrator administrator){
        administrators.add(administrator);
    }

    public String getName(){return name;}
    public String getInstituteCode(){return instituteCode;}

    public EducationDivision getEducationDivision(){return educationDivision;}
    public HostelDivision getHostelDivision(){return hostelDivision;}
    public TransportDivision getTransportDivision(){return transportDivision;}
    
}
