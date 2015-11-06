package com.example.liuyujie.testnote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.List;
import java.util.Map;

public class ResultActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.list);
        Intent intent = getIntent();
        // 获取该intent所携带的数据
        Bundle data = intent.getExtras();
        // 从Bundle数据包中取出数据
        List<Map<String, String>> list = (List<Map<String, String>>)
                data.getSerializable("data");
        // 将List封装成SimpleAdapter
        SimpleAdapter adapter = new SimpleAdapter(ResultActivity.this,
                list,R.layout.cell, new String[] {"content","time"},
                new int[] {R.id.list_content,R.id.list_time });
        // 填充ListView
        listView.setAdapter(adapter);
    }
}