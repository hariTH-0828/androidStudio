package test.AnggaRisky.fin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    public void onClickbtnSubmit(View view){
        ImageButton loginBtn = findViewById(R.id.onSubmit);
        EditText userName = findViewById(R.id.editName);

        Toast.makeText(SignupActivity.this,"Welcome, "+ userName.getText().toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }
}