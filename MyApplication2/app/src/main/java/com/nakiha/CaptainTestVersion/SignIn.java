package com.nakiha.CaptainTestVersion;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {
    final int TO_FULL_SCREEN = 4;
    EditText userNameText = null;
    EditText passWordText = null;
    ImageButton mainActivityButton = null;
    Button setOfSignInButton = null;
    Button forgetPassWordButton = null;

    String WRONG = "密码或用户名错误";
    String REMAIN_TO_DEVELOP = "功能尚待开发";
    String rightUserName = "Guest";
    String rightPassWord = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        HideBar(getWindow().getDecorView());
        init();
    }

    private void init(){
        userNameText = findViewById(R.id.userName);
        passWordText = findViewById(R.id.passWord);

        mainActivityButton = findViewById(R.id.mainActivity);
        setOfSignInButton = findViewById(R.id.setOfSignIn);
        forgetPassWordButton = findViewById(R.id.forgetPassWord);

        mainActivityButton.setOnClickListener(listener);
        setOfSignInButton.setOnClickListener(listener);
        forgetPassWordButton.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.mainActivity:
                    if(isRight(userNameText.getText().toString(),passWordText.getText().toString())) {
                        //Toast.makeText(getApplicationContext(), "Main Activity", Toast.LENGTH_SHORT).show();
                        Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivityForResult(mainActivityIntent, TO_FULL_SCREEN);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),WRONG,Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.setOfSignIn:
                    Toast.makeText(getApplicationContext(),rightUserName+"\n"+rightPassWord,Toast.LENGTH_SHORT).show();
                    break;
                case R.id.forgetPassWord:
                    Toast.makeText(getApplicationContext(),REMAIN_TO_DEVELOP,Toast.LENGTH_SHORT).show();
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
    //验证密码
    private boolean isRight(String userName, String passWord) {
        return (CompareWord(userName,rightUserName)&& CompareWord(passWord,rightPassWord));
    }
    private boolean CompareWord(String first,String second){
        if (first.length()==second.length()){
            int index;
            for (index=0;index<second.length();index++){
                if(first.charAt(index)!=second.charAt(index))
                    break;
            }
            return (index==second.length());
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case TO_FULL_SCREEN:
                HideBar(getWindow().getDecorView());
        }
    }
}

