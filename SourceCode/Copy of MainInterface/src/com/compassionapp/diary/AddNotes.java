package com.compassionapp.diary;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.compassionapp.maininterface.MainActivity;
import com.compassion.maininterface.R;
import com.compassionapp.meditation.countdownPage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddNotes extends Activity
{

	private TextView titleLable;
	private EditText titleText;
	private EditText contentText;
	private Button saveBtn;
	private Button cancelBtn;
	private MynotesOperate mynotes = null;
	private SQLiteOpenHelper helper = null;

	private String dateofNotes;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.add_notes);
		Typeface tf = Typeface.createFromAsset(getAssets(),"gothic.ttf");
		titleLable = (TextView) findViewById(R.id.textView1);
		titleLable.setTypeface(tf);
		titleText = (EditText) findViewById(R.id.editText1);
		titleText.setTypeface(tf);
		contentText = (EditText) findViewById(R.id.editText2);
		contentText.setTypeface(tf);;
		saveBtn = (Button) findViewById(R.id.button1);
		saveBtn.setTypeface(tf);
		cancelBtn = (Button) findViewById(R.id.button2);
		cancelBtn.setTypeface(tf);
		saveBtn.setOnClickListener(new InsertOnClickListenerImpl());
		cancelBtn.setOnClickListener(new CancelOnClickListenerImpl());
		helper = new MyDatabaseHelper(this);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		dateofNotes = formatter.format(curDate);

	}

	private class InsertOnClickListenerImpl implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			AddNotes.this.mynotes = new MynotesOperate(AddNotes.this.helper.getWritableDatabase());
			AddNotes.this.mynotes.insert(titleText.getText().toString(), contentText.getText().toString(), dateofNotes);
			Intent intent = new Intent(AddNotes.this, NotesList.class);
			AddNotes.this.startActivity(intent);
			AddNotes.this.finish();

		}
	}

	private class CancelOnClickListenerImpl implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			Intent intent = new Intent(AddNotes.this, NotesList.class);
			AddNotes.this.startActivity(intent);
			AddNotes.this.finish();

		}
	}

	@Override
	// 這�?必須�?要不然的�?案件無法�?��
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
				new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon)
				.setTitle("Cancel?")
				.setMessage("Do you wanna cancel ?")
				.setNegativeButton("No", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						
					}
				}).setPositiveButton("Yes", new DialogInterface.OnClickListener()

				{
					public void onClick(DialogInterface dialog, int whichButton)
					{
						
						Intent intent = new Intent();
						intent.setClass(AddNotes.this, NotesList.class);
						startActivity(intent);
						AddNotes.this.finish();
					}
				}).show();

				return true;
			
			}
			
		else{
			return super.onKeyDown(keyCode, event);
		}
	}

}
