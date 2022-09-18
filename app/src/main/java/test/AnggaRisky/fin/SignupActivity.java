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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText sEmail, sRepassword, sPassword, sUsername;
    public ImageButton signUp;
    public TextView signIn;
    private FirebaseAuth sAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        sAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Variable Declaration
        sEmail = findViewById(R.id.editMailID);
        sUsername = findViewById(R.id.editUsername);
        sPassword = findViewById(R.id.editPassword);
        sRepassword = findViewById(R.id.editRepass);
        signUp = findViewById(R.id.onRegister);
        signIn = findViewById(R.id.onSignIn);

        // Button Function Definition
        signUp.setOnClickListener(view -> createUser());
        signIn.setOnClickListener(view1 -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    // This method is user to CREATE_USER in Firebase
    private void createUser(){
        String email = sEmail.getText().toString();
        String password = sPassword.getText().toString();
        String username = sUsername.getText().toString();
        String repassword = sRepassword.getText().toString();

        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if(!password.isEmpty() && password.equals(repassword) && password.length() >= 8) {
                // START Create_user_with_email
                sAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d("TAG", "createUserWithEmail : Success");
                            Toast.makeText(SignupActivity.this, "Registration Successfully " + username, Toast.LENGTH_LONG).show();
                            String userID = sAuth.getCurrentUser().getUid();
                            writeNewUser(userID, username, email);
                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                }).addOnFailureListener(SignupActivity.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("TAG", "user is already exist");
                            Toast.makeText(SignupActivity.this, "This user already exist", Toast.LENGTH_SHORT).show();
                        }
                });
            } else{
                sPassword.setError("Empty Field is not allowed");
                Toast.makeText(SignupActivity.this,"Error Password!!",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(SignupActivity.this,"Incorrect Email Format",Toast.LENGTH_LONG).show();
        }

    }

    public void writeNewUser(String userID, String name, String email){
        User user = new User(name, email);
        mDatabase.child("users").child(userID).child("username").setValue(name);
    }

}