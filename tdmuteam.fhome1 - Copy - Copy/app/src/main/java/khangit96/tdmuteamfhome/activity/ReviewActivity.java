package khangit96.tdmuteamfhome.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import khangit96.tdmuteamfhome.R;

public class ReviewActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Review> reviewArrayList;
    private ListViewReviewAdapter adapter;
    private int NHATRO_POS;
    private int countReview = 0;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nhận xét");

        addControls();
        addEvents();
        loadReviewFirebase();
    }

    /*
    * add event click listener
    * */
    private void addEvents() {
        FloatingActionButton fabAddReview = (FloatingActionButton) findViewById(R.id.fabAddReview);
        fabAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAddReview();
            }
        });

        //listview scroll reached bottom

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (listView.getLastVisiblePosition() - listView.getHeaderViewsCount() -
                        listView.getFooterViewsCount()) >= (adapter.getCount() - 1)) {
                    loadReviewFirebase();

                }
            }

            @Override
            public void onScroll(AbsListView lw, final int firstVisibleItem,
                                 final int visibleItemCount, final int totalItemCount) {


            }
        });
    }

    /*
    * add controls lie button,editext
    * */
    public void addControls() {
        progressDialog = new ProgressDialog(ReviewActivity.this);
        listView = (ListView) findViewById(R.id.list_item);
        reviewArrayList = new ArrayList<>();
        adapter = new ListViewReviewAdapter(this, R.layout.item_listview_review, reviewArrayList);
        listView.setAdapter(adapter);

        Bundle bd = getIntent().getExtras();
        if (bd != null) {
            NHATRO_POS = bd.getInt("NHATRO_POS");
        }

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("NhaTro/" + NHATRO_POS + "/Review");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                countReview = (int) dataSnapshot.getChildrenCount();
                mDatabase.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*
    *  load review firebase
    * */
    public void loadReviewFirebase() {
        progressDialog.setMessage("Đang tải nhận xét...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("NhaTro/" + NHATRO_POS + "/Review");
        mDatabase.orderByKey().startAt(String.valueOf(adapter.getCount() + 1), null).limitToFirst(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getChildrenCount() != 0) {

                    for (DataSnapshot dt : dataSnapshot.getChildren()) {
                        Review review = dt.getValue(Review.class);
                        reviewArrayList.add(review);
                        adapter.notifyDataSetChanged();
                    }
                }
                progressDialog.dismiss();

                mDatabase.removeEventListener(this);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /*
    * show dialog add review
    * */
    private void showDialogAddReview() {
        final EditText inputReview = new EditText(this);
        inputReview.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        inputReview.setHint("Nhập nội dung nhận xét vào đây.");
        inputReview.setHeight(100);
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setTitle("Thêm nhận xét");
        builder.setView(inputReview);
        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (inputReview.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập nội dung nhận xét! ", Toast.LENGTH_LONG).show();
                    return;
                }
                countReview++;
                DatabaseReference mAddReview = FirebaseDatabase.getInstance().getReference().child("NhaTro/" + NHATRO_POS + "/Review/" + countReview);
                Review review = new Review(inputReview.getText().toString(), "khangit");
                reviewArrayList.add(review);
                listView.post(new Runnable() {
                    @Override
                    public void run() {
                        // Select the last row so it will scroll into view...
                        listView.setSelection(listView.getCount() - 1);
                    }
                });
                mAddReview.setValue(review);


            }
        });
        builder.setNegativeButton("Huỷ", null);
        builder.show();
    }


    /*
    * menu item
    * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
