package khangit96.demogson3;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 7/17/2016.
 */
public class Data {
    public String address;
    @SerializedName("person")
    List<Person> personList;
}
