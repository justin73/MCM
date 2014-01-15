package com.compassionapp.generalSetting;


import com.compassion.maininterface.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class FAQList extends Activity
{
	private ExpandableListView elistview=null;
	private TreeViewAdapter adapter=null;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.faq);
		this.elistview=(ExpandableListView)super.findViewById(R.id.ExpandableListView01);
		this.adapter=new TreeViewAdapter(this);
		this.elistview.setDividerHeight(2);
		this.elistview.setAdapter(this.adapter);
		super.registerForContextMenu(this.elistview);//注册上下文菜�?
		Typeface tf = Typeface.createFromAsset(getAssets(),"gothic.ttf");
		this.elistview.setCacheColorHint(00000000);// 防止下滑时的黑色背景
		this.elistview.setOnChildClickListener(new OnChildClickListenerImpl());
		this.elistview.setOnGroupClickListener(new OnGroupClickListenerImpl());
		this.elistview.setOnGroupCollapseListener(new OnGroupCollapseListenerImpl());
		this.elistview.setOnGroupExpandListener(new OnGroupExpandListenerImpl());
		
	}
	private class OnChildClickListenerImpl implements OnChildClickListener{

		@Override
		public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
		{
			// TODO Auto-generated method stub
			
			return false;
		}
		
	}
	private class OnGroupClickListenerImpl implements OnGroupClickListener{

		@Override
		public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id)
		{
			// TODO Auto-generated method stub
			return false;
		}


		
		
	}
	private class OnGroupCollapseListenerImpl implements OnGroupCollapseListener{

		@Override
		public void onGroupCollapse(int groupPosition)
		{
			// TODO Auto-generated method stub
			
		}
		
	}
	private class OnGroupExpandListenerImpl implements OnGroupExpandListener{

		@Override
		public void onGroupExpand(int groupPosition)
		{
			// TODO Auto-generated method stub
			
		}
		
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
		// TODO Auto-generated method stub
		/*super.onCreateContextMenu(menu, v, menuInfo);
		ExpandableListView.ExpandableListContextMenuInfo info=
				(ExpandableListView.ExpandableListContextMenuInfo)menuInfo;
		int type=ExpandableListView.getPackedPositionType(info.packedPosition);
		int group=ExpandableListView.getPackedPositionGroup(info.packedPosition);
		int child=ExpandableListView.getPackedPositionChild(info.packedPosition);*/
		
	}
	
	@Override
	// 這�?必須�?要不然的�?案件無法�?��
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			Intent intent = new Intent();
			intent.setClass(this, optionsListview.class);
			startActivity(intent);
			FAQList.this.finish();
			return true;
		} else
		{
			return super.onKeyDown(keyCode, event);
		}
	}

}
