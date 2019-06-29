package com.mim;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.content.*;
import android.text.*;
import cn.bmob.v3.datatype.*;
import cn.bmob.v3.listener.*;
import java.io.*;
import cn.bmob.v3.exception.*;
import java.util.*;
import android.text.method.*;

public class add extends Activity
{
	/*
	 1）写入数据：
     //步骤1：创建一个SharedPreferences对象
     SharedPreferences sharedPreferences= getSharedPreferences("data",Context.MODE_PRIVATE);
     //步骤2： 实例化SharedPreferences.Editor对象
     SharedPreferences.Editor editor = sharedPreferences.edit();
     //步骤3：将获取过来的值放入文件
     editor.putString("name", “Tom”);
     editor.putInt("age", 28);
     editor.putBoolean("marrid",false);
     //步骤4：提交               
     editor.commit();

	 2）读取数据：
     SharedPreferences sharedPreferences= getSharedPreferences("data", Context .MODE_PRIVATE);
     String userId=sharedPreferences.getString("name","");

	 3）删除指定数据
     editor.remove("name");
     editor.commit();

	 4）清空数据
     editor.clear();
     editor.commit();
	 5)判断是否存在
     contains();
	 */
	SharedPreferences.Editor editor;
	SharedPreferences datamap;
	EditText edtext;
	TextView top;
	private BmobFile bmobFile;
	TextView ty;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);

		datamap= getSharedPreferences("data", Context .MODE_PRIVATE);
		editor = datamap.edit();
		edtext=findViewById(R.id.edit);
		top=findViewById(R.id.addTextView1);
		if(datamap.contains("mtext")){
			edtext.setText(datamap.getString("mtext",""));

		}
		ty=findViewById(R.id.addTextView2);
		int result;
		result = 0;
		int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = this.getResources().getDimensionPixelSize(resourceId);
		}
		LinearLayout topbar=findViewById(R.id.bar);
		LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) topbar.getLayoutParams();
		linearParams.height = result;
		topbar.setLayoutParams(linearParams); 

		if (getActionBar() != null){
			getActionBar().hide();
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			//透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
			View v = this.getWindow().getDecorView();
			v.setSystemUiVisibility(View.GONE);
		} else if (Build.VERSION.SDK_INT >= 19) {
			//for new api versions.
			View decorView = getWindow().getDecorView();
			int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
			decorView.setSystemUiVisibility(uiOptions);
		}
		edtext.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

				}

				@Override
				public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

				}

				@Override

				public void afterTextChanged(Editable editable) {

					//长度发生变化，监听到输入的长度为 editText.getText().length()
					top.setText(String.valueOf(edtext.getText().length()) + "字");
				}
			});




	}

	public void look(View v){
		MyImageGetter imageGetter = new MyImageGetter(this,MainActivity. contentTextView);  
		MyTagHandler tagHandler = new MyTagHandler(this);  
		MainActivity.contentTextView.setText(Html.fromHtml(edtext.getText().toString(), imageGetter, tagHandler));  
	MainActivity	.contentTextView.setMovementMethod(LinkMovementMethod.getInstance());  //超链接
		
		//MainActivity.contentTextView.setText(edtext.getText().toString());
		if(datamap.contains("mtext")){
			editor.remove("name");
			editor.commit();
			editor.putString("mtext",""+edtext.getText().toString()); 
			editor.commit();
		}else{
			editor.putString("mtext",""+edtext.getText().toString()); 
			editor.commit();
		}
		this.finish();
	}

	public void choosePicture(View v){

		Intent it = new Intent(Intent.ACTION_PICK);
		//设置格式
		it.setType("image/*");
		startActivityForResult(it, 1000);
	}
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);


		if(requestCode == 1000 && resultCode == RESULT_OK){

			String aa = RealPathFromUriUtils.getRealPathFromUri(this, data.getData());

			update(aa);


		}
	}
	public void update(String in)
	{

		bmobFile = new BmobFile(new File(in));
		bmobFile.uploadblock(new UploadFileListener()
			{
				@Override
				public void done(BmobException e)
				{
					if (e == null)
					{


						edtext.setText(edtext.getText().toString()+"<img src=\""+bmobFile.getFileUrl()+"\"/>");
						Toast.makeText(add.this,"上传成功",10).show();
						ty.setText("图片");
					}else{
						Toast.makeText(add.this,"上传失败"+e.getMessage(),10).show();
						ty.setText("图片");
					}
				}
				@Override
				public void onProgress(Integer a){
					ty.setText(a+"％");
				}
			});


	}



	@Override
    public void onBackPressed() {
		if(datamap.contains("mtext")){
			editor.remove("name");
			editor.commit();
			editor.putString("mtext",""+edtext.getText().toString()); 
			editor.commit();
		}else{
			editor.putString("mtext",""+edtext.getText().toString()); 
			editor.commit();
		}
		finish();

    }
	public void back(View v){

		if(datamap.contains("mtext")){
			editor.remove("name");
			editor.commit();
			editor.putString("mtext",""+edtext.getText().toString()); 
			editor.commit();
		}else{
			editor.putString("mtext",""+edtext.getText().toString()); 
			editor.commit();
		}
		finish();

	}
}

