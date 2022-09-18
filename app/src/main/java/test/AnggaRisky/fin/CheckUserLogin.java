package test.AnggaRisky.fin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckUserLogin extends AppCompatActivity {

    private FirebaseAuth Auth;
    private DatabaseReference mDatabase;
    private FirebaseDatabase userDB;

    public String uname;


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = Auth.getCurrentUser();
        if(currentUser != null){
            usernameGET();
        }
        if(currentUser == null){
            startActivity(new Intent(CheckUserLogin.this, MainActivity.class));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_user_login);

        Auth = FirebaseAuth.getInstance();
        userDB = FirebaseDatabase.getInstance();
        mDatabase = userDB.getReference("users");

    }

    public void usernameGET(){
        String userid = Auth.getCurrentUser().getUid();
        DatabaseReference username = mDatabase.child(userid).child("username");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uname = snapshot.getValue(String.class);
                Log.d("TAG", "Value is : "+username);
                if(uname != null) reload(uname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "Gailed to read value", error.toException());
            }
        });
    }

    private void reload(String uname){
        startActivity(new Intent(CheckUserLogin.this, HomeActivity.class).putExtra("username", uname));
    }
}