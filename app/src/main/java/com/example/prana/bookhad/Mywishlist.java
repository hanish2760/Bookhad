package com.example.prana.bookhad;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.prana.bookhad.R.id.wishes;

public class Mywishlist extends AppCompatActivity {

    private FloatingActionButton ab;
    private ListView wlist;
    private DatabaseReference datab;
    private FirebaseAuth mauth;
    private List<String> wbooks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywishlist);
        wlist=(ListView)findViewById(wishes);
        wbooks=new ArrayList<>();
        final ArrayAdapter<String> stringArrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,wbooks);
        wlist.setAdapter(stringArrayAdapter);
        mauth=FirebaseAuth.getInstance();
        datab= FirebaseDatabase.getInstance().getReference().child("Users").child(mauth.getCurrentUser().getUid()).child("Wishlist");
        datab.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Book bk=(Book)dataSnapshot.getValue(Book.class);
                if(!bk.tit.equals("null")){
                    String temp="Title: "+bk.tit+"  Author: "+bk.authr;;

                    wbooks.add(temp);

                    stringArrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ab=(FloatingActionButton)findViewById(R.id.addbook);
        ab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mywishlist.this,Home2.class));
            }
        });
    }
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(new Intent(this,Main2Activity.class));
    }
}
