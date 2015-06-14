package sxh.connection.function.ericssonlabs;

import com.google.zxing.WriterException;

import sxh.connection.R;
import sxh.connection.function.FunctionAccessor;
import sxh.connection.function.FunctionImpl;
import sxh.connection.function.zxing.activity.CaptureActivity;
import sxh.connection.function.zxing.encoding.EncodingHandler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BarCodeTestActivity extends Activity {
    /** Called when the activity is first created. */
	private TextView resultTextView;
	private EditText qrStrEditText;
	private ImageView qrImgImageView;

    FunctionAccessor fa = new FunctionImpl();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchange);
        
        resultTextView = (TextView) this.findViewById(R.id.tv_scan_result);
        qrImgImageView = (ImageView) this.findViewById(R.id.iv_qr_image);
        
        Button scanBarCodeButton = (Button) this.findViewById(R.id.btn_scan_barcode);
        scanBarCodeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//打开扫描界面扫描条形码或二维码
				Intent openCameraIntent = new Intent(BarCodeTestActivity.this,CaptureActivity.class);
				startActivityForResult(openCameraIntent, 0);
			}
		});
        
        Button generateQRCodeButton = (Button) this.findViewById(R.id.btn_add_qrcode);
        generateQRCodeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
				Bitmap qrCodeBitmap = fa.get_qrcode();
				qrImgImageView.setImageBitmap(qrCodeBitmap);
			}
		});
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//处理扫描结果（在界面上显示）
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			resultTextView.setText(scanResult);
		}
	}
}