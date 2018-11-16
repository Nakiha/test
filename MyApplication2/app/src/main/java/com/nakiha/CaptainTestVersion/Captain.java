package com.nakiha.CaptainTestVersion;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Captain extends AppCompatActivity {

    final int TO_FULL_SCREEN = 4;
    Button signUpButton = null;
    Button signInButton = null;
    Button aboutUs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captain);
        HideBar(getWindow().getDecorView());
        init();
    }

    private void init(){
        signUpButton = findViewById(R.id.signUp);
        signInButton = findViewById(R.id.signIn);
        aboutUs = findViewById(R.id.aboutUs);

        signUpButton.setOnClickListener(listener);
        signInButton.setOnClickListener(listener);
        aboutUs.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.signUp:
//                    Toast.makeText(getApplicationContext(),"Sign up",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.signIn:
//                    Toast.makeText(getApplicationContext(),"Sign in",Toast.LENGTH_SHORT).show();
                    Intent singInIntent = new Intent(getApplicationContext(),SignIn.class);
                    startActivityForResult(singInIntent,TO_FULL_SCREEN);
                    break;
                case R.id.aboutUs:
//                    Toast.makeText(getApplicationContext(),"About us",Toast.LENGTH_SHORT).show();
                    Intent aboutUsIntent = new Intent(getApplicationContext(),AboutUs.class);
                    startActivityForResult(aboutUsIntent,TO_FULL_SCREEN);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case TO_FULL_SCREEN:
                HideBar(getWindow().getDecorView());
        }
    }

    //参数传递提示举例
    //getWindow().getDecorView()
    public void HideBar(View decorView){
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
    }
}
