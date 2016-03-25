package musicapp.khangit.smartwaterbottle;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.achartengine.GraphicalView;

import java.util.ArrayList;


/**
 * Created by Administrator on 3/16/2016.
 */
public class Statistic extends android.support.v4.app.Fragment {
    private GraphicalView mChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_statistic, container, false);
      //  createChart(rootView);
        Chart(rootView);
        return rootView;
    }

 /*   private void createChart(View rootview) {
        //Khởi tạo biểu đồ gồm 5 giá trị thời gian
        int count = 5;
        Date[] dt = new Date[5];
        for (int i = 0; i < count; i++) {
            GregorianCalendar gc = new GregorianCalendar(2012, 10, i + 1);
            dt[i] = (Date) gc.getTime();
        }

        //Mảng giá trị đầu vào
        int[] visits = {2000, 2500, 2700, 2100, 2800};
        int[] Likes = {2200, 2700, 2900, 2800, 3200};

        // Khởi tạo TimeSeries là lượt Visits

        TimeSeries visitsSeries = new TimeSeries("Visits");

        // Khởi tạo TimeSeries là lượt Likes
        TimeSeries LikesSeries = new TimeSeries("Likes");

        // Thêm dữ liệu đồng loạt vào lượt Visits and lượt Likes
        for (int i = 0; i < dt.length; i++) {

            visitsSeries.add(dt[i], visits[i]);
            LikesSeries.add(dt[i], Likes[i]);
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
       multiRenderer.setLegendHeight(200);
        multiRenderer.setYAxisAlign(Paint.Align.RIGHT, 0);
        multiRenderer.setYLabelsAlign(Paint.Align.LEFT);//Chữ nằm về phía bên phải của cột

        multiRenderer.setXLabelsColor(Color.CYAN);//Màu sắc cho chữ trục X
        multiRenderer.setYTitle("Count");//Title trục Y
        multiRenderer.setZoomButtonsVisible(false);//Không cho phép zoom nút button
        multiRenderer.setApplyBackgroundColor(true);
        // Thêm visitsRenderer and LikesRenderer vào multipleRenderer
        multiRenderer.addSeriesRenderer(visitsRenderer);
        multiRenderer.addSeriesRenderer(LikesRenderer);
        multiRenderer.setGridColor(getResources().getColor(R.color.white));
         multiRenderer.setBackgroundColor(getResources().getColor(R.color.white));
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
                    String selectedSeries = "Visits";
                    if (seriesIndex == 0)
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
    }*/
    public void Chart(View rootview){
        LineChart lineChart = (LineChart) rootview.findViewById(R.id.chart);


        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(2f, 3));
        entries.add(new Entry(18f, 4));
        entries.add(new Entry(9f, 5));

        LineDataSet dataset = new LineDataSet(entries, "# of Calls");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        LineData data = new LineData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);

        lineChart.setData(data);
        lineChart.animateY(5000);
    }
}
