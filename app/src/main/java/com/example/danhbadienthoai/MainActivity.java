package com.example.danhbadienthoai;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    private RecyclerView rvDanhBa;
    private ContactAdapter adapter;
    private EditText edtTimKiem;
    private ImageButton btnXoa;
    private Button btnThem;
    private static final int REQUEST_CODE_ADD = 2001;
    private static final int REQUEST_CODE_EDIT = 2010;
    public static final int RESULT_YES = 2222;
    public static final String EXTRA_DETAILS_NAME = "EXTRA_DETAILS_NAME";
    public static final String EXTRA_DETAILS_PHONE = "EXTRA_DETAILS_PHONE";
    AsyncTaskListContact asyncTask;


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
        if (requestCode == REQUEST_CODE_ADD && resultCode == Activity.RESULT_OK) {
            String ten = data.getStringExtra(AddActivity.EXTRA_DATA1);
            String sdt = data.getStringExtra(AddActivity.EXTRA_DATA2);
            adapter.addItem(new SoDienThoai(ten, sdt));
        }
        if (requestCode == REQUEST_CODE_EDIT && resultCode == MainActivity.RESULT_YES) {
            String name = data.getStringExtra(DetailsActivity.EXTRA_EDIT_UPDATE_NAME).trim();
            String phone = data.getStringExtra(DetailsActivity.EXTRA_EDIT_UPDATE_PHONE).trim();
            adapter.addItem(new SoDienThoai(name, phone));
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
        rvDanhBa.setHasFixedSize(true);
        rvDanhBa.setAdapter(adapter);
    }

    private void initAction() {
        // sự kiện thêm
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity.this, AddActivity.class);
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
                intent.putExtra(EXTRA_DETAILS_NAME, infor.getTen());
                intent.putExtra(EXTRA_DETAILS_PHONE, infor.getSdt());
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });
    }

    private void initData() {
        asyncTask = new AsyncTaskListContact(adapter, MainActivity.this);
        asyncTask.execute();
    }
}
