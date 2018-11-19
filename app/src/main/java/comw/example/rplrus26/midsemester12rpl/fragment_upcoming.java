package comw.example.rplrus26.midsemester12rpl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_upcoming extends Fragment {

    View root;
    RecyclerView recyclerView;
    public ArrayList<Result> results;

    public fragment_upcoming(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.fragment_upcoming, container, false);
        recyclerView = root.findViewById(R.id.gv_detail_upcoming);
        load_data_upcoming();
        return root;
    }

    private void load_data_upcoming() {
        json_api service = retrofitclientinstance.getRetrofitInstance().create(json_api.class);
        Call<jsonRespond> call = service.getJsonUpcoming();
        call.enqueue(new Callback<jsonRespond>() {
            @Override
            public void onResponse(Call<jsonRespond> call, Response<jsonRespond> response) {
                jsonRespond jsonRespond = response.body();
                results = new ArrayList<>(Arrays.<Result>asList(jsonRespond.getResults()));
                Log.d("responku", "onResponse: " + jsonRespond.getResults());
                RecyclerViewAdapterRetrofit adapter = new RecyclerViewAdapterRetrofit(root.getContext(), results);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
//                shimmerFrameLayout.stopShimmerAnimation();
//                shimmerFrameLayout.setVisibility(View.GONE);
            }


            @Override
            public void onFailure(Call<jsonRespond> call, Throwable t) {
                Log.d("responku", "onFailure: gagal");
            }
        });
    }
}
