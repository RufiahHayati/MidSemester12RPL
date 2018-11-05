package comw.example.rplrus26.midsemester12rpl;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders>  {

    private List<Member> MemberArrayList;
    private Context context;

    public RecyclerViewAdapter(Context context, ArrayList<Member> MemberArrayList){
        this.context=context;
        this.MemberArrayList = MemberArrayList;
    }


    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolders holder, final int position) {
        final Member member = MemberArrayList.get(position);
        Glide.with(context)
                .load(member.getPhotoId())
                .into(holder.personPhoto);
        holder.personName.setText(MemberArrayList.get(position).getUsername());
        holder.klik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = MemberArrayList.get(position).getUsername();
                final String deskripsi = MemberArrayList.get(position).getDeskripsi();
                final String photoId = MemberArrayList.get(position).getPhotoId();
                final String tanggal = MemberArrayList.get(position).getTanggal();
                final String id = MemberArrayList.get(position).getID();
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
                intent.putExtra(Intent.EXTRA_TEXT, member.getUsername()+" \n"+member.getDeskripsi());
                intent.setType("text/plain");
                context.startActivity(intent);
                Toast.makeText(context,"Segera Datang", Toast.LENGTH_SHORT).show();

            }
        });

        holder.klik_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setCancelable(true);
                builder.setMessage(" Are you sure want to delete data ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return MemberArrayList.size();
    }
}
