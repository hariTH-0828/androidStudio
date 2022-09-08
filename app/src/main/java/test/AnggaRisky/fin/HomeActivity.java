package test.AnggaRisky.fin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    public Button Logout;
    private FirebaseAuth logoutAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Logout = findViewById(R.id.onLogout);

        TextView userName = (TextView) findViewById(R.id.textWelcome);
        userName.setText("Welcome "+getIntent().getStringExtra("userName"));

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void logout(){
        logoutAuth = FirebaseAuth.getInstance();
        Toast.makeText(this, "Logout successfull!", Toast.LENGTH_SHORT).show();
        logoutAuth.signOut();
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
        finish();
    }
}