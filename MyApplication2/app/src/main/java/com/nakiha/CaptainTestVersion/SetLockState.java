package com.nakiha.CaptainTestVersion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetLockState extends AppCompatActivity {
    EditText MACAddressEditText = null;
    Button sendMACButton =null;
    Button stateUnlockedButton = null;
    Button stateLockButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_lock_state);
        HideBar(getWindow().getDecorView());
        init();
    }

    private void init(){
        MACAddressEditText = findViewById(R.id.MACAddress);
        sendMACButton = findViewById(R.id.SendMACAddress);
        stateUnlockedButton = findViewById(R.id.setLockStateUnlocked);
        stateLockButton = findViewById(R.id.setLockStateLocked);

        MACAddressEditText.setOnClickListener(listener);
        sendMACButton.setOnClickListener(listener);
        stateLockButton.setOnClickListener(listener);
        stateUnlockedButton.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
         switch (v.getId()){
             case R.id.setLockStateUnlocked:
                 MainActivity.isSet = true;
                 MainActivity.isOpen = true;
                 finish();
                 break;
             case R.id.setLockStateLocked:
                 MainActivity.isSet = true;
                 MainActivity.isOpen = false;
                 finish();
                 break;
             case R.id.SendMACAddress:
                 MainActivity.address = MACAddressEditText.getText().toString();
                 Toast.makeText(getApplicationContext(),"远程设备MAC地址被设置为\n"+MACAddressEditText.getText().toString(),Toast.LENGTH_SHORT).show();
                 break;
         }
        }
    };

    //参数传递提示举例
    //getWindow().getDecorView()
    public void HideBar(View decorView){
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
    }
}
