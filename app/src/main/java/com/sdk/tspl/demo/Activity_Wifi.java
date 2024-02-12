package com.sdk.tspl.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import tspl.HPRTPrinterHelper;


public class Activity_Wifi extends Activity {
    private Context thisCon = null;
    private HPRTPrinterHelper HPRTPrinter = new HPRTPrinterHelper();
    private EditText edtIP = null;
    private EditText edtPort = null;
    private TextView txtTips = null;

//	private String PrinterName="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_wifi);

        thisCon = this.getApplicationContext();
        edtIP = (EditText) findViewById(R.id.txtIPAddress);
        edtPort = (EditText) findViewById(R.id.txtWifiPort);
        txtTips = (TextView) findViewById(R.id.txtTips);

        Intent intent = getIntent();
//		PrinterName=intent.getStringExtra("PN");
    }

    public void onClickConnect(View view) {
        if (!checkClick.isClickEvent()) return;

        try {
            if (HPRTPrinter != null) {
                HPRTPrinter.PortClose();
            }

            String strIP = edtIP.getText().toString().trim();
            String strPort = edtPort.getText().toString().trim();
            if (strIP.length() == 0) {
                Toast.makeText(thisCon, R.string.activity_wifi_noIP, Toast.LENGTH_SHORT).show();
                return;
            }

//			HPRTPrinter=new HPRTPrinterHelper(thisCon,PrinterName);
            if (HPRTPrinterHelper.PortOpen(thisCon, "WiFi," + strIP + "," + strPort) != 0) {
                txtTips.setText(thisCon.getString(R.string.activity_main_connecterr));
            } else {
                HPRTPrinterHelper.PortClose();
                Intent intent = new Intent();
                intent.putExtra("is_connected", "OK");
                intent.putExtra("IPAddress", strIP);
                intent.putExtra("Port", strPort);
                setResult(HPRTPrinterHelper.ACTIVITY_CONNECT_WIFI, intent);
                finish();
            }
            HPRTPrinter = null;
        } catch (Exception e) {
            Log.d("HPRTSDKSample", (new StringBuilder("Activity_Wifi --> onClickConnect ")).append(e.getMessage()).toString());
        }
    }

    public void onClickCancel(View view) {
        if (!checkClick.isClickEvent()) return;

        try {
            HPRTPrinterHelper.PortClose();

            this.finish();
        } catch (Exception e) {
            Log.d("HPRTSDKSample", (new StringBuilder("Activity_Wifi --> onClickCancel ")).append(e.getMessage()).toString());
        }
    }
}
