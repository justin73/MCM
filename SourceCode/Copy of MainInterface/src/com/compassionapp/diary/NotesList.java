package com.compassionapp.diary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.compassion.maininterface.R;
import com.compassionapp.maininterface.MainActivity;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

public class NotesList extends Activity
{
	private SQLiteOpenHelper helper = null;
	private MynotesOperate mynotesOperate = null;
	private ListView listView;
	private LinearLayout linearLayout = null;
	private int currentPage = 1;
	private int lineSize = 15;
	private int allRecorders = 0;
	private int pageSize = 1;
	private int lastItem = 0;
	private SimpleAdapter simpleAdapter = null;
	private LinearLayout loadLayout = null;
	private TextView loadInfo = null;
	private List<Map<String, Object>> all = null;
	private android.widget.LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notes_list);
		Typeface tf = Typeface.createFromAsset(getAssets(),"gothic.ttf");
		this.linearLayout = (LinearLayout) super.findViewById(R.id.LinearLayout1);
		this.loadLayout = new LinearLayout(this);
		helper = new MyDatabaseHelper(this);
		loadInfo = new TextView(this);
		loadInfo.setTypeface(tf);
		loadInfo.setClickable(true);
		loadInfo.setTextColor(R.drawable.black);
		this.loadInfo.setGravity(Gravity.CENTER);
		loadInfo.setTextSize(25.0f);
		loadLayout.addView(this.loadInfo, this.layoutParams);
		this.loadLayout.setGravity(Gravity.CENTER);
		this.showAllData();
		this.pageSize = (this.allRecorders + this.currentPage - 1) / this.lineSize;
		this.loadInfo.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(NotesList.this, AddNotes.class);
				NotesList.this.startActivity(intent);
				NotesList.this.finish();

			}
		});

	}

	private class OnScrollListenerImpl implements OnScrollListener
	{

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
		{
			// TODO Auto-generated method stub
			NotesList.this.lastItem = firstVisibleItem + visibleItemCount - 1;
			if (NotesList.this.pageSize == NotesList.this.currentPage)
			{
				loadInfo.setText(" + ");
			} else if (allRecorders <= 15)
			{
				loadInfo.setText("+");
			} else
			{
				loadInfo.setText("Loading... ");
			}
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState)
		{
			// TODO Auto-generated method stub
			if (NotesList.this.lastItem == NotesList.this.simpleAdapter.getCount()// 判断当前记录是否已在�?���?
					&& NotesList.this.currentPage < NotesList.this.pageSize && scrollState == OnScrollListener.SCROLL_STATE_IDLE)
			{

				NotesList.this.currentPage++;
				NotesList.this.listView.setSelection(NotesList.this.lastItem);
				NotesList.this.appendData();

			}
		}

	}

	private void showAllData()
	{
		this.helper = new MyDatabaseHelper(NotesList.this);
		this.listView = new ListView(NotesList.this);
		this.listView.setOnItemClickListener(new OnItemClickListenerImpl());
		listView.setCacheColorHint(00000000);// 防止下滑时的黑色背景
		MynotesCursor cur = new MynotesCursor(this.helper.getReadableDatabase());
		this.allRecorders = cur.getCount();
		this.all = cur.find(this.currentPage, this.lineSize);
		this.simpleAdapter = new SimpleAdapter(this, all, R.layout.tab_info, new String[]
		{ "title", "date" }, new int[]
		{ R.id.title, R.id.date });
		
		this.listView.addFooterView(NotesList.this.loadLayout);
		this.listView.setAdapter(this.simpleAdapter);
		this.listView.setOnScrollListener(new OnScrollListenerImpl());
		registerForContextMenu(this.listView);
		NotesList.this.linearLayout.addView(this.listView);
	}

	private void appendData()
	{
		MynotesCursor cur = new MynotesCursor(this.helper.getReadableDatabase());
		List<Map<String, Object>> newData = cur.find(this.currentPage, this.lineSize);
		this.all.addAll(newData);
		this.simpleAdapter.notifyDataSetChanged();
	}

	private class OnItemClickListenerImpl implements OnItemClickListener
	{

		@SuppressWarnings("unchecked")
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
		{
			Map<String, Object> map = (Map<String, Object>) arg0.getItemAtPosition(arg2);

			Intent intent = new Intent();
			intent.putExtra("noteId", map.get("id").toString());
			intent.putExtra("noteTitle", map.get("title").toString());
			intent.putExtra("noteContent", map.get("content").toString());
			intent.setClass(NotesList.this, EditNotes.class);
			startActivity(intent);
			NotesList.this.finish();
		}

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Function");
		menu.setHeaderIcon(R.drawable.icon);
		menu.add(0, 1, 0, "Delete Item");

	}

	/**
	 * ContextMenu中�?项被选中的事�?
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		String title = ((TextView) menuInfo.targetView.findViewById(R.id.title)).getText().toString();
		String date = ((TextView) menuInfo.targetView.findViewById(R.id.date)).getText().toString();
		switch (item.getItemId())
		{
		case 1:
			/**
			 * getListAdapter().getItemId(menuInfo.position)返回的�?是Long型，
			 * 而list.remove(position)是String java.util.ArrayList.remove(int
			 * index)�?�?��当执行到这里的时候，如果直接使用Long型的话，由于不是int型的，所以默�?使用的是:boolean
			 * java.util.ArrayList.remove(Object object)
			 * 两个remove()不匹配，�?��重载的是remove(Object object)，�?这个方法是不能删除item�?
			 * �?���?��将得到的position强制转换为int型的才能运行
			 */
			int pos = (int) listView.getAdapter().getItemId(menuInfo.position);
			if (all.remove(pos) != null)
			{
				NotesList.this.mynotesOperate = new MynotesOperate(NotesList.this.helper.getReadableDatabase());
				NotesList.this.mynotesOperate.delete(title, date);

				this.linearLayout.removeView(listView);
				this.listView.removeAllViewsInLayout();
				this.linearLayout.addView(listView);
				this.simpleAdapter.notifyDataSetChanged();
			} else
			{
				System.out.println("failed");
			}
			// 通知适配器更�?

			break;
		case 2:
			break;
		case 3:
			Toast.makeText(getApplicationContext(), "I am android !", Toast.LENGTH_SHORT).show();
			break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add(Menu.NONE, Menu.FIRST + 1, 1, "Add").setIcon(android.R.drawable.ic_menu_add);
		return true;

	}

	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case Menu.FIRST + 1:
			Toast.makeText(this, "Add New Diary", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(NotesList.this, AddNotes.class);
			this.startActivity(intent);
			NotesList.this.finish();
		}

		return false;
	}

	@Override
	// 這�?必須�?要不然的�?案件無法�?��
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			Intent intent = new Intent();
			intent.setClass(this, MainActivity.class);
			startActivity(intent);
			NotesList.this.finish();
			return true;
		}
		else{
			return super.onKeyDown(keyCode, event);
		}
	}

}
