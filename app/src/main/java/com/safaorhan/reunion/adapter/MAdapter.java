package com.safaorhan.reunion.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.safaorhan.reunion.R;
import com.safaorhan.reunion.activity.MessagingActivity;
import com.safaorhan.reunion.model.Conversation;
import com.safaorhan.reunion.model.Message;
import com.safaorhan.reunion.model.User;

import java.util.ArrayList;
import java.util.List;

import static com.safaorhan.reunion.FirestoreHelper.getMe;
import static com.safaorhan.reunion.FirestoreHelper.sent;
import static com.safaorhan.reunion.activity.ConversationsActivity.myConversationRef;



public class MAdapter   extends FirestoreRecyclerAdapter<Message, MAdapter.MessageHolder> {

public static String layoutTitle;
  public static   ArrayList<Message> myMessagesList = new ArrayList<Message>();


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MAdapter(@NonNull FirestoreRecyclerOptions<Message> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageHolder holder, int position, @NonNull Message message) {
        message.setConversation(getSnapshots().getSnapshot(position).getDocumentReference("conversation"));
        holder.bind(message);

    }

    public static MAdapter get() {
        Query query = FirebaseFirestore.getInstance()
                .collection("messages")
                .whereEqualTo("conversation" , myConversationRef)
                .orderBy("sentAt")
                .limit(50);


        FirestoreRecyclerOptions<Message> options = new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .build();

        return new MAdapter(options);
    }


    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.messaging, parent, false);
        return new MessageHolder(itemView);

    }

    public class MessageHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView opponentNameText;
        TextView lastMessageText;
        ImageView tick ;
        public MessageHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            opponentNameText=itemView.findViewById(R.id.textView);
            lastMessageText=itemView.findViewById(R.id.textView1);
            tick = itemView.findViewById(R.id.tick);
        }

        public void bind(final Message message)

        {

            message.getFrom().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        final User opponent = document.toObject(User.class);
                        opponentNameText.setText(opponent.getName());

                        lastMessageText.setText(message.getText());
                        getMe().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    User user = document.toObject(User.class);
                                    if(user.getName().equals(opponent.getName()))
                                    {
                                        tick.setVisibility(View.VISIBLE);
                                    }

                                }
                            }
                        });
                        }
                }
            });
/*
            FirebaseFirestore.getInstance()
                    .collection("messages")
                    .whereEqualTo("conversation",myConversationRef)
                     .limit(10)
                     .get()
                     .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                       Message message =document.toObject(Message.class);
                                       lastMessageText.setText(message.getText());
                                       message.getFrom().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot document = task.getResult();
                                                User opponent = document.toObject(User.class);
                                                opponentNameText.setText(opponent.getName());

                                            }
                                        }
                                    });





                                }
                            }
                        }
                    });



*/
        }

    }


    public void getMessagess(DocumentReference convRef) {

        FirebaseFirestore.getInstance()
                .collection("messages")
                .whereEqualTo("conversation", convRef)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Message message = document.toObject(Message.class);
                                myMessagesList.add(message);


                            }
                        }
                    }
                });


    }
        public interface MessageClickListener {
        void onMessageClick(DocumentReference conversationRef);

    }


}
