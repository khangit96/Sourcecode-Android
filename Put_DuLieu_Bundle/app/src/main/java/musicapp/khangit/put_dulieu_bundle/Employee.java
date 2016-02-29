package musicapp.khangit.put_dulieu_bundle;

import java.io.Serializable;

/**
 * Created by Administrator on 2/25/2016.
 */
public class Employee implements Serializable{

    private String name;
    private String age;

  public Employee(String N,String A){
      this.name=N;
      this.age=A;
  }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
