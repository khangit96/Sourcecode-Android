package com.hnib.smslater.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.hnib.smslater.R;
import com.hnib.smslater.activity.MainActivity;
import com.hnib.smslater.model.ContactPojo;
import com.hnib.smslater.model.SmsPojo;
import com.hnib.smslater.utils.PreferenceUtils;
import com.hnib.smslater.view.FlowLayout;


/**
 * Created by caucukien on 15/12/2015.
 */
public class DetailSmsFragment extends BaseFragment {

    private EditText etDate;
    private EditText etTime;
    private EditText etMessage;
    private FlowLayout layoutContact;
    private SmsPojo currentSms;

    public DetailSmsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        long id = PreferenceUtils.readFromPreferences(getActivity(), PreferenceUtils.KEY_SMS_SELECTED_ID, -1L);
        if (id > 0) { // case modify
            loadSmsDataBaseOnId(id);
        }



    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.canExit = false;
        ((MainActivity)getActivity()).findViewById(R.id.tabs).setVisibility(View.GONE);
        ((MainActivity)getActivity()).setupBackButtonActionBar();
        ((MainActivity)getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DetailSmsFragment.this.isVisible()){
                    popBackStack();
                }

            }
        });
        //disable swipe
        ((MainActivity)getActivity()).viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_detail_sms, container, false);
        layoutContact = (FlowLayout) view.findViewById(R.id.layout_list_contact);
        etDate = (EditText) view.findViewById(R.id.et_date);
        etTime = (EditText) view.findViewById(R.id.et_time);
        etMessage = (EditText) view.findViewById(R.id.et_message);
        initDataViewAfterLoadDataBase();

        return view;
    }

    private void loadSmsDataBaseOnId(long id) {

        currentSms = new Select()
                .from(SmsPojo.class)
                .where("ID = ?", id)
                .executeSingle();

    }

    private void initDataViewAfterLoadDataBase() {
        if (currentSms != null) {
            etDate.setText(currentSms.date.split(" ")[1]);
            etTime.setText(currentSms.time);
            etMessage.setText(currentSms.content);
            for (ContactPojo contactPojo : currentSms.getContactPojos()) {
                addContactToLayout(contactPojo);
            }
        }
    }

    private void addContactToLayout(final ContactPojo contact) {
        //init textview

        final TextView textview = new TextView(getActivity());
        textview.setLayoutParams(new FlowLayout.LayoutParams(
                FlowLayout.LayoutParams.WRAP_CONTENT,
                FlowLayout.LayoutParams.WRAP_CONTENT));
        textview.setPadding(10, 10, 10, 10);

        textview.setText(contact.chooseToDisplayNameorNumber());

        textview.setTextColor(Color.WHITE);
        textview.setTypeface(textview.getTypeface(), Typeface.BOLD);
        textview.setBackgroundResource(R.drawable.round_border_color_primary);
        textview.setGravity(Gravity.CENTER);
        layoutContact.addView(textview);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_edit) {
            editSelectedSms();
            return true;
        }
        if (id == R.id.action_delete) {
            showDialogConfirmDelete();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void editSelectedSms() {
        int selected = ((MainActivity)getActivity()).viewPager.getCurrentItem();
        if(selected == 0){
            Log.d("binh", "selected fragment position = 0");
            startFragment(R.id.root_frame, new ComposeSmsFragment());
        }else{
            Log.d("binh", "selected fragment position = 1");
            startFragment(R.id.root_frame2, new ComposeSmsFragment());
        }

    }

    private void deleteSelectedSms() {
        currentSms.delete();
        int posTab = ((MainActivity)getActivity()).viewPager.getCurrentItem();
        MainActivity.tabPosition = posTab;
        startMainActivity();

    }

    private void showDialogConfirmDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
        builder.setMessage(getResources().getString(R.string.alert_confirm_remove));
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteSelectedSms();
                ((MainActivity) getActivity()).showSnackBar(getResources().getString(R.string.alert_remove_successful));
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

}
