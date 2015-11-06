package com.example.liuyujie.testnote;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class SelectAct extends Activity implements OnClickListener {

	private Button s_delete, s_back;
	private TextView s_tv;
	private NotesDB notesDB;
	private SQLiteDatabase dbWriter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select);
		// System.out.println(getIntent().getIntExtra(NotesDB.ID, 0));
		s_delete = (Button) findViewById(R.id.s_delete);
		s_back = (Button) findViewById(R.id.s_back);
		s_tv = (TextView) findViewById(R.id.s_tv);
		notesDB = new NotesDB(this);
		dbWriter = notesDB.getWritableDatabase();
		s_back.setOnClickListener(this);
		s_delete.setOnClickListener(this);
		s_tv.setText(getIntent().getStringExtra(NotesDB.CONTENT));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.s_delete:
			deleteDate();
			finish();
			break;

		case R.id.s_back:
			finish();
			break;
		}
	}

	public void deleteDate() {
		dbWriter.delete(NotesDB.TABLE_NAME,
				"_id=" + getIntent().getIntExtra(NotesDB.ID, 0), null);
	}
}
