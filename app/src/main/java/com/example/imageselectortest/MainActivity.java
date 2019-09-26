package com.example.imageselectortest;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scrat.app.selectorlibrary.ImageSelector;
import java.util.List;

/*测试开源图片选择器，开源地址https://github.com/open-android/ImageSelector
   支持jitpack
   支持选择多张
   支持选择图片数量上限
   支持图片选择顺序
   支持图片预览
 */
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SELECT_IMG = 1;//请求码
    private static final int MAX_SELECT_COUNT = 9;//最大图片数
    private TextView mContentTv;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mContentTv = (TextView) findViewById(R.id.content);
        imageView=(ImageView)findViewById(R.id.image_view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SELECT_IMG) {
            showContent(data);
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showContent(Intent data) {
        List<String> paths = ImageSelector.getImagePaths(data);
        if (paths.isEmpty()) {
            mContentTv.setText("无图片");
            return;
        }
        mContentTv.setText(paths.toString());

        for(String path:paths){
            mContentTv.append("\n"+path);
        }
        Glide.with(this).load(paths.get(0)).into(imageView);
    }
    //方法在布局文件中调用
    public void selectImg(View v) {
        ImageSelector.show(this, REQUEST_CODE_SELECT_IMG, MAX_SELECT_COUNT);
    }
}
