package com.safaorhan.reunion.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.safaorhan.reunion.FirestoreHelper;
import com.safaorhan.reunion.R;

import com.safaorhan.reunion.adapter.MAdapter;
import com.safaorhan.reunion.model.Conversation;
import com.safaorhan.reunion.model.Message;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Nullable;

import static com.safaorhan.reunion.activity.ConversationsActivity.myConversationRef;


public class MessagingActivity extends AppCompatActivity implements MAdapter.MessageClickListener{


    RecyclerView recyclerView;

    TextView Text;

private RecyclerView recyclerview_message;
Button b;
MAdapter messageAdapter;
EditText sendText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
         recyclerview_message = findViewById(R.id.recyclerViewMessaging);
         messageAdapter=MAdapter.get();
         recyclerview_message.setLayoutManager(new LinearLayoutManager(this));
         recyclerview_message.setAdapter(messageAdapter);
        sendText=findViewById(R.id.send_text);

        Intent i = getIntent();
        final String convId = i.getStringExtra("conversationId");

        b=findViewById(R.id.fab);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             com.safaorhan.reunion.model.Message myMessage = new com.safaorhan.reunion.model.Message();
             myMessage.setText(sendText.getText().toString());
             myMessage.setFrom(FirestoreHelper.getMe());
             myConversationRef.update("id" ,convId);
             FirestoreHelper.sendMessage(myMessage.getText().toString(),myConversationRef);
              getMessagess(myConversationRef);



            }
        });

    }



    @Override
    public void onMessageClick(DocumentReference conversationRef) {

    }


    public void getMessagess(DocumentReference convRef)
    {

        FirebaseFirestore.getInstance()
                .collection("messages")
                .whereEqualTo("conversation",convRef)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {

                for (QueryDocumentSnapshot document : task.getResult()) {

                    Message message =document.toObject(Message.class);
                    Toast.makeText(getApplicationContext(), message.getText(), Toast.LENGTH_SHORT).show();


                }
            }
        }
    });






}}
