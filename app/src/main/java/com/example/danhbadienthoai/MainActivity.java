package com.example.danhbadienthoai;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TintTypedArray;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends Activity {

    private RecyclerView rvDanhBa;
    private List<Object> listDB;
    private SoDienThoaiAdapter adapter;
    private EditText edtTimKiem;
    private ImageButton btnXoa;
    private Button btnThem;
    private static final int REQUEST_CODE = 2001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhXa();

        getContactList();

        adapter = new SoDienThoaiAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvDanhBa.setLayoutManager(linearLayoutManager);

        rvDanhBa.setAdapter(adapter);

        adapter.reset(listDB);

        actionClick();

    }

    private void anhXa() {
        rvDanhBa = (RecyclerView) findViewById(R.id.rvDanhBa);
        edtTimKiem = (EditText) findViewById(R.id.edtTimKiem);
        btnXoa = (ImageButton) findViewById(R.id.btnXoa);
        btnThem = (Button) findViewById(R.id.btnThem);
    }

    private void actionClick() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity.this, AddSDTActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Kiểm tra requestCode có trùng với REQUEST_CODE vừa dùng
        if (requestCode == REQUEST_CODE) {

            // resultCode được set bởi AddActivity
            // RESULT_OK chỉ ra rằng kết quả này đã thành công
            if (resultCode == Activity.RESULT_OK) {
                // Nhận dữ liệu từ Intent trả về
                String ten = data.getStringExtra(AddSDTActivity.EXTRA_DATA1);
                String sdt = data.getStringExtra(AddSDTActivity.EXTRA_DATA2);
                adapter.mDataSet.add(new SoDienThoaiAdapter.DataSDT(new SoDienThoai(ten, sdt)));
                adapter.notifyDataSetChanged();

            }
        }
    }

    private void getContactList() {

        listDB = new ArrayList<>();

        String[] PROJECTION = new String[]{
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor != null) {
            HashSet<String> mobileNoSet = new HashSet<String>();
            try {
                final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String name, number;
                String header = null;
                while (cursor.moveToNext()) {
                    name = cursor.getString(nameIndex);
                    number = cursor.getString(numberIndex);
                    number = number.replace(" ", "");
                    if (!mobileNoSet.contains(number)) {
                        if (name != null && name.length() > 1) {
                            String newHeader = String.valueOf(name.charAt(0));
                            if (!newHeader.equals(header)) {
                                header = newHeader;
                                listDB.add(new ChuCai(newHeader));
                            }
                        }
                        listDB.add(new SoDienThoai(name, number));
                        mobileNoSet.add(number);
                    }
                }
            } finally {
                cursor.close();
            }
        }
    }
}
