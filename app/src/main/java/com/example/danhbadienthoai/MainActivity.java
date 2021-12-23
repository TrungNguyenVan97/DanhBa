package com.example.danhbadienthoai;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends Activity {

    private RecyclerView rvDanhBa;
    private ContactAdapter adapter;
    private EditText edtTimKiem;
    private ImageButton btnXoa;
    private Button btnThem;
    private static final int REQUEST_CODE_ADD = 2001;
    private static final int REQUEST_CODE_DETAILS = 2002;
    public static final String EXTRA_DETAILS_NAME = "EXTRA_DETAILS_NAME";
    public static final String EXTRA_DETAILS_PHONE = "EXTRA_DETAILS_PHONE";
    AsynctaskDetails asynctaskDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        initView();
        initAction();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Kiểm tra requestCode có trùng với REQUEST_CODE vừa dùng
        if (requestCode == REQUEST_CODE_ADD) {
            // resultCode được set bởi AddActivity
            // RESULT_OK chỉ ra rằng kết quả này đã thành công
            if (resultCode == Activity.RESULT_OK) {
                // Nhận dữ liệu từ Intent trả về
                String ten = data.getStringExtra(AddSDTActivity.EXTRA_DATA1);
                String sdt = data.getStringExtra(AddSDTActivity.EXTRA_DATA2);
                adapter.addItem(new SoDienThoai(ten, sdt));
            }
        }
    }

    private void findView() {
        rvDanhBa = (RecyclerView) findViewById(R.id.rvDanhBa);
        edtTimKiem = (EditText) findViewById(R.id.edtTimKiem);
        btnXoa = (ImageButton) findViewById(R.id.btnXoa);
        btnThem = (Button) findViewById(R.id.btnThem);
    }

    private void initView() {
        adapter = new ContactAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        rvDanhBa.setLayoutManager(linearLayoutManager);
        rvDanhBa.setAdapter(adapter);
    }

    private void initAction() {
        // sự kiện thêm
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity.this, AddSDTActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });

        // sự kiện xóa
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.removeItem();
            }
        });

        // xem chi tiết
        adapter.setCallBack(new ContactAdapter.CallBack() {
            @Override
            public void onCLickDetails(int position) {
                SoDienThoai infor = adapter.getItemAt(position);
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(EXTRA_DETAILS_NAME,infor.getTen());
                intent.putExtra(EXTRA_DETAILS_PHONE,infor.getSdt());
                startActivityForResult(intent, REQUEST_CODE_DETAILS);
            }
        });
    }

    private void initData() {
        asynctaskDetails = new AsynctaskDetails(adapter, MainActivity.this);
        asynctaskDetails.execute();
    }
}
