package com.example.prana.bookhad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Matched extends AppCompatActivity {
    private GridView gv;
 //   private DatabaseReference db;
    private ArrayList<Book> wishes;
    private FirebaseAuth mAuth;
    private DatabaseReference db,db2,db3;
    private ArrayList<String> glist;
    private List<String> l;
    private ArrayAdapter<String> gadapter;
    private ArrayList<String> numbers;
    long k=0;
    int c=0,cmt=0;
    int cnt=0;
    private List<String> uids;
    Map<String,Object> tem;
    private List<String> names;
    HashSet<String> set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matched);
        gv=(GridView)findViewById(R.id.Mgrid);
        wishes=new ArrayList<Book>();
        numbers=new ArrayList<String>();
        names=new ArrayList<String>();
        l=new ArrayList<String>();
        uids=new ArrayList<String>();
      //  List<String> glist=new List<String>()
        glist=new ArrayList<String>();
        gadapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,glist);
        gv.setAdapter(gadapter);
        glist.add("User");
        glist.add("Book");
        //glist.add("found");
        gadapter.notifyDataSetChanged();

        mAuth=FirebaseAuth.getInstance();
        DatabaseReference db4;
        db= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("Wishlist");

        db4=db;
        db.addChildEventListener(new ChildEventListener() {

            int count=0;
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Book bk=(Book)dataSnapshot.getValue(Book.class);
                wishes.add(bk);
                if(!bk.tit.equals("null")){
                l.add(bk.tit);}
                count++;
                //Toast.makeText(Matched.this,"in the on child added"+Integer.toString(count),Toast.LENGTH_SHORT).show();

               /* if(count>=dataSnapshot.getChildrenCount()){
                    k=1;
                    Toast.makeText(Matched.this,"in the on child added",Toast.LENGTH_LONG).show();

                }*/
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
        db4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(Matched.this,"value Method",Toast.LENGTH_LONG).show();
//                Toast.makeText(Matched.this,l.toString(),Toast.LENGTH_LONG).show();
                if(!l.isEmpty())
                func1();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

/*
        while(true){
            Toast.makeText(Matched.this,"in while...",Toast.LENGTH_SHORT).show();
            if(k==1)break;
        }
*//*
        FirebaseUser us=mAuth.getCurrentUser();
        final String uid=us.getUid();
        tem=new HashMap<String, Object>();
        db2=FirebaseDatabase.getInstance().getReference().child("Users");
        db2.addChildEventListener(new ChildEventListener() {

            int cnt=0;
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                uids.add(dataSnapshot.getKey());

             if(c>=dataSnapshot.getChildrenCount()){
                c=1;
             }

             /*   tem.put(temp.ge);
                if(temp.containsKey(uid)){

                }else{
                    Book b=temp.get()
                }*/
            //}
            /*

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
        while(true){
            if(c==1){
                c=-1;
                break;
            }

        }
        int cnt=uids.size();
        set=new HashSet<String>(l);
        for(int i=0;i<uids.size();i++){
            db3=FirebaseDatabase.getInstance().getReference().child("Users").child(uids.get(i)).child("Shelf");
            db3.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Book bk = (Book) dataSnapshot.getValue(Book.class);
                    if (!bk.tit.equals("null")) {
                        String temp=bk.tit;
                        if(set.contains(temp)){
                            glist.add(bk.tit);
                            glist.add(bk.authr);
                            glist.add("found");
                            gadapter.notifyDataSetChanged();

                        }
                    }
                    if(cmt>=dataSnapshot.getChildrenCount()){
                        c=9;
                    }

                }
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }


                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }


                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }


                public void onCancelled(DatabaseError databaseError) {

                }
            });
            while(true){
                if(c==9){
                    c=0;
                    break;
                }
            }
        }
*/
/*db.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
//this is called wen all the callbacks of onchild added are done
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});*/

    }
    protected void func1(){
        FirebaseUser us=mAuth.getCurrentUser();
        final String uid=us.getUid();
        //tem=new HashMap<String, Object>();
        db2=FirebaseDatabase.getInstance().getReference().child("Users");
        DatabaseReference db5=db2;
        db2.addChildEventListener(new ChildEventListener() {


            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Toast.makeText(Matched.this,dataSnapshot.getKey(),Toast.LENGTH_LONG).show();
                uids.add(dataSnapshot.getKey());
                getnames(dataSnapshot.getKey());
                cnt++;
            }



            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }


            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        db5.addValueEventListener(new ValueEventListener() {
            int namenumber=0;
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator e=uids.iterator();
                while (e.hasNext()){
                    String tempuid=(String)e.next();
                    if(!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(tempuid)){
                        func2(tempuid,namenumber);
                    }
                    namenumber++;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    protected void getnames(String uid){

        DatabaseReference zz=FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Name");
        DatabaseReference xx=FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("PhoneNo");
        zz.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                names.add(dataSnapshot.getValue().toString());
                //Toast.makeText(Matched.this,dataSnapshot.getValue().toString(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        xx.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                numbers.add(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    protected  void func2(String tempuid, final int namenumber){
            DatabaseReference du1=FirebaseDatabase.getInstance().getReference().child("Users").child(tempuid).child("Shelf");
            DatabaseReference du2=du1;

            du1.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    //Toast.makeText(Matched.this,dataSnapshot.getValue().toString(),Toast.LENGTH_SHORT).show();


                    Book c=(Book)dataSnapshot.getValue(Book.class);
                    //Toast.makeText(Matched.this,Boolean.toString(l.contains(c.tit)),Toast.LENGTH_SHORT).show();
                    //Toast.makeText(Matched.this,names.get(namenumber),Toast.LENGTH_SHORT).show();
                    if(l.contains(c.tit)){
                        glist.add(names.get(namenumber)+"\n"+numbers.get(namenumber));
                        glist.add(c.tit+" \n "+c.authr);
                        gadapter.notifyDataSetChanged();
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
    }
}
