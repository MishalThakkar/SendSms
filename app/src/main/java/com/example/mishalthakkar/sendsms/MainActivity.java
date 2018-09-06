package com.example.mishalthakkar.sendsms;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int SEND_SMS_PERMISSION_CODE = 111;
    EditText EditText_Mobile_Number,EditText_Message;
    Button SendSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText_Mobile_Number = findViewById(R.id.editText);
        EditText_Message = findViewById(R.id.editText2);
        SendSms = findViewById(R.id.button);

        SendSms.setEnabled(false);

        if (checkPermission(Manifest.permission.SEND_SMS))
        {
            SendSms.setEnabled(true);
        }else
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_CODE);
        }

        SendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile_number = EditText_Mobile_Number.getText().toString();
                String message = EditText_Message.getText().toString();

                if(!TextUtils.isEmpty(message) && !TextUtils.isEmpty(mobile_number))
                {
                        if(checkPermission(Manifest.permission.SEND_SMS))
                        {

                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(mobile_number,null,message,null,null);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
                        }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Enter a message and a phone number",Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private boolean checkPermission(String permission)
    {
        int checkPermission = ContextCompat.checkSelfPermission(this,permission);
        return checkPermission == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode){
            case SEND_SMS_PERMISSION_CODE :
                if(grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
                    SendSms.setEnabled(true);
        }
    }
}
