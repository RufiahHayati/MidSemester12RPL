package comw.example.rplrus26.midsemester12rpl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class fragment_nowPlaying extends Fragment {

    View view;
    RecyclerView recyclerView;
    public ArrayList<Member> MemberList = new ArrayList<>();
    JSONArray Hasiljson;
    Member member;
    RecyclerViewAdapter adapter;

    public fragment_nowPlaying(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        recyclerView = view.findViewById(R.id.gv_detail_nowPlaying);
        new Nowdata().execute();
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    public class Nowdata extends AsyncTask<Void, Void, JSONObject> {


        @Override
        protected void onPreExecute() {
            //kasih loading
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject jsonObject;
            try {

                String url="https://api.themoviedb.org/3/movie/now_playing?api_key=9f880a971182565a05cacbaae6e9195b";
                System.out.println("url ku " +url);
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream inputStream = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        inputStream, "iso-8859-1"
                ), 8);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                inputStream.close();
                String json = stringBuilder.toString();
                System.out.println("json " + json);
                jsonObject = new JSONObject(json);
            } catch (Exception e) {
                jsonObject = null;

                System.out.println("Hello" + e.getLocalizedMessage());
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            //System.out.println("Hasilnya Adalah " +jsonObject.toString());

            if (jsonObject == null) {
            }else if (jsonObject!=null) {
                try {
                    MemberList = new ArrayList<>();
                    Hasiljson = jsonObject.getJSONArray("results");
//                    JSONArray Hasiljson = jsonObject.getJSONArray("results");
//                    MemberArrayList = new ArrayList<>();
                    for (int i = 0; i < Hasiljson.length(); i++) {
                        member = new Member();
                        String myurl = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/";
                        String photo = Hasiljson.getJSONObject(i).getString("poster_path");
                        member.setID(Hasiljson.getJSONObject(i).getString("id"));
                        member.setUsername(Hasiljson.getJSONObject(i).getString("title"));
                        member.setPhotoId(myurl + photo);
                        member.setDeskripsi(Hasiljson.getJSONObject(i).getString("overview"));
                        member.setTanggal(Hasiljson.getJSONObject(i).getString("release_date"));
                        //Toast.makeText(getApplicationContext(), member.getUsername(), Toast.LENGTH_SHORT).show();

                        MemberList.add(member);
                    }
                    //pasang data arraylist ke lisview
                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(view.getContext(), MemberList);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
                    recyclerView.setNestedScrollingEnabled(false);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
