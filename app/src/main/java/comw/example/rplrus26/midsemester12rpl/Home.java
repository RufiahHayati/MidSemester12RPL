package comw.example.rplrus26.midsemester12rpl;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

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
import java.util.List;

public class Home extends AppCompatActivity {

    public ArrayList<Member>MemberArrayList = new ArrayList<>();
    RecyclerView gv_nama;
    Member member;
    RecyclerViewAdapter adapter;
    JSONArray Hasiljson;
    LinearLayout listdata, listload;

    SearchView SearchPendek;
    ViewPager viewPager;
    Toolbar toolbar;
    TabLayout tabLayout;

    //use array

//    String[][] Member = new String[][]{
//            {"  Park Jinyoung", "Lead-Vocal, Park Jin Young dilahirkan di Jinhae-gu, Korea Selatan, pada tanggal 22 September, 1994. Park Jinyoung masuk Grup BoyBand GOT7."},
//            {"  Jung Hoseok", "Lead-Rapper, Jung Hoseok dilahirkan di Gwangju, Korea Selatan, 18 February 1994. Jhoseok paling rapi dari semua member BTS. Jung Hoseok masuk Grup BoyBand BTS."},
//            {"  Kim Mingyu", "Vocal, Kim Mingyu di lahirkan di Anyang, Gyeonggi-do, Korea Selatan pada tanggal 6 April 1997. Dia saat ini berumur 19 tahun. Zodiak nya adalah Aries. Kim Mingyu masuk Grup BoyBand Seventeen."},
//            {"  Lee Minhyuk", "Visual, Minhyuk (민혁) merupakan seorang penyanyi yang lahir di Gwangju, Korea Selatan pada tanggal 3 November 1993. Minhyuk masuk Grup BoyBand Monsta X."},
//            {"  Lee Hoseok", "Rapper, Wonho (원호) atau Lee Hoseok merupakan seorang penyanyi yang lahir di Anyang, Gyeonggi-do pada tanggal 1 Maret 1993. Wonho masuk Grup BoyBand Monsta X."},
//            {"  Ooh Sehun", "Sub-Vokalist, Sehun lahir di Seoul, Korea Selatan pada tanggal 12 April 1994. Sehun paling muda dari semua member EXO. Ooh Sehun masuk Grup BoyBand EXO."},
//            {"  Kim Woobin", "Drama, Kim Woo Bin adalah adalah seorang aktor Korea Selatan kelahiran 16 Juli, 1989. Saat memasuki usia 20 tahunan, ia berprofesi sebagai seorang model sebelum akhirnya beralih profesi menjadi aktor di tahun 2011."},
//            {"  Park Jimin", "Vocal Utama dan Dancer, Jimin adalah seorang penyanyi dan juga penari yang berasal dari Korea selatan, Jimin lahir pada tanggal 13 Oktober 1995 di Busan, Korea Selatan. Jimin masuk Grup BoyBand BTS."},
//            {"  Kim Hanbin", "Rapper dan Penyayi, B.I merupakan seorang rapper, penyanyi, penari, penulis lagu, koreografer asal Korea Selan yang lahir pada tanggal 22 Oktober 1996 di Korea Selatan, B.I lahir dengan nama lengkap Kim Han-bin."},
//    };
//
//    int [] image_idol = new int[]{
//            R.drawable.jinyoung, R.drawable.jhope, R.drawable.mingyu, R.drawable.minhyuk, R.drawable.wonho, R.drawable.seehun, R.drawable.kim, R.drawable.jimin, R.drawable.hanbin
//    };

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_marks,menu);
        final MenuItem searchItem = menu.findItem(R.id.Cari);
        SearchManager searchManager = (SearchManager)Home.this.getSystemService(Context.SEARCH_SERVICE);

        if (SearchPendek !=null){
            SearchPendek.setIconified(false);

        }
        if (searchItem != null){
            SearchPendek =(SearchView)searchItem.getActionView();

        }
        SearchPendek.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                Toast.makeText(getApplicationContext(), " " + query, Toast.LENGTH_SHORT).show();
                MemberArrayList.clear();

                for(Member d : MemberArrayList){
                    if(d.getUsername() != null && d.getUsername().contains(query)) {
                        Log.d("Test", "onQueryTextSubmit: " + d.getUsername());

                        if(!MemberArrayList.contains(d))
                            MemberArrayList.add(d);

                    }
                }

                RecyclerViewAdapter searchedItemAdapter = new RecyclerViewAdapter(getApplicationContext(), MemberArrayList);
                gv_nama.setAdapter(searchedItemAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                List<Member> newList = new ArrayList<>();
                return true;
            }
        });

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.out:
                SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(Home.this,MainActivity.class);
                startActivity(intent);
                finish();
            case R.id.Favorite:
                Intent a = new Intent(this,FavoriteActivity.class);
                startActivity(a);
                finish();
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Now Playing"));
        tabLayout.addTab(tabLayout.newTab().setText("Upcoming"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);

        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        gv_nama.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if (!recyclerView.canScrollVertically(1)) {
//                    more = gv_nama.getLayoutManager().onSaveInstanceState();
//                    ambildata = new ambildata();
//                    ambildata.execute();
//                }
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });

//        MemberArrayList = new ArrayList<Member>();
//        for (int i =0; i < Member.length; i++){
//            member = new Member();
//            member.setUsername(Member[i][0]);
//            member.setPhotoId(image_idol[i]);
//            member.setDeskripsi(Member[i][1]);
//            MemberArrayList.add(member);
//        }
//        gv_nama = findViewById(R.id.gv_detail);
//        gv_nama.setLayoutManager(new LinearLayoutManager(Home.this));
//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(Home.this, MemberArrayList);
//        gv_nama.setAdapter(adapter);

       // new ambildata().execute((Void)null);
    }

//    @SuppressLint("StaticFieldLeak")
//    public class ambildata extends AsyncTask<Void, Void, JSONObject> {
//
//
//        @Override
//        protected void onPreExecute() {
//            //kasih loading
//        }
//
//        @Override
//        protected JSONObject doInBackground(Void... params) {
//            JSONObject jsonObject;
//            try {
//
//                String url="https://api.themoviedb.org/3/movie/now_playing?api_key=9f880a971182565a05cacbaae6e9195b";
//                System.out.println("url ku " +url);
//                DefaultHttpClient httpClient = new DefaultHttpClient();
//                HttpGet httpGet = new HttpGet(url);
//                HttpResponse httpResponse = httpClient.execute(httpGet);
//                HttpEntity httpEntity = httpResponse.getEntity();
//                InputStream inputStream = httpEntity.getContent();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(
//                        inputStream, "iso-8859-1"
//                ), 8);
//                StringBuilder stringBuilder = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    stringBuilder.append(line).append("\n");
//                }
//                inputStream.close();
//                String json = stringBuilder.toString();
//                System.out.println("json " + json);
//                jsonObject = new JSONObject(json);
//            } catch (Exception e) {
//                jsonObject = null;
//
//                System.out.println("Hello" + e.getLocalizedMessage());
//            }
//            return jsonObject;
//        }
//
//        @Override
//        protected void onPostExecute(JSONObject jsonObject) {
//            //System.out.println("Hasilnya Adalah " +jsonObject.toString());
//
//            listload.setVisibility(View.GONE);
//            listdata.setVisibility(View.VISIBLE);
//
//            if (jsonObject == null) {
//            }else if (jsonObject!=null) {
//                try {
//                    MemberArrayList = new ArrayList<>();
//                    Hasiljson = jsonObject.getJSONArray("results");
////                    JSONArray Hasiljson = jsonObject.getJSONArray("results");
////                    MemberArrayList = new ArrayList<>();
//                    for (int i = 0; i < Hasiljson.length(); i++) {
//                        member = new Member();
//                        String myurl = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/";
//                        String photo = Hasiljson.getJSONObject(i).getString("poster_path");
//                        member.setID(Hasiljson.getJSONObject(i).getString("id"));
//                        member.setUsername(Hasiljson.getJSONObject(i).getString("title"));
//                        member.setPhotoId(myurl + photo);
//                        member.setDeskripsi(Hasiljson.getJSONObject(i).getString("overview"));
//                        member.setTanggal(Hasiljson.getJSONObject(i).getString("release_date"));
//                        //Toast.makeText(getApplicationContext(), member.getUsername(), Toast.LENGTH_SHORT).show();
//
//                        MemberArrayList.add(member);
//                    }
//                    //pasang data arraylist ke lisview
//                    adapter = new RecyclerViewAdapter(Home.this, MemberArrayList);
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
//                    gv_nama.setLayoutManager(new LinearLayoutManager(Home.this));
//                    gv_nama.getLayoutManager().onRestoreInstanceState(more);
//                    gv_nama.setAdapter(adapter);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//
//
//                }
//            }
//        }
//    }

}