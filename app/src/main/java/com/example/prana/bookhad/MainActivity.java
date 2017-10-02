package com.example.prana.bookhad;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks  mCallbacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private DatabaseReference mydb;
    private Button Verify,Send;
    private DatabaseReference check;
    private EditText No,Code;
    private String mVerificationId;
    private static final String TAG = "NOTHING";
int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb= FirebaseDatabase.getInstance().getReference().child("Users");
        check=FirebaseDatabase.getInstance().getReference().child("Users");
        Verify=(Button)findViewById(R.id.verify);
        Send=(Button)findViewById(R.id.sendid);
        No=(EditText)findViewById(R.id.number);
        Code=(EditText)findViewById(R.id.code);

        mAuth=FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(MainActivity.this,Main2Activity.class));
        }

        mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                // [START_EXCLUDE silent]
                //mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    Toast.makeText(MainActivity.this, "Phone number wrong", Toast.LENGTH_LONG).show();
                    //mPhoneNumberField.setError("Invalid phone number.");
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Toast.makeText(MainActivity.this, "Too much load", Toast.LENGTH_SHORT).show();
                    //Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                      //      Snackbar.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }
                //Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                mVerificationId=s;
                mResendToken=forceResendingToken;
            }
        };
        Verify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ver();
            }
        });
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();

            }
        });
    }
    private void ver(){
        String otp=Code.getText().toString();
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mVerificationId,otp);
        signInWithPhoneAuthCredential(credential);
    }
    private void send(){
        String phonenum=No.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phonenum,60, TimeUnit.SECONDS,this,mCallbacks);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(MainActivity.this,"Logged in",Toast.LENGTH_LONG).show();
                            FirebaseUser us=mAuth.getCurrentUser();
                            final String userid=us.getUid();
                  //      int g=checks(userid);
                            /*while(g==9){
                                if(flag==2)break;
                            }*/
                            Toast.makeText(MainActivity.this,Integer.toString(flag),Toast.LENGTH_LONG).show();

                            mydb.child(userid).child("Shelf").push().setValue(new Book());
                            mydb.child(userid).child("Wishlist").push().setValue(new Book());
                            startActivity(new Intent(MainActivity.this,UserDetails.class));

                            //FirebaseUser user = task.getResult().getUser();
                            // [START_EXCLUDE]
                            //updateUI(STATE_SIGNIN_SUCCESS, user);
                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                              //  mVerificationField.setError("Invalid code.");
                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
                            //updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                        }
                    }
                });
    }
    private int checks(final String userid){
//        DatabaseReference db2=check;
        check.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String,Object> temp=(Map<String, Object>) dataSnapshot.getValue();

                if(temp!=null){
                if(temp.containsKey(userid)) {
                    Toast.makeText(MainActivity.this, "Welcome Back!", Toast.LENGTH_LONG).show();
                 //   gotohome();
                    flag = 2;
                    //*al.add(userid);
                   // f[0] = 1;*//*
               }}
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //f[0] = 2;
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //f[0] = 3;
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //f[0] = 4;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //f[0] = 5;
            }
        });
        /*db2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(flag!=2){
                    gotohome();
                }
                else{

                    mydb.child(userid).child("Shelf").push().setValue(new Book());
                    mydb.child(userid).child("Wishlist").push().setValue(new Book());
                    startActivity(new Intent(MainActivity.this,UserDetails.class));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        return 9;
    }
    private void gotohome(){

        Intent n=new Intent(this,Main2Activity.class);
        startActivity(n);
    }

}
