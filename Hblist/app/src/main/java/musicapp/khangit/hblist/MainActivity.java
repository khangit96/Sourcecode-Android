package musicapp.khangit.hblist;
import android.graphics.Matrix;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import java.io.ByteArrayOutputStream;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    ImageButton btnCapture, btnUpload;
    ImageView imageView;
    private Uri fileUri;
    String picturePath;
    Uri selectedUriImage;
    Bitmap selectedBitmap;
    String ba1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Home");
        addControls();
        addEvents();
    }


    public void addControls()
    {
        btnCapture = (ImageButton) findViewById(R.id.btnCapture);
        btnUpload = (ImageButton) findViewById(R.id.btnUpload);
        imageView = (ImageView) findViewById(R.id.Imageprev);
        btnUpload.setEnabled(false);
    }

    public void addEvents() {
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePicture();
            }
        });
    }

 /*   public Bitmap getThumbnail(String pathHinh)
    {
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathHinh, bounds);
        if ((bounds.outWidth == -1) || (bounds.outHeight == -1))
            return null;
        int originalSize = (bounds.outHeight > bounds.outWidth) ?
                bounds.outHeight
                : bounds.outWidth;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = originalSize / 500;
        return BitmapFactory.decodeFile(pathHinh, opts);
    }

    //Hàm chụp ảnh
    private void capturePicture() {
        // Kiểm tra Camera trong thiết bị
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // Mở camera mặc định
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // Tiến hành gọi Capture Image intent
            startActivityForResult(intent, 100);

        } else {
            Toast.makeText(getApplication(), "Camera không được hỗ trợ", Toast.LENGTH_LONG).show();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == 100||requestCode==200) && resultCode == RESULT_OK) {
            //Lấy URI hình kết quả trả về
            selectedUriImage = data.getData();
            //lấy đường dẫn hình
            picturePath=getPicturePath(selectedUriImage);
            //lấy thumbnail để tối ưu bộ nhớ
            selectedBitmap=getThumbnail(picturePath);
            selectedBitmap=rotateImageIfRequired(selectedBitmap,selectedUriImage);
            imageView.setImageBitmap(selectedBitmap);
            btnUpload.setEnabled(true);
        }
    }
    public String getPicturePath(Uri uriImage)
    {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uriImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String path = cursor.getString(columnIndex);
        cursor.close();
        return path;
    }
    private  Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) {

        // Detect rotation
        int rotation=getRotation();
        if(rotation!= 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(rotation);
            Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
            img.recycle();
            return rotatedImg;
        }else{
            return img;
        }
    }
    private int getRotation() {
        String[] filePathColumn = {MediaStore.Images.Media.ORIENTATION};
        Cursor cursor = getContentResolver().query(selectedUriImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int rotation =0;
        rotation = cursor.getInt(0);
        cursor.close();
        return rotation;
    }*/
 private void capturePicture() {
     // Kiểm tra Camera trong thiết bị
     if (getApplicationContext().getPackageManager().hasSystemFeature(
             PackageManager.FEATURE_CAMERA)) {
         // Mở camera mặc định
         Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
         intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

         // Tiến hành gọi Capture Image intent
         startActivityForResult(intent, 100);

     } else {
         Toast.makeText(getApplication(), "Camera không được hỗ trợ", Toast.LENGTH_LONG).show();
     }
 }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
