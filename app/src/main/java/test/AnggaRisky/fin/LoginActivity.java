package test.AnggaRisky.fin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {

    private EditText getEmail, getPassword;
    public ImageButton submit;

    private FirebaseAuth lAuth;
    public FirebaseDatabase userDB;
    private DatabaseReference mDatabase;
    public String uname;

    /*@Override
    protected void onStart() {
        super.onStart();
        getUsername();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lAuth = FirebaseAuth.getInstance();
        userDB = FirebaseDatabase.getInstance();
        mDatabase = userDB.getReference("users");
        getEmail = findViewById(R.id.setEmail);
        getPassword = findViewById(R.id.setPassword);
        submit = findViewById(R.id.onSignin);

        submit.setOnClickListener(view -> loginUser());
    }

    private void loginUser(){

        String vEmail = getEmail.getText().toString();
        String vPass = getPassword.getText().toString();

        if(!vEmail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(vEmail).matches()) {
            if(!vPass.isEmpty() && vPass.length() >= 8) {
                lAuth.signInWithEmailAndPassword(vEmail, vPass).addOnCompleteListener(LoginActivity.this, task -> {
                    if (task.isSuccessful()) {
                       Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();

                        String userid = Objects.requireNonNull(lAuth.getCurrentUser()).getUid();
                        DatabaseReference username = mDatabase.child(userid).child("username");

                        username.runTransaction(new Transaction.Handler() {
                            @NonNull
                            @Override
                            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                                //String name = currentData.getValue(String.class);
                                return Transaction.success(currentData);
                            }

                            @Override
                            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                                uname = currentData.getValue(String.class);
                                if(uname != null)
                                    reload(uname);
                            }
                        });
                    } else {
                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
            }else getPassword.setError("Invalid Password");
        }else getEmail.setError("Email is in Incorrect Format");
    }

    private void getUsername(){
        String userid = Objects.requireNonNull(lAuth.getCurrentUser()).getUid();
        DatabaseReference username = mDatabase.child(userid).child("username");

        username.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                //String name = currentData.getValue(String.class);
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                uname = currentData.getValue(String.class);
                if(uname != null)
                    reload(uname);
            }
        });
    }
    public void reload(String uname){
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("username", uname);
        startActivity(intent);
    }
}