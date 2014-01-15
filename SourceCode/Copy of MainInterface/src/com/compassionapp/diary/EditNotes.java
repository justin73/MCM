package com.compassionapp.diary;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.compassion.maininterface.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditNotes extends Activity
{
	private TextView titleLable;
	private EditText titleText;
	private EditText contentText;
	private Button saveBtn;
	private Button cancelBtn;
	private Button deleteBtn;
	private MynotesOperate mynotes = null;
	private SQLiteOpenHelper helper = null;
	private int noteId;

	private String dateofNotes;

	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.edit_notes);
		Typeface tf = Typeface.createFromAsset(getAssets(),"gothic.ttf");
		
		titleLable = (TextView) findViewById(R.id.textView1);
		titleLable.setTypeface(tf);
		titleText = (EditText) findViewById(R.id.editText1);
		titleText.setTypeface(tf);
		contentText = (EditText) findViewById(R.id.editText2);
		contentText.setTypeface(tf);
		saveBtn = (Button) findViewById(R.id.button1);
		saveBtn.setTypeface(tf);
		cancelBtn = (Button) findViewById(R.id.button2);
		cancelBtn.setTypeface(tf);
		deleteBtn = (Button) findViewById(R.id.button3);
		deleteBtn.setTypeface(tf);
		helper = new MyDatabaseHelper(this);
		saveBtn.setOnClickListener(new InsertOnClickListenerImpl());
		cancelBtn.setOnClickListener(new CancelOnClickListenerImpl());
		deleteBtn.setOnClickListener(new DeleteOnClickListenerImpl());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		dateofNotes = formatter.format(curDate);
		Intent intent = getIntent();
		noteId = Integer.parseInt(intent.getStringExtra("noteId"));
		String noteTitle = intent.getStringExtra("noteTitle");
		String noteContent = intent.getStringExtra("noteContent");
		titleText.setText(noteTitle);
		contentText.setText(noteContent);
	}

	private class InsertOnClickListenerImpl implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			EditNotes.this.mynotes = new MynotesOperate(EditNotes.this.helper.getWritableDatabase());
			EditNotes.this.mynotes.update(noteId, titleText.getText().toString(), contentText.getText().toString(), dateofNotes);
			Intent intent = new Intent(EditNotes.this, NotesList.class);
			EditNotes.this.startActivity(intent);
			EditNotes.this.finish();

		}
	}

	private class CancelOnClickListenerImpl implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			new AlertDialog.Builder(EditNotes.this).setIcon(R.drawable.icon).setTitle("Cancel?").setMessage("Do you want to cancel?").setNegativeButton("No", new DialogInterface.OnClickListener()
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
					intent.setClass(EditNotes.this, NotesList.class);
					startActivity(intent);
					EditNotes.this.finish();
				}
			}).show();

		}
	}

	private class DeleteOnClickListenerImpl implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			new AlertDialog.Builder(EditNotes.this)
			.setIcon(R.drawable.icon)
			.setTitle("Delete?")
			.setMessage("Do you want to delete this piece of diary?")
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
					EditNotes.this.mynotes = new MynotesOperate(EditNotes.this.helper.getWritableDatabase());
					EditNotes.this.mynotes.delete(noteId);
					Intent intent = new Intent();
					intent.setClass(EditNotes.this, NotesList.class);
					startActivity(intent);
					EditNotes.this.finish();
				}
			}).show();

		}
	}

	@Override
	// 這�?必須�?要不然的�?案件無法�?��
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Cancel?").setMessage("Do you want to quite without saving it ?").setNegativeButton("No", new DialogInterface.OnClickListener()
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
					intent.setClass(EditNotes.this, NotesList.class);
					startActivity(intent);
					EditNotes.this.finish();
				}
			}).show();

			return true;

		}

		else
		{
			return super.onKeyDown(keyCode, event);
		}
	}
}
