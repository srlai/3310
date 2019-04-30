package com.example.csci3310_projcet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    EditText mTextcnfPassword;
    Button mButtonRegister;
    TextView mTextViewLogin;
    EditText mTextEmail;
    private FirebaseAuth firebaseAuth;
    String user_email;
    String user_password;
    String user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth=FirebaseAuth.getInstance();
        mTextUsername=findViewById(R.id.edittext_username);
        mTextPassword=findViewById(R.id.edittext_password);
        mTextcnfPassword=findViewById(R.id.edittext_cnf_password);
        mButtonRegister=findViewById(R.id.edittext_register);
        mTextViewLogin=findViewById(R.id.textview_login);
        mTextEmail=findViewById(R.id.edittext_email);

        //Register Click
        mButtonRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(!mTextEmail.getText().toString().isEmpty()&&!mTextPassword.getText().toString().isEmpty()&&
                    mTextPassword.getText().toString().equals(mTextcnfPassword.getText().toString())){
                    user_name=mTextUsername.getText().toString().trim();
                    user_email=mTextEmail.getText().toString().trim();
                    user_password=mTextPassword.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                               // Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                                sendUserData();
                                sendEmailVerification();
                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }else
                {
                    Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Login Click
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(registerIntent);
            }
        });
    }
    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(RegisterActivity.this,MainActivity.class));

                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "Verification mail not sent!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference users = db.collection("users");
        Map<String, Object> user = new HashMap<>();
        String id=firebaseAuth.getUid().toString();
        user.put("Uid",id);
        user.put("Email", user_email);
        user.put("Name", user_name);


        db.collection("users").document(id)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

        //UserProfile userProfile = new UserProfile(user_name,user_email,id);
       // myRef.setValue(userProfile);

    }
}
