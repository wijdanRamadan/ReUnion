package com.safaorhan.reunion.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.firebase.firestore.DocumentReference;

import com.safaorhan.reunion.FirestoreHelper;
import com.safaorhan.reunion.R;

import com.safaorhan.reunion.adapter.MAdapter;

import static com.safaorhan.reunion.activity.ConversationsActivity.myConversationRef;


public class MessagingActivity extends AppCompatActivity implements MAdapter.MessageClickListener{


    RecyclerView recyclerView;

    EditText todoText;

private RecyclerView recyclerview_message;
Button b;
MAdapter messageAdapter;
TextInputEditText sendText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);









sendText=findViewById(R.id.send_text);



        b=findViewById(R.id.fab);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             com.safaorhan.reunion.model.Message myMessage = new com.safaorhan.reunion.model.Message();
             myMessage.setText(sendText.getText().toString());
             myMessage.setFrom(FirestoreHelper.getMe());

             FirestoreHelper.sendMessage(myMessage.getText().toString(),myConversationRef);



            }
        });

    }



    @Override
    public void onMessageClick(DocumentReference conversationRef) {

    }
}
