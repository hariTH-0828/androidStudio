package test.AnggaRisky.fin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private Button Signout;
    private FirebaseAuth logoutAuth;

    public void logout(View view){
        Signout = findViewById(R.id.onSubmit);
        logoutAuth = FirebaseAuth.getInstance();

        Signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutAuth.signOut();
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}