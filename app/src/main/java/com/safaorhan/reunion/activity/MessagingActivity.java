package com.safaorhan.reunion.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.widget.Button;


import com.google.firebase.firestore.DocumentReference;

import com.safaorhan.reunion.R;

import com.safaorhan.reunion.adapter.MAdapter;


public class MessagingActivity extends AppCompatActivity implements MAdapter.MessageClickListener{
Button send ;
private RecyclerView recyclerview_message;

MAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        recyclerview_message =(RecyclerView)findViewById(R.id.recyclerViewMessaging);
        messageAdapter =MAdapter.get();
        recyclerview_message.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_message.setAdapter(messageAdapter);

    }



    @Override
    public void onMessageClick(DocumentReference conversationRef) {

    }
}
