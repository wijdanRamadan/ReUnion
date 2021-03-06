package com.safaorhan.reunion.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.safaorhan.reunion.R;
import com.safaorhan.reunion.adapter.ConversationAdapter;

import java.io.Serializable;


public class ConversationsActivity extends AppCompatActivity implements ConversationAdapter.ConversationClickListener {

    private static final String TAG = ConversationsActivity.class.getSimpleName();
    public static  DocumentReference myConversationRef;

    RecyclerView recyclerView;
    ConversationAdapter conversationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);

        recyclerView = findViewById(R.id.recyclerView);

        conversationAdapter = ConversationAdapter.get();
        conversationAdapter.setConversationClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(conversationAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        conversationAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        conversationAdapter.stopListening();
    }

    @Override
    public void onConversationClick(DocumentReference conversationRef) {
          myConversationRef =conversationRef;

             Intent  x = new Intent(this , MessagingActivity.class);
          x.putExtra("conversationId", conversationRef.getId());
             startActivity(x);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.conversations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_people) {

            Intent intent = new Intent(this, UsersActivity.class);
            startActivity(intent);
            return true;
        }
        if(id==R.id.log_out)
        {
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

                return super.onOptionsItemSelected(item);
        }
    }

