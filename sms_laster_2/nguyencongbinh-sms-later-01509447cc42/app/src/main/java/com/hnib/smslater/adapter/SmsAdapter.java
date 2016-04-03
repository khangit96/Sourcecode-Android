package com.hnib.smslater.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.hnib.smslater.R;
import com.hnib.smslater.activity.MainActivity;
import com.hnib.smslater.application.AppApplication;
import com.hnib.smslater.model.ContactPojo;
import com.hnib.smslater.model.SmsPojo;
import com.hnib.smslater.utils.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caucukien on 14/12/2015.
 */
public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.FixtureViewHolder> {

    public List<SmsPojo> smsPojos = new ArrayList<>();
    public final String TAG = SmsAdapter.class.getSimpleName();
    private Context context;


    public static class FixtureViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_time, tv_name, tv_message;
        public ImageView img_sms;
        public ImageView img_delete;

        FixtureViewHolder(View itemView) {
            super(itemView);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_message = (TextView) itemView.findViewById(R.id.tv_message);
            img_sms = (ImageView) itemView.findViewById(R.id.img_sms);
            img_delete = (ImageView) itemView.findViewById(R.id.img_delete);

        }


    }

    public void setSmsPojos(List smsPojos) {

        this.smsPojos = smsPojos;
    }

    @Override
    public FixtureViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.row_sms, viewGroup, false);
        final FixtureViewHolder holder = new FixtureViewHolder(v);

        return holder;
    }


    @Override
    public void onBindViewHolder(FixtureViewHolder holder, final int position) {
        final SmsPojo smsPojo = smsPojos.get(position);
        StringBuilder timeBuilder = new StringBuilder();
        timeBuilder.append(smsPojo.time + " | " + smsPojo.date);
        String currentDay = CommonUtils.getCurrentDateTime().split(" ")[0];
        if (currentDay.equals(smsPojo.date.split(" ")[1])) {
            timeBuilder.append(" (Today)");
        }
        int dayBetween = CommonUtils.getDayBetweenTwoDate(currentDay, smsPojo.date.split(" ")[1]);
        if (dayBetween == 1) {
            timeBuilder.append(" (Tomorrow)");
        }
        holder.tv_time.setText(timeBuilder.toString());


        StringBuilder stringBuilder = new StringBuilder();
        List<ContactPojo> contactPojos = smsPojo.getContactPojos();
        for (ContactPojo contactPojo : contactPojos) {
            String display = contactPojo.toString();
            if (stringBuilder.length() > 30) {
                stringBuilder.append("...");
                break;
            }
            stringBuilder.append(display + "; ");
        }
        holder.tv_name.setText(stringBuilder);
        if (smsPojo.content.length() > 60) {
            holder.tv_message.setText(smsPojo.content.substring(0, 60) + "...");
        } else {
            holder.tv_message.setText(smsPojo.content);
        }

        if (smsPojo.status == 0) {
            Picasso.with(context).load(R.drawable.ic_clock).fit().into(holder.img_sms);
        } else if (smsPojo.status == 1) {
            Picasso.with(context).load(R.drawable.ic_sent).fit().into(holder.img_sms);
        } else {
            Picasso.with(context).load(R.drawable.ic_failed).fit().into(holder.img_sms);
        }
        Picasso.with(context).load(R.drawable.ic_remove).fit().into(holder.img_delete);

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("binh", "deleted sms at position:" + position);
                showDialogConfirmDelete();

            }

            private void deleteSelectedSMS(int position) {

                smsPojos.remove(position);
                setSmsPojos(smsPojos);
                notifyDataSetChanged();
                new Delete().from(SmsPojo.class).where("Id = ?", smsPojo.getId()).executeSingle();
                for (SmsPojo smsPojo1 : smsPojos) {
                    Log.d("binh", "smsPojos adapter content: " + smsPojo1.content);
                }
                ((MainActivity) context).showSnackBar(AppApplication.getAppContext().getResources().getString(R.string.alert_remove_successful));
                if(smsPojos.size()==0){
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                }
            }

            private void showDialogConfirmDelete() {

                Log.d("binh", "position: " + position);
                Log.d("binh", "sms id: " + smsPojo.getId());
                Log.d("binh", "sms content: " + smsPojo.content);

                AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
                builder.setMessage(AppApplication.getAppContext().getResources().getString(R.string.alert_confirm_remove));
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteSelectedSMS(position);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }

        });

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        if (smsPojos != null) {
            return smsPojos.size();
        } else {
            return 0;
        }

    }
}

