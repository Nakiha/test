package com.nakiha.CaptainTestVersion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        HideBar(getWindow().getDecorView());
    }
    //参数传递提示举例
    //getWindow().getDecorView()
    public void HideBar(View decorView){
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
    }
}
