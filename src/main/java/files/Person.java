package files;

public abstract class Person {
    private String name;
    private String loginID;
    private String password;
    public Person(String Name,String loginId,String Password){
        name=Name;
        loginID=loginId;
        password=Password;
    }
    public String getName(){
        return name;
    }
    public String getID(){
        return loginID;
    }
    public String getRawPassword(){
        return password;
    }
    public boolean matchPassword(String Password){
        if(password.equals(Password))
            return true;
        else
            return false;
    }
    public String resetPassword(String CurrentPass,String NewPass){
        if(matchPassword(CurrentPass)){password=NewPass;return "Password reset successfull";}
        else return "Incorrect Current Password.";
    }
}
