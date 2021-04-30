package com.example.labmob2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    ListView contact_lv;
    TextView contact_tv;
    List<ContactsInfo> contactsInfoList;
    ContactAdapter dataAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        contact_lv = findViewById(R.id.contacts_lv);
        contact_tv = findViewById(R.id.contacts_query_tv);
        contact_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contact_lv.getVisibility() == View.GONE){
                    getContacts();
                    contact_lv.setVisibility(View.VISIBLE);
                }else{
                    contact_lv.setVisibility(View.GONE);
                }
            }
        });

    }

    public void getContacts(){
        ContentResolver contentResolver = getContentResolver();
        String contactId = null;
        String displayName = null;
        contactsInfoList = new ArrayList<ContactsInfo>();
        Cursor cursor = contentResolver.query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.NUMBER + " LIKE " + "?",
                new String[]{getString(R.string.contact_pattern)},
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER)));
                if(hasPhoneNumber > 0){
                    ContactsInfo contactsInfo = new ContactsInfo();
                    contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                    displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                    contactsInfo.setContactId(contactId);
                    contactsInfo.setDisplayName(displayName);

                    String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    contactsInfo.setPhoneNumber(phoneNumber);
                    contactsInfoList.add(contactsInfo);

                }
            }
        }

        cursor.close();

        if(contactsInfoList.size() > 0){
            dataAdapter = new ContactAdapter(ContactsActivity.this, R.layout.list_view_row, contactsInfoList);
            contact_lv.setAdapter(dataAdapter);
        }
    }
}