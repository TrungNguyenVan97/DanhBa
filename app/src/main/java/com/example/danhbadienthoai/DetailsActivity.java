package com.example.danhbadienthoai;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class DetailsActivity extends Activity {

    TextView tvName, tvPhone;
    Button btnEdit;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        findView();
        initAction();
        receiveData();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public void  receiveData(){

    }

    public void findView() {
        tvName = (TextView) findViewById(R.id.tvName);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
    }

    public void initAction() {

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, AddSDTActivity.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}