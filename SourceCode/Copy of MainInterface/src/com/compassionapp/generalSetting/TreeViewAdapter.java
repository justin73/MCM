package com.compassionapp.generalSetting;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class TreeViewAdapter extends BaseExpandableListAdapter
{	
	public String[] title={"What is compassion?","Being compassionate is like dancing...",
			"How does My Compassion App work?","I’m new to meditating? How do I do it?",
			"OK, but why keep a diary?","That sounds pretty easy? Is that all there is to it?",
			"Want to know more about living a compassionate life?",
			"Compassion meditation can be challenging."};
	public String[][]content={
			{"\nCompassion is an emotional response when you see suffering and desire to help alleviate that suffering. Compassion is something we all feel but too often, most of us don’t act on our desire to end the suffering we see.\n"},
			{"\n…the more you practice, the better you get. This app helps you put more compassion in your life by spending a few moments each day thinking and meditating on it.\n"},
			{"\nMy Compassion App sends you a daily quote, at a time you choose, about compassion and its positive effects on you and those around you.\n\nThe app helps you practice compassion by creating a few minutes of timed, silent meditation on compassion in your life?you decide how long.\n\nAnd you can keep track of your thoughts and experiences practicing compassion with the app’s daily diary.\n"},
			{"\nHere are a couple of suggestions:\n\n1.You can walk or sit to meditate;\n\n2.Start small?commit to just 2 minutes a day. You can do it for 5 minutes or 10 if you feel good about it, but commit to something you can stick with.\n\n3.Pick a trigger?you can use the app’s timed, daily quote as a trigger or something like brushing your teeth or washing the dishes at supper to remind you to meditate.\n\n4.Find a quiet spot?if you sit, sit comfortably on a pillow or a chair. If you walk, walk on a smooth, level surface, indoors or out. This isn’t hiking or aerobics; your focus should be on more compassion in your life, not staying upright.\n\n5.Set the timer and focus on your breath - follow it in through your nostrils, your throat, your chest and belly. Sit or walk straight, keep your eyes open but looking at the ground in front of you with a soft focus. Now think about your day ahead and how compassion can be part of it.\n"},
			{"\nWhen you start thinking about compassion and how you can be a more compassionate person, you’ll probably start asking lots of questions about your life and how you’re living it. Meditation isn’t about figuring everything out, but it is about observing yourself and your world, seeing yourself more directly and honestly. This is important stuff! So take a few moments to jot down the thoughts, the ideas, and the sensations that seem worth remembering.\n"},
			{"\nYup, it's that simple. Try meditating on compassion in your life for at least 2 minutes following the same trigger every day. Do this for a month and you’ll have a daily compassion meditation habit; we think you’ll also be a lot more self-aware and already practicing more compassion with the people around you.*\n\n\n\n*With thanks to Leo Batuta at zenhabits.net\n"},
			{"\nFor more compassion meditation resources, visit the MCM website at http://www.montrealcitymission.org/en/.\n"},
			{"\nUnlike other meditations such as loving kindness or appreciative joy, you don’t start with yourself or with positive things in your life. Compassion looks at suffering and how to resolve it. It often means we have to think about difficult people or consider difficult actions we normally avoid.\n\n True compassion has to be sincere – you can’t “fake it ‘til you make it”. For beginner practitioners it is recommended to focus on those for whom you feel easy, genuine compassion. As experience grows, expand your compassion more broadly until it is spontaneous and limitless.\n\n" +
					"1.Select a comfortable posture in a quiet and peaceful place to meditate.\n\nGive yourself a few moments to become aware of your mind and body, relaxing muscle and mental tension.\n\n" +
					"2.Turn your mind toward a person who has experienced a misfortune for whom you feel an easy, instinctive empathy and desire to relieve suffering.\n\nFocus on somebody who has had a difficult day, an accident or illness, who has lost a loved one - they all can be the subject of true compassion. Sincerity is critical so choose somebody close and sympathetic; it’s hard to show genuine compassionate for strangers or for people for whom we don’t feel any empathy.\n\n" +
					"3.Wish the focus of your meditation freedom from their sorrow or stress and recovery to a happier present.\n\nIf your attempt to feel compassion turns to feelings of guilt or resentment or self-criticism, observe these feelings as objectively as you can. You haven't failed in showing sincere compassion – you’ve only met some of the obstacles in your mind that you need to overcome.\n\n " +
					"4.Practice gentle mindfulness and awareness of just compassion.\n\nContinue directing compassion to situations you are aware of, moving from those close to you to those further away. The goal is give each person or situation the meditation time needed to let the heart and mind become peaceful and to develop equanimity towards the subject of compassion.\nExpand your compassion as you become steadier in ability to regard suffering and imagine its resolution. With care, you may start to direct your attention and attempt to feel sincere compassion towards someone who has done harmful things.\n\n" +
					"5.Recognize and let go of barriers that occur in your mind.\n\nSorrow, frustration, desire, resentment, anger, disgust, and coldness can all be barriers to developing peace, forgiveness and understanding and they can all be overcome. This is one of the greatest benefits of practicing compassion meditation.\n\n " +
					"6.Be aware how true compassion can become false and harmful compassion.\n\nThe traps are numerous: sorrow and despair because of our own powerlessness to relieve more suffering; despair that we cannot “change the world”; ego trips based on a conviction that we should be recognized as humanitarians or saintly people; a sense of moral or ethical superiority over those who are the subjects of our meditation; anger that makes us turn away as opposed to anger that moves us to action.\n\n" +
					"7.Consider ways that you can be more compassionate in your day to day life."
			}
	};
	private Context context=null;
	
	
	public TreeViewAdapter(Context context){
		this.context=context;
	}
	@Override
	public Object getChild(int groupPosition, int childPosition)
	{
		// TODO Auto-generated method stub
		return this.content[groupPosition][childPosition];
	}
	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		// TODO Auto-generated method stub
		return childPosition;
	}
	
	public TextView buildTextView(){ //自定义方�?建立文本
		@SuppressWarnings("deprecation")
		//Typeface tf = Typeface.createFromAsset(getAssets(),"gothic.ttf");
		AbsListView.LayoutParams params=new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		//上行 用來設置每一行listivew的大�?寬度及高�?
		TextView textview=new TextView(this.context);
		textview.setLayoutParams(params);
		textview.setTextSize(25);
		textview.setGravity(Gravity.LEFT);
		textview.setPadding(40,8,3,3);
		Typeface tf=Typeface.create("gothic.ttf", 1);
		textview.setTypeface(tf);
		return textview;
	}
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		//返回子项组件
		TextView textView=buildTextView();//创建textview
		textView.setText(getChild(groupPosition, childPosition).toString());//设置显示文字
		textView.setTextColor(Color.BLUE);
		textView.setTextSize(15.0f);
		Typeface tf=Typeface.create("gothic.ttf", childPosition);
		textView.setTypeface(tf);
		return textView;
	}
	@Override
	public int getChildrenCount(int groupPosition)
	{
		// TODO Auto-generated method stub
		return this.content[groupPosition].length;
	}
	@Override
	public Object getGroup(int arg0)
	{
		// TODO Auto-generated method stub
		return this.title[arg0];
	}
	@Override
	public int getGroupCount()
	{
		// TODO Auto-generated method stub
		return this.title.length;
	}
	@Override
	public long getGroupId(int arg0)
	{
		// TODO Auto-generated method stub
		return arg0;
	}
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		TextView textView=buildTextView();
		textView.setTextSize(25);
		textView.setText(this.getGroup(groupPosition).toString());
		textView.setTextColor(Color.BLACK);
		return textView;
	}
	@Override
	public boolean hasStableIds()
	{
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isChildSelectable(int arg0, int arg1)
	{
		// TODO Auto-generated method stub
		return true;
	}

	
}
