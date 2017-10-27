package axell.belajarfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import axell.belajarfirebase.Model.Makul;

public class ShowMakul extends AppCompatActivity {
    RecyclerView recyclerMakul;
    ShowMakulAdapter showMakulAdapter;
    List<Makul> makulList = new ArrayList<>();

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_makul);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        recyclerMakul = findViewById(R.id.recyclerMakul);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerMakul.setLayoutManager(linearLayoutManager);
        showMakulAdapter = new ShowMakulAdapter(getApplicationContext(), makulList);
        GetMakul();
    }

    private void GetMakul(){
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid()).child("makul");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                makulList.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Makul makul = data.getValue(Makul.class);
                    makulList.add(makul);
                }
                recyclerMakul.setAdapter(showMakulAdapter);
                showMakulAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
