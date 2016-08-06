package khangit96.demoreclyerviewcardview;

/**
 * Created by Administrator on 7/7/2016.
 */
public class Item {
    public String name;
    public Integer age;

    public Item(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
