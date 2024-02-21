package com.sdk.tspl.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;

import tspl.HPRTPrinterHelper;
import tspl.PublicFunction;


public class Activity_TextFormat  extends Activity 
{	
	private Context thisCon=null;
	private PublicFunction PFun=null;
	private EditText txtText=null;
	private EditText txtformat_x=null;
	private EditText txtformat_y=null;
	private Spinner spnformat_font=null;
	private Spinner spnformat_codepage=null;
	private Spinner spnformat_rotation=null;
	private ArrayAdapter arrformat_font;
	private ArrayAdapter arrformat_codepage;
	private ArrayAdapter arrformatrotation;
	private Spinner spnformat_x_multiplication=null;
	private ArrayAdapter arrformat_x_multiplication;
	private Spinner spnformat_y_multiplication=null;
	private ArrayAdapter arrformat_y_multiplication;
	private String formatfont="0";
	private int x_multiplication=1;
	private String y_multiplication="0";
	private int qrcoderotation=0;
	private String codepage="Default";
	private String[] mList ;
	private int bold=0;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);	   
		this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_text_format);			
		thisCon=this.getApplicationContext();
		
		txtText = (EditText) findViewById(R.id.txtText);
		txtformat_x = (EditText) findViewById(R.id.txtformat_x);
		txtformat_y = (EditText) findViewById(R.id.txtformat_y);
		spnformat_font = (Spinner) findViewById(R.id.spnformat_font);
		spnformat_codepage = (Spinner) findViewById(R.id.spnformat_codepage);

		String[] sList = "0,1,2,3,4,5,6,7,8,9".split(",");
//		String[] sList = getResources().getStringArray(R.array.activity_text_bold);
		arrformat_font = new ArrayAdapter<String>(Activity_TextFormat.this,android.R.layout.simple_spinner_item, sList);
		arrformat_font.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
		spnformat_font.setAdapter(arrformat_font);
		spnformat_font.setOnItemSelectedListener(new OnItemSelectedformatfont());

		mList = getResources().getStringArray(R.array.codepage_list);
		arrformat_codepage = new ArrayAdapter<String>(Activity_TextFormat.this,android.R.layout.simple_spinner_item, mList);
		arrformat_codepage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnformat_codepage.setAdapter(arrformat_codepage);
		spnformat_codepage.setOnItemSelectedListener(new OnItemSelectedCodePage());
		
		spnformat_rotation = (Spinner) findViewById(R.id.spnformat_rotation);			
		arrformatrotation = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);				
		arrformatrotation=ArrayAdapter.createFromResource(this, R.array.activity_1dbarcodes_hri_rotation, android.R.layout.simple_spinner_item);
		arrformatrotation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
		spnformat_rotation.setAdapter(arrformatrotation);	
		spnformat_rotation.setOnItemSelectedListener(new OnItemSelectedformatrotation());
		
		sList = "1,2,3,4,5,6,7".split(",");
		spnformat_x_multiplication = (Spinner) findViewById(R.id.spnformat_x_multiplication);			
		arrformat_x_multiplication = new ArrayAdapter<String>(Activity_TextFormat.this,android.R.layout.simple_spinner_item, sList);				
		arrformat_x_multiplication.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
		spnformat_x_multiplication.setAdapter(arrformat_x_multiplication);	
		spnformat_x_multiplication.setOnItemSelectedListener(new OnItemSelectedformat_x_multiplication());
		
		spnformat_y_multiplication = (Spinner) findViewById(R.id.spnformat_y_multiplication);			
		arrformat_y_multiplication = new ArrayAdapter<String>(Activity_TextFormat.this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.activity_text_bold));
		arrformat_y_multiplication.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
		spnformat_y_multiplication.setAdapter(arrformat_y_multiplication);	
		spnformat_y_multiplication.setOnItemSelectedListener(new OnItemSelectedformat_y_multiplication());
	}
	private class OnItemSelectedCodePage implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			codepage=mList[position];
			Log.e("TAG 111", "OnItemSelectedCodePage:"+mList[position]);
			Log.e("TAG 222", "OnItemSelectedCodePage Value of Code Page:"+codepage);


		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}
	}

	private class OnItemSelectedformatrotation implements OnItemSelectedListener
	{				
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3){
			qrcoderotation=arg2*90;
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0){
			// TODO Auto-generated method stub			
		}
	}
	private class OnItemSelectedformatfont implements OnItemSelectedListener{
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3){
			switch (arg2) {
			case 0:
				formatfont="0";
				break;
			case 1:
				formatfont="1";
				break;
			case 2:
				formatfont="2";
				break;
			case 3:
				formatfont="3";
				break;
			case 4:
				formatfont="4";
				break;
			case 5:
				formatfont="5";
				break;
			case 6:
				formatfont="6";
				break;
			case 7:
				formatfont="7";
				break;
			case 8:
				formatfont="8";
				break;
			case 9:
				formatfont="9";
				break;

			default:
				break;
			}
			
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0){
			// TODO Auto-generated method stub			
		}
	}
	private class OnItemSelectedformat_x_multiplication implements OnItemSelectedListener{
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3){
			x_multiplication=arg2+1;
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0){
			// TODO Auto-generated method stub			
		}
	}
	private class OnItemSelectedformat_y_multiplication implements OnItemSelectedListener{
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
		{
			bold=arg2;
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0){
			// TODO Auto-generated method stub			
		}
	}
	
	public void onClickPrint(View view){
    	if (!checkClick.isClickEvent()) return;
    	
    	try{
    		String sText=txtText.getText().toString().trim();
	    	if(sText.length()==0){
	    		Toast.makeText(thisCon, getString(R.string.activity_1dbarcodes_no_data), Toast.LENGTH_SHORT).show();
	    		return;
	    	}
			sText = sText.replace("\n", "\r\n");
			Log.d("Printer", "sText: "+(HPRTPrinterHelper.bytetohex(sText.getBytes(StandardCharsets.UTF_8))));
			if(HPRTPrinterHelper.printAreaSize("100", "30")==-1){
				Toast.makeText(thisCon,getString(R.string.activity_main_disconnected),Toast.LENGTH_LONG).show();
				return;
			}
			HPRTPrinterHelper.CLS();
			if (bold!=0){
				HPRTPrinterHelper.Bold(bold);
			}
	    	HPRTPrinterHelper.printText(txtformat_x.getText().toString(), txtformat_y.getText().toString(), formatfont, ""+qrcoderotation, x_multiplication, sText);
			if (bold!=0){
				HPRTPrinterHelper.Bold(0);
			}
	    	HPRTPrinterHelper.Print("1", "1");
    	}
		catch (Exception e){
			Log.d("HPRTSDKSample", (new StringBuilder("Activity_TextFormat --> onClickPrint ")).append(e.getMessage()).toString());
		}
    }
}
