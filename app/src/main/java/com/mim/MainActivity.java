package com.mim;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.text.*;
import android.text.method.*;
import android.view.*;
import android.content.*;

public class MainActivity extends Activity 
{
	public static TextView contentTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		String HTML = "జ్ఞ ‌ా，我想要的会来主动找我的" +
	"<img src=\"http://bmob-cdn-22178.b0.upaiyun.com/2019/02/11/2e600860409f7c9780c6722aa92da0b6.jpg\"/>" 
	+"日常寻找列文虎克"
	+"<img src=\"http://bmob-cdn-22178.b0.upaiyun.com/2019/02/12/77d8db7e40373d2f8087c12082cfb5b6.jpg\"/>" 

	;
		 contentTextView =findViewById(R.id.mainTextView1);
		MyImageGetter imageGetter = new MyImageGetter(this, contentTextView);  
		MyTagHandler tagHandler = new MyTagHandler(this);  
		contentTextView.setText(Html.fromHtml(HTML, imageGetter, tagHandler));  
		contentTextView.setMovementMethod(LinkMovementMethod.getInstance());  //超链接
		
    }
	public void add(View v){
		startActivity(new Intent(this,add.class));
	}
}
