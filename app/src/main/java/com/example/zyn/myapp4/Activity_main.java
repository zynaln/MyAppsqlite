package com.example.zyn.myapp4;



import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class Activity_main extends Activity{
    public DBManager dbHelper;
    GridView mGridView;
    SQLiteDatabase mDb;
    SQLiteDatabaseDao dao;
    ArrayList<HashMap<String, Object>> listData;
    SimpleAdapter listItemAdapter;// 适配器

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        dbHelper = new DBManager(this);
        dbHelper.openDatabase();
        dbHelper.closeDatabase();

        dao = new SQLiteDatabaseDao();

        mGridView=(GridView)findViewById(R.id .MyGridView);
       GridView list =(GridView)findViewById(R.id .MyGridView);
        listItemAdapter = new SimpleAdapter(Activity_main.this,
                listData,R.layout.liem,
                new String[] { "Name",
                      }, new int[] { R.id.name});
        list.setAdapter(listItemAdapter);
        mGridView.setOnItemClickListener(new GridViewItemOnClick() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Intent intent = new Intent(Activity_main.this, Activity_list.class);
                startActivity(intent);
            }
        });

    }
    class SQLiteDatabaseDao {

        public SQLiteDatabaseDao() {
           /* mDb = openOrCreateDatabase("users.db",
                    SQLiteDatabase.CREATE_IF_NECESSARY, null);*/
      mDb = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);

            getAllData("Coursewares");
        }
        // 查询所有数据
        public void getAllData(String table) {
            Cursor c = mDb.rawQuery("select * from " + table, null);
            int columnsSize = c.getColumnCount();
            listData = new ArrayList<HashMap<String, Object>>();
            // 获取表的内容
            while (c.moveToNext()) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                for (int i = 0; i < columnsSize; i++) {
                    map.put("CoursewareId", c.getString(0));
                    map.put("Name", c.getString(1));
                    map.put("Keyword", c.getString(2));
                    map.put("Type", c.getString(3));
                    map.put("Description", c.getString(4));
                }
                listData.add(map);
            }
        }
    }
    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
        mDb.close();
    }


    private class GridViewItemOnClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int position,long arg3) {
            Toast.makeText(getApplicationContext(), position + "",
                    Toast.LENGTH_SHORT).show();
        }
    }
}

