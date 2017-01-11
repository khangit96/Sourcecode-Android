package khangit96.quanlycaphe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id.arieridwan.lib.PageLoader;
import khangit96.quanlycaphe.R;
import khangit96.quanlycaphe.adapter.ChooseCompanyAdapter;
import khangit96.quanlycaphe.model.Config;
import khangit96.quanlycaphe.model.RecyclerItemClickListener;

public class ChooseCompanyActivity extends AppCompatActivity {

    ArrayList<Config> configList;
    ChooseCompanyAdapter adapter;
    RecyclerView recyclerView;
    PageLoader pageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_company);

        addControls();
        addEvents();
        loadDataConfigFromFirebase();
    }

    private void addControls() {
        pageLoader = (PageLoader) findViewById(R.id.pageloader);
        pageLoader.startProgress();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewChooseCompany);
        LinearLayoutManager mManager = new LinearLayoutManager(getApplicationContext());
        mManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(mManager);

        configList = new ArrayList<>();

        adapter = new ChooseCompanyAdapter(getApplicationContext(), configList);
        recyclerView.setAdapter(adapter);
    }

    private void addEvents() {
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Config.COMPANY_KEY = configList.get(position).key;
                startActivity(new Intent(ChooseCompanyActivity.this, MainActivity.class));
                finish();
            }

        }));
    }

    public void loadDataConfigFromFirebase() {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dt : dataSnapshot.getChildren()) {

                    Config config = dt.child("Config").getValue(Config.class);
                    config.key = dt.getKey();
                    configList.add(config);
                }

                configList.add(new Config("Mây Và Nước", "13", "dsksfl"));
                configList.add(new Config("KCoffe", "13", "dsksfl"));
                configList.add(new Config("Gió Và Nước", "13", "dsksfl"));

                adapter.notifyDataSetChanged();
                pageLoader.stopProgress();
                pageLoader.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                findViewById(R.id.tvWhere).setVisibility(View.VISIBLE);
                findViewById(R.id.activity_choose_company).setBackgroundColor(getResources().getColor(R.color.bg_login));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
