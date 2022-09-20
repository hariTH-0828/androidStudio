package test.AnggaRisky.fin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    public Button Logout;
    public TextView userName;

    private FirebaseAuth homeAuth;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Logout = findViewById(R.id.onLogout);

        userName = findViewById(R.id.textWelcome);
        userName.setText("Welcome "+getIntent().getStringExtra("username"));

        Logout.setOnClickListener(view -> logout());
    }

    private void logout(){
        homeAuth = FirebaseAuth.getInstance();
        Toast.makeText(this, "Logout successfull!", Toast.LENGTH_SHORT).show();
        homeAuth.signOut();
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        finish();
    }
}