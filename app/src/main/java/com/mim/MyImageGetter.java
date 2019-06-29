package com.mim;
import android.text.*;
import android.widget.*;
import java.lang.ref.*;
import android.content.*;
import android.graphics.drawable.*;
import android.graphics.*;
import android.os.*;
import com.nostra13.universalimageloader.core.*;
import com.nostra13.universalimageloader.core.listener.*;
import android.view.*;
import com.nostra13.universalimageloader.core.assist.*;

public class MyImageGetter implements Html.ImageGetter {  
	Context c;
    TextView container;
    int width;


    public MyImageGetter(Context c,TextView t) {
        this.c = c;
        this.container = t;
        width = c.getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public Drawable getDrawable(String source) {
        final UrlDrawable urlDrawable = new UrlDrawable();
		DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.moren)// 设置图片下载期间显示的图片
			.showImageOnFail(R.drawable.error)// 设置图片加载或解码过程中发生错误显示的图片
			.cacheInMemory(false)// 设置下载的图片是否缓存在内存中
			.cacheOnDisk(false)// 设置下载的图片是否缓存在SD卡中
			.build();// 创建DisplayImageOptions对象
		// 使用ImageLoader加载图片

        ImageLoader.getInstance().loadImage(source,options,
            new SimpleImageLoadingListener() {

				@Override
				public void onLoadingFailed(String imageuri,View view,FailReason failreason){
					Bitmap loadedImage = BitmapFactory.decodeResource(c.getResources(), R.drawable.error); // 不存在设置默认图片


                    urlDrawable.bitmap = loadedImage;
                    urlDrawable.setBounds(0, 0, loadedImage.getWidth(),
										  loadedImage.getHeight());
                    container.invalidate();
                    container.setText(container.getText()); // ??????
				}
				@Override
				public void onLoadingStarted(String imageuri,View view){
					Bitmap loadedImage = BitmapFactory.decodeResource(c.getResources(), R.drawable.moren); // 不存在设置默认图片


                    urlDrawable.bitmap = loadedImage;
                    urlDrawable.setBounds(0, 0, loadedImage.getWidth(),
										  loadedImage.getHeight());
                    container.invalidate();
                    container.setText(container.getText()); // ??????
				}

                @Override
                public void onLoadingComplete(String imageUri, View view,
											  Bitmap loadedImage) {
                    // ??????
                    float scaleWidth = ((float) width) / loadedImage.getWidth();

                    // ???????matrix??
                    Matrix matrix = new Matrix();
                    matrix.postScale(scaleWidth, scaleWidth);
                    loadedImage = Bitmap.createBitmap(loadedImage, 0, 0,
													  loadedImage.getWidth(), loadedImage.getHeight(),
													  matrix, true);
                    urlDrawable.bitmap = loadedImage;
                    urlDrawable.setBounds(0, 0, loadedImage.getWidth(),
										  loadedImage.getHeight());
                    container.invalidate();
                    container.setText(container.getText()); // ??????
                }

            });

        return urlDrawable;
    }

    @SuppressWarnings("deprecation")
    public class UrlDrawable extends BitmapDrawable {
        protected Bitmap bitmap;

        @Override
        public void draw(Canvas canvas) {
            // override the draw to facilitate refresh function later
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0, 0, getPaint());
            }
        }
    }
}

