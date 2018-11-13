package comw.example.rplrus26.midsemester12rpl;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import comw.example.rplrus26.midsemester12rpl.database.DatabaseHelper;
import comw.example.rplrus26.midsemester12rpl.database.MahasiswaHelper;
import comw.example.rplrus26.midsemester12rpl.database.MahasiswaModel;

public class ModelAdapter extends RecyclerView.Adapter<RecyclerViewHolders>  {
    MahasiswaModel mahasiswaModel;
    private List<MahasiswaModel> MemberArrayList;
    private Context context;
    private Parcelable more;

    MahasiswaHelper mahasiswaHelper;
    DatabaseHelper databaseHelper;

    public ModelAdapter(Context context, ArrayList<MahasiswaModel> MemberArrayList){
        this.context=context;
        this.MemberArrayList = MemberArrayList;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        mahasiswaHelper = new MahasiswaHelper(layoutView.getContext());
        databaseHelper = new DatabaseHelper(layoutView.getContext());
        return rcv;
    }



    @Override
    public void onBindViewHolder(final RecyclerViewHolders holder, final int position) {
        mahasiswaModel = MemberArrayList.get(position);
        Glide.with(context)
                .load(mahasiswaModel.getUrl())
                .into(holder.personPhoto);
        holder.personName.setText(MemberArrayList.get(position).getName());
        holder.klik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = MemberArrayList.get(position).getName();
                final String deskripsi = MemberArrayList.get(position).getNim();
                final String photoId = MemberArrayList.get(position).getUrl();
                final String tanggal = MemberArrayList.get(position).gettanggal();
                final String id = MemberArrayList.get(position).getId();
                final boolean flag = true;
                Intent i = new Intent(context.getApplicationContext(), Detail_Nama.class);
                i.putExtra("id", id);
                i.putExtra("username", username);
                i.putExtra("deskripsi", deskripsi);
                i.putExtra("photo", photoId);
                i.putExtra("tanggal", tanggal);
                context.startActivity(i);
            }
        });

        holder.klik_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, mahasiswaModel.getName()+" \n"+mahasiswaModel.getNim());
                intent.setType("text/plain");
                context.startActivity(intent);
                Toast.makeText(context,"Segera Datang", Toast.LENGTH_SHORT).show();

            }
        });

        holder.klik_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position2 = position;
                final String name = mahasiswaModel.getId();
                mahasiswaHelper.open();
                mahasiswaHelper.beginTransaction();
                mahasiswaHelper.delete(name);
                mahasiswaHelper.setTransactionSuccess();
                mahasiswaHelper.endTransaction();
                mahasiswaHelper.close();
                MemberArrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, MemberArrayList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return MemberArrayList.size();
    }
}