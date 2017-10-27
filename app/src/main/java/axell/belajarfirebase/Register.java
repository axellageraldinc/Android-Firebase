package axell.belajarfirebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import axell.belajarfirebase.Model.User;

public class Register extends AppCompatActivity {
    private String users = "users";
    private String userId = "userId";
    private String email = "email";
    private String password = "password";
    private String fullname = "fullname";

    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabase;

    EditText txtEmail, txtPassword, txtName;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtName = findViewById(R.id.txtName);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtEmail.getText().toString().isEmpty()
                        && !txtPassword.getText().toString().isEmpty()
                        && !txtName.getText().toString().isEmpty())
                    RegisterUser();
                else
                    Toast.makeText(Register.this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void RegisterUser(){
        email = txtEmail.getText().toString();
        password = txtPassword.getText().toString();
        fullname = txtName.getText().toString();

        //register akun dengan email dan password ke firebase Authentication
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Register.this, "User registration success!", Toast.LENGTH_SHORT).show();
                    txtEmail.setText("");
                    txtPassword.setText("");
                    txtName.setText("");
                    userId = task.getResult().getUser().getUid(); //ambil userId yang barusan di-register
                    User user = new User(fullname);
                    firebaseDatabase.child(users).child(userId).setValue(user); //insert ke users di Firebase Database
                } else{
                    Toast.makeText(Register.this, "User registration failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
