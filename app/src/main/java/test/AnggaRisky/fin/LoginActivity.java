package test.AnggaRisky.fin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {

    private EditText getEmail, getPassword;
    public ImageButton submit;
    private FirebaseAuth lAuth;

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

        if(!vEmail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(vEmail).matches()){
            if(!vPass.isEmpty() && vPass.length() >= 8){
                lAuth.signInWithEmailAndPassword(vEmail, vPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(LoginActivity.this, "Welcome "+vEmail, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("userName", vEmail);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this,"Incorrect username and password", Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                Toast.makeText(LoginActivity.this, "Password is incorrect", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(LoginActivity.this, "Invalid Email ID", Toast.LENGTH_LONG).show();
        }
    }
}