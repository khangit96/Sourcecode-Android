package com.demogetimagefromphoto;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    Button btnCapture, btnChoose,btDecode,btEncode;
    ImageView imageView;
    private Uri fileUri;
    String picturePath;
    Uri selectedUriImage;
    Bitmap selectedBitmap;
    String ba1;
    private static final int SELECT_PHOTO = 100;
    private String imagePath = "";
    private String enCodeImage="";
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addControls();
        addEvents();
    }

    public void addControls() {
        btnCapture = (Button) findViewById(R.id.btnCapture);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        btDecode = (Button) findViewById(R.id.btDecode);
        btEncode = (Button) findViewById(R.id.btEncode);
        tv=(TextView)findViewById(R.id.tv);
        imageView = (ImageView) findViewById(R.id.Imageprev);
    }

    public void addEvents() {
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //capturePicture();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });
        //encode image
        btEncode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  enCodeImage=encodeImage(imagePath);
                    if(enCodeImage!=null){
                        tv.setText("ok");
                    }
                    else{
                        tv.setText("null");
                    }
            }
        });
        //decode image
        btDecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] b = Base64.decode(enCodeImage,Base64.DEFAULT);
                Bitmap bmp = BitmapFactory.decodeByteArray(b,0,b.length);
               imageView.setImageBitmap(bmp);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {

                    Uri selectedImage = imageReturnedIntent.getData();
                    //get image path
                    imagePath = getPath(selectedImage);
                    Toast.makeText(getApplicationContext(),imagePath, Toast.LENGTH_LONG).show();
                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    //Set imageview form bitMap
                    /*Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                   imageView.setImageBitmap(yourSelectedImage);*/

                }
        }
    }

    //Hàm lấy đường dẫn hình ảnh từ photo
    public String getPath(Uri uri) {

        if (uri == null) {
            return null;
        }

        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }

        return uri.getPath();
    }

    private String encodeImage(String path)
    {
        File imagefile = new File(path);
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(imagefile);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        //Base64.de
        return encImage;
    }
    /* *//**
     * hàm xử lý lấy thumbnail để tối ưu bộ nhớ
     * @param pathHinh
     * @return
     *//*
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

    *//**
     * Hàm xử lys lấy encode hình để gửi lên Server
     *//*
    private void uploadPictureToServer() {
        Log.e("path", "----------------" + picturePath);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        byte[] ba = bao.toByteArray();
        ba1 = Base64.encodeToString(ba, Base64.DEFAULT);

        Log.e("base64", "-----" + ba1);

// Upload hình  lên server
      //  UploadToServerTask uploadToServer=new UploadToServerTask(MainActivity.this,ba1);
        //uploadToServer.execute();
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

    *//**
     * Lấy đường dẫn file hình theo uri hình
     * @param uriImage
     * @return
     *//*
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
            btnChoose.setEnabled(true);
        }
    }
    *//**
     * Hàm hiển thị Camera folder và cho phép hiển thị hình người sử dụng chọn
     * lên giao diện, hình này sẽ được gửi lên Server nếu muốn
     *//*
    public void processChonHinh()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image*//*");
        startActivityForResult(intent, 200);
    }
    *//**
     * Quay lại hình nếu chưa đúng
     * @param img
     * @param selectedImage
     * @return
     *//*
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
    *//**
     * Lấy Rotation của hình
     * @return
     *//*
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
    }*/
}
