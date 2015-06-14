package sxh.connection.function.photos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;

import sxh.connection.R;
import sxh.connection.function.FunctionAccessor;
import sxh.connection.function.FunctionImpl;

/**
 * Created by Eleanor on 2015/6/13.
 */
public class GetPhotoActivity extends Activity {
    FunctionAccessor fa = new FunctionImpl();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_photo);

        Button btn1 = (Button) findViewById(R.id.btn_01);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = fa.get_photo_from_album();
                startActivityForResult(intent, PHOTOREQUESTCODE.PHOTOZOOM.toInt());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == PHOTOREQUESTCODE.NONE.toInt())
            return;
        if (requestCode == PHOTOREQUESTCODE.PHOTOZOOM.toInt()){
            if (data == null) return;
            Intent intent = fa.zoom_photo(data.getData(), true);
            startActivityForResult(intent, PHOTOREQUESTCODE.PHOTORESULT.toInt());
        }
        if (requestCode == PHOTOREQUESTCODE.PHOTORESULT.toInt()){
            if (data == null) return;
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 - 100)压缩文件
                ImageView imageView = (ImageView) findViewById(R.id.imageID);
                imageView.setImageBitmap(photo);
            }
        }
    }

}
