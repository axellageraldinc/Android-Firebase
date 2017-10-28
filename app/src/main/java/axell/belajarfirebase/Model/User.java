package axell.belajarfirebase.Model;

/**
 * Created by axellageraldinc on 10/27/17.
 */

public class User {
    private String id, name;

    public User(){}

    public User(String name){
        this.name = name;
    }
    public User(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
