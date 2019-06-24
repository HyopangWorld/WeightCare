package com.sqisoft.review.weightcare;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class FoodCategoryActivity extends AppCompatActivity {
    WeightCareDatabase weightdbHelper = null;
    Category category = null;
    Cursor queryArray = null;
    String tableName = "fastfood";
    String[] bg_url = {
            "brandcate_chi",
            "brandcate_piz",
            "brandcate_ham",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fastfoodcategory);

        this.weightdbHelper = new WeightCareDatabase(this);
        //음식 메뉴 버튼 생성
        this.createBtn();
    }

    public void createBtn(){
        queryArray = weightdbHelper.selectAll(tableName);
        if(queryArray == null){ return; }

        LinearLayout linear = (LinearLayout) findViewById(R.id.fastfoodLayout);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

        queryArray.moveToFirst();
        while (!queryArray.isAfterLast()){
            int f_id = queryArray.getInt(0);
            category = new Category(this, f_id, queryArray.getString(1), queryArray.getString(2), bg_url[(f_id-1)]); // category 정보 입력

            ImageButton nBtn = category.createBtn(); //버튼 생성
            nBtn.setLayoutParams(param);
            nBtn.setOnClickListener(new View.OnClickListener() { //버튼 리스너 set
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FoodCategoryActivity.this, BrandCategoryActivity.class);
                    intent.putExtra("f_id", v.getId());
                    intent.putExtra("bg_url", String.valueOf(v.getTag()));
                    startActivity(intent);
                }
            });
            linear.addView(nBtn);

            queryArray.moveToNext();
        }
        queryArray.close();
    }
}
