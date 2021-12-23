package com.example.danhbadienthoai;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class EditActivity extends Activity {

    ImageButton btnEditBack;
    EditText editName, editPhone;
    Button btnEditLuu, btnEditHuy;
    public static final String EXTRA_EDIT_NAME = "EXTRA_EDIT_NAME";
    public static final String EXTRA_EDIT_PHONE = "EXTRA_EDIT_PHONE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        findView();
        setData();
        initAction();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setData() {
        editName.setText("  " + getIntent().getStringExtra(DetailsActivity.EXTRA_EDIT_NAME));
        editPhone.setText("  " + getIntent().getStringExtra(DetailsActivity.EXTRA_EDIT_PHONE));
    }

    private void findView() {
        btnEditBack = (ImageButton) findViewById(R.id.btnEditBack);
        editName = (EditText) findViewById(R.id.editName);
        editPhone = (EditText) findViewById(R.id.editPhone);
        btnEditLuu = (Button) findViewById(R.id.btnEditLuu);
        btnEditHuy = (Button) findViewById(R.id.btnEditHuy);
    }

    private void initAction() {

        btnEditLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();
                if (name.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(EditActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_LONG).show();
                } else {
                    Intent editData = new Intent(EditActivity.this, DetailsActivity.class);
                    editData.putExtra(EXTRA_EDIT_NAME, name);
                    editData.putExtra(EXTRA_EDIT_PHONE, phone);
                    setResult(DetailsActivity.RESULT_OK, editData);
                    finish();
                }
            }
        });

        btnEditHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}