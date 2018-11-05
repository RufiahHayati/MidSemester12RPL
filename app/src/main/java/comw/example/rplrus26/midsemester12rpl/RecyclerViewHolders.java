package comw.example.rplrus26.midsemester12rpl;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewHolders extends RecyclerView.ViewHolder {

    public TextView personName;
    public ImageView personPhoto;
    public Button klik;
    public Button klik_Share;
    public Button klik_btn;

    public RecyclerViewHolders(View itemView) {
        super(itemView);

        personName = (TextView)itemView.findViewById(R.id.person_name);
        personPhoto = (ImageView)itemView.findViewById(R.id.circleView);
        klik = (Button)itemView.findViewById(R.id.btn_detail);
        klik_Share = (Button)itemView.findViewById(R.id.btn_detailShare);
        klik_btn = (Button) itemView.findViewById(R.id.btnklik);
    }
}
