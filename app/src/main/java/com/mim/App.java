package com.mim;
import android.app.*;
import com.nostra13.universalimageloader.core.*;

public class App extends Application {
   
    @Override
    public void onCreate() {
        super.onCreate();
       
		ImageLoaderConfiguration config = new ImageLoaderConfiguration
			.Builder(this)
			.threadPoolSize(10)//线程池内加载的数
			.build();//开始构建

        ImageLoader.getInstance().init(config);
		//Bmob.initialize(getApplicationContext(),"");

    }

    

}


