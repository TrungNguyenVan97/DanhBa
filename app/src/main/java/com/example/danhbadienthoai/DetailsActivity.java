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
    private static final int REQUEST_CODE_EDIT = 2010;
    public static final String EXTRA_EDIT_NAME = "EXTRA_EDIT_NAME";
    public static final String EXTRA_EDIT_PHONE = "EXTRA_EDIT_PHONE";
    public static final String EXTRA_EDIT_NAME2 = "EXTRA_EDIT_NAME2";
    public static final String EXTRA_EDIT_PHONE2 = "EXTRA_EDIT_PHONE2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        findView();
        setData();
        initAction();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT && resultCode == DetailsActivity.RESULT_OK) {
            String name = data.getStringExtra(EditActivity.EXTRA_EDIT_NAME).trim();
            String phone = data.getStringExtra(EditActivity.EXTRA_EDIT_PHONE).trim();
            tvName.setText(name);
            tvPhone.setText(phone);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setData() {
        tvName.setText("  " + getIntent().getStringExtra(MainActivity.EXTRA_DETAILS_NAME));
        tvPhone.setText("  " + getIntent().getStringExtra(MainActivity.EXTRA_DETAILS_PHONE));
    }

    private void findView() {
        tvName = (TextView) findViewById(R.id.tvName);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
    }

    private void initAction() {

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, EditActivity.class);
                intent.putExtra(EXTRA_EDIT_NAME, tvName.getText().toString());
                intent.putExtra(EXTRA_EDIT_PHONE, tvPhone.getText().toString());
                startActivityForResult(intent, REQUEST_CODE_EDIT);
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