package com.example.recyclerview_contact_database;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.recyclerview_contact_database.Model.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add;
    ArrayList<Contact>contactslist=new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        add=findViewById(R.id.add);
        swipeRefreshLayout=findViewById(R.id.swipe);

        ShowData();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Add_Contact_Activity.class);
                startActivity(intent);
            }
        });
        Recyclerview_Adapter adapter=new Recyclerview_Adapter(MainActivity.this,contactslist);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void ShowData() {
        My_Database my_database=new My_Database(MainActivity.this);
        Cursor cursor=my_database.ShowData();
        while (cursor.moveToNext())
        {
            int id=cursor.getInt(0);
            String name=cursor.getString(1);
            String number=cursor.getString(2);
            String imguri=cursor.getString(3);
            Contact contact=new Contact(id,name,number,imguri);
            contactslist.add(contact);
            Log.d("AAA", "ShowData: Create Data"+contact);
        }
    }




}