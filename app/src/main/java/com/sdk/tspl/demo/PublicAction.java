package com.sdk.tspl.demo;

import android.content.Context;
import android.util.Log;

import tspl.HPRTPrinterHelper;
import tspl.PublicFunction;


public class PublicAction
{	
	private Context context=null;	
	public PublicAction()
	{
		
	}
	public PublicAction(Context con)
	{
		context = con;
	}
	
//	public void BeforePrintAction()
//	{
//		try
//		{
//			PublicFunction PFun=new PublicFunction(context);
//			if(PFun.ReadSharedPreferencesData("Cut").equals("1") && PrinterProperty.Cut)    		
//				HPRTPrinterHelper.CutPaper(HPRTPrinterHelper.HPRT_PARTIAL_CUT,PrinterProperty.CutSpacing);
//			if(PFun.ReadSharedPreferencesData("Cashdrawer").equals("1") && PrinterProperty.Cashdrawer)    		
//				HPRTPrinterHelper.OpenCashdrawer(0);
//			if(PFun.ReadSharedPreferencesData("Buzzer").equals("1") && PrinterProperty.Buzzer)    		
//				HPRTPrinterHelper.BeepBuzzer((byte)1,(byte)10,(byte)0);
//		}
//		catch(Exception e)
//		{
//			Log.e("HPRTSDKSample", (new StringBuilder("PublicAction --> BeforePrintAction ")).append(e.getMessage()).toString());
//		}
//	}
	
//	public void AfterPrintAction()
//	{
//		try
//		{
//			PublicFunction PFun=new PublicFunction(context);
//						
//    		if(PFun.ReadSharedPreferencesData("Cashdrawer").equals("2") && PrinterProperty.Cashdrawer)    		
//    			HPRTPrinterHelper.OpenCashdrawer(0);
//    		if(PFun.ReadSharedPreferencesData("Buzzer").equals("2") && PrinterProperty.Buzzer)    		
//    			HPRTPrinterHelper.BeepBuzzer((byte)1,(byte)10,(byte)10);   
//    		
//    		int iFeed=Integer.valueOf(PFun.ReadSharedPreferencesData("Feeds"));
//    		ArrayAdapter arrFeeds;
//    		arrFeeds = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item);					
//    		arrFeeds=ArrayAdapter.createFromResource(context, R.array.feeds_list, android.R.layout.simple_spinner_item);
//    		arrFeeds.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//    		iFeed=(Integer.valueOf(arrFeeds.getItem(iFeed).toString().replace("mm", "")));
//    		HPRTPrinterHelper.PrintAndFeed(iFeed*4);
//    		if(PFun.ReadSharedPreferencesData("Cut").equals("2") && PrinterProperty.Cut)    
//    			
//    			HPRTPrinterHelper.CutPaper(HPRTPrinterHelper.HPRT_PARTIAL_CUT,PrinterProperty.CutSpacing);
//			else
//				HPRTPrinterHelper.PrintAndFeed(PrinterProperty.TearSpacing);
//		}
//		catch(Exception e)
//		{
//			Log.e("HPRTSDKSample", (new StringBuilder("PublicAction --> AfterPrintAction ")).append(e.getMessage()).toString());
//		}
//	}
	
	public String LanguageEncode()
	{
		try
		{
			PublicFunction PFun=new PublicFunction(context);
			String sLanguage=PFun.ReadSharedPreferencesData("Codepage").split(",")[1].toString();
			Log.e("onItemSelected", "GET LANGUAGE ENCODE LANGUAGE =>"+sLanguage);
			Log.e("onItemSelected", "CGET LANGUAGE ENCODE LANGUAGE FROM SHARED PREFRENCE =>"+PFun.ReadSharedPreferencesData("Codepage"));

			String sLEncode="gb2312";
			int intLanguageNum=0;
			
			sLEncode=PFun.getLanguageEncode(sLanguage);		
//			intLanguageNum= PFun.getCodePageIndex(sLanguage);	
//			HPRTPrinterHelper.SetCharacterSet((byte)intLanguageNum);
			HPRTPrinterHelper.LanguageEncode=sLEncode;
		
			return sLEncode;
		}
		catch(Exception e)
		{			
			Log.e("HPRTSDKSample", (new StringBuilder("PublicAction --> AfterPrintAction ")).append(e.getMessage()).toString());
			return "";
		}
	}
}