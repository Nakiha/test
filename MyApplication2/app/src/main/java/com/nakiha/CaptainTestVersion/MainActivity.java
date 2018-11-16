package com.nakiha.CaptainTestVersion;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String REMAIN_TO_DEVELOP = "功能尚待开发";
    String PLEASE_WAIT = "请稍等";
    String BLUE_TOOTH_HAS_BEEN_OPENED = "蓝牙已开启";
    String BLUE_TOOTH_HAS_NOT_BEEN_OPENED = "蓝牙未开启";
    String LOCK_IS_LOCKED = "锁处于已上锁状态";
    String LOCK_IS_UNLOCKED = "锁处于已打开状态";
    String PLEASE_SET_STATE = "请先设置门锁状态";

    private static final int REQUEST_BT_ENABLE_CODE = 0x1002;
    final int REQUEST_ENABLE_BLUE = 6;
    final int DURATION = 600;
    public int DELAY_TIME = 500;
    final int TO_FULL_SCREEN = 4;
    public static String address = "00:18:E4:35:64:14";

    Button showListButton = null;
    Button openBluetoothButton = null;
    Button lockButton = null;
    Button unlockButton = null;
    Button getHelpButton = null;
    Button setLockStateButton =null;

    ImageView imageLock = null;
    ImageView imageUnlock = null;

    BluetoothAdapter mBluetoothAdapter = null;

    public static boolean isSet = false;
    public static boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HideBar(getWindow().getDecorView());
        init();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null){
            Toast.makeText(this,"找不到蓝牙设备呢",Toast.LENGTH_LONG).show();
            finish();
        }
        if(!mBluetoothAdapter.isEnabled()){
            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetoothIntent, REQUEST_BT_ENABLE_CODE);
        }

        while (!mBluetoothAdapter.isEnabled()){
            try{
                Thread.currentThread();
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        BluetoothDevice AutoDevice = mBluetoothAdapter.getRemoteDevice(address);
        BluetoothService.newTask(new BluetoothService(mHandler, BluetoothService.TASK_START_CONN_THREAD,new Object[]{AutoDevice}));

    }

    private void init(){
        showListButton = findViewById(R.id.showList);
        openBluetoothButton = findViewById(R.id.openBluetooth);
        lockButton = findViewById(R.id.lock);
        unlockButton = findViewById(R.id.unlock);
        getHelpButton = findViewById(R.id.getHelp);
        setLockStateButton = findViewById(R.id.setLockState);

        showListButton.setOnClickListener(listener);
        openBluetoothButton.setOnClickListener(listener);
        lockButton.setOnClickListener(listener);
        unlockButton.setOnClickListener(listener);
        getHelpButton.setOnClickListener(listener);
        setLockStateButton.setOnClickListener(listener);
    }

    //参数传递提示举例
    //getWindow().getDecorView()
    public void HideBar(View decorView){
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
    }

    //为按钮起到打开蓝牙的作用
    private boolean TryToOpenBluetooth(@Nullable Context contextForToast){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null){
            if (contextForToast != null) {
                Toast.makeText(contextForToast, "找不到蓝牙呢_〆(´Д｀ )", Toast.LENGTH_LONG).show();
            }
            return false;
        }
        else {
            OpenBluetoothForResult(mBluetoothAdapter,REQUEST_ENABLE_BLUE);
        }
        return true;
    }
    private void OpenBluetoothForResult(BluetoothAdapter adapter, int requestCode){
        if (!adapter.isEnabled()) {
            Intent enableBlueIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBlueIntent, requestCode);
        }
    }

    //region 设置监听器
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.showList:
                    Toast.makeText(getApplicationContext(), REMAIN_TO_DEVELOP,Toast.LENGTH_SHORT).show();
                    break;
                case R.id.openBluetooth:
                    TryToOpenBluetooth(getApplicationContext());
                    break;

                    //上锁按钮
                case R.id.lock:
                    //imageLock.setVisibility(View.VISIBLE);
                    //处理未设置状态的情况
                    if(isSet) {
                        //处理蓝牙未打开的情况
                        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
                            Toast.makeText(getApplicationContext(), BLUE_TOOTH_HAS_NOT_BEEN_OPENED, Toast.LENGTH_SHORT).show();
                            break;
                        }
                        //锁如果打开，传递lock
                        if (isOpen) {
                            Toast.makeText(getApplicationContext(), PLEASE_WAIT, Toast.LENGTH_SHORT).show();
                            String message = "*unlock#";
                            isOpen = false;
                            BluetoothService.newTask(new BluetoothService(mHandler, BluetoothService.TASK_SEND_MSG, new Object[]{message}));
                            //延时启动活动
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent lockPageIntent = new Intent(getApplicationContext(), LockPage.class);
                                    startActivityForResult(lockPageIntent, TO_FULL_SCREEN);
                                }
                            }, DELAY_TIME);
                        } else {
                            Toast.makeText(getApplicationContext(), LOCK_IS_LOCKED, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),PLEASE_SET_STATE,Toast.LENGTH_SHORT).show();
                    }
                    break;

                    //开锁按钮
                case R.id.unlock:
                    if(isSet) {
                        //imageUnlock.setVisibility(View.VISIBLE);
                        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
                            Toast.makeText(getApplicationContext(), BLUE_TOOTH_HAS_NOT_BEEN_OPENED, Toast.LENGTH_SHORT).show();
                            break;
                        }
                        //锁如果关闭，传递unlock
                        if (!isOpen) {
                            Toast.makeText(getApplicationContext(), PLEASE_WAIT, Toast.LENGTH_SHORT).show();
                            String message = "*lock#";
                            isOpen = true;
                            BluetoothService.newTask(new BluetoothService(mHandler, BluetoothService.TASK_SEND_MSG, new Object[]{message}));
                            //延时启动活动
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent unlockPageIntent = new Intent(getApplicationContext(), UnlockPage.class);
                                    startActivityForResult(unlockPageIntent, TO_FULL_SCREEN);
                                }
                            }, DELAY_TIME);
                        } else {
                            Toast.makeText(getApplicationContext(), LOCK_IS_UNLOCKED, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),PLEASE_SET_STATE,Toast.LENGTH_SHORT).show();
                        }
                    break;

                case R.id.getHelp:
                    Toast.makeText(getApplicationContext(),REMAIN_TO_DEVELOP,Toast.LENGTH_SHORT).show();
                    break;
                case R.id.setLockState:
                    Intent setLockStateIntent = new Intent(getApplicationContext(),SetLockState.class);
                    startActivityForResult(setLockStateIntent,TO_FULL_SCREEN);
            }
        }
    };
    //endregion

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BLUE:
                Toast.makeText(MainActivity.this,BLUE_TOOTH_HAS_BEEN_OPENED,Toast.LENGTH_SHORT).show();
                SetDetectability(getApplicationContext(),DURATION);
                break;
            case TO_FULL_SCREEN:
                HideBar(getWindow().getDecorView());
                break;
        }
    }

    //启动蓝牙的可检测性
    public void SetDetectability(@Nullable Context contextForToast, int duration){

        Intent discoverableIntent = new
                Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,duration);
        startActivity(discoverableIntent);
        if (contextForToast != null){
            Toast.makeText(contextForToast,"可检测时间被设置为: "+String.valueOf(duration) +"秒",Toast.LENGTH_SHORT).show();
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothService.TASK_SEND_MSG:
                    Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothService.TASK_RECV_MSG:
                    if(msg.obj.toString().equals("on")){
                    }
                    else if(msg.obj.toString().equals("off")){
                    }
                    break;
                case BluetoothService.TASK_GET_REMOTE_STATE:
                    //tv_title.setText(msg.obj.toString());
                    break;
                default:
                    break;
            }
        }
    };

    protected void onDestory(){
        BluetoothService.stop(this);
        mBluetoothAdapter.disable();
        super.onDestroy();
    }

}

