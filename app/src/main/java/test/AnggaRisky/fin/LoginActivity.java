package test.AnggaRisky.fin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText getEmail, getPassword;
    public ImageButton submit;
    private FirebaseAuth lAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = lAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lAuth = FirebaseAuth.getInstance();
        getEmail = findViewById(R.id.setEmail);
        getPassword = findViewById(R.id.setPassword);
        submit = findViewById(R.id.onSignin);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser(){
        String vEmail = getEmail.getText().toString();
        String vPass = getPassword.getText().toString();

        if(!vEmail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(vEmail).matches()) {
            if(!vPass.isEmpty() && vPass.length() >= 8) {
                lAuth.signInWithEmailAndPassword(vEmail, vPass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "signInWithEmail:success");
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                            reload();
                        } else {
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else getPassword.setError("Invalid Password");
        }else getEmail.setError("Email is in Incorrect Format");
    }

    private void reload(){
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}