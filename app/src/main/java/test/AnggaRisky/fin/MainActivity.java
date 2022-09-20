package test.AnggaRisky.fin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    public ImageButton loginBtn;
    public ImageButton signupBtn;

    private FirebaseAuth Auth;
    private FirebaseUser user;

    private DatabaseReference  mDatabase;

    public String uname;

    @Override
    protected void onStart() {
        super.onStart();

        Auth = FirebaseAuth.getInstance();
        user = Auth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        if(user != null){
            getUsername();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = findViewById(R.id.onLogin);
        signupBtn = findViewById(R.id.onSignup);

        signupBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        loginBtn.setOnClickListener(view1 -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void getUsername(){
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase.child(userid).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    uname = String.valueOf(task.getResult().getValue());
                    startActivity(new Intent(MainActivity.this, HomeActivity.class).putExtra("username", uname));
                }
            }
        });
    }
}