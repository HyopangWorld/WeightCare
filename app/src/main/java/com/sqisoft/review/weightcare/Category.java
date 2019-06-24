package com.sqisoft.review.weightcare;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Category extends AppCompatActivity {
    /*
    *  category를 구현하기 위한 class
    *  1. 버튼 생성시 공통되는 부분 묶음 메서드
    *  2. DB에서 받아온 category의 정보를 가지고 있다.
    * */
    private int id;
    private String name;
    private String img_url;
    private String bg_url;
    private Context context;

    public Category(){}

    public Category(Context context, int id, String name, String img_url , String bg_url){
        this.context = context;
        this.id = id;
        this.name = name;
        this.img_url = img_url;
        this.bg_url = bg_url;
    }

    public ImageButton createBtn(){
        ImageButton nBtn;

         //img btn 생성
         nBtn = new ImageButton(this.context);
         nBtn.setImageResource(this.context.getResources().getIdentifier(getImg_url(), "drawable", this.context.getPackageName()));
         nBtn.setPadding(0,0,0,0);
         nBtn.setAdjustViewBounds(true);
         nBtn.setScaleType(ImageView.ScaleType.FIT_XY); // 이미지 크기에 상관없이 이미지틀에 크기를 맞춤
         nBtn.setId(this.id);
         nBtn.setTag(this.bg_url);

         return nBtn;
    }

    public Context getContext() {
        return this.context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public String getImg_url() {
        return this.img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBg_url() {
        return bg_url;
    }

    public void setBg_url(String bg_url) {
        this.bg_url = bg_url;
    }
}
