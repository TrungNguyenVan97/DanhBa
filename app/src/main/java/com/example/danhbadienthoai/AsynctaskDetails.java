package com.example.danhbadienthoai;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AsynctaskDetails extends AsyncTask<Integer, Void, List<Object>> {

    ContactAdapter adapter;
    Activity activity;

    public AsynctaskDetails(ContactAdapter adapter, Activity activity) {
        this.adapter = adapter;
        this.activity = activity;
    }

    @Override
    protected List<Object> doInBackground(Integer... integers) {
        return getContactList();
    }

    @Override
    protected void onPostExecute(List<Object> objectList) {
        super.onPostExecute(objectList);
        if (adapter != null) {
            adapter.reset(objectList);
        }
    }

    private List<Object> getContactList() {
        List<Object> listDB = new ArrayList<>();
        String[] PROJECTION = new String[]{
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        ContentResolver cr = activity.getContentResolver();
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
                            String newHeader = String.valueOf(name.charAt(0)).toUpperCase();
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
        return listDB;
    }
}

