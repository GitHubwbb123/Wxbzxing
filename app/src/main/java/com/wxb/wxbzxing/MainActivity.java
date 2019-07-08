package com.wxb.wxbzxing;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.wxb.zxing.Activity.CaptureActivity;

public class MainActivity extends Activity {
    private Context mContext;
    private Activity mActivity;
    private static final int REQUEST_SCAN = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mActivity = this;

        init();
    }

    private void init() {
        findViewById(R.id.ll_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRuntimeRight();
            }
        });
    }

    /**
     * 获得运行时权限
     */
    private void getRuntimeRight() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            jumpScanPage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    jumpScanPage();
                } else {
                    Toast.makeText(mContext, "拒绝", Toast.LENGTH_LONG).show();
                }
            default:
                break;
        }
    }

    /**
     * 跳转到扫码页
     */
    private void jumpScanPage() {
        startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class),REQUEST_SCAN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_SCAN && resultCode == RESULT_OK){
            Toast.makeText(mContext,data.getStringExtra("barCode"),Toast.LENGTH_LONG).show();
        }
    }
}

       /* contentIv = (ImageView) findViewById(R.id.contentIv);
            case R.id.encodeBtn:
                contentEtString = contentEt.getText().toString().trim();
                if (TextUtils.isEmpty(contentEtString)) {
                    Toast.makeText(this, "请输入要生成二维码图片的字符串", Toast.LENGTH_SHORT).show();
                    return;
                }

                bitmap = CodeCreator.createQRCode(contentEtString, 400, 400, null);
                if (bitmap != null) {
                    contentIv.setImageBitmap(bitmap);
                }

                break;

           */