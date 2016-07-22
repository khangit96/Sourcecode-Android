package khangit96.demointerface;

/**
 * Created by Administrator on 7/22/2016.
 */
public class OnclickListener {
    MyOnclickListener listener;

    public interface MyOnclickListener {
        public void onClick(String text);
    }

    public void getListener(MyOnclickListener listener) {
        this.listener = listener;
    }

    public void showData() {
        if (listener != null) {
            listener.onClick("Nguyễn Hồ Duy Khang");
        }
    }

}
