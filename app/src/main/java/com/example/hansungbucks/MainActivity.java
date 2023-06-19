package com.example.hansungbucks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.hansungbucks.R;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {
    static {
        // OpenCVLoader 클래스를 사용하여 OpenCV가 성공적으로 로드되었는지 확인합니다.
        if(OpenCVLoader.initDebug()){
            Log.d("MainActivity: ","OpenCV is loaded");
        }
        else {
            Log.d("MainActivity: ","OpenCV failed to load");
        }
    }

    private ImageButton camera_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // camera_button에 클릭 리스너가 설정됩니다.
        // 버튼을 클릭하면 명시적 인텐트를 사용하여 CameraActivity가 시작됩니다.
        ImageButton camera_button = findViewById(R.id.camera_button);
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent.FLAG_ACTIVITY_CLEAR_TASK 및 Intent.FLAG_ACTIVITY_CLEAR_TOP 플래그가 추가되어
                // 활동 스택을 지우고 CameraActivity가 새 작업으로 시작되도록 합니다.
                startActivity(new Intent(MainActivity.this,CameraActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }
}