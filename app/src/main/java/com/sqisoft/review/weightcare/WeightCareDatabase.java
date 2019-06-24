package com.sqisoft.review.weightcare;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

public class WeightCareDatabase extends SQLiteOpenHelper {
    String[] tableNames = {
            "fastfood", "brand", "menu", "vegetable"
    };

    String[] insertDataes = {
            "insert into fastfood (name, img_url) values ('치킨', 'foodcate_chi');",
            "insert into fastfood (name, img_url) values ('피자', 'foodcate_piz');",
            "insert into fastfood (name, img_url) values ('햄버거', 'foodcate_ham');",

            "insert into brand (name, img_url, f_id) values ('bbq', 'bbq', 1);",
            "insert into brand (name, img_url, f_id) values ('bhc', 'bhc', 1);",
            "insert into brand (name, img_url, f_id) values ('kfc', 'kfc', 1);",
            "insert into brand (name, img_url, f_id) values ('교촌치킨', 'kyochon', 1);",

            "insert into brand (name, img_url, f_id) values ('도미노', 'domino', 2);",
            "insert into brand (name, img_url, f_id) values ('피자스쿨', 'pizzaschool', 2);",
            "insert into brand (name, img_url, f_id) values ('피자헛', 'pizzahut', 2);",
            "insert into brand (name, img_url, f_id) values ('미스터피자', 'mrpizza', 2);",

            "insert into brand (name, img_url, f_id) values ('맥도날드', 'macdonald', 3);",
            "insert into brand (name, img_url, f_id) values ('버거킹', 'burgerking', 3);",
            "insert into brand (name, img_url, f_id) values ('롯데리아', 'lotteria', 3);",
            "insert into brand (name, img_url, f_id) values ('맘스터치', 'monstouch', 3);",

            "insert into menu (name, kcal, img_url, b_id) values ('치즐링', 243, 'cheese',1);",
            "insert into menu (name, kcal, img_url, b_id) values ('치킨강정', 283, 'gangjoung',1);",
            "insert into menu (name, kcal, img_url, b_id) values ('황금올리브', 408, 'gold',1);",

            "insert into menu (name, kcal, img_url, b_id) values ('베이컨피자', 381, 'bacon',5);",
            "insert into menu (name, kcal, img_url, b_id) values ('불고기피자', 381, 'bulgogi',5);",
            "insert into menu (name, kcal, img_url, b_id) values ('포테이토피자', 305, 'potato',5);",

            "insert into menu (name, kcal, img_url, b_id) values ('콤비네이션피자', 242, 'combi',6);",
            "insert into menu (name, kcal, img_url, b_id) values ('고구마피자', 260, 'goguma',6);",
            "insert into menu (name, kcal, img_url, b_id) values ('페퍼로니피자', 223, 'pepper',6);",

            "insert into menu (name, kcal, img_url, b_id) values ('치즈킹', 318, 'cheeseking',7);",
            "insert into menu (name, kcal, img_url, b_id) values ('더블바비큐', 250, 'dbbabicu',7);",
            "insert into menu (name, kcal, img_url, b_id) values ('토핑킹', 301, 'topping',7);",

            "insert into menu (name, kcal, img_url, b_id) values ('따블퐈', 439, 'dbpha',8);",
            "insert into menu (name, kcal, img_url, b_id) values ('까르네콤보', 427, 'kkarnecombo',8);",
            "insert into menu (name, kcal, img_url, b_id) values ('로맨틱콤보', 504, 'romanticcombo',8);",

            "insert into menu (name, kcal, img_url, b_id) values ('1955버거', 504, 'mac1955',9);",
            "insert into menu (name, kcal, img_url, b_id) values ('빅맥', 512, 'bigmac',9);",
            "insert into menu (name, kcal, img_url, b_id) values ('상하이버거', 454, 'sanghai',9);",

            "insert into menu (name, kcal, img_url, b_id) values ('통새우스테이크버거', 839, 'shrimp',10);",
            "insert into menu (name, kcal, img_url, b_id) values ('불고기와퍼', 682, 'kingbulgogi',10);",
            "insert into menu (name, kcal, img_url, b_id) values ('콰트로치즈와퍼주니어', 446, 'quatro',10);",

            "insert into menu (name, kcal, img_url, b_id) values ('AZ버거', 830, 'az',11);",
            "insert into menu (name, kcal, img_url, b_id) values ('원조빅불', 523, 'bigbul',11);",
            "insert into menu (name, kcal, img_url, b_id) values ('모짜렐라인더버거', 715, 'mozza',11);",

            "insert into menu (name, kcal, img_url, b_id) values ('할라피뇨통살버거', 243, 'hala',12);",
            "insert into menu (name, kcal, img_url, b_id) values ('싸이버거', 283, 'ssye',12);",
            "insert into menu (name, kcal, img_url, b_id) values ('휠렛버거', 408, 'wall',12);",

            "insert into vegetable (name, kcal) values ('오이', 14);",
            "insert into vegetable (name, kcal) values ('방울토마토', 16);",
            "insert into vegetable (name, kcal) values ('샐러리', 12);"
    };

    String[] createDataes = {
            "create table if not exists fastfood( f_id integer primary key autoincrement, name TEXT, img_url TEXT );",
            "create table if not exists brand( b_id integer primary key autoincrement,name TEXT,img_url TEXT,f_id integer,FOREIGN KEY(f_id) REFERENCES fastfood(f_id));",
            "create table if not exists menu(id integer primary key autoincrement,name TEXT,kcal TEXT,img_url TEXT,b_id integer,foreign key(b_id) references brand(b_id));",
            "create table if not exists vegetable(v_id integer primary key autoincrement,name TEXT,kcal integer);"
    };

    SQLiteDatabase db;

    //db 연결
    public WeightCareDatabase(Context context) {
        super(context, "weightcare.db", null, 1);
        db = this.getWritableDatabase();
    }

    //data 존재 여부 확인
    public int isExistsData(){
        Cursor result = db.rawQuery("select exists ( select * from vegetable );", null);
        result.moveToFirst();
        int isExists = result.getInt(0);
        result.close();

        return isExists;
    }

    //테이블 삭제
    public void dropTable(){
        for (String tableName : tableNames) {
            db.execSQL("drop table "+ tableName + ";");
        }
    }

    //테이블 생성
    public void createTable(){
        for (String sql: createDataes) {
            db.execSQL(sql);
        }
    }

    //data 입력
    public void insertData(){
        for (String sql: insertDataes) {
            db.execSQL(sql);
        }
    }

    //table 전체 select
    public Cursor selectAll(String tbName){
        String sql = "select * from " + tbName + ";";
        Cursor results = db.rawQuery(sql, null);

        return results;
    }

    // id값에 해당하는 값 select
    public Cursor selectAll(String tbName, String idName, int id){
        String sql = "select * from " + tbName + " where "+idName+"="+id+";";
        Cursor results = db.rawQuery(sql, null);

        return results;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
