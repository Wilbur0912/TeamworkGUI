package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login = this.findViewById(R.id.login);
        EditText account = this.findViewById(R.id.account);
        TextView tv = this.findViewById(R.id.log);
        account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = account.getText().toString();
                //tv.setText(account.getText().toString());
                if(text.contains("@")||text.contains("#")||text.contains("*")){
                    tv.setText("invalid character");
                    //Toast.makeText(LoginActivity.this,"帳號密碼錯誤",Toast.LENGTH_SHORT).show();
                }
                else{
                    tv.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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
                    Toast.makeText(LoginActivity.this,"帳號密碼錯誤",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}