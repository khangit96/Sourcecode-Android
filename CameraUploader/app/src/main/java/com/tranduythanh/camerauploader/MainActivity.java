package com.tranduythanh.camerauploader;

import android.graphics.Matrix;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

public class MainActivity extends ActionBarActivity {
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
    public void addEvents()
    {
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePicture();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPictureToServer();
            }
        });
    }

    /**
     * hàm xử lý lấy thumbnail để tối ưu bộ nhớ
     * @param pathHinh
     * @return
     */
    public Bitmap getThumbnail(String pathHinh)
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

    /**
     * Hàm xử lys lấy encode hình để gửi lên Server
     */
    private void uploadPictureToServer() {
        Log.e("path", "----------------" + picturePath);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        byte[] ba = bao.toByteArray();
        ba1 =Base64.encodeToString(ba,Base64.DEFAULT);

        Log.e("base64", "-----" + ba1);

        // Upload hình  lên server
        UploadToServerTask uploadToServer=new UploadToServerTask(MainActivity.this,ba1);
        uploadToServer.execute();
    }

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

    /**
     * Lấy đường dẫn file hình theo uri hình
     * @param uriImage
     * @return
     */
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
    /**
     * Hàm hiển thị Camera folder và cho phép hiển thị hình người sử dụng chọn
     * lên giao diện, hình này sẽ được gửi lên Server nếu muốn
     */
    public void processChonHinh()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 200);
    }
    /**
     * Quay lại hình nếu chưa đúng
     * @param img
     * @param selectedImage
     * @return
     */
    private  Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) {

        // Detect rotation
        int rotation=getRotation();
        if(rotation!=0){
            Matrix matrix = new Matrix();
            matrix.postRotate(rotation);
            Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
            img.recycle();
            return rotatedImg;
        }else{
            return img;
        }
    }
    /**
     * Lấy Rotation của hình
     * @return
     */
    private int getRotation() {
        String[] filePathColumn = {MediaStore.Images.Media.ORIENTATION};
        Cursor cursor = getContentResolver().query(selectedUriImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int rotation =0;
        rotation = cursor.getInt(0);
        cursor.close();
        return rotation;
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
        if(id==R.id.mnuImageList)
        {
            processChonHinh();
        }
        return super.onOptionsItemSelected(item);
    }
}
