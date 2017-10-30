package axell.belajarfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import axell.belajarfirebase.Model.Makul;

public class UpdateMakul extends AppCompatActivity {
    EditText txtMakul, txtDosen;
    TextView txtMakulId;
    Button btnUpdate, btnDelete;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_makul);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        txtMakul = findViewById(R.id.txtMakul);
        txtDosen = findViewById(R.id.txtDosen);
        txtMakulId = findViewById(R.id.txtMakulId);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        Intent intent = getIntent();
        final String id_makul = intent.getStringExtra("id_makul");
        txtMakulId.setText(id_makul);

        SetEditText();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Makul makul = new Makul(txtMakulId.getText().toString(), txtMakul.getText().toString(), txtDosen.getText().toString());
//                databaseReference.setValue(makul);
                databaseReference.child("users").child(firebaseUser.getUid()).child("makul").child(txtMakulId.getText().toString()).setValue(makul).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent1 = new Intent(getApplicationContext(), ShowMakul.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);
                    }
                });
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("users").child(firebaseUser.getUid()).child("makul").child(txtMakulId.getText().toString()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(UpdateMakul.this, "Makul berhasil dihapus!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else{
                            Toast.makeText(UpdateMakul.this, "Gagal menghapus makul!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
            }
        });
    }

    private void SetEditText(){
        databaseReference.child("users").child(firebaseUser.getUid()).child("makul").child(txtMakulId.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    Makul makul = dataSnapshot.getValue(Makul.class);
                    txtMakulId.setText(makul.getId());
                    txtMakul.setText(makul.getNama_makul());
                    txtDosen.setText(makul.getDosen());
                } catch (Exception ex){
                    System.out.println("Gagal onDataChange SetEditText : " + ex.toString());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
