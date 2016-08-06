package khangit96.demofirebase1;

/**
 * Created by Administrator on 7/19/2016.
 */
public class Person {
    public String name;
    public String address;

    public Person() {
      /*Blank default constructor essential for Firebase*/
    }

    //Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
