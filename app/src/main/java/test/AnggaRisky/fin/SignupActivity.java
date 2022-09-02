package test.AnggaRisky.fin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    private EditText sEmail, sRepassword, sPassword;
    private ImageButton SignUp;
    private FirebaseAuth sAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        sEmail = findViewById(R.id.editMailID);
        sPassword = findViewById(R.id.editPassword);
        sRepassword = findViewById(R.id.editRepass);
        SignUp = findViewById(R.id.onSubmit);

        sAuth = FirebaseAuth.getInstance();

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }

    private void createUser(){
        String email = sEmail.getText().toString();
        String password = sPassword.getText().toString();
        String repassword = sRepassword.getText().toString();

        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if(!password.isEmpty() && password.equals(repassword)) {
                sAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(SignupActivity.this, "Registration Successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignupActivity.this, "Registration Failed!", Toast.LENGTH_LONG).show();
                    }
                });
            } else{
//                sPassword.setError("Empty Field is not allowed");
                Toast.makeText(SignupActivity.this,"Error Password!!",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(SignupActivity.this,"Incorrect Email Format",Toast.LENGTH_LONG).show();
        }

    }

    public void onClickLogin(View view) {
        TextView signIn = findViewById(R.id.onSignIn);
        signIn.setOnClickListener(view1 -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}