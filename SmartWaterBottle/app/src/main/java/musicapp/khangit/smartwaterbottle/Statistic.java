package musicapp.khangit.smartwaterbottle;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Administrator on 3/16/2016.
 */
public class Statistic extends android.support.v4.app.Fragment {
    private GraphicalView mChart;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_statistic, container, false);
        createChart(rootView);
        return rootView;
    }
    private void createChart(View rootview){
        //Khởi tạo biểu đồ gồm 5 giá trị thời gian
        int count = 5;
        Date[] dt = new Date[5];
        for(int i=0;i<count;i++){
            GregorianCalendar gc = new GregorianCalendar(2012, 10, i+1);
            dt[i] = (Date) gc.getTime();
        }

        //Mảng giá trị đầu vào
        int[] visits = {2000,2500,2700,2100,2800};
        int[] Likes = {2200, 2700, 2900, 2800, 3200};

        // Khởi tạo TimeSeries là lượt Visits
        TimeSeries visitsSeries = new TimeSeries("Visits");

        // Khởi tạo TimeSeries là lượt Likes
        TimeSeries LikesSeries = new TimeSeries("Likes");

        // Thêm dữ liệu đồng loạt vào lượt Visits and lượt Likes
        for(int i=0;i<dt.length;i++){
            visitsSeries.add(dt[i], visits[i]);
            LikesSeries.add(dt[i],Likes[i]);
        }

        //Khởi tạo 1 dataset để quản lý tất cả các giá trị
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        //Thêm tất cả thông tin lượt Visits vào dataset
        dataset.addSeries(visitsSeries);

        //Thêm tất cả thông tin lượt Likes vào dataset
        dataset.addSeries(LikesSeries);

        //Tạo XYSeriesRenderer để tùy chỉnh các giá trị của lượt Visits
        XYSeriesRenderer visitsRenderer = new XYSeriesRenderer();
        visitsRenderer.setColor(Color.RED);//Màu đỏ
        visitsRenderer.setPointStyle(PointStyle.CIRCLE);//Chấm tròm
        visitsRenderer.setFillPoints(true);//Đổ đầy chấm
        visitsRenderer.setLineWidth(2);//Độ rộng dòng
        visitsRenderer.setDisplayChartValues(true);//Cho phép hiển thị giá trị

        //Tạo XYSeriesRenderer để tùy chỉnh các giá trị của lượt Likes
        XYSeriesRenderer LikesRenderer = new XYSeriesRenderer();
        LikesRenderer.setColor(Color.GREEN);
        LikesRenderer.setPointStyle(PointStyle.CIRCLE);
        LikesRenderer.setFillPoints(true);
        LikesRenderer.setLineWidth(2);
        LikesRenderer.setDisplayChartValues(true);

        //Khởi tạo một đối tượng XYMultipleSeriesRenderer để tùy chỉnh biểu đồ theo ý muốn
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();

        //Thiết lập title
        multiRenderer.setChartTitle("Visits vs Likes Chart");
        multiRenderer.setXTitle("Days");//Title trục X

        multiRenderer.setYAxisAlign(Paint.Align.RIGHT, 0);
        multiRenderer.setYLabelsAlign(Paint.Align.LEFT);//Chữ nằm về phía bên phải của cột

        multiRenderer.setXLabelsColor(Color.CYAN);//Màu sắc cho chữ trục X
        multiRenderer.setYTitle("Count");//Title trục Y
        multiRenderer.setZoomButtonsVisible(false);//Không cho phép zoom nút button

        // Thêm visitsRenderer and LikesRenderer vào multipleRenderer
        multiRenderer.addSeriesRenderer(visitsRenderer);
        multiRenderer.addSeriesRenderer(LikesRenderer);

        //Lấy đối tượng LinearLayout từ XML để sử dụng
        LinearLayout chartContainer = (LinearLayout) rootview.findViewById(R.id.chart_container);

        //Tạo biểu đồ
        mChart = (GraphicalView) ChartFactory.getTimeChartView(getContext(), dataset, multiRenderer, "dd-MMM-yyyy");

        multiRenderer.setClickEnabled(true);//Cho phép click
        multiRenderer.setSelectableBuffer(10);//Thiết lập vùng đệm

        //Thiết lập một sự kiện lắng nghe từ giao diện (không cần đoạn này cũng đc)
        mChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Format formatter = new SimpleDateFormat("dd-MMM-yyyy");
                SeriesSelection seriesSelection = mChart.getCurrentSeriesAndPoint();
                if (seriesSelection != null) {
                    int seriesIndex = seriesSelection.getSeriesIndex();
                    String selectedSeries="Visits";
                    if(seriesIndex==0)
                        selectedSeries = "Visits";
                    else
                        selectedSeries = "Likes";
                    long clickedDateSeconds = (long) seriesSelection.getXValue();
                    Date clickedDate = new Date(clickedDateSeconds);
                    String strDate = formatter.format(clickedDate);
                    int amount = (int) seriesSelection.getValue();
                    //Hiển thị toast
                    Toast.makeText(getContext(), selectedSeries + " on " + strDate + " : " + amount, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Add cái biểu đồ này vào LinearLayout của xml
        chartContainer.addView(mChart);
    }
}
