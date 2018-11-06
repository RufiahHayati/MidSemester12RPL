package comw.example.rplrus26.midsemester12rpl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import comw.example.rplrus26.midsemester12rpl.database.MahasiswaHelper;
import comw.example.rplrus26.midsemester12rpl.database.MahasiswaModel;


public class FavoriteActivity extends AppCompatActivity {

    private MahasiswaHelper mahasiswaHelper;
    private LinearLayoutManager Layout;
    RecyclerView RView;
    private ArrayList<MahasiswaModel> models;
    ModelAdapter adapter;
    TextView TextData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        Layout = new LinearLayoutManager(FavoriteActivity.this);
        TextData = findViewById(R.id.TextData);
        RView = findViewById(R.id.Recycler);
        RView.setLayoutManager(Layout);
        mahasiswaHelper = new MahasiswaHelper(getApplicationContext());
        mahasiswaHelper.open();
        models = new ArrayList<>();
        RView.setVisibility(View.VISIBLE);
        models = mahasiswaHelper.getAllData();
        Log.e("TAG", "onCreate: "+models );
        adapter = new ModelAdapter(getApplicationContext(), models);
        RView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        RView.setAdapter(adapter);
    }
}