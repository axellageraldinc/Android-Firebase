package axell.belajarfirebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import axell.belajarfirebase.Model.Makul;

public class AddMakul extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    EditText txtMakul, txtDosen;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_makul);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = firebaseAuth.getCurrentUser();

        txtMakul = findViewById(R.id.txtMakul);
        txtDosen = findViewById(R.id.txtDosen);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddMakul();
            }
        });
    }

    private void AddMakul(){
        String id_makul = UUID.randomUUID().toString();
        String nama_makul = txtMakul.getText().toString();
        String dosen = txtDosen.getText().toString();
        String userId = firebaseUser.getUid();
        Makul makul = new Makul(nama_makul, dosen);
//        databaseReference.child("users").child(userId).child("makul").setValue(makul); //Kode disamping hanya akan me-replace data makul yang sudah ada dengan yang baru
        Map<String, Object> param = new HashMap<>();
        param.put("/users/" + userId + "/makul/" + id_makul, makul);
        databaseReference.updateChildren(param).addOnCompleteListener(new OnCompleteListener<Void>() { //ekuivalen dengan insert into makul (id, nama_makul, dosen) ...., dimana makul itu ada di dalam users
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                txtMakul.setText("");
                txtDosen.setText("");
                Toast.makeText(AddMakul.this, "Success!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
