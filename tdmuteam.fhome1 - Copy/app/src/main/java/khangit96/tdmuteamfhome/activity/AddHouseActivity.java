package khangit96.tdmuteamfhome.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import khangit96.tdmuteamfhome.R;
import khangit96.tdmuteamfhome.model.House;

public class AddHouseActivity extends AppCompatActivity {
    Button btPostHouse;
    EditText edHouseName, edHouseAddress, edHousePrice, edWaterPrice, edElectricPrice;
    ImageView imgTakePhoTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
        addEvents();


    }

    private void addEvents() {
        btPostHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = edHouseAddress.getText().toString();
                String name = edHouseName.getText().toString();
                String electricPrice = edElectricPrice.getText().toString();
                String waterPrice = edWaterPrice.getText().toString();
                String housePrice = edHousePrice.getText().toString();
                String phone = "01695638717";
                String status = "Còn phòng";
                double latitude = 10.980581;
                double longtitude = 106.674394;
                boolean verified = false;

                House house = new House(address, electricPrice, waterPrice, housePrice, longtitude, phone, name, status, verified, latitude);
                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("NhaTro/23/");
                mRef.setValue(house);

                hideControls();
            }
        });

        imgTakePhoTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureCameraImage();
            }
        });
    }

    private void addControls() {
        btPostHouse = (Button) findViewById(R.id.btPostHouse);
        edHouseName = (EditText) findViewById(R.id.edHouseName);
        edHouseAddress = (EditText) findViewById(R.id.edHouseAddress);
        edHousePrice = (EditText) findViewById(R.id.edHousePrice);
        edWaterPrice = (EditText) findViewById(R.id.edWaterPrice);
        edElectricPrice = (EditText) findViewById(R.id.edElectricPrice);
        imgTakePhoTo = (ImageView) findViewById(R.id.imgTakePhoTo);
    }

    private void hideControls() {
        findViewById(R.id.lnAdd).setVisibility(View.GONE);

    }

    private static final int CAMERA_PHOTO = 111;
    private Uri imageToUploadUri;

    private void captureCameraImage() {
        Intent chooserIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(Environment.getExternalStorageDirectory(), "POST_IMAGE.jpg");
        chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        imageToUploadUri = Uri.fromFile(f);
        startActivityForResult(chooserIntent, CAMERA_PHOTO);
    }

    private Bitmap getBitmap(String path) {

        Uri uri = Uri.fromFile(new File(path));
        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
            in = getContentResolver().openInputStream(uri);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();


            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }

            Bitmap b = null;
            in = getContentResolver().openInputStream(uri);
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = b.getHeight();
                int width = b.getWidth();
                //   Log.d("", "1th scale operation dimenions - width: " + width + ", height: " + height);

                double y = Math.sqrt(IMAGE_MAX_SIZE
                        / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
                        (int) y, true);
                b.recycle();
                b = scaledBitmap;

                System.gc();
            } else {
                b = BitmapFactory.decodeStream(in);
            }
            in.close();

            // Log.d("", "bitmap size - width: " + b.getWidth() + ", height: " +
          //  b.getHeight());
            return b;
        } catch (IOException e) {
            //Log.e("", e.getMessage(), e);
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_PHOTO && resultCode == Activity.RESULT_OK) {
            if (imageToUploadUri != null) {
                Uri selectedImage = imageToUploadUri;
                getContentResolver().notifyChange(selectedImage, null);
                Bitmap reducedSizeBitmap = getBitmap(imageToUploadUri.getPath());
                if (reducedSizeBitmap != null) {
                    imgTakePhoTo.setImageBitmap(reducedSizeBitmap);
                  /*  Button uploadImageButton = (Button) findViewById(R.id.uploadUserImageButton);
                    uploadImageButton.setVisibility(View.VISIBLE);*/
                } else {
                    Toast.makeText(this, "Error while capturing Image", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            }
        }
    }

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
