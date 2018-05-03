package com.example.rog.tourism.cityselect.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.example.rog.tourism.TourismApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ------------------------------------------
 * tourism
 * com.example.rog.tourism.cityselect.data
 * ROG
 * 2018/04/30/11:09
 * by KinFish
 * -------------------------------------------
 **/
public class DBManager {
    private static final String ASSETS_NAME = "china_cities";
    private static final String DB_NAME = "china_cities.db";
    private static final String TABLE_NAME ="china_cities";
    private static final String CITYCODE ="citycode";
    private static final String CITYNAME = "cityname";
    private static final String PINYIN = "pinyin";
    private static final int BUFFER_SIZE = 1024;
    private String DB_PATH;
    private Context mContext;
    private Map<String, SQLiteDatabase> databases = new HashMap<String, SQLiteDatabase>();
    public DBManager(Context context) {
        this.mContext = context;
        /*内部数据库绝对路径*/
        DB_PATH = File.separator + "data"
                + Environment.getDataDirectory().getAbsolutePath() + File.separator
                + context.getPackageName() + File.separator + "databases" + File.separator;
    }

    /*复制数据库文件*/
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void copyDBFiles(){
        File dir = new File(DB_PATH);
        if (!dir.exists()){
            dir.mkdirs();
        }
        File dbFile = new File(DB_PATH+DB_NAME);
        if(!dbFile.exists()){
            InputStream is;
            OutputStream os;
            try {
                is = mContext.getResources().getAssets().open(ASSETS_NAME);
                os = new FileOutputStream(dbFile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int length;
                while((length = is.read(buffer, 0, buffer.length)) > 0){
                    os.write(buffer,0,length);
                }
                os.flush();
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    /*从数据库中获取所有城市的数据并返回*/
    public List<China_Cities> getAllCities(){
        // 初始化，只需要调用一次
// 获取管理对象，因为数据库需要通过管理对象才能够获取
        DBManager mg = new DBManager(TourismApp.getContext());
// 通过管理对象获取数据库
        SQLiteDatabase db = mg.getDatabase(DB_NAME);
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        List<China_Cities> result = new ArrayList<>();
        China_Cities china_cities;
        while (cursor.moveToNext()){
            String citycode = cursor.getString(cursor.getColumnIndex(CITYCODE));
            String cityname = cursor.getString(cursor.getColumnIndex(CITYNAME));
            String pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));

           /*表结构：citycode pinyin cityname*/
            china_cities = new China_Cities(citycode,pinyin,cityname);
            result.add(china_cities);

        }
        cursor.close();
        db.close();
        Collections.sort(result,new China_CitiesComparator());
        return result;
    }

    private SQLiteDatabase getDatabase(String dbfile) {
        if(databases.get(dbfile) != null){
            Log.i("DBM", String.format("Return a database copy of %s", dbfile));
            return (SQLiteDatabase) databases.get(dbfile);
        }
        if(mContext==null)
            return null;
        Log.i("DBM", String.format("Create database %s", dbfile));
        String spath = DB_PATH;
        String sfile = getDatabaseFile(dbfile);
        File file = new File(sfile);
        SharedPreferences dbs = mContext.getSharedPreferences(DBManager.class.toString(), 0);
        boolean flag = dbs.getBoolean(dbfile, false); // Get Database file flag, if true means this database file was copied and valid
        if(!flag || !file.exists()){
            file = new File(spath);
            if(!file.exists() && !file.mkdirs()){
                Log.i("DBM", "Create \""+spath+"\" fail!");
                return null;
            }
            if(!copyAssetsToFilesystem(dbfile, sfile)){
                Log.i("DBM", String.format("Copy %s to %s fail!", dbfile, sfile));
                return null;
            }
            dbs.edit().putBoolean(dbfile, true).commit();
        }
        SQLiteDatabase db = SQLiteDatabase.openDatabase(sfile, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        if(db != null){
            databases.put(dbfile, db);
        }
        return db;
    }

    private boolean copyAssetsToFilesystem(String assetsSrc, String des){
        Log.i("DBM", "Copy "+assetsSrc+" to "+des);
        InputStream istream = null;
        OutputStream ostream = null;
        try{
            AssetManager am = mContext.getAssets();
            istream = am.open(assetsSrc);
            ostream = new FileOutputStream(des);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = istream.read(buffer))>0){
                ostream.write(buffer, 0, length);
            }
            istream.close();
            ostream.close();
        }
        catch(Exception e){
            e.printStackTrace();
            try{
                if(istream!=null)
                    istream.close();
                if(ostream!=null)
                    ostream.close();
            }
            catch(Exception ee){
                ee.printStackTrace();
            }
            return false;
        }
        return true;
    }

    private String getDatabaseFile(String dbfile) {
        return DB_PATH+"/"+dbfile;
    }

    public String searchCitycode(String cityname){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH+DB_NAME,null);
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME +"  where "+CITYNAME +" = '"+cityname+"'", null);
        cursor.close();
        db.close();
        return cursor.getString(cursor.getColumnIndex(CITYCODE));
    }

    /*城市查找，对输入的字母进行模糊查询*/
    public List<China_Cities> searchCity(final String keyword){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH+DB_NAME,null);
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME +" where cityname like \"%" + keyword
                + "%\" or pinyin like \"%" + keyword + "%\"", null);
        List<China_Cities> result = new ArrayList<>();
        China_Cities china_cities;
        while (cursor.moveToNext()){
            String citycode = cursor.getString(cursor.getColumnIndex(CITYCODE));
            String cityname = cursor.getString(cursor.getColumnIndex(CITYNAME));
            String pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
            china_cities = new China_Cities(citycode, pinyin,cityname);
            result.add(china_cities);
        }
        cursor.close();
        db.close();
        Collections.sort(result, new China_CitiesComparator());
        return result;


    }

    /*将城市信息按照字母排序*/
    private class China_CitiesComparator implements Comparator<China_Cities> {
        @Override
        public int compare(China_Cities lhs, China_Cities rhs) {
            String a = lhs.getPinyin().substring(0, 1);
            String b = rhs.getPinyin().substring(0, 1);
            return a.compareTo(b);
        }
    }
}
