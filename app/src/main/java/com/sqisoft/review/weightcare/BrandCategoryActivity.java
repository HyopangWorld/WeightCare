package com.sqisoft.review.weightcare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class BrandCategoryActivity extends AppCompatActivity {
    WeightCareDatabase weightdbHelper = null;
    Category category = null;
    Cursor queryArray = null;
    String tableName = "brand";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_category);

        Intent intent = getIntent();
        weightdbHelper = new WeightCareDatabase(this);

        createBtn(intent.getIntExtra("f_id", 0), intent.getStringExtra("bg_url"));
    }

    public String createBtn(int id, String bg){
        queryArray = weightdbHelper.selectAll(tableName, "f_id", id);
        if(queryArray == null){ return null; }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.brandLayout);
        gridLayout.setBackground(getDrawable(this.getResources().getIdentifier(bg, "drawable", this.getPackageName())));
        gridLayout.canScrollVertically(GridLayout.OVER_SCROLL_IF_CONTENT_SCROLLS);
        ViewGroup.LayoutParams gridLayoutParam = new ViewGroup.LayoutParams(540, 450);

        GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(GridLayout.spec(0,2), GridLayout.spec(0,2));
        gridParam.width = 480;  gridParam.height = 800;
        View bgView = new View(this);
        gridLayout.addView(bgView,gridParam);

        queryArray.moveToFirst();
        while (!queryArray.isAfterLast()){
            int b_id = queryArray.getInt(0);
            category = new Category(this, b_id, queryArray.getString(1), queryArray.getString(2), "");

            ImageButton nBtn = category.createBtn(); // 버튼 생성
            nBtn.setBackgroundDrawable(null);
            nBtn.setScaleType(ImageView.ScaleType.FIT_CENTER);
            nBtn.setOnClickListener(new View.OnClickListener() { //버튼 리스너 set
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BrandCategoryActivity.this, MenuActivity.class);
                    intent.putExtra("b_id", v.getId());
                    startActivity(intent);
                }
            });
            gridLayout.addView(nBtn,gridLayoutParam);

            queryArray.moveToNext();
        }
        queryArray.close();

        return "";
    }
}
