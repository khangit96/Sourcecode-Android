package khangit96.demosnackbar;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    CoordinatorLayout layoutRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
    }

    private void setupUI() {

        /*layoutRoot = (CoordinatorLayout) findViewById(R.id.layoutRoot);

        Snackbar.make(layoutRoot, "Hello SnackBar!", Snackbar.LENGTH_INDEFINITE)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                })
                .show();*/
        // Create the Snackbar
        layoutRoot = (CoordinatorLayout) findViewById(R.id.layoutRoot);
    /*    Snackbar snackbar = Snackbar.make(layoutRoot, "", Snackbar.LENGTH_LONG);
// Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
// Hide the text
        TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);

// Inflate our custom view
        LayoutInflater layoutInflater=LayoutInflater.from(this);
        View snackView = layoutInflater.inflate(R.layout.my_snackbar, null);
// Configure the view
        TextView textViewTop = (TextView) snackView.findViewById(R.id.text);
        textViewTop.setText("Ok");
        textViewTop.setTextColor(Color.WHITE);

// Add the view to the Snackbar's layout
        layout.addView(snackView, 0);
// Show the Snackbar
        snackbar.show();*/
        Snackbar snackbar = Snackbar.make(layoutRoot, "No Internet Connection", Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        snackbar.show();
    }

}
