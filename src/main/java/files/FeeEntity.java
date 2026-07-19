package files;


public class FeeEntity {
    private String title;
    private double amount;
    public FeeEntity(String title,double amount){
        this.title=title;
        this.amount=amount;
    }
    public String getTitle(){return title;}
    public double getAmount(){return amount;}
}
