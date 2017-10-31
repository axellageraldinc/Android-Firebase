package axell.belajarfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import axell.belajarfirebase.Model.User;

public class MainActivity extends AppCompatActivity {
    TextView txtUserId, txtEmail, txtFullname;
    Button btnAddData, btnShowData, btnLogout;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener!=null)
            firebaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("makul_on_write");
        FirebaseMessaging.getInstance().subscribeToTopic("makul_on_delete");
        txtUserId = findViewById(R.id.txtUserId);
        txtEmail = findViewById(R.id.txtEmail);
        txtFullname = findViewById(R.id.txtFullname);
        btnAddData = findViewById(R.id.btnAddData);
        btnShowData = findViewById(R.id.btnShowData);
        btnLogout = findViewById(R.id.btnLogout);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser!=null){
                    final String userId = firebaseUser.getUid();

                    final String email = firebaseUser.getEmail();
                    databaseReference.child("users").child(userId).child("biodata").addValueEventListener(new ValueEventListener() { //ekuivalen dengan SELECT * from users where userId=?
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            String fullname = user.getName();
                            txtUserId.setText(userId);
                            txtEmail.setText(email);
                            txtFullname.setText(fullname);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
            });
        }
            }
        };

        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddMakul.class);
                startActivity(intent);
            }
        });
        btnShowData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowMakul.class);
                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
