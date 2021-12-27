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

    private TextView tvName, tvPhone, tvID;
    private Button btnEdit;
    private ImageButton btnBack;
    private Boolean needUpdate = false;
    private static final int REQUEST_CODE_EDIT = 2010;
    public static final String EXTRA_EDIT = "EXTRA_EDIT";
    public static final String EXTRA_EDIT_UPDATE = "EXTRA_EDIT_UPDATE";

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
            if (data.getExtras() != null) {
                SoDienThoai contact = (SoDienThoai) data.getExtras().get(EditActivity.EXTRA_UPDATE);
                tvName.setText(contact.getTen());
                tvPhone.setText(contact.getSdt());
                tvID.setText(contact.getId());
                needUpdate = true;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (needUpdate) {
            String name = tvName.getText().toString().trim();
            String phone = tvPhone.getText().toString().trim();
            String id = tvID.getText().toString().trim();
            SoDienThoai contactUpdate = new SoDienThoai(name, phone, id);
            Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(EXTRA_EDIT_UPDATE, contactUpdate);
            intent.putExtras(bundle);
            setResult(MainActivity.RESULT_YES, intent);
            finish();
        } else {
            super.onBackPressed();
        }
    }

    private void setData() {
        SoDienThoai contact = (SoDienThoai) getIntent().getExtras().get(MainActivity.EXTRA_DETAILS);
        tvName.setText(contact.getTen());
        tvPhone.setText(contact.getSdt());
    }

    private void findView() {
        tvName = (TextView) findViewById(R.id.tvName);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvID = (TextView) findViewById(R.id.tvID);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
    }

    private void initAction() {

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoDienThoai contact = (SoDienThoai) getIntent().getExtras().get(MainActivity.EXTRA_DETAILS);
                Intent intent = new Intent(DetailsActivity.this, EditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(EXTRA_EDIT, contact);
                intent.putExtras(bundle);
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