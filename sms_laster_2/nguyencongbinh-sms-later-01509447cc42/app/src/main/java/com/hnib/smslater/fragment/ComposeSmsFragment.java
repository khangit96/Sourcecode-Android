package com.hnib.smslater.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.hnib.smslater.R;
import com.hnib.smslater.activity.MainActivity;
import com.hnib.smslater.model.ContactPojo;
import com.hnib.smslater.model.SmsPojo;
import com.hnib.smslater.receiver.SmsAlarmReceiver;
import com.hnib.smslater.utils.AppConstants;
import com.hnib.smslater.utils.CommonUtils;
import com.hnib.smslater.utils.PreferenceUtils;
import com.hnib.smslater.utils.SoundPoolManager;
import com.hnib.smslater.view.FlowLayout;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * Created by caucukien on 15/12/2015.
 */
public class ComposeSmsFragment extends BaseFragment implements OnDateSetListener, TimePickerDialog.OnTimeSetListener, View.OnClickListener {

    private static final String TIME_PATTERN = "HH:mm";
    private static final String DATE_PATTERN = "dd-MM-yyyy";
    private static final int PICK_CONTACT = 100;

    private EditText etDate;
    private EditText etTime;
    private EditText etMessage;
    private AutoCompleteTextView tvSendTo;
    private ImageButton btn_datePicker, btn_timePicker;
    private ImageButton btn_add;
    private Calendar calendar;

    private ArrayList<ContactPojo> arrContact;
    private ContactAdapter mContactAdapter;

    private List<ContactPojo> receivers;

    private FlowLayout layoutListContact;
    private long idSms;
    private SmsPojo currentSms;


    public ComposeSmsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        initDataCurrentSMS();
        receivers = new ArrayList<ContactPojo>();
        arrContact = new ArrayList<ContactPojo>();
        calendar = Calendar.getInstance();


    }

    private void initDataCurrentSMS() {
        // get ID SMS selected, if it less than 0, case is new SMS.
        idSms = PreferenceUtils.readFromPreferences(getActivity(), PreferenceUtils.KEY_SMS_SELECTED_ID, -1L);
        if (idSms > 0) { // case modify
            currentSms = loadSmsDataBaseOnId(idSms);
        } else {
            currentSms = new SmsPojo();//case compose new SMS
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.canExit = false;
        ((MainActivity) getActivity()).findViewById(R.id.tabs).setVisibility(View.GONE);
        ((MainActivity) getActivity()).setupBackButtonActionBar();
        ((MainActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("binh", "home button selected");
                if (ComposeSmsFragment.this.isVisible()) {
                    showDialogConfirmCancel();
                }

            }
        });
        //disable swipe
        ((MainActivity) getActivity()).viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    private void showDialogConfirmCancel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
        builder.setMessage(getResources().getString(R.string.alert_confirm_cancel));
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                popBackStack();
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

    @Override
    public void onPause() {
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_compose_sms, container, false);
        layoutListContact = (FlowLayout) view.findViewById(R.id.layout_list_contact);


        tvSendTo = (AutoCompleteTextView) view.findViewById(R.id.tv_sendto);
        tvSendTo.setThreshold(1);
        mContactAdapter = new ContactAdapter(getActivity(), R.layout.row_contact, arrContact);
        tvSendTo.setAdapter(mContactAdapter);
        etDate = (EditText) view.findViewById(R.id.et_date);
        etDate.setOnFocusChangeListener(onDateFocusChangeListener);
        etDate.setOnClickListener(onDateClickListener);
        etTime = (EditText) view.findViewById(R.id.et_time);
        etTime.setOnFocusChangeListener(onTimeFocusChangeListener);
        etTime.setOnClickListener(onTimeClickListener);
        etMessage = (EditText) view.findViewById(R.id.et_message);
        btn_datePicker = (ImageButton) view.findViewById(R.id.btnDatePicker);
        btn_timePicker = (ImageButton) view.findViewById(R.id.btnTimePicker);
        btn_add = (ImageButton) view.findViewById(R.id.btn_add);
        btn_datePicker.setOnClickListener(this);
        btn_timePicker.setOnClickListener(this);
        btn_add.setOnClickListener(this);

        tvSendTo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                int result = actionId & EditorInfo.IME_MASK_ACTION;
                switch (result) {
                    case EditorInfo.IME_ACTION_DONE:
                        btn_add.performClick(); // add current number
                        break;
                    case EditorInfo.IME_ACTION_NEXT:
                        // next stuff
                        break;
                }
                return true;
            }
        });
        etMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideSoftKeyboard();
                }
            }
        });

        updateDateandTime();
        initDataViewAfterLoadDataBase();

        initAdmob(view);
        return view;
    }

    protected void initAdmob(View view) {
        final AdView adView = (AdView) view.findViewById(R.id.adView);
        if (CommonUtils.isNetWorkAvailable() == false) {
            adView.setVisibility(View.GONE);
            return;
        }
        if (adView != null) {
            if (AppConstants.isAdmobEnabledComposeScreen) {

                adView.setVisibility(View.VISIBLE);
                AdRequest.Builder builder = new AdRequest.Builder();
                AdRequest adRequest = builder.build();
                builder.addTestDevice("F36E25E3C328F38CB722C2DAD93ABA6B");
                builder.addTestDevice("E74FBD30CF340F99B6FD8BF2B2DD4356");
                // Start loading the ad in the background.
                adView.loadAd(adRequest);
            } else {
                adView.setVisibility(View.GONE);
            }
        }
    }

    private SmsPojo loadSmsDataBaseOnId(long id) {

        return new Select()
                .from(SmsPojo.class)
                .where("ID = ?", id)
                .executeSingle();
    }

    private void initDataViewAfterLoadDataBase() {
        if (idSms > 0) {
            etMessage.setText(currentSms.content);
            etDate.setText(currentSms.date.split(" ")[1]);
            etTime.setText(currentSms.time);
            etMessage.setText(currentSms.content);
            receivers = currentSms.getContactPojos();
            for (ContactPojo contactPojo : currentSms.getContactPojos()) {
                addContactToLayout(contactPojo);
            }
        }
    }


    private void updateDateandTime() {
        //DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getActivity());
        //DateFormat timeFormat = android.text.format.DateFormat.getDateFormat(getActivity());

        //android.text.format.DateFormat df = new android.text.format.DateFormat();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN, Locale.US);
        SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.US);

        etDate.setText(dateFormat.format(calendar.getTime()));
        etTime.setText(timeFormat.format(calendar.getTime()));
    }


    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        updateDateandTime();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        updateDateandTime();
    }


    private void clearAutoCompleteText() {
        tvSendTo.setText("");
        tvSendTo.setHint("To:");
    }

    private void addContactToLayout(final ContactPojo contact) {
        //init textview

        final TextView textView = new TextView(getActivity());
        textView.setLayoutParams(new FlowLayout.LayoutParams(
                FlowLayout.LayoutParams.WRAP_CONTENT,
                FlowLayout.LayoutParams.WRAP_CONTENT));
        // check to display name or number
        textView.setText(contact.toString());

        textView.setTextColor(Color.WHITE);
        textView.setBackgroundResource(R.drawable.round_border_color_primary);
        //textView.setBackgroundColor(getResources().getColor(R.color.color_primary));
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(10, 5, 10, 5);
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_cross, 0); //add icon cross to right
        layoutListContact.addView(textView);

        // remove textView when click
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView.getParent() == layoutListContact) {// check to make sure layout is including the textView
                    layoutListContact.removeView(textView);
                    int index = findIndexReceiver(contact);
                    if (index != -1) {
                        receivers.remove(index);
                    }
                    for (ContactPojo contactPojo : receivers) {
                        Log.d("binh", "receiver after remove: name " + contactPojo.name + " number: " + contactPojo.mobileNumber);

                    }
                    Log.d("binh", "--------------------------------");

                }

            }
        });

    }

    public int findIndexReceiver(ContactPojo contact) {
        int index = -1;
        for (int i = 0; i < receivers.size(); i++) {
            if (contact.mobileNumber.equals(receivers.get(i).mobileNumber)) {
                index = i;
            }
        }
        return index;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_compose, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_save) {

            if (validateSMS()) {
                Log.d("binh", "Saving SMS");
                hideSoftKeyboard();
                saveSmsToDatabase();
                setAlarmForCurrentSMS();
                MainActivity.tabPosition = 0;
                startMainActivity();

            } else {
                //play sound error
                SoundPoolManager.getInstance(getActivity()).playSound(1);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class ContactAdapter extends ArrayAdapter<ContactPojo> {

        private ArrayList<ContactPojo> items;

        private int viewResourceId;

        public ContactAdapter(Context context, int viewResourceId, ArrayList<ContactPojo> items) {
            super(context, viewResourceId, items);
            this.items = items;

            this.viewResourceId = viewResourceId;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            final ViewHolder viewHolder;
            if (v == null) {
                viewHolder = new ViewHolder();
                LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(viewResourceId, null);

                viewHolder.tvName = (TextView) v.findViewById(R.id.tvName);
                viewHolder.tvMobileNumber = (TextView) v.findViewById(R.id.tvMobileNumber);
                viewHolder.imgProfile = (ImageView) v.findViewById(R.id.imgProfile);


                v.setTag(viewHolder);

            } else {

                viewHolder = (ViewHolder) v.getTag();

            }
            final ContactPojo contactPojo = items.get(position);
            String type = "Other";
            switch (contactPojo.type) {
                case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                    type = "Home";
                    break;
                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                    type = "Mobile";
                    break;
                case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                    type = "Work";
                    break;
            }

            viewHolder.tvName.setText(contactPojo.name);
            viewHolder.tvMobileNumber.setText(contactPojo.mobileNumber);

            if (contactPojo.uri != null) {
                Uri uri = Uri.parse(contactPojo.uri);
                if (uri != null) {
                    Picasso.with(getActivity()).load(uri).fit().into(viewHolder.imgProfile);
                    // viewHolder.imgProfile.setImageURI(uri);
                } else {
                    Picasso.with(getActivity()).load(R.drawable.ic_contact).fit().into(viewHolder.imgProfile);
                    //viewHolder.imgProfile.setImageResource(R.drawable.ic_contact);
                }
            } else {
                Picasso.with(getActivity()).load(R.drawable.ic_contact).fit().into(viewHolder.imgProfile);
                // viewHolder.imgProfile.setImageResource(R.drawable.ic_contact);
            }

            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ContactPojo contactObject = contactPojo;
                    String name = contactObject.name;
                    String number = contactObject.mobileNumber;

                    addContactToLayout(contactObject);
                    clearAutoCompleteText();
                    tvSendTo.dismissDropDown();
                    receivers.add(contactObject);
                    for (ContactPojo contactPojo : receivers) {
                        Log.d("binh", "receiver after add from contacts: name " + contactPojo.name + " number: " + contactPojo.mobileNumber);
                    }
                    Log.d("binh", "--------------------------------");

                }
            });

            return v;
        }

        @Override
        public Filter getFilter() {
            return nameFilter;
        }

        public class ViewHolder {
            public TextView tvName, tvMobileNumber;
            public ImageView imgProfile;
        }

        Filter nameFilter = new Filter() {
            @Override
            public String convertResultToString(Object resultValue) {
                String str = ((ContactPojo) (resultValue)).name;
                return str;
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint != null) {
                    ArrayList<ContactPojo> suggestions = getPhoneNumber(constraint.toString(), getActivity());

                    int size = arrContact.size();
                    //System.out.println("suggestions=" + suggestions.size() + "=arrContact=" + size);
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                    return filterResults;
                } else {

                }
                return new FilterResults();
            }

            @Override
            protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
                ArrayList<ContactPojo> filteredList = (ArrayList<ContactPojo>) results.values;
                if (results != null && results.count > 0) {
                    clear();
                    for (ContactPojo c : filteredList) {
                        add(c);
                    }
                    notifyDataSetChanged();
                }
            }
        };

    }

    public ArrayList<ContactPojo> getPhoneNumber(String name, Context context) {
        ArrayList<ContactPojo> arrayList = new ArrayList<ContactPojo>();
        String ret = null;
        String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " like'" + name + "%'";
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.PHOTO_URI};
        Cursor cur = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, selection, null, null);
        while (cur.moveToNext()) {
            ContactPojo contact = new ContactPojo();
            contact.name = cur.getString(0);
            contact.mobileNumber = cur.getString(1);
            contact.type = cur.getInt(2);
            contact.uri = cur.getString(3);
            arrayList.add(contact);

        }

        cur.close();
        return arrayList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDatePicker:
                showDatePickerDialog();
                break;

            case R.id.btnTimePicker:
                showTimePickerDialog();
                break;

            case R.id.btn_add:
                doAddInputNumber();
                break;

            default:
                break;

        }

    }

    private View.OnClickListener onDateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDatePickerDialog();

        }
    };

    private View.OnClickListener onTimeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showTimePickerDialog();

        }
    };

    private OnFocusChangeListener onDateFocusChangeListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                showDatePickerDialog();
            }
        }
    };

    private OnFocusChangeListener onTimeFocusChangeListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                showTimePickerDialog();
            }
        }
    };

    private void showDatePickerDialog() {
        DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getActivity().getFragmentManager(), "datePicker");

    }

    private void showTimePickerDialog() {
        TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getActivity().getFragmentManager(), "timePicker");

    }

    //TODO: should check if duplicate number
    private void doAddInputNumber() {
        String number = tvSendTo.getText().toString();
        // validate input contact
        boolean boo = CommonUtils.validateInputNumber(number);
        if (!boo) {
            hideSoftKeyboard();
            Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            tvSendTo.startAnimation(shake); // shake the view
            ((MainActivity) getActivity()).showSnackBar(getResources().getString(R.string.alert_invalid_input_number));
            return;
        }
        String phoneName = CommonUtils.getContactNameFromNumber(number, getActivity());
        ContactPojo contactPojo = null;
        if (!TextUtils.isEmpty(phoneName)) {
            contactPojo = new ContactPojo(phoneName, number);
        } else {
            contactPojo = new ContactPojo(AppConstants.UNKNOWN, number);
        }
        addContactToLayout(contactPojo);
        clearAutoCompleteText();
        receivers.add(contactPojo);
        for (ContactPojo contact : receivers) {
            Log.d("binh", "receiver after add manual: name " + contact.name + " number: " + contact.mobileNumber);
        }
        Log.d("binh", "--------------------------------");
    }


    private boolean validateSMS() {

        if (receivers.size() <= 0) {
            if (tvSendTo.getText().toString().length() > 0) {
                Animation blinkAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
                btn_add.startAnimation(blinkAnimation); // shake the view
                ((MainActivity) getActivity()).showSnackBar(getResources().getString(R.string.alert_click_add_button));
            } else {
                Animation shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                tvSendTo.startAnimation(shakeAnimation); // shake the view
                ((MainActivity) getActivity()).showSnackBar(getResources().getString(R.string.alert_empty_contact));
            }

            return false;
        }
        if (TextUtils.isEmpty(etMessage.getText().toString())) {
            Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            etMessage.startAnimation(shake); // shake the view
            ((MainActivity) getActivity()).showSnackBar(getResources().getString(R.string.alert_empty_message));
            return false;
        }
        if (!validateDate()) {
            ((MainActivity) getActivity()).showSnackBar(getResources().getString(R.string.invalid_date_time));
            return false;
        }
        return true;
    }

    private boolean validateDate() {
        String currentDateTime = CommonUtils.getCurrentDateTime();
        Log.d("binh", "currentDate: " + currentDateTime);
        String targetDateTime = etDate.getText().toString() + " " + etTime.getText().toString();
        Log.d("binh", "targetDate: " + targetDateTime);
        int compare = CommonUtils.compareTwoDate(targetDateTime, currentDateTime);

        if (compare == 1) {
            return true;
        } else {
            return false;
        }
    }


    private void saveSmsToDatabase() { // this method use for both add new record or update selected record

        String dayOfWeek = CommonUtils.getDayOfWeek(etDate.getText().toString());
        currentSms.content = etMessage.getText().toString();
        currentSms.date = dayOfWeek + " " + etDate.getText().toString();
        currentSms.time = etTime.getText().toString();
        currentSms.status = 0;
        currentSms.save();

        if (idSms > 0) { //case modify
            List<ContactPojo> contactPojos = currentSms.getContactPojos();
            for (ContactPojo contactPojo : contactPojos) {
                ContactPojo.delete(ContactPojo.class, contactPojo.getId());
            }
        }

        //set new contact for current sms
        for (int i = 0; i < receivers.size(); i++) {
            ContactPojo contactPojo = new ContactPojo();
            contactPojo.name = receivers.get(i).name;
            contactPojo.mobileNumber = receivers.get(i).mobileNumber;
            contactPojo.smsPojo = currentSms;
            contactPojo.save();
        }


    }

    private void setAlarmForCurrentSMS() {
        Intent alarmIntent = new Intent(mActivity, SmsAlarmReceiver.class);
        alarmIntent.putExtra(AppConstants.KEY_CURRENT_SMS, currentSms.getId());

        long id = currentSms.getId();
        int requestCode = (int) id; //set request code pendingIntent is of selected SMS id;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mActivity, requestCode, alarmIntent, PendingIntent.FLAG_ONE_SHOT);

        Log.d("binh", "etDate:" + etDate.getText().toString());
        Log.d("binh", "sms Date:" + currentSms.date);


        String[] fulldate = currentSms.date.split(" ");
        String[] date;
        if (fulldate.length >= 3) {
            date = fulldate[2].split("-"); //date format is dd-MM-yyyy
        } else {
            date = fulldate[1].split("-"); //date format is dd-MM-yyyy
        }

        int year = Integer.parseInt(date[2]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[0]);
        String[] time = currentSms.time.split(":"); //date format is HH:mm
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);

        //set data for calendar
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month - 1); // because Januari is index 0
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        AlarmManager manager = (AlarmManager) mActivity.getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        ((MainActivity) getActivity()).showSnackBar(getResources().getString(R.string.alarm_set));
    }


}
