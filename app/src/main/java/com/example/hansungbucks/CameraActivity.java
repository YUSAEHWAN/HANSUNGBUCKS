package com.example.hansungbucks;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.hansungbucks.R;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.IOException;

// CameraActivity 클래스는 Activity 클래스를 확장하고 OpenCV에서 CvCameraViewListener2 인터페이스를 구현합니다.
// 카메라 기능을 처리하고 연령 및 성별 인식 모델을 통합합니다.
public class CameraActivity extends Activity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = "MainActivity";

    private Mat mRgba;
    private Mat mGray;

    // mOpenCvCameraView: 카메라가 캡처한 프레임을 표시할 카메라 보기를 나타냅니다.
    private CameraBridgeViewBase mOpenCvCameraView;
    private age_gender_recognition age_gender_recognition;

    // mLoaderCallback: OpenCV의 초기화를 처리하고 OpenCV가 성공적으로 로드되면 카메라 보기를 활성화하는 BaseLoaderCallback 개체입니다.
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG,"OpenCV Is loaded");
                    mOpenCvCameraView.enableView();
                }
                default:
                {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    // getAgeValue 및 getGenderValue: 이 변수는 연령 및 성별 인식 모델에서 얻은 연령 및 성별 값을 저장합니다.
    public int getAgeValue = 0;
    public float getGenderValue = 0;

    public CameraActivity(){
        Log.i(TAG,"Instantiated new " + this.getClass());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // 카메라 뷰를 설정하고, 카메라 권한을 요청하고, 연령 및 성별 인식 모델을 초기화합니다.
        int MY_PERMISSIONS_REQUEST_CAMERA = 0;

        if (ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(CameraActivity.this, new String[] {Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }

        setContentView(R.layout.activity_camera);

        mOpenCvCameraView = (CameraBridgeViewBase)findViewById(R.id.frame_Surface);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);

        mOpenCvCameraView.setCameraIndex(1); // 전면 카메라 설정


        try {
            // 연령 및 성별 인식 모델을 나타내는 age_gender_recognition 클래스의 인스턴스가 생성됩니다.
            // 모델은 지정된 TFLite 모델 파일(model.tflite) 및 입력 이미지 크기(96x96x3)로 로드됩니다.
            int inputSize = 96;
            age_gender_recognition = new age_gender_recognition(getAssets(),CameraActivity.this,"model.tflite", inputSize);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // 연령 및 성별 감지 프로세스를 트리거하는 데 사용되는 활동 레이아웃의 버튼을 나타냅니다.
        // 버튼을 클릭하면 detect_Activity를 시작하고 연령 및 성별 값을 인텐트의 엑스트라로 전달합니다.
        Button button_age = findViewById(R.id.detect_btn);
        button_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraActivity.this, detect_Activity.class);
                intent.putExtra("AGE_VALUE", getAgeValue);
                intent.putExtra("GENDER_VALUE", getGenderValue);
                startActivity(intent);
            }
        });
    }

    // onResume() 메서드는 OpenCV 라이브러리의 초기화 및 로드를 처리하기 위해 CameraActivity 클래스에서 재정의됩니다.
    @Override
    protected void onResume() {
        super.onResume();

        // OpenCVLoader에서 제공하는 initDebug() 메서드를 호출하여 OpenCV 라이브러리가 이미 로드되어 있는지 확인합니다.
        // 이 메서드는 디버깅 목적으로 사용되며 라이브러리가 성공적으로 로드되면 true를 반환합니다.
        if (OpenCVLoader.initDebug()) {
            Log.d(TAG,"OpenCV initialization is done" + "getAgeValue");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        else {
            Log.d(TAG,"OpenCV is not loaded. try again");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0,this,mLoaderCallback);
        }
    }

    // onPause() 메서드는 활동 일시 중지를 처리하도록 재정의됩니다.
    @Override
    protected void onPause() {
        super.onPause();

        // 카메라 뷰가 null이 아니면 카메라 보기를 비활성화하고 이와 관련된 모든 리소스를 해제합니다.
        if (mOpenCvCameraView != null) {
            mOpenCvCameraView.disableView();
        }
    }

    // onDestroy() 메서드는 활동 소멸을 처리하도록 재정의됩니다
    public void onDestroy() {
        super.onDestroy();

        // 카메라 뷰가 null이 아니면 카메라 보기를 비활성화하고 이와 관련된 모든 리소스를 해제합니다.
        if(mOpenCvCameraView != null) {
            mOpenCvCameraView.disableView();
        }
    }

    // 카메라 뷰가 시작될 때 호출되는 메서드입니다.
    public void onCameraViewStarted(int width ,int height) {
        // mRgba는 RGBA 카메라 프레임을 나타내는 지정된 너비와 높이를 가진 새로운 Mat 객체로 초기화됩니다.
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        // mGray는 그레이스케일 카메라 프레임을 나타내는 지정된 너비와 높이를 가진 새로운 Mat 객체로 초기화됩니다.
        mGray = new Mat(height, width, CvType.CV_8UC1);
    }

    // 카메라 보기가 중지될 때 호출되는 메서드입니다.
    // 카메라 프레임과 관련된 모든 리소스를 해제하는 역할을 합니다
    public void onCameraViewStopped() {
        mRgba.release();
    }

    // 카메라 뷰에서 수신한 각 카메라 프레임에 대해 호출되는 메서드입니다.
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        // mRgba는 inputFrame.rgba()의 RGBA 카메라 프레임으로 업데이트됩니다.
        mRgba = inputFrame.rgba();
        // mGray는 inputFrame.gray()의 그레이스케일 카메라 프레임으로 업데이트됩니다.
        mGray = inputFrame.gray();

        // RGBA 카메라 프레임에서 연령 및 성별 인식을 수행
        mRgba = age_gender_recognition.recognizeImage(mRgba);
        getAgeValue = age_gender_recognition.savedAgeValue;
        getGenderValue = age_gender_recognition.savedGenderValue;

//        Core.flip(mRgba, mRgba, -1); // 이미지 상하 반전
//        Core.flip(mGray, mGray, -1); // 이미지 상하 반전

        // 업데이트된 RGBA 카메라 프레임(mRgba)이 반환됩니다.
        return mRgba;
    }
}