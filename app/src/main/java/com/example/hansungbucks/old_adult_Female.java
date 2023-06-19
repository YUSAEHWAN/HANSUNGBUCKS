package com.example.hansungbucks;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hansungbucks.R;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

public class old_adult_Female extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private int totalCount = 0; // 수량이 저장되는 변수
    private int totalMoney = 0; // 총 금액이 저장되는 변수

    private TextToSpeech tts;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_old_adult_female);
        TextView totalCountTextView = findViewById(R.id.total_count);
        TextView totalPayTextView = findViewById(R.id.total_money);

        // 홈 버튼 클릭 이벤트를 설정합니다.
        ImageButton home_btn = findViewById(R.id.ui_home_button);
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(old_adult_Female.this, MainActivity.class);
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

        tts = new TextToSpeech(this, this);
        tts.setLanguage(new Locale("ko"));

        ImageButton btn_1 = findViewById(R.id.menu_1);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                increaseCount(totalCountTextView); // 버튼 1 클릭시 수량 증가

                TextView orderText = findViewById(R.id.order_text);
                // 이미지 소스에 따라 다른 TTS 메시지를 설정합니다.
                Drawable buttonImage = btn_1.getDrawable();

                String text;

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

                if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.americano_1).getConstantState())) {
                    text = "아메리카노는 간단하고 기본적인 커피 음료입니다. 다른 커피 음료에 비해 덜 쓴 맛과 향이 특징입니다.";
                } else if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.chocoba_juice_1).getConstantState())) {
                    text = "초코 바나나 주스는 초콜릿의 풍미와 바닐라의 향이 조화롭게 어우러져 고소하면서도 달콤한 맛을 즐길 수 있습니다.";
                } else {
                    text = "블루베리 치즈 케이크는 치즈 케이크의 부드러움과 고소함에 블루베리의 달콤함과 상큼함이 조화롭게 어우러져 특별한 맛을 즐길 수 있습니다.";
                }
                speakOut(text);
            }
        });

        ImageButton btn_2 = findViewById(R.id.menu_2);
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                increaseCount(totalCountTextView); // 버튼 1 클릭시 수량 증가

                TextView orderText = findViewById(R.id.order_text);
                // 이미지 소스에 따라 다른 TTS 메시지를 설정합니다.
                Drawable buttonImage = btn_2.getDrawable();

                String text;

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

                if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.cafe_latte_1).getConstantState())) {
                    text = "카페 라떼는 에스프레소와 스팀밀크를 조화롭게 섞어 부드럽고 풍부한 맛을 갖는 커피 음료입니다.";
                } else if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.cookie_cream_frappe_1).getConstantState())) {
                    text = "쿠키 앤 크림 프라페는 부드럽고 진한 초콜릿과 쿠키 조각이 들어간 상큼하고 시원한 음료로, 디저트와 커피의 맛을 동시에 즐길 수 있는 인기 있는 메뉴입니다.";
                } else {
                    text = "녹차 생크림 롤은 녹차의 풍부한 향과 부드러운 생크림이 어우러져 맛있게 즐길 수 있는 롤 케이크입니다.";
                }
                speakOut(text);
            }
        });

        ImageButton btn_3 = findViewById(R.id.menu_3);
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                increaseCount(totalCountTextView); // 버튼 1 클릭시 수량 증가

                TextView orderText = findViewById(R.id.order_text);
                // 이미지 소스에 따라 다른 TTS 메시지를 설정합니다.
                Drawable buttonImage = btn_3.getDrawable();

                String text;

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

                if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.cafuchino_1).getConstantState())) {
                    text = "카푸치노는 진한 커피 풍미와 부드러운 우유 풍미를 함께 즐길 수 있는 이탈리아식 커피 음료입니다.";
                } else if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.grapefruit_smoothie_1).getConstantState())) {
                    text = "자몽 에이드는 상큼하고 시원한 자몽 주스에 신선한 레몬 주스와 설탕을 섞어 만든 음료로, 상큼한 맛을 즐길 수 있습니다.";
                } else {
                    text = "아이스크림 크로플은 바삭하고 고소한 크로플 안에 신선한 아이스크림을 넣어 만든 달콤하고 부드러운 디저트입니다.";
                }
                speakOut(text);
            }
        });

        ImageButton btn_4 = findViewById(R.id.menu_4);
        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                increaseCount(totalCountTextView); // 버튼 1 클릭시 수량 증가

                TextView orderText = findViewById(R.id.order_text);
                // 이미지 소스에 따라 다른 TTS 메시지를 설정합니다.
                Drawable buttonImage = btn_4.getDrawable();

                String text;

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

                if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.caramel_1).getConstantState())) {
                    text = "카라멜 마끼아또는 진한 에스프레소에 부드러운 스팀밀크와 달콤한 카라멜 시럽이 조화롭게 어우러져, 고소하면서도 달콤한 맛을 느낄 수 있는 커피 음료입니다.";
                } else if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.lemon_smoothie_1).getConstantState())) {
                    text = "유자 레몬 에이드는 상큼한 레몬 주스와 향긋한 유자를 조합하여 만든 청량하고 상쾌한 음료로, 유자의 특별한 향과 레몬의 신선한 맛이 어우러져 맛있게 즐길 수 있습니다.";
                } else {
                    text = "쑥팥 생크림 케이크는 부드러운 생크림과 달콤한 쑥팥 크림이 케이크 안에 들어간 맛있는 디저트로, 고소한 쑥과 달콤한 팥의 조화로운 조합이 입안에서 펼쳐지는 케이크입니다.";
                }
                speakOut(text);
            }
        });

        ImageButton btn_5 = findViewById(R.id.menu_5);
        btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                increaseCount(totalCountTextView); // 버튼 1 클릭시 수량 증가

                TextView orderText = findViewById(R.id.order_text);
                // 이미지 소스에 따라 다른 TTS 메시지를 설정합니다.
                Drawable buttonImage = btn_5.getDrawable();

                String text;

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

                if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.choco_latte_1).getConstantState())) {
                    text = "초코 라떼는 커피와 초콜릿을 혼합한 음료입니다. 달콤하고 부드러운 커피 향이 조화롭게 어우러진 것이 특징입니다.";
                } else if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.mango_smoothie_1).getConstantState())) {
                    text = "망고 스무디는 망고, 얼음, 우유, 요거트를 갈아서 만든 음료입니다. 망고의 진한 과즙과 단맛을 즐길 수 있는 음료입니다.";
                } else {
                    text = "단호박 에그 샌드위치는 단호박과 계란이 조화롭게 어우러져 풍미와 영양을 동시에 즐길 수 있는 메뉴입니다. 건강하고 가볍지만 영양가가 높은 것이 특징입니다.";
                }
                speakOut(text);
            }
        });

        ImageButton btn_6 = findViewById(R.id.menu_6);
        btn_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                increaseCount(totalCountTextView); // 버튼 1 클릭시 수량 증가

                TextView orderText = findViewById(R.id.order_text);
                // 이미지 소스에 따라 다른 TTS 메시지를 설정합니다.
                Drawable buttonImage = btn_6.getDrawable();

                String text;

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

                if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.almond_latte_1).getConstantState())) {
                    text = "아몬드 라떼는 커피와 아몬드 우유를 혼합하여 만든 음료입니다. 부드럽고 고소한 맛이 특징입니다.";
                } else if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.mintchoco_frappe_1).getConstantState())) {
                    text = "민트 초코 프라페는 상큼한 민트와 달콤한 초콜릿을 혼합하여 만든 시원하고 상쾌한 음료입니다.";
                } else {
                    text = "딸기 생크림 케이크는 부드러운 케이크와 신선한 딸기, 생크림으로 구성되어 맛과 향이 조화로운 인기 있는 케이크입니다.";
                }
                speakOut(text);
            }
        });

        ImageButton btn_7 = findViewById(R.id.menu_7);
        btn_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                increaseCount(totalCountTextView); // 버튼 1 클릭시 수량 증가

                TextView orderText = findViewById(R.id.order_text);
                // 이미지 소스에 따라 다른 TTS 메시지를 설정합니다.
                Drawable buttonImage = btn_7.getDrawable();

                String text;

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

                if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.almond_americano_1).getConstantState())) {
                    text = "아몬드 아메리카노는 아메리카노에 아몬드 시럽을 추가한 커피 음료입니다. 커피의 향과 맛에 고소하고 부드러운 아몬드 맛이 특집입니다.";
                } else if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.strawberry_smoothie_1).getConstantState())) {
                    text = "딸기 스무디는 딸기, 얼음, 우유, 요거트를 갈아서 만든 음료입니다. 딸기의 달콤하고 상큼한 맛을 느낄 수 있는 음료입니다.";
                } else {
                    text = "딸기 치즈 마카롱은 부드럽고 촉촉한 과자에 신선한 딸기와 부드러운 치즈 크림을 채운 맛있는 디저트입니다.";
                }
                speakOut(text);
            }
        });

        ImageButton btn_8 = findViewById(R.id.menu_8);
        btn_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                increaseCount(totalCountTextView); // 버튼 1 클릭시 수량 증가

                TextView orderText = findViewById(R.id.order_text);
                // 이미지 소스에 따라 다른 TTS 메시지를 설정합니다.
                Drawable buttonImage = btn_8.getDrawable();

                String text;

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

                if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.dabang_coffee_1).getConstantState())) {
                    text = "다방 커피는 다른 커피에 비해 좀 더 부드럽고 달콤한 맛이 특징입니다.";
                } else if (buttonImage.getConstantState().equals(getResources().getDrawable(R.drawable.strawberry_banana_juice_1).getConstantState())) {
                    text = "딸기 바나나 주스는 딸기와 바나나의 자연스러운 달콤함과 풍부한 영양소를 함께 즐길 수 있는 인기 있는 음료 중 하나입니다.";
                } else {
                    text = "바닐라 마카롱은 부드럽고 촉촉한 과자에 달콤하고 풍부한 향의 바닐라가 더해진 디저트입니다.";
                }
                speakOut(text);
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

        Button btn_card = findViewById(R.id.card_button);
        btn_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "카드를 오른쪽 아래 투입구에 넣어주세요";
                speakOut(text);
            }
        });

        Button btn_help = findViewById(R.id.help_button);
        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "조금만 기다려주세요. 직원이 도와드리러 가고 있어요";
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
