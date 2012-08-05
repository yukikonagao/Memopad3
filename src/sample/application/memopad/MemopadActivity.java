<<<<<<< HEAD
package sample.application.memopad;

import java.text.DateFormat;
import java.util.Date;
import android.app.Activity;
import android.os.Bundle;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.text.Selection;
import android.widget.EditText;
import java.text.DateFormat;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.text.TextWatcher;//8/4追加




public class MemopadActivity extends Activity {
    /** Called when the activity is first created. */
	
		boolean memoChanged = false;//8/5追加
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);
       
    EditText et = (EditText) this.findViewById(R.id.editText1);
    SharedPreferences pref = this.getSharedPreferences("MemoPrefs", MODE_PRIVATE);
    	memoChanged = pref.getBoolean("memoChanged", false);//8/5追加
    et.setText(pref.getString("memo",""));
    et.setSelection(pref.getInt("cusor",0));
    
    //8/5追加 ↓     
   TextWatcher tw = new TextWatcher(){
	   @Override
	   public void afterTextChanged(Editable s) {
		   
	   }
	 //8/5追加↑
	   @Override
	   public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	   }
	   
	   @Override
	   public void onTextChanged(CharSequence s, int start, int before, int count) {
		   memoChange = true;
	   }
		   }
     };
     et.addTextChangeListener(tw);


	@Override
    public void onStop(){
		super.onStop();
    	EditText et = (EditText) this.findViewById(R.id.editText1);
    	SharedPreferences pref = this.getSharedPreferences("MemoPrefs", MODE_PRIVATE);
    	SharedPreferences.Editor editor = pref.edit();
    	editor.putBoolean("memoChanged", memoChanged);//8/5追加
    	editor.putString("memo", et.getText().toString());
    	editor.putInt("cursor",Selection.getSelectionStart(et.getText()));
    	/*
    	Editable e = et.getText();
    	Integer i = Selection.getSelectionStart(e);
    	editor.putInt("cursor", i);
    	*/
    	
    	editor.commit();
    }
    
	public void saveMemo(){
		EditText et = (EditText)this.findViewById(R.id.editText1);
		String title;
		String memo = et.getText().toString();
		
		if(memo.trim().length()>0){
			if(memo.indexOf("\n")==-1){
				title = memo.substring(0, Math.min(memo.length(),20));
			}
			else{
				title = memo.substring(0, Math.min(memo.indexOf("\n"),20));
				memoChanged = false;//8/5追加。ここでいいのか？
			}
			
			String ts = DateFormat.getDateTimeInstance().format(new Date());
			MemoDBHelper memos = new MemoDBHelper(this);
			SQLiteDatabase db = memos.getWritableDatabase();
			ContentValues values = new ContentValues();
			
			values.put("title", title+"\n"+ts);
			values.put("memo", memo);
			
			db.insertOrThrow("memoDB", null,values);
			memos.close();
		}
	}
    
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自動生成されたメソッド・スタブ
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
		EditText et = (EditText) this.findViewById(R.id.editText1);
		
		switch(requestCode){
		case 0:
			et.setText(data.getStringExtra("text"));
			memoChange = false;//8/5追加
			break;
		}
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater mi = this.getMenuInflater();
		mi.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		EditText et = (EditText) this.findViewById(R.id.editText1);
		switch(item.getItemId()){
		case R.id.menu_save:
			this.saveMemo();//保存を押したら、保存される。
			break;//上記の（saveMemo）が出たら、そこから抜ける。
		case R.id.menu_open:
				if(memoChanged) saveMemo();//8/5追加
			Intent i = new Intent(this, MemoList.class);
			this.startActivityForResult(i,0);//以前作成したメモを呼び出す。
			break;
		case R.id.menu_new:
				if(memoChanged) sameMemo();//8/5追加
			et.setText("");//新しいメモを作るので、何もないメモ帳が表示される。
			break;
		}
		return super.onOptionsItemSelected(item);
	}
    
}

=======
package sample.application.memopad;

import java.text.DateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.Selection;
import android.widget.EditText;
import java.text.DateFormat;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.text.TextWatcher;


public class MemopadActivity extends Activity {
    /** Called when the activity is first created. */
	boolean memoChanged = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);
       
        
       
    EditText et = (EditText) this.findViewById(R.id.editText1);
    SharedPreferences pref = this.getSharedPreferences("MemoPrefs", MODE_PRIVATE);
    memoChanged = pref.getBoolean("memoChanged", false);
    et.setText(pref.getString("memo",""));
    et.setSelection(pref.getInt("cusor",0));
    
    TextWatcher tw = new TextWatcher() {
    	
		public void afterTextChanged(Editable s) {		
		}
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {		
		}
		public void onTextChanged(CharSequence s, int start, int before,int count) {
			memoChanged = true;		
		}
    };
    		et.addTextChangedListener(tw);       
     }

	


	@Override
    public void onStop(){
		super.onStop();
    	EditText et = (EditText) this.findViewById(R.id.editText1);
    	SharedPreferences pref = this.getSharedPreferences("MemoPrefs", MODE_PRIVATE);
    	SharedPreferences.Editor editor = pref.edit();
    	editor.putBoolean("memoChanged", memoChanged);
    	editor.putString("memo", et.getText().toString());
    	editor.putInt("cursor",Selection.getSelectionStart(et.getText()));
    	/*
    	Editable e = et.getText();
    	Integer i = Selection.getSelectionStart(e);
    	editor.putInt("cursor", i);
    	*/
    	
    	editor.commit();
    }
    
	public void saveMemo(){
		EditText et = (EditText)this.findViewById(R.id.editText1);
		String title;
		String memo = et.getText().toString();
		
		if(memo.trim().length()>0){
			if(memo.indexOf("\n")==-1){
				title = memo.substring(0, Math.min(memo.length(),20));
			}
			else{
				title = memo.substring(0, Math.min(memo.indexOf("\n"),20));
				memoChanged = false;
			}
			
			String ts = DateFormat.getDateTimeInstance().format(new Date());
			MemoDBHelper memos = new MemoDBHelper(this);
			SQLiteDatabase db = memos.getWritableDatabase();
			ContentValues values = new ContentValues();
			
			values.put("title", title+"\n"+ts);
			values.put("memo", memo);
			
			db.insertOrThrow("memoDB", null,values);
			memos.close();
		}
	}
    
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
		EditText et = (EditText) this.findViewById(R.id.editText1);
		
		switch(requestCode){
		case 0:
			et.setText(data.getStringExtra("text"));
			memoChanged = false;
			break;
		}
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater mi = this.getMenuInflater();
		mi.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		EditText et = (EditText) this.findViewById(R.id.editText1);
		switch(item.getItemId()){
		case R.id.menu_save:
			this.saveMemo();//保存を押したら、保存される。
			break;//上記の（saveMemo）が出たら、そこから抜ける。
		case R.id.menu_open:
			if(memoChanged) saveMemo();
			Intent i = new Intent(this, MemoList.class);
			this.startActivityForResult(i,0);//以前作成したメモを呼び出す。
			break;
		case R.id.menu_new:
			if(memoChanged) saveMemo();
			et.setText("");//新しいメモを作るので、何もないメモ帳が表示される。
			break;
		}
		return super.onOptionsItemSelected(item);
	}
    
}

>>>>>>> commmit!
