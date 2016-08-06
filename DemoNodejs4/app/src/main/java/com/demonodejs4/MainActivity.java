package com.demonodejs4;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Socket msocket;
    Button btnCapture, btnChoose, btDecode, btEncode;
    ImageView imageView;
    private static final int SELECT_PHOTO = 100;
    private String imagePath = "";
    private String enCodeImage = "";
    TextView tv;
    EditText ed;

    {
        try {
            msocket = IO.socket("http://demo-khangit.rhcloud.com/");
            //msocket = IO.socket("http://192.168.1.7:3000");
        } catch (URISyntaxException e) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addControls();
        addEvents();
        msocket.connect();
        msocket.on("serverNotice", onMessage);
    }

    public void addControls() {
        btnCapture = (Button) findViewById(R.id.btnCapture);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        btDecode = (Button) findViewById(R.id.btDecode);
        btEncode = (Button) findViewById(R.id.btEncode);
        tv = (TextView) findViewById(R.id.tv);
        ed = (EditText) findViewById(R.id.ed);
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
                /*Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image*//*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);*/
                openGallery();

            }
        });
        //encode image
        btEncode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  String convertByteToString=FileLocal_To_Byte(imagePath).toString();
                //  byte[] convertStringToByte=convertByteToString.getBytes();
               /* String encImage = Base64.encodeToString(convertStringToByte, Base64.DEFAULT);
                byte[] b = Base64.decode(encImage, Base64.DEFAULT);
                Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
                tv.setText(encImage);
                imageView.setImageBitmap(bmp);*/
                // String encImage = Base64.encodeToString(convertStringToByte, Base64.DEFAULT);
                //tv.setText(encImage);
               /* String convertByteToString = FileLocal_To_Byte(imagePath).toString();
                byte[] convertStringToByte = convertByteToString.getBytes();
                String encImage = Base64.encodeToString(convertStringToByte, Base64.DEFAULT);
                byte[] b = Base64.decode(Base64.encodeToString(convertStringToByte, Base64.DEFAULT), Base64.DEFAULT);
                Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
                imageView.setImageBitmap(bmp);*/
            }
        });
        //decode image
        btDecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // msocket.emit("sendImageToServer",FileLocal_To_Byte(imagePath));
                sendImage(imagePath);
            }
        });
    }
    public void sendImage(String path)
    {
      //  JSONObject sendData = new JSONObject();

           // sendData.put("image", encodeImage(path));
           // Bitmap bmp = decodeImage(sendData.getString("image"));
          //  addImage(bmp);
            msocket.emit("sendImageToServer", encodeImage(path));

    }
    /*Hàm lắng nghe đầu tiên để thông báo nếu kết nối với database thành công hay chưa*/
    private Emitter.Listener onMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String noiDung = data.getString("noiDung");
                        byte[] b = Base64.decode(noiDung, Base64.DEFAULT);
                        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
                        imageView.setImageBitmap(bmp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };

    /*  @Override
      protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
          super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
          switch (requestCode) {
              case SELECT_PHOTO:
                  if (resultCode == RESULT_OK) {

                      Uri selectedImage = imageReturnedIntent.getData();
                      //get image path
                      imagePath = getPath(selectedImage);
                      Toast.makeText(getApplicationContext(), imagePath, Toast.LENGTH_LONG).show();
                      InputStream imageStream = null;
                      try {
                          imageStream = getContentResolver().openInputStream(selectedImage);
                      } catch (FileNotFoundException e) {
                          e.printStackTrace();
                      }
                      //Set imageview form bitMap
                      *//*Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                   imageView.setImageBitmap(yourSelectedImage);*//*

                }
        }
    }*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK
                && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imagePath = cursor.getString(columnIndex);
            Toast.makeText(getApplicationContext(),imagePath,Toast.LENGTH_LONG).show();
            cursor.close();
            //Log.d("onActivityResult",imgDecodableString);
           // ChatFragment fragment = (ChatFragment) getFragmentManager().findFragmentById(R.id.chat);
           // fragment.sendImage(imgDecodableString);
        }
    }

    private void openGallery() {
        Intent galleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryintent, 1);
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

    public byte[] FileLocal_To_Byte(String path) {
        File file = new File(path);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bytes;
    }
}
