package com.example.danhbadienthoai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends Activity {

    EditText edtTen, edtSDT;
    Button btnLuu, btnHuy;
    public static final String EXTRA_BUNDLE = "EXTRA_BUNDLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtSDT = (EditText) findViewById(R.id.edtNhapSDT);
        edtTen = (EditText) findViewById(R.id.edtNhapten);
        btnLuu = (Button) findViewById(R.id.btnLuu);
        btnHuy = (Button) findViewById(R.id.btnHuy);

        onClick();

    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    private void onClick() {
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtTen.getText().toString().trim();
                String number = edtSDT.getText().toString().trim();
                String id = name+number;
                SoDienThoai contact = new SoDienThoai(name,number,id);

                if (name.isEmpty() || number.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_LONG).show();
                } else {
                    Intent data = new Intent(AddActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(EXTRA_BUNDLE,contact);
                    data.putExtras(bundle);
                    setResult(Activity.RESULT_OK, data);
                    finish();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}

