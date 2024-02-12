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

import tspl.HPRTPrinterHelper;


public class Activity_1DBarcodes  extends Activity 
{	
	private Context thisCon=null;
	private Spinner spnBarcodeType=null;
	private ArrayAdapter arrBarcodeType;
	private Spinner spnBarcodeWidth=null;
	private ArrayAdapter arrBarcodeWidth;
	private Spinner spnBarcode_readable=null;
	private Spinner spnBarcode_rotation=null;
	private Spinner spnBarcode_narrow=null;
	private ArrayAdapter arrBarcodeHRILayout;
	private ArrayAdapter arrBarcoderotation;
	private ArrayAdapter arrBarcodenarrow;
	private EditText txtBarcodeData=null;
	private EditText txtBarcodeHeight=null;
	private EditText txtBarcode_x=null;
	private EditText txtBarcode_y=null;
	
	private int justification=0;
	private int BarcodeType=0;
	private int BarcodeWidth=2;
	private int Barcodereadable=0;
	private int Barcoderotation=0;
	private int Barcodenarrow=0;
	private String Barcodetype;
	private PublicAction pAct;
		
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);	   
		this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_1dbarcodes);			
		thisCon=this.getApplicationContext();
		pAct = new PublicAction(thisCon);	
		spnBarcodeType = (Spinner) findViewById(R.id.spnBarcodeType);			
		//arrBarcodeType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
		String[] barcode=new String[PrinterProperty.Barcode.split(",").length-1];
		if (PrinterProperty.Barcode.contains("QRCODE")) {
			for (int i = 0; i < PrinterProperty.Barcode.split(",").length-1; i++) {
				 barcode[i]=PrinterProperty.Barcode.split(",")[i];
			}
		}else {
			barcode=new String[PrinterProperty.Barcode.split(",").length];
			for (int i = 0; i < PrinterProperty.Barcode.split(",").length; i++) {
				 barcode[i]=PrinterProperty.Barcode.split(",")[i];
			}
		}
		arrBarcodeType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,barcode);
		arrBarcodeType=ArrayAdapter.createFromResource(this, R.array.activity_1dbarcodes_barcode_type, android.R.layout.simple_spinner_item);
		arrBarcodeType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
		spnBarcodeType.setAdapter(arrBarcodeType);
		spnBarcodeType.setOnItemSelectedListener(new OnItemSelectedBarcodeType());
		
		spnBarcodeWidth = (Spinner) findViewById(R.id.spnBarcodeWidth);			
		arrBarcodeWidth = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);				
		arrBarcodeWidth=ArrayAdapter.createFromResource(this, R.array.activity_1dbarcodes_width, android.R.layout.simple_spinner_item);
		arrBarcodeWidth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
		spnBarcodeWidth.setAdapter(arrBarcodeWidth);
		spnBarcodeWidth.setOnItemSelectedListener(new OnItemSelectedBarcodeWidth());
		
		spnBarcode_readable = (Spinner) findViewById(R.id.spnBarcode_readable);			
		arrBarcodeHRILayout = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);				
		arrBarcodeHRILayout=ArrayAdapter.createFromResource(this, R.array.activity_1dbarcodes_hri_position, android.R.layout.simple_spinner_item);
		arrBarcodeHRILayout.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
		spnBarcode_readable.setAdapter(arrBarcodeHRILayout);	
		spnBarcode_readable.setOnItemSelectedListener(new OnItemSelectedBarcodeHRILayout());
		
		spnBarcode_rotation = (Spinner) findViewById(R.id.spnBarcode_rotation);			
		arrBarcoderotation = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);				
		arrBarcoderotation=ArrayAdapter.createFromResource(this, R.array.activity_1dbarcodes_hri_rotation, android.R.layout.simple_spinner_item);
		arrBarcoderotation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
		spnBarcode_rotation.setAdapter(arrBarcoderotation);	
		spnBarcode_rotation.setOnItemSelectedListener(new OnItemSelectedBarcoderotation());
		
		spnBarcode_narrow = (Spinner) findViewById(R.id.spnBarcode_narrow);			
		arrBarcodenarrow = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);				
		arrBarcodenarrow=ArrayAdapter.createFromResource(this, R.array.activity_1dbarcodes_width, android.R.layout.simple_spinner_item);
		arrBarcodenarrow.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
		spnBarcode_narrow.setAdapter(arrBarcodenarrow);	
		spnBarcode_narrow.setOnItemSelectedListener(new OnItemSelectedBarcodenarrow());
		
		txtBarcodeData=(EditText)this.findViewById(R.id.txtBarcodeData);
		txtBarcodeHeight=(EditText)this.findViewById(R.id.txtBarcodeHeight);
		txtBarcode_x=(EditText)this.findViewById(R.id.txtBarcode_x);
		txtBarcode_y=(EditText)this.findViewById(R.id.txtBarcode_y);
		
	}
	
	private class OnItemSelectedBarcodeType implements OnItemSelectedListener
	{				
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
		{						
			Barcodetype=spnBarcodeType.getSelectedItem().toString();
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0) 
		{
			// TODO Auto-generated method stub			
		}
	}
	
	private class OnItemSelectedBarcodeWidth implements OnItemSelectedListener
	{				
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
		{			
			BarcodeWidth=arg2+2;
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0) 
		{
			// TODO Auto-generated method stub			
		}
	}
	
	private class OnItemSelectedBarcodeHRILayout implements OnItemSelectedListener
	{				
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
		{			
			Barcodereadable=arg2;
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0) 
		{
			// TODO Auto-generated method stub			
		}
	}
	private class OnItemSelectedBarcoderotation implements OnItemSelectedListener
	{				
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
		{			
			Barcoderotation=arg2*90;
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0) 
		{
			// TODO Auto-generated method stub			
		}
	}
	private class OnItemSelectedBarcodenarrow implements OnItemSelectedListener
	{				
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
		{			
			Barcodenarrow=arg2+2;
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0) 
		{
			// TODO Auto-generated method stub			
		}
	}

	
	public void onClickPrint(View view) 
	{
    	if (!checkClick.isClickEvent()) return;
    	
    	try
    	{
	    	if(txtBarcodeData.getText().toString().trim().length()==0)
	    	{
	    		Toast.makeText(thisCon, getString(R.string.activity_1dbarcodes_no_data), Toast.LENGTH_SHORT).show();
	    		return;
	    	}
	    	if(HPRTPrinterHelper.printAreaSize("100", "80")==-1){
				Toast.makeText(thisCon,getString(R.string.activity_main_disconnected),Toast.LENGTH_LONG).show();
				return;
			}
	    	HPRTPrinterHelper.CLS();
	    	HPRTPrinterHelper.printBarcode(txtBarcode_x.getText().toString(), txtBarcode_y.getText().toString(), Barcodetype,txtBarcodeHeight.getText().toString(), ""+Barcodereadable,""+Barcoderotation,""+Barcodenarrow,""+BarcodeWidth,txtBarcodeData.getText().toString().trim());
	    	HPRTPrinterHelper.Print("1", "1");
    	}
		catch (Exception e) 
		{			
			Log.d("HPRTSDKSample", (new StringBuilder("Activity_1DBarcodes --> onClickPrint ")).append(e.getMessage()).toString());
		}
    }
}