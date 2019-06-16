package blood.connect.kesha.firebasefcm;

/**
 * Created by kesha on 06-06-2018.
 */

public class User {
    public String name;
    public String bloodtype;
    public String username;
    public String email;
    public String pass;
    public int Donator;
    public String mobo;
    String city;
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public String getUsername()
    {
        return username;
    }

    public String getEmail()
    {
        return email;
    }
    public String getPass() {
        return pass;
    }
    public User(String name,String bloodtype,String username, String email, String pass,int Donator, String mobo,String city) {
        this.name=name;
        this.bloodtype=bloodtype;
        this.username = username;
        this.email = email;
        this.pass=pass;
        this.Donator=Donator;
        this.mobo=mobo;
        this.city=city;
    }

}