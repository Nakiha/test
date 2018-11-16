package com.nakiha.CaptainTestVersion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class LockPage extends AppCompatActivity {

    ImageButton returnButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_page);
        HideBar(getWindow().getDecorView());
        init();
    }

    private void init(){
        returnButton = findViewById(R.id.returnButtonOfLocked);

        returnButton.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.returnButtonOfLocked:
                    finish();
                    break;
            }
        }
    };
    public void HideBar(View decorView){
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
    }
}
