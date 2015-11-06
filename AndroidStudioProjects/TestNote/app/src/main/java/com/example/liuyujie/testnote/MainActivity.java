package com.example.liuyujie.testnote;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity implements OnClickListener {

	private ListView lv;
	private Intent i;
	private MyAdapter adapter;
	private NotesDB notesDB;
	private SQLiteDatabase dbReader;
	private Cursor cursor;
	Button search=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		search= (Button) findViewById(R.id.search);
		initView();
        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View source) {
                String key = ((EditText) findViewById(R.id.key)).getText().toString();
                Cursor cursor = notesDB.getReadableDatabase().rawQuery(
                        "select * from notes where content like ? or time like?",
                        new String[]{"%" + key + "%","%" + key + "%"});
                Bundle data = new Bundle();
                data.putSerializable("data", converCursorToList(cursor));
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtras(data);
                startActivity(intent);
            }
        });
	}
    protected ArrayList<Map<String, String>>
    converCursorToList(Cursor cursor)
    {
        ArrayList<Map<String, String>> result = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Map<String, String> map = new HashMap<>();
            map.put("content", cursor.getString(1));
            map.put("time", cursor.getString(2));
            result.add(map);
        }
        return result;
    }


    public void initView() {
		lv = (ListView) findViewById(R.id.list);
		Button textbtn = (Button) findViewById(R.id.text);
		textbtn.setOnClickListener(this);
		notesDB = new NotesDB(this);
		dbReader = notesDB.getReadableDatabase();
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				cursor.moveToPosition(position);
				Intent i = new Intent(MainActivity.this, SelectAct.class);
				i.putExtra(NotesDB.ID,
						cursor.getInt(cursor.getColumnIndex(NotesDB.ID)));
				i.putExtra(NotesDB.CONTENT, cursor.getString(cursor
						.getColumnIndex(NotesDB.CONTENT)));
				i.putExtra(NotesDB.TIME,
						cursor.getString(cursor.getColumnIndex(NotesDB.TIME)));
				i.putExtra(NotesDB.PATH,
						cursor.getString(cursor.getColumnIndex(NotesDB.PATH)));
				i.putExtra(NotesDB.VIDEO,
						cursor.getString(cursor.getColumnIndex(NotesDB.VIDEO)));
				startActivity(i);
			}
		});
	}

	@Override
	public void onClick(View v) {
		i = new Intent(this, AddContent.class);
		if (v.getId()==R.id.text) {
			i.putExtra("flag", "1");
			startActivity(i);
		}
	}

	public void selectDB() {
		cursor = dbReader.query(NotesDB.TABLE_NAME, null, null, null, null,
				null, null);
		adapter = new MyAdapter(this, cursor);
		lv.setAdapter(adapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		selectDB();
	}

}
