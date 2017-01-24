package khangit96.tdmuteamfhome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import khangit96.tdmuteamfhome.R;
import khangit96.tdmuteamfhome.model.House;

/**
 * Created by Administrator on 1/1/2017.
 */

public class CustomListHouseArea extends RecyclerView.Adapter<CustomListHouseArea.MyViewHolder> {

    List<House> houseList;
    List<Integer> imgList;
    List<String> distanceList;
    Context context;

    public CustomListHouseArea(Context context, List<House> houseList,
                               List<Integer> imgList, List<String> distanceList) {
        this.context = context;
        this.houseList = houseList;
        this.imgList = imgList;
        this.distanceList = distanceList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_house_area, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        House house = houseList.get(position);

        holder.tvHouseName.setText(house.tenChuHo);
        holder.tvHouseAddress.setText(house.diaChi);
        holder.tvHouePrice.setText(house.giaPhong +" vnđ/tháng");
        holder.tvHouseDistance.setText(distanceList.get(position));
        holder.img.setImageResource(imgList.get(position));
    }

    @Override
    public int getItemCount() {
        return houseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvHouseName, tvHouePrice, tvHouseDistance, tvHouseAddress;
        ImageView img;

        public MyViewHolder(View view) {
            super(view);
            tvHouseName = (TextView) view.findViewById(R.id.tvHouseName);
            tvHouePrice = (TextView) view.findViewById(R.id.tvHousePrice);
            tvHouseDistance = (TextView) view.findViewById(R.id.tvHouseDistance);
            tvHouseAddress = (TextView) view.findViewById(R.id.tvHouseAddress);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }

}
