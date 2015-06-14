package sxh.connection.function.postcard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.io.File;

import sxh.connection.R;
import sxh.connection.function.FunctionAccessor;
import sxh.connection.function.FunctionImpl;
import sxh.connection.function.ericssonlabs.BarCodeTestActivity;
import sxh.connection.function.photos.PHOTOREQUESTCODE;
import sxh.connection.function.zxing.encoding.EncodingHandler;

/**
 * Created by Eleanor on 2015/6/13.
 */
public class MakePostcardActivity extends Activity{

    FunctionAccessor fa = new FunctionImpl();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postcard);


        Button generateButton = (Button) this.findViewById(R.id.btn_generate);
        Button takePhotoButton = (Button) findViewById(R.id.btn_take_photo);
        Button albumButton = (Button) findViewById(R.id.btn_album);
        Button sendButton = (Button) findViewById(R.id.btn_send);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText strEditText = (EditText) findViewById(R.id.editText);
                String contentString = strEditText.getText().toString();
                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                ImageView imageView1 = (ImageView) findViewById(R.id.imageViewOrigin);
                imageView1.setDrawingCacheEnabled(true);
                Bitmap photo = imageView1.getDrawingCache();
                Bitmap postCard = fa.make_postcard(contentString, photo);
                imageView.setImageBitmap(postCard);
                imageView1.setDrawingCacheEnabled(false);
            }
        });


        albumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = fa.get_photo_from_album();
                startActivityForResult(intent, PHOTOREQUESTCODE.PHOTOZOOM.toInt());
            }
        });

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = fa.take_photo();
                startActivityForResult(intent, PHOTOREQUESTCODE.PHOTORAPH.toInt());
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = fa.send_card("xll94@163.com", "postcard");
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == PHOTOREQUESTCODE.NONE.toInt())
            return;
        if (requestCode == PHOTOREQUESTCODE.PHOTORAPH.toInt()){
            File picture = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
            Intent intent = fa.zoom_photo(Uri.fromFile(picture), false);
            startActivityForResult(intent, PHOTOREQUESTCODE.PHOTORESULT.toInt());
        }
        if (requestCode == PHOTOREQUESTCODE.PHOTOZOOM.toInt()){
            if (data == null) return;
            Intent intent = fa.zoom_photo(data.getData(), false);
            startActivityForResult(intent, PHOTOREQUESTCODE.PHOTORESULT.toInt());
        }
        if (requestCode == PHOTOREQUESTCODE.PHOTORESULT.toInt()){
            if (data == null) return;
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");

                photo = fa.compress_photo(photo, false, 0);

                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                ImageView imageView1 = (ImageView) findViewById(R.id.imageViewOrigin);
                imageView1.setAlpha(0f);
                imageView.setImageBitmap(photo);
                imageView1.setImageBitmap(photo);
            }
        }
    }


}
