package com.example.hansungbucks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class detect_Activity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_gender);

        Intent intent = getIntent();
        int ageValue = intent.getIntExtra("AGE_VALUE", 0);
        float genderValue = intent.getFloatExtra("GENDER_VALUE", 0);

        ImageView gifImageView = findViewById(R.id.gif_image);
        Glide.with(this).asGif().load(R.drawable.canceling).into(gifImageView);


//        TextView ageTextView = findViewById(R.id.age_text);
//        ageTextView.setText(String.valueOf(ageValue));
//
//        TextView genderTextView = findViewById(R.id.gender_text);

        if (genderValue > 0.70)
        {
            String message = "예측 결과: " + ageValue + "살 여자입니다.";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//            genderTextView.setText("여자");
//            genderTextView.setText(String.valueOf(genderValue));
        }
        else
        {
            String message = "예측 결과: " + ageValue + "살 남자입니다.";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//            genderTextView.setText("남자");
//            genderTextView.setText(String.valueOf(genderValue));
        }


        if (ageValue <= 30 && genderValue > 0.70)
        {
            Intent intent1 = new Intent(detect_Activity.this, teenager_Female.class);
            startActivity(intent1);
        }
        else if (ageValue > 30 && ageValue <= 50 && genderValue > 0.70)
        {
            Intent intent2 = new Intent(detect_Activity.this, young_adult_Female.class);
            startActivity(intent2);
        }
        else if (ageValue > 51 && genderValue > 0.70)
        {
            Intent intent3 = new Intent(detect_Activity.this, old_adult_Female.class);
            startActivity(intent3);
        }
        else if (ageValue <= 30 && genderValue <= 0.70)
        {
            Intent intent4 = new Intent(detect_Activity.this, teenager_male.class);
            startActivity(intent4);
        }
        else if (ageValue > 30 && ageValue <= 50 && genderValue <= 0.70)
        {
            Intent intent5 = new Intent(detect_Activity.this, young_adult_male.class);
            startActivity(intent5);
        }
        else if (ageValue > 51 && genderValue <= 0.70)
        {
            Intent intent6 = new Intent(detect_Activity.this, old_adult_male.class);
            startActivity(intent6);
        }

    }
}
