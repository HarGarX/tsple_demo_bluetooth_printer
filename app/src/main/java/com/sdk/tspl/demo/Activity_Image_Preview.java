package com.sdk.tspl.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class Activity_Image_Preview extends Activity 
{	
	ImageView view;
	String ImagePath;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);	   
		this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_image_preview);			
				
		view=(ImageView)this.findViewById(R.id.webPreview);
		
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
        startActivityForResult(intent, 1);
	}

	public void onClickOK(View view) 
	{
    	if (!checkClick.isClickEvent()) return;
    	
    	try
    	{    		
    		Intent intent = new Intent();
            intent.putExtra("ImagePath", ImagePath);            
            setResult(10, intent);  
            finish();
    	}
		catch (Exception e) 
		{			
			Log.d("HPRTSDKSample", (new StringBuilder("Activity_Print_Image --> onClickPhoto ")).append(e.getMessage()).toString());
		}
    }
	
	public void onClickCancel(View view) 
	{
    	if (!checkClick.isClickEvent()) return;
    	
    	try
    	{
    		finish();
    	}
		catch (Exception e) 
		{			
			Log.d("HPRTSDKSample", (new StringBuilder("Activity_Print_Image --> onClickPrint ")).append(e.getMessage()).toString());
		}
    }
		
    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) 
    {  
        // TODO Auto-generated method stub  
        super.onActivityResult(requestCode, resultCode, data);  
        if (resultCode == Activity.RESULT_OK) 
        {  
            String sdStatus = Environment.getExternalStorageState();  
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) 
            {
                Log.d("HPRTSDKSample", (new StringBuilder("Activity_Image_Preview --> onActivityResult ")).append("SD card is not avaiable/writeable right now.").toString());
                return;  
            }  
            new DateFormat();  
            String name = DateFormat.format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA)) + ".jpg";   
            Toast.makeText(this, name, Toast.LENGTH_LONG).show();  
            Bundle bundle = data.getExtras();  
            Bitmap bitmap = (Bitmap) bundle.get("data");//
          
            FileOutputStream b = null;           
            File file = new File("/sdcard/HPRTSDKSample/");  
            if(!file.exists())
            	file.mkdirs();//
            ImagePath = "/sdcard/HPRTSDKSample/"+name;  
  
            try 
            {  
                b = new FileOutputStream(ImagePath);  
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);//
            } 
            catch (FileNotFoundException e) 
            {  
                e.printStackTrace();  
            } 
            finally 
            {  
                try 
                {  
                    b.flush();  
                    b.close();  
                } 
                catch (IOException e) 
                {  
                    e.printStackTrace();  
                }  
            }  
            try  
            {  
                view.setImageBitmap(bitmap);//
            }
            catch(Exception e)  
            {  
                Log.e("error", e.getMessage());  
            }                
        }  
    }  
}
