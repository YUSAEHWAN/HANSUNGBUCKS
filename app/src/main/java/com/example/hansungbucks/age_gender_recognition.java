package com.example.hansungbucks;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.hansungbucks.R;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.GpuDelegate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;
import java.util.TreeMap;

public class age_gender_recognition {

    // 실시간 프레임에 모델을 로드하고 예측
    private Interpreter interpreter;

    private int INPUT_SIZE;
    private float IMAGE_STD = 255.0f;
    private float IMAGE_MEAN = 0;

    // 인터프리터를 위한 GPU와 스레드를 설정하는 데 사용됩니다
    private GpuDelegate gpuDelegate = null;

    private int height = 0;
    private int width = 0;

    // haar-cascade classifier가 로드되는데 사용됩니다.
    private CascadeClassifier cascadeClassifier;

    // 연령 및 성별 인식 시스템을 초기화하는 메소드를 정의합니다.
    age_gender_recognition(AssetManager assetManager, Context context, String modelPath, int inputSize) throws IOException {

        INPUT_SIZE = inputSize;

        // GPU 가속을 활성화하기 위해 GPU Delegate와 스레드 개수 정의
        Interpreter.Options options = new Interpreter.Options();
        gpuDelegate = new GpuDelegate();
        options.addDelegate(gpuDelegate);
        // 4개의 스레드로 설정(폰의 사양에 맞게 변경 가능, 숫자를 높게 잡을 경우 프레임 비율이 더 좋아짐)
        options.setNumThreads(4);

        // 모델 로드, 로드된 모델은 인터프리터 변수에 저장
        interpreter = new Interpreter(loadModelFile(assetManager,modelPath),options);
        // 이 작업 모델이 인터프리터에 로드 될 경우 출력
        Log.d("Age_Gender_Recognition","CNN model is loaded");

        
        // 얼굴 감지를 위한 haar cascade 모델을 로드하는 작업 수행
        // 먼저 얼굴을 감지하고 잘라낸 얼굴은 인터프리터를 통해 전달
        // output으로 (age, gender)가 출력되게 하는 작업
        // 인터프리터 얼굴 감지 입력을 사용하지 않는다면 그것은 잘못된 출력을 주기 때문에 face detection을 사용합니다.
        try {
            // 입력 스트림 만들기: haarcascade_frontalface_alt에서 입력 스트림을 가져옵니다.
            // 이 파일은 일반적으로 res/raw 디렉토리에 저장됩니다.
            InputStream is = context.getResources().openRawResource(R.raw.haarcascade_frontalface_alt);

            // getDir를 사용하여 앱의 개인 디렉터리에 "cascade"라는 디렉터리가 만들어집니다.
            File cascadeDir = context.getDir("cascade", Context.MODE_PRIVATE);

            // 캐스케이드 파일 만들기: mCascadeFile이라는 파일 개체가 "haarcascade_frontalface_alt"라는 이름으로 캐스케이드 디렉터리 내에 만들어집니다.
            File mCascadeFile = new File(cascadeDir,"haarcascade_frontalface_alt");

            // 출력 스트림 만들기: 이 코드는 캐스케이드 모델 데이터를 mCascadeFile에 쓰기 위해 FileOutputStream을 만듭니다.
            FileOutputStream os = new FileOutputStream(mCascadeFile);

            // 입력 스트림에서 버퍼로 데이터를 읽고 출력 스트림에 씁니다.
            byte[] buffer = new byte[4096];
            int byteRead;

            while ((byteRead = is.read(buffer)) != -1){
                os.write(buffer,0, byteRead);
            }
            // 파일 쓰기가 완료되면 close() 메서드를 사용하여 입력 및 출력 스트림을 닫습니다.
            is.close();
            os.close();

            // 캐스케이드 분류기 로드: CascadeClassifier는 저장된 캐스케이드 모델의 파일 경로(mCascadeFile.getAbsolutePath())로 초기화됩니다.
            // 이렇게 하면 분류자가 얼굴 감지 모델을 로드할 수 있습니다.
            cascadeClassifier = new CascadeClassifier(mCascadeFile.getAbsolutePath());
            
            // 로그 분류기 로드: 파일이 성공적으로 로드되면 Haar 캐스케이드 분류기가 로드되었음을 나타내는 로그 메시지가 인쇄됩니다.
            Log.d("Age_gender_Recognition", "haarcascade Classifier is Loaded");
        }
        // 예외 처리: 파일 읽기 또는 쓰기 프로세스 중에 IOException이 발생하면 이를 포착하고 스택 추적을 인쇄합니다.
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int savedAgeValue = 0;
    public float savedGenderValue = 0;

    // 사전에 학습된 모델을 사용하여 주어진 입력 이미지(mat_image)에 대해 나이 및 성별 인식을 수행하는 메서드를 정의합니다.
    public Mat recognizeImage(Mat mat_image) {

        // 입력 이미지 뒤집기: 올바른 정렬을 위해 입력 이미지(mat_image)가 90도 회전됩니다.
        Core.flip(mat_image.t(), mat_image,1);

        // 흑백 이미지로 변환
        Mat grayscaleImage = new Mat();
        Imgproc.cvtColor(mat_image, grayscaleImage, Imgproc.COLOR_RGBA2GRAY);

        height = grayscaleImage.height();
        width = grayscaleImage.width();

        // 얼굴 크기 정의: 그레이스케일 이미지의 높이를 기준으로 얼굴의 크기가 계산됩니다.
        int absoluteFaceSize = (int)(height*0.1);
        // 감지된 얼굴은 MatOfRect 객체에 저장됩니다.
        MatOfRect faces = new MatOfRect();

        if(cascadeClassifier != null) {
            cascadeClassifier.detectMultiScale(grayscaleImage, faces,1.1,2,2,
                    new Size(absoluteFaceSize, absoluteFaceSize),new Size());
        }

        Rect[] faceArray = faces.toArray();
        // 감지된 얼굴들에 대해 반복
        for (int i=0; i<faceArray.length; i++) {
            // 얼굴 주위에 사각형 그리기: 감지된 얼굴을 시각화하기 위해서 rectangle()을 사용하여 얼굴 주위에 사각형을 그립니다.
            Imgproc.rectangle(mat_image, faceArray[i].tl(), faceArray[i].br(), new Scalar(0, 255, 0, 255),2);
            //                                                                   color:   R  G  B  alpha

            // 얼굴 영역 자르기: 관심 영역(ROI)은 감지된 얼굴의 왼쪽 상단 및 오른쪽 하단 모서리의 좌표를 사용하여 정의됩니다.
            Rect roi = new Rect((int)faceArray[i].tl().x,(int)faceArray[i].tl().y,
                    (int)(faceArray[i].br().x)-(int)(faceArray[i].tl().x),
                    (int)faceArray[i].br().y-(int)(faceArray[i].tl().y));

            // 자른 이미지를 비트맵으로 변환: cropped_rgba 이미지가 비트맵 개체로 변환됩니다.
            Mat cropped = new Mat(grayscaleImage, roi);
            Mat cropped_rgba = new Mat(mat_image, roi);

            Bitmap bitmap = null;
            bitmap = Bitmap.createBitmap(cropped_rgba.cols(), cropped_rgba.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(cropped_rgba, bitmap);

            // 비트맵 크기 조정: 비트맵이 96x96 픽셀의 고정 크기로 조정됩니다.
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,96,96,false);

            // 비트맵을 byte buffer로 변환
            ByteBuffer byteBuffer = convertBitmapToByteBuffer(scaledBitmap);

            // 인터프리터용 입력 준비: 바이트 버퍼는 인터프리터용 입력으로 사용할 개체 배열(입력)에 저장됩니다.
            Object[] input = new Object[1];
            input[0] = byteBuffer;

            // 인터프리터용 출력 맵 준비: 인터프리터의 출력 텐서를 저장하기 위해 맵(output_map)이 생성됩니다.
            // 연령 및 성별 배열이 초기화되어 출력 맵에 입력됩니다
            Map<Integer,Object> output_map = new TreeMap<>();
            float[][] age = new float[1][1];
            float[][] gender = new float[1][1];

            output_map.put(0, age);
            output_map.put(1, gender);

            // 인터프리터 실행
            interpreter.runForMultipleInputsOutputs(input, output_map);

            // 연령 및 성별 값 추출: 예상 연령 및 성별 값이 출력 맵에서 추출됩니다.
            Object age_o = output_map.get(0);
            Object gender_o = output_map.get(1);

            int age_value = (int)(float)Array.get(Array.get(age_o,0),0);
            float gender_value = (float)Array.get(Array.get(gender_o,0),0);

            // 성별 값 임계값: 임계값 0.70은 성별이 남성인지 여성인지 결정하는 데 사용됩니다.
            // 성별 값이 0.70보다 크면 여성으로 간주됩니다. 그렇지 않으면 남성으로 간주됩니다.
            if(gender_value > 0.70) {
                // 연령 및 성별 레이블 표시: 예상 연령 및 성별을 나타내는 텍스트 레이블은 putText()를 사용하여 cropped_rgba 이미지에 추가됩니다.
                Imgproc.putText(cropped_rgba,"Female, " + age_value
                        ,new Point(10,20),1,1.5, new Scalar(255,0,0,255),2);
                //         starting point                              color      R G B alpha
            }
            else {
                Imgproc.putText(cropped_rgba,"Male, " + age_value
                        ,new Point(10,20),1,1.5, new Scalar(0,0,255,255),2);
                //         starting point                              color      R G B alpha
            }
            // 연령 값과 성별 값 로그로 출력
            Log.d("Age_gender_recognition","Out " + age_value + "," + gender_value);

            // 연령 값과 성별 값 저장:
            savedAgeValue = age_value;
            savedGenderValue = gender_value;

            // 잘라낸 이미지를 원본 이미지로 다시 복사: cropped_rgba 이미지는 주석이 달린 얼굴을 표시하기 위해 원본 이미지(mat_image)로 다시 복사됩니다
            cropped_rgba.copyTo(new Mat(mat_image, roi));
        }

        // 이미지 뒤집기: 이미지를 -90도로 뒤집어 원래 방향으로 복원합니다.
        Core.flip(mat_image.t(), mat_image,0);
        // 주석이 달린 이미지 반환
        return mat_image;
    }

    // 크기 조정된 비트맵 이미지를 인터프리터 입력에 적합한 ByteBuffer 형식으로 변환하는 메소드를 정의합니다.
    private ByteBuffer convertBitmapToByteBuffer(Bitmap scaledBitmap) {

        // ByteBuffer 할당: byteBuffer라는 이름의 ByteBuffer가 4*1*size_image*size_image*3 바이트의 용량으로 할당됩니다.
        // 4개 요소는 float 데이터 유형,
        // 1개는 배치 크기(단일 이미지로 가정),
        // size_image는 이미지의 너비와 높이,
        // 3개는 RGB 채널용입니다.
        ByteBuffer byteBuffer;
        int size_image = 96;
        byteBuffer = ByteBuffer.allocateDirect(4*1*size_image*size_image*3);

        // 바이트 순서 설정
        byteBuffer.order(ByteOrder.nativeOrder());

        // 크기 조정된 비트맵에서 픽셀 값 가져오기: 크기 조정된 비트맵의 픽셀 값은 scaledBitmap.getPixels()를 사용하여 추출되고 intValues라는 배열에 저장됩니다.
        int[] intValues = new int[size_image*size_image];
        scaledBitmap.getPixels(intValues,0, scaledBitmap.getWidth(),0,0, scaledBitmap.getWidth(), scaledBitmap.getHeight());

        // 픽셀 값 반복: 코드는 중첩된 for 루프를 사용하여 비트맵 이미지의 각 픽셀을 반복합니다.
        int pixel = 0;
        for (int i=0; i<size_image; ++i) {
            for(int j=0; j<size_image; ++j) {
                final int val = intValues[pixel++];
                // RGB 값 추출: 각 픽셀에 대해 RGB 값은 비트 연산(>>) 및 비트 마스크(&)를 사용하여 추출됩니다.
                // 그런 다음 추출된 값을 255.0f로 나누어 0-255 범위에서 0-1 범위로 조정합니다.
                // 추출된 RGB 값은 byteBuffer.putFloat()를 사용하여 ByteBuffer에 넣습니다.
                // 값을 넣는 순서는 R, G, B로 각각 빨강, 녹색, 파랑 채널을 나타냅니다.
                byteBuffer.putFloat((((val>>16)& 0xFF))/255.0f);
                byteBuffer.putFloat((((val>>8)& 0xFF))/255.0f);
                byteBuffer.putFloat(((val & 0xFF))/255.0f);
            }
        }
        // ByteBuffer 반환: 채워진 ByteBuffer가 반환됩니다.
        return byteBuffer;
    }

    // 모델 파일을 MappedByteBuffer로 로드하는 역할을 하는 메소드를 정의합니다.
    private MappedByteBuffer loadModelFile(AssetManager assetManager, String modelPath) throws IOException {

        // AssetManager를 사용하여 지정된 modelPath에 대한 AssetFileDescriptor를 엽니다.
        AssetFileDescriptor assetFileDescriptor = assetManager.openFd(modelPath);

        // 입력 스트림 만들기: AssetFileDescriptor는 inputStream이라는 FileInputStream 개체를 만드는 데 사용됩니다.
        FileInputStream inputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());

        // 파일 채널 가져오기: FileInputStream 개체는 입력 스트림과 연결된 파일 채널(FileChannel)을 검색하는 데 사용됩니다
        FileChannel fileChannel = inputStream.getChannel();

        // 시작 오프셋 및 선언된 길이 검색: AssetFileDescriptor는 파일의 시작 오프셋 및 선언된 길이에 대한 정보를 제공합니다.
        // 이러한 값은 각각 getStartOffset() 및 getDeclaredLength() 메서드를 사용하여 가져옵니다.
        long startOffset = assetFileDescriptor.getStartOffset();
        long declaredLength = assetFileDescriptor.getDeclaredLength();

        // 파일 채널을 MappedByteBuffer에 매핑: 파일의 지정된 부분을 MappedByteBuffer에 매핑하기 위해 fileChannel.map() 메서드가 호출됩니다.
        // 맵 모드는 READ_ONLY로 설정되며 시작 오프셋과 선언된 길이가 인수로 제공됩니다.
        // 매핑된 바이트 버퍼 반환: 메서드는 로드된 모델 파일을 나타내는 생성된 MappedByteBuffer를 반환합니다.
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declaredLength);
    }
}
