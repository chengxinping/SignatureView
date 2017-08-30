package cn.xpcheng.signatureview;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.xpcheng.signatureview.view.SignatureView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    SignatureView mSignatureView;
    Button btnSubmit, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSignatureView = (SignatureView) findViewById(R.id.sign_view);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnClear.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        //修改画笔宽度
        mSignatureView.setLineWidth(5);
        //修改画笔颜色
        mSignatureView.setLineColor(Color.GREEN);
    }

    /**
     * 获取签名图片
     *
     * @return 签名图片
     */
    private Bitmap getBitmap() {
        if (mSignatureView != null)
            return mSignatureView.getBitmap();
        else
            return null;
    }

    /**
     * 清除图片
     */
    private void clearBitmap() {
        if (mSignatureView != null) {
            mSignatureView.clear();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnClear:
                clearBitmap();
                break;
            case R.id.btnSubmit:
                //保存
                String path = Environment.getExternalStorageDirectory()
                        + "/" + getPackageName() + "/sign";
                String name = mSignatureView.saveBitmap(path);
                Toast.makeText(this, "保存图片：" + name, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
