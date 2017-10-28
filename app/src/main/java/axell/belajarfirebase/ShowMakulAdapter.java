package axell.belajarfirebase;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import axell.belajarfirebase.Model.Makul;

/**
 * Created by axellageraldinc on 10/27/17.
 */

public class ShowMakulAdapter extends RecyclerView.Adapter<ShowMakulAdapter.ItemViewHolder> {
    Context context;
    List<Makul> makulList = new ArrayList<>();

    public ShowMakulAdapter(Context context, List<Makul> makulList){
        this.context = context;
        this.makulList = makulList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_makul_adapter, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(v);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.txtMakul.setText(makulList.get(position).getNama_makul());
        holder.txtDosen.setText(makulList.get(position).getDosen());
        holder.txtMakulId.setText(makulList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return makulList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtMakul, txtDosen, txtMakulId;
        public ItemViewHolder(View itemView) {
            super(itemView);
            txtMakul = itemView.findViewById(R.id.txtMakul);
            txtDosen = itemView.findViewById(R.id.txtDosen);
            txtMakulId = itemView.findViewById(R.id.txtMakulId);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UpdateMakul.class);
                    intent.putExtra("id_makul", txtMakulId.getText().toString());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
