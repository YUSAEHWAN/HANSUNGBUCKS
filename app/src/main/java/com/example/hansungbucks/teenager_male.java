package com.example.hansungbucks;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class teenager_male extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private int totalCount = 0; // 수량이 저장되는 변수
    private int totalMoney = 0; // 총 금액이 저장되는 변수

    private TextToSpeech tts;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_teenager_male);
        TextView totalCountTextView = findViewById(R.id.total_count);
        TextView totalPayTextView = findViewById(R.id.total_money);

        // 홈 버튼 클릭 이벤트를 설정합니다.
        ImageButton home_btn = findViewById(R.id.ui_home_button);
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(teenager_male.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // 커피 버튼 클릭 이벤트를 설정합니다.
        Button coffee_btn = findViewById(R.id.coffee_button);
        coffee_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이미지 버튼들의 ID를 사용하여 이미지를 변경합니다.
                ImageButton Button1 = findViewById(R.id.menu_1);
                Button1.setImageResource(R.drawable.americano_1);

                ImageButton Button2 = findViewById(R.id.menu_2);
                Button2.setImageResource(R.drawable.cafe_latte_1);

                ImageButton Button3 = findViewById(R.id.menu_3);
                Button3.setImageResource(R.drawable.cafuchino_1);

                ImageButton Button4 = findViewById(R.id.menu_4);
                Button4.setImageResource(R.drawable.caramel_1);

                ImageButton Button5 = findViewById(R.id.menu_5);
                Button5.setImageResource(R.drawable.choco_latte_1);

                ImageButton Button6 = findViewById(R.id.menu_6);
                Button6.setImageResource(R.drawable.almond_latte_1);

                ImageButton Button7 = findViewById(R.id.menu_7);
                Button7.setImageResource(R.drawable.almond_americano_1);

                ImageButton Button8 = findViewById(R.id.menu_8);
                Button8.setImageResource(R.drawable.dabang_coffee_1);
            }
        });

        // 음료 버튼 클릭 이벤트를 설정합니다.
        Button beverage_btn = findViewById(R.id.beverage_button);
        beverage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이미지 버튼들의 ID를 사용하여 이미지를 변경합니다.
                ImageButton Button1 = findViewById(R.id.menu_1);
                Button1.setImageResource(R.drawable.chocoba_juice_1);

                ImageButton Button2 = findViewById(R.id.menu_2);
                Button2.setImageResource(R.drawable.cookie_cream_frappe_1);

                ImageButton Button3 = findViewById(R.id.menu_3);
                Button3.setImageResource(R.drawable.grapefruit_smoothie_1);

                ImageButton Button4 = findViewById(R.id.menu_4);
                Button4.setImageResource(R.drawable.lemon_smoothie_1);

                ImageButton Button5 = findViewById(R.id.menu_5);
                Button5.setImageResource(R.drawable.mango_smoothie_1);

                ImageButton Button6 = findViewById(R.id.menu_6);
                Button6.setImageResource(R.drawable.mintchoco_frappe_1);

                ImageButton Button7 = findViewById(R.id.menu_7);
                Button7.setImageResource(R.drawable.strawberry_smoothie_1);

                ImageButton Button8 = findViewById(R.id.menu_8);
                Button8.setImageResource(R.drawable.strawberry_banana_juice_1);
            }
        });

        // 디저트 버튼 클릭 이벤트를 설정합니다.
        Button dessert_btn = findViewById(R.id.dessert_button);
        dessert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이미지 버튼들의 ID를 사용하여 이미지를 변경합니다.
                ImageButton Button1 = findViewById(R.id.menu_1);
                Button1.setImageResource(R.drawable.blueberry_cake_1);

                ImageButton Button2 = findViewById(R.id.menu_2);
                Button2.setImageResource(R.drawable.greentea_cake_1);

                ImageButton Button3 = findViewById(R.id.menu_3);
                Button3.setImageResource(R.drawable.icecream_croffle_1);

                ImageButton Button4 = findViewById(R.id.menu_4);
                Button4.setImageResource(R.drawable.mugwort_cake_1);

                ImageButton Button5 = findViewById(R.id.menu_5);
                Button5.setImageResource(R.drawable.sandwich_1);

                ImageButton Button6 = findViewById(R.id.menu_6);
                Button6.setImageResource(R.drawable.strawberry_cake_1);

                ImageButton Button7 = findViewById(R.id.menu_7);
                Button7.setImageResource(R.drawable.strawberry_macaron_1);

                ImageButton Button8 = findViewById(R.id.menu_8);
                Button8.setImageResource(R.drawable.vanilla_macaron_1);
            }
        });

        // 전체 취소 버튼 클릭 이벤트를 설정합니다.
        Button cancel_btn = findViewById(R.id.cancel_button);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText orderText = findViewById(R.id.order_text);
                TextView totalCountTextView = findViewById(R.id.total_count);
                TextView totalPayTextView = findViewById(R.id.total_money);

                try {
                    orderText.setText("상품을 선택해주세요"); // order_text 내용 지우기
                    totalCount = 0; // total_count 내용 초기화
                    totalMoney = 0; // total_money 내용 초기화

                    // 예외 처리 후에 수량과 금액 업데이트
                    totalCountTextView.setText("수량: " + totalCount + " 개");
                    totalPayTextView.setText("결제 금액: " + totalMoney + " 원");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ImageButton btn_1 = findViewById(R.id.menu_1);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseCount(totalCountTextView); // 버튼 1 클릭시 수량 증가

                TextView orderText = findViewById(R.id.order_text);
                Drawable buttonImage = btn_1.getDrawable();

                if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.americano_1).getConstantState())) {
                    String previousText = orderText.getText().toString();
                    String newOrderText = previousText + "아메리카노 : 2000원 \n";
                    orderText.setText(newOrderText);
                    increaseMoney_Coffee(totalPayTextView);   // 버튼 1 클릭시 금액 증가
                } else if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.chocoba_juice_1).getConstantState())) {
                    String previousText = orderText.getText().toString();
                    String newOrderText = previousText + "초코 바나나 주스 : 3000원 \n";
                    orderText.setText(newOrderText);
                    increaseMoney_Beverage(totalPayTextView);   // 버튼 1 클릭시 금액 증가
                } else {
                    String previousText = orderText.getText().toString();
                    String newOrderText = previousText + "블루베리 치즈 케이크 : 4000원 \n";
                    orderText.setText(newOrderText);
                    increaseMoney_Dessert(totalPayTextView);   // 버튼 1 클릭시 금액 증가
                }
            }
        });

        ImageButton btn_2 = findViewById(R.id.menu_2);
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseCount(totalCountTextView); // 버튼 2 클릭시 수량 증가

                TextView orderText = findViewById(R.id.order_text);
                Drawable buttonImage = btn_2.getDrawable();

                if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.cafe_latte_1).getConstantState())) {
                    String previousText = orderText.getText().toString();
                    String newOrderText = previousText + "카페라떼 : 2000원 \n";
                    orderText.setText(newOrderText);
                    increaseMoney_Coffee(totalPayTextView);   // 버튼 2 클릭시 금액 증가
                } else if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.cookie_cream_frappe_1).getConstantState())) {
                    String previousText = orderText.getText().toString();
                    String newOrderText = previousText + "쿠키 앤 크림 프라페 : 3000원 \n";
                    orderText.setText(newOrderText);
                    increaseMoney_Beverage(totalPayTextView);   // 버튼 2 클릭시 금액 증가
                } else {
                    String previousText = orderText.getText().toString();
                    String newOrderText = previousText + "녹차 생크림 롤 : 4000원 \n";
                    orderText.setText(newOrderText);
                    increaseMoney_Dessert(totalPayTextView);   // 버튼 2 클릭시 금액 증가
                }
            }
        });

        ImageButton btn_3 = findViewById(R.id.menu_3);
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseCount(totalCountTextView); // 버튼 3 클릭시 수량 증가

                TextView orderText = findViewById(R.id.order_text);
                Drawable buttonImage = btn_3.getDrawable();

                if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.cafuchino_1).getConstantState())) {
                    String previousText = orderText.getText().toString();
                    String newOrderText = previousText + "카푸치노 : 2000원 \n";
                    orderText.setText(newOrderText);
                    increaseMoney_Coffee(totalPayTextView);   // 버튼 3 클릭시 금액 증가
                } else if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.grapefruit_smoothie_1).getConstantState())) {
                    String previousText = orderText.getText().toString();
                    String newOrderText = previousText + "자몽 에이드 : 3000원 \n";
                    orderText.setText(newOrderText);
                    increaseMoney_Beverage(totalPayTextView);   // 버튼 3 클릭시 금액 증가
                } else {
                    String previousText = orderText.getText().toString();
                    String newOrderText = previousText + "아이스크림 크로플 : 4000원 \n";
                    orderText.setText(newOrderText);
                    increaseMoney_Dessert(totalPayTextView);   // 버튼 3 클릭시 금액 증가
                }
            }
        });

        ImageButton btn_4 = findViewById(R.id.menu_4);
        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseCount(totalCountTextView); // 버튼 4 클릭시 수량 증가

                TextView orderText = findViewById(R.id.order_text);
                Drawable buttonImage = btn_4.getDrawable();

                if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.caramel_1).getConstantState())) {
                    String previousText = orderText.getText().toString();
                    String newOrderText = previousText + "카라멜 마끼아또 : 2000원 \n";
                    orderText.setText(newOrderText);
                    increaseMoney_Coffee(totalPayTextView);   // 버튼 4 클릭시 금액 증가
                } else if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.lemon_smoothie_1).getConstantState())) {
                    String previousText = orderText.getText().toString();
                    String newOrderText = previousText + "유자레몬 에이드 : 3000원 \n";
                    orderText.setText(newOrderText);
                    increaseMoney_Beverage(totalPayTextView);   // 버튼 4 클릭시 금액 증가
                } else {
                    String previousText = orderText.getText().toString();
                    String newOrderText = previousText + "쑥밭 생크림 케이크 : 4000원 \n";
                    orderText.setText(newOrderText);
                    increaseMoney_Dessert(totalPayTextView);   // 버튼 4 클릭시 금액 증가
                }
            }
        });

        ImageButton btn_5 = findViewById(R.id.menu_5);
        btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseCount(totalCountTextView); // 버튼 5 클릭시 수량 증가

                TextView orderText = findViewById(R.id.order_text);
                Drawable buttonImage = btn_5.getDrawable();

                if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.choco_latte_1).getConstantState())) {
                    String previousText = orderText.getText().toString();
                    String newOrderText = previousText + "초코라떼 : 2000원 \n";
                    orderText.setText(newOrderText);
                    increaseMoney_Coffee(totalPayTextView);   // 버튼 5 클릭시 금액 증가
                } else if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.mango_smoothie_1).getConstantState())) {
                    String previousText = orderText.getText().toString();
                    String newOrderText = previousText + "망고 스무디 : 3000원 \n";
                    orderText.setText(newOrderText);
                    increaseMoney_Beverage(totalPayTextView);   // 버튼 5 클릭시 금액 증가
                } else {
                    String previousText = orderText.getText().toString();
                    String newOrderText = previousText + "단호박 에그 샌드위치 : 4000원 \n";
                    orderText.setText(newOrderText);
                    increaseMoney_Dessert(totalPayTextView);   // 버튼 5 클릭시 금액 증가
                }
            }
        });

        ImageButton btn_6 = findViewById(R.id.menu_6);
        btn_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseCount(totalCountTextView); // 버튼 6 클릭시 수량 증가

                TextView orderText = findViewById(R.id.order_text);
                Drawable buttonImage = btn_6.getDrawable();

                if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.almond_latte_1).getConstantState())) {
                    String previousText = orderText.getText().toString();
                    String newOrderText = previousText + "아몬드라떼 : 2000원 \n";
                    orderText.setText(newOrderText);
                    increaseMoney_Coffee(totalPayTextView);   // 버튼 6 클릭시 금액 증가
                } else if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.mintchoco_frappe_1).getConstantState())) {
                    String previousText = orderText.getText().toString();
                    String newOrderText = previousText + "민트초코 프라페 : 3000원 \n";
                    orderText.setText(newOrderText);
                    increaseMoney_Beverage(totalPayTextView);   // 버튼 6 클릭시 금액 증가
                } else {
                    String previousText = orderText.getText().toString();
                    String newOrderText = previousText + "딸기 생크림 케이크 : 4000원 \n";
                    orderText.setText(newOrderText);
                    increaseMoney_Dessert(totalPayTextView);   // 버튼 6 클릭시 금액 증가
                }
            }
        });

        ImageButton btn_7 = findViewById(R.id.menu_7);
        btn_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseCount(totalCountTextView); // 버튼 7 클릭시 수량 증가

                TextView orderText = findViewById(R.id.order_text);
                Drawable buttonImage = btn_7.getDrawable();

                if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.almond_americano_1).getConstantState())) {
                    String previousText = orderText.getText().toString();
                    String newOrderText = previousText + "아몬드 아메리카노 : 2000원 \n";
                    orderText.setText(newOrderText);
                    increaseMoney_Coffee(totalPayTextView);   // 버튼 7 클릭시 금액 증가
                } else if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.strawberry_smoothie_1).getConstantState())) {
                    String previousText = orderText.getText().toString();
                    String newOrderText = previousText + "딸기 스무디 : 3000원 \n";
                    orderText.setText(newOrderText);
                    increaseMoney_Beverage(totalPayTextView);   // 버튼 7 클릭시 금액 증가
                } else {
                    String previousText = orderText.getText().toString();
                    String newOrderText = previousText + "딸기 치즈 마카롱 : 4000원 \n";
                    orderText.setText(newOrderText);
                    increaseMoney_Dessert(totalPayTextView);   // 버튼 7 클릭시 금액 증가
                }
            }
        });

        ImageButton btn_8 = findViewById(R.id.menu_8);
        btn_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseCount(totalCountTextView); // 버튼 8 클릭시 수량 증가

                TextView orderText = findViewById(R.id.order_text);
                Drawable buttonImage = btn_8.getDrawable();

                if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.dabang_coffee_1).getConstantState())) {
                    String previousText = orderText.getText().toString();
                    String newOrderText = previousText + "다방커피 : 2000원 \n";
                    orderText.setText(newOrderText);
                    increaseMoney_Coffee(totalPayTextView);   // 버튼 8 클릭시 금액 증가
                } else if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.strawberry_banana_juice_1).getConstantState())) {
                    String previousText = orderText.getText().toString();
                    String newOrderText = previousText + "딸기 바나나 주스 : 3000원 \n";
                    orderText.setText(newOrderText);
                    increaseMoney_Beverage(totalPayTextView);   // 버튼 8 클릭시 금액 증가
                } else {
                    String previousText = orderText.getText().toString();
                    String newOrderText = previousText + "바닐라 마카롱 : 4000원 \n";
                    orderText.setText(newOrderText);
                    increaseMoney_Dessert(totalPayTextView);   // 버튼 8 클릭시 금액 증가
                }
            }
        });

        tts = new TextToSpeech(this, this);
        tts.setLanguage(new Locale("ko"));

        Button btn_card = findViewById(R.id.card_button);
        btn_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "카드를 오른쪽 아래 투입구에 넣어주세요";
                speakOut(text);
            }
        });

        Button btn_barcode = findViewById(R.id.barcode_button);
        btn_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "바코드를 왼쪽 아래에 스캔해주세요";
                speakOut(text);
            }
        });
        
    }

    // 수량 증가시켜주는 함수
    private void increaseCount(TextView totalCountTextView){
        totalCount++;
        totalCountTextView.setText("수량: " + totalCount + " 개");
    }

    // 금액 증가시켜주는 함수(커피 종류)
    private void increaseMoney_Coffee(TextView totalPayTextView){
        totalMoney += 2000;
        totalPayTextView.setText("결제금액: " + totalMoney + " 원");
    }

    // 금액 증가시켜주는 함수(음료 종류)
    private void increaseMoney_Beverage(TextView totalPayTextView){
        totalMoney += 3000;
        totalPayTextView.setText("결제금액: " + totalMoney + " 원");
    }

    // 금액 증가시켜주는 함수(디저트 종류)
    private void increaseMoney_Dessert(TextView totalPayTextView){
        totalMoney += 4000;
        totalPayTextView.setText("결제금액: " + totalMoney + " 원");
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.getDefault());

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language not supported");
            }
        } else {
            Log.e("TTS", "Initialization failed");
        }
    }

    private void speakOut(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
