package com.sqisoft.review.weightcare;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    List bg_url = new ArrayList<Drawable>();
    List vKcalies = new ArrayList<Integer>();
    int mKcal;
    String[] kcalTxtArr;

    Intent intent = null;
    WeightCareDatabase weightdbHelper = null;
    Cursor mqueryArray = null;
    Cursor vqueryArray = null;
    String tableName = "menu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        weightdbHelper = new WeightCareDatabase(this);

        intent = getIntent();
        int b_id = intent.getIntExtra("b_id", 0);

        if (b_id == 2 || b_id == 3 || b_id == 4) {
            // 이미지 없는 브랜드 처리
            setContentView(R.layout.preparing);
        } else {
            //메뉴 슬라이드 생성
            createMenuSlide(b_id);
        }
    }

    public void createMenuSlide(int b_id) {
        vqueryArray = weightdbHelper.selectAll("vegetable");
        mqueryArray = weightdbHelper.selectAll(tableName, "b_id", b_id);
        if (mqueryArray == null && vqueryArray == null) {
            return;
        }

        //채소 정보 읽어오기
        vqueryArray.moveToFirst();
        while (!vqueryArray.isAfterLast()) {
            vKcalies.add(vqueryArray.getInt(2));
            vqueryArray.moveToNext();
        }
        vqueryArray.close();

        //menu 정보 읽어오기 , kcal 계산하기
        kcalTxtArr = new String[mqueryArray.getCount()];
        int i = 0;

        mqueryArray.moveToFirst();
        while (!mqueryArray.isAfterLast()) {
            bg_url.add(getDrawable(this.getResources().getIdentifier(mqueryArray.getString(3), "drawable", this.getPackageName()))); //list에 넣기
            mKcal = Integer.valueOf(mqueryArray.getString(2));

            String kcalTxt = "";
            for (Object vKcal : vKcalies) {
                kcalTxt += "   " + (mKcal / ((Integer) vKcal)) + "   ";
            }
            kcalTxtArr[i] = kcalTxt;
            i++;

            mqueryArray.moveToNext();
        }
        mqueryArray.close();

        //ViewPager 생성
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyPageAdapter(this, bg_url, kcalTxtArr)); // 이미지 가져오기
    }

    //뒤로가기 버튼
    public void clickBack(View v) {
        super.onBackPressed();
    }

    public void clickSns(View view) {
        // WRITE_EXTERNAL_STORAGE 외부 공간 사용 권한 허용
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        view.getRootView().buildDrawingCache();   // 캡처할 뷰를 지정하여 buildDrawingCache() 한다
        Bitmap captureView = view.getRootView().getDrawingCache();   // 캡쳐할 뷰를 지정하여 getDrawingCache() 한다

        FileOutputStream fos;   // FileOutputStream 이용 파일 쓰기 한다
        String strFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures";
        File folder = new File(strFolderPath);
        if (!folder.exists()) {  // 해당 폴더 없으면 만들어라
            folder.mkdirs();
        }

        String strFilePath = strFolderPath + "/" + "weightcare" + ".png";
        File fileCacheItem = new File(strFilePath);

        try {
            fos = new FileOutputStream(fileCacheItem);
            captureView.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Toast.makeText(this, "화면캡쳐완료", Toast.LENGTH_SHORT).show();
        }

        File dirName = new File(Environment.getExternalStorageDirectory().getAbsolutePath()  + "/Pictures");  //디렉토리를 지정합니다.
        File file = new File(dirName, "weightcare.png"); //image 파일의 경로를 설정합니다.

        Uri uri = FileProvider.getUriForFile(MenuActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);

        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType("image/*");

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_SUBJECT, "[비만캐리] 평소에 생각없이 먹던 음식, 다이어트 푸드와 비교해서 고칼로리를 체감해보자!");
        intent.putExtra(Intent.EXTRA_TEXT, "비만캐리 앱으로 다른 음식도 직접 비교해 보세요!");

        startActivity(Intent.createChooser(intent, "함께 공유해보세요"));
    }
}
