package com.sqisoft.review.weightcare;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    WeightCareDatabase weightdbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*table 생성*/
        this.weightdbHelper = new WeightCareDatabase(this);
//        weightdbHelper.dropTable();
        weightdbHelper.createTable();
        if(weightdbHelper.isExistsData() == 0){
            weightdbHelper.insertData();
        }

        Intent intent = new Intent(SplashActivity.this ,FoodCategoryActivity.class);
        startActivity(intent);
        finish();
    }
}
