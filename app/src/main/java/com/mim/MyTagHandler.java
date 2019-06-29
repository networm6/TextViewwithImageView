package com.mim;
import android.text.*;
import android.content.*;
import org.xml.sax.*;
import java.util.*;
import android.text.style.*;
import android.view.*;
import android.widget.*;
import android.app.*;
import android.graphics.drawable.*;
import com.nostra13.universalimageloader.core.*;
import android.view.View.*;
import java.text.*;
import android.graphics.*;
import java.io.*;


public class MyTagHandler implements Html.TagHandler {  


	private PopupWindow popupWindow;
	private nzoom imageview;//悬浮窗的内容
	private Button keep,change;

    private  Context  mContext;  

    public MyTagHandler(Context context) {  
        mContext = context.getApplicationContext();  
		View popView = LayoutInflater.from(context).inflate(R.layout.popview, null);
        imageview =  popView.findViewById(R.id.popviewnzoom1);
		keep=(Button)popView.findViewById(R.id.popviewButton1);
		change=popView.findViewById(R.id.popviewButton2);
        popView.findViewById(R.id.popviewLinearLayout1).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (popupWindow != null && popupWindow.isShowing()) {
						popupWindow.dismiss();
					}
				}
			});
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);// 设置允许在外点击消失
        ColorDrawable dw = new ColorDrawable(0x50000000);
        popupWindow.setBackgroundDrawable(dw);

    }  

    @Override  
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {  

        // 处理标签<img>  
        if (tag.toLowerCase(Locale.getDefault()).equals("img")) {  
            // 获取长度  
            int len = output.length();  
            // 获取图片地址  
            ImageSpan[] images = output.getSpans(len-1, len, ImageSpan.class);  
            String imgURL = images[0].getSource();  

            // 使图片可点击并监听点击事件  
            output.setSpan(new ClickableImage( imgURL), len-1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  
        }  
    }  

    private class ClickableImage extends ClickableSpan {  
		private String name=null;
        private String url;



		public ClickableImage( String url) {

			this.url = url;
		}

		@Override
		public void onClick(View widget) {
			// 进行图片点击之后的处理
			popupWindow.showAtLocation(widget, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
			DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(false)//让图片进行内存缓存
                .cacheOnDisk(false)//让图片进行sdcard缓存
                .showImageForEmptyUri(R.drawable.error)//图片地址有误
                .showImageOnFail(R.drawable.error)//当图片加载出现错误的时候显示的图片
                .showImageOnLoading(R.drawable.moren)//图片正在加载的时候显示的图片

                .build();

			ImageLoader.getInstance().displayImage(url,imageview,options);
			keep.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
						name=df.format(new Date())+"space";
						saveBitmap(name,((BitmapDrawable)imageview.getDrawable()).getBitmap());
						Toast.makeText(mContext,"保存成功，文件名"+name,10).show();
					}
				});
			change.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {



						imageview.setImageBitmap(rotateBimap(mContext,90,((BitmapDrawable)imageview.getDrawable()).getBitmap()));


						Toast.makeText(mContext,"旋转成功",10).show();
					}
				});
		}
		private Bitmap rotateBimap(Context context,float degree,Bitmap srcBitmap) {
			Matrix matrix = new Matrix();
			matrix.reset();
			matrix.setRotate(degree);
			Bitmap bitmap = Bitmap.createBitmap(srcBitmap,0,0,srcBitmap.getWidth(),srcBitmap.getHeight()
												,matrix,true);
			return bitmap;
		}
		public void saveBitmap(String picName,Bitmap bm) {

			File f = new File("/sdcard/", picName+".png");
			if (f.exists()) {
				f.delete();
			}
			try {
				FileOutputStream out = new FileOutputStream(f);
				bm.compress(Bitmap.CompressFormat.PNG, 100, out);
				out.flush();
				out.close();
				Toast.makeText(mContext,"保存成功，文件名"+name+".png",10).show();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(mContext,"失败"+e.toString(),10).show();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(mContext,"失败"+e.toString(),10).show();

			}
		}

	}



}  


