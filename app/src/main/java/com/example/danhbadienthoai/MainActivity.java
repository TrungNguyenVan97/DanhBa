package com.example.danhbadienthoai;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private RecyclerView rvDanhBa;
    private ContactAdapter adapter;
    private SearchView svTimKiem;
    private ImageButton btnXoa;
    private Button btnThem;
    private static final int REQUEST_CODE_ADD = 2001;
    private static final int REQUEST_CODE_EDIT = 2010;
    public static final int RESULT_YES = 2222;
    public static final String EXTRA_DETAILS = "EXTRA_DETAILS";
    AsyncTaskListContact asyncTask;
    private Handler handler = new Handler();

    final Runnable r = new Runnable() {
        public void run() {
            try {
                adapter.getFilter().filter(svTimKiem.getQuery());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

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
            if (data.getExtras() != null) {
                SoDienThoai contact = (SoDienThoai) data.getExtras().get(AddActivity.EXTRA_BUNDLE);
                adapter.addItem(contact);
            }
        }
        if (requestCode == REQUEST_CODE_EDIT && resultCode == MainActivity.RESULT_YES) {
            if (data.getExtras() != null) {
                SoDienThoai contact = (SoDienThoai) data.getExtras().get(DetailsActivity.EXTRA_EDIT_UPDATE);
                String id = contact.getTen().trim() + contact.getSdt().trim();
                if (!id.equals(contact.getId())) {
                    adapter.removeItemByID(contact.getId());
                    adapter.addItem(new SoDienThoai(contact.getTen(), contact.getSdt(), id));
                }
            }
        }
    }

    private void findView() {
        rvDanhBa = (RecyclerView) findViewById(R.id.rvDanhBa);
        svTimKiem = (SearchView) findViewById(R.id.svTimKiem);
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
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
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
                Bundle bundle = new Bundle();
                bundle.putSerializable(EXTRA_DETAILS, infor);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });
        // tìm kiếm
        svTimKiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(r,300);
                return false;
            }
        });
    }

    private void initData() {
        asyncTask = new AsyncTaskListContact(adapter, MainActivity.this);
        asyncTask.execute();
    }
}
