package com.sqisoft.review.weightcare;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

public class MyPageAdapter extends PagerAdapter {
    private LayoutInflater mInflater;
    List<Drawable> bg_url = null;
    String[] kcalTxt;
    Context context;

    public MyPageAdapter(Context context, List<Drawable> bg_url, String[] kcalTxt) {
        super();
        this.context = context;
        this.bg_url = bg_url;
        mInflater = LayoutInflater.from(context);
        this.kcalTxt = kcalTxt;
    }

    // ViewPager View를 생성/등록
    public Object instantiateItem(View pager, int position){
        View v = mInflater.inflate(R.layout.page, null);
        LinearLayout background = (LinearLayout) v.findViewById(R.id.background);
        background.setBackground((Drawable) bg_url.get(position));

        //kcal 판 생성
        TextView kcalpan = new TextView(context);
        int tag = View.generateViewId(); // 1.자동 ID 값 생성
        kcalpan.setId(tag); // 2-1.ID 값 넣기
        kcalpan.setVisibility(View.GONE);
        kcalpan.setText(kcalTxt[position]);
        kcalpan.setBackgroundResource(R.drawable.kcalpan);
        kcalpan.setTextSize(36.0f);
        kcalpan.setGravity(Gravity.BOTTOM);
        kcalpan.setPadding(0,0,0,45);

        LinearLayout.LayoutParams layoutParamsKcalPan = new LinearLayout.LayoutParams(800, 650 );
        layoutParamsKcalPan.setMargins(132, 50, 0, 0);

        // kcal계산버튼 생성
        final Button kcalBtn = new Button(context);
        kcalBtn.setGravity(Gravity.TOP);
        kcalBtn.setBackgroundResource(R.drawable.kcalbtn);
        kcalBtn.setTag(tag); // 2-2. ID 값을 button tag로 넘겨주기
        kcalBtn.setOnClickListener(new View.OnClickListener() { //버튼 리스너 set
            @Override
            public void onClick(View v) {
                View kcalpanView = v.getRootView().findViewById((Integer)v.getTag()); // 3. button tag 값을 통해 kcalPan ID를 찾는다.
                if(kcalpanView.getVisibility() == View.GONE) { kcalpanView.setVisibility(View.VISIBLE); }
                else{ kcalpanView.setVisibility(View.GONE); }
            }
        });

        LinearLayout.LayoutParams layoutParamsKcalBtn = new LinearLayout.LayoutParams(160, 160);
        layoutParamsKcalBtn.setMargins(60, 550, 0, 0);

        background.addView(kcalBtn, layoutParamsKcalBtn);
        background.addView(kcalpan, layoutParamsKcalPan);

        ((ViewPager)pager).addView(v, null);

        return v;
    }

    // view 갯수
    public int getCount() {
        return 3;
    }

    //삭제메서드
    public void destroyItem(View pager, int position, Object view) {
        ((ViewPager)pager).removeView((View)view);
    }

    //사용여부 반환 메서드
    public boolean isViewFromObject(View v, Object obj) {
        return v == obj;
    }
}