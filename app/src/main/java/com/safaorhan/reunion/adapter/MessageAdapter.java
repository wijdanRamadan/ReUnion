package com.safaorhan.reunion.adapter;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.safaorhan.reunion.R;

import java.util.ArrayList;




    import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.safaorhan.reunion.FirestoreHelper;
import com.safaorhan.reunion.R;
import com.safaorhan.reunion.model.Conversation;
import com.safaorhan.reunion.model.Message;
import com.safaorhan.reunion.model.User;

import java.util.ArrayList;

public class MessageAdapter   extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
        private static final String TAG = com.safaorhan.reunion.adapter.ConversationAdapter.class.getSimpleName();
        ArrayList<Message> messageList = new ArrayList<>();

        @Override
        public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.messaging, parent, false);
            return new MessageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MessageViewHolder holder, int position) {
            Message message = messageList.get(position);
            holder.bind(message);
        }

        @Override
        public int getItemCount() {
            return messageList.size();
        }




    class MessageViewHolder extends RecyclerView.ViewHolder {
            TextView opponentNameText;
            TextView lastMessageText;
            Button fab;
EditText editText;
            public MessageViewHolder(View itemView) {
                super(itemView);
                opponentNameText = itemView.findViewById(R.id.opponentNameText);
                lastMessageText = itemView.findViewById(R.id.lastMessageText);
                editText=itemView.findViewById(R.id.send_text);
                fab=itemView.findViewById(R.id.fab);}

            public void bind(final Message message) {
                opponentNameText.setText(FirestoreHelper.getUsers().toString());
                lastMessageText.setText(FirestoreHelper.getConversations().toString());
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       FirestoreHelper.sendMessage("test 2",FirestoreHelper.getMe());
                    }
                });
            }
        }
    public interface MessageClickListener {
        void onMessageClick(DocumentReference conversationRef);

    }
    }