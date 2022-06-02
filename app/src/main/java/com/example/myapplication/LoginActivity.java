package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText editText;
    private String blockCharacterSet = "~#^|@$%&*!";

    private InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        login();
    }
    public void login(){
        Button login = this.findViewById(R.id.login);
        EditText account = this.findViewById(R.id.account);
        editText = (EditText) findViewById(R.id.account);
        editText.setFilters(new InputFilter[] { filter });

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText account = LoginActivity.this.findViewById(R.id.account);
                EditText password = LoginActivity.this.findViewById(R.id.password);
                if(account.getText().toString().equals("user") && password.getText().toString().equals("123456")){
                    //Toast.makeText(LoginActivity.this,"帳號密碼正確",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                else{
                    Toast.makeText(LoginActivity.this,"Incorrect password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}