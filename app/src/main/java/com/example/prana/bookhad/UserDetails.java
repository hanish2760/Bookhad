package com.example.prana.bookhad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDetails extends AppCompatActivity {

    private Button nex;
    private EditText nam;
    private DatabaseReference namestore;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        nex=(Button)findViewById(R.id.next);
        nam=(EditText)findViewById(R.id.name);
        auth=FirebaseAuth.getInstance();
        namestore= FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid());
        nex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n=nam.getText().toString();
                if(!TextUtils.isEmpty(n)) {
                    namestore.child("Name").setValue(n);
                    namestore.child("PhoneNo").setValue(auth.getCurrentUser().getPhoneNumber());
                    startActivity(new Intent(UserDetails.this, Main2Activity.class));
                }
                else{
                    Toast.makeText(UserDetails.this,"Enter valid name",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
