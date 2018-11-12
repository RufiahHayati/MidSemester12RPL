package comw.example.rplrus26.midsemester12rpl;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import comw.example.rplrus26.midsemester12rpl.database.DatabaseHelper;
import comw.example.rplrus26.midsemester12rpl.database.MahasiswaHelper;
import comw.example.rplrus26.midsemester12rpl.database.MahasiswaModel;

public class Detail_Nama extends AppCompatActivity {

    MahasiswaHelper mahasiswaHelper;
    DatabaseHelper databaseHelper;
    MahasiswaModel mahasiswaModel;
    ImageView iv_nama;
    TextView tnama, tdeskripsi;
    Button btn_trailer;
    FloatingActionButton fab;
    boolean flag = true;
    String username;
    String deskripsi;
    String photoId;
    String id;
    //String trailer;
    String movieId;
    String tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__nama);

        iv_nama = findViewById(R.id.img_photo);
        tnama = findViewById(R.id.tusername);
        tdeskripsi = findViewById(R.id.tdeskripsi);
        btn_trailer = findViewById(R.id.btn_trailer);
        mahasiswaHelper = new MahasiswaHelper(Detail_Nama.this);
        databaseHelper = new DatabaseHelper(Detail_Nama.this);
        mahasiswaModel = new MahasiswaModel();
        fab = findViewById(R.id.fab);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        username = bundle.getString("username");
        deskripsi = bundle.getString("deskripsi");
        photoId = bundle.getString("photo");
        tanggal = bundle.getString("tanggal");
        movieId = bundle.getString("id");
        tnama.setText(username);
        tdeskripsi.setText(deskripsi);
        Glide.with(Detail_Nama.this).load(photoId).into(iv_nama);
        //new ambilURLYoutube(movieId).execute((Void) null);

        btn_trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (trailer.equals("trailer") || trailer == null)
                //{
                //Tidak ada trailer
                //} else
                //{
                String url = "https://youtu.be/u9Mv98Gr5pY";
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
                //}
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flag == true) {
                    mahasiswaHelper.open();
                    mahasiswaHelper.beginTransaction();
                    MahasiswaModel m = new MahasiswaModel(username, deskripsi, photoId, tanggal);
                    mahasiswaHelper.insertTransaction(m);
                    mahasiswaHelper.setTransactionSuccess();
                    mahasiswaHelper.endTransaction();
                    mahasiswaHelper.close();
                    Snackbar.make(view, "Tersimpan", Snackbar.LENGTH_LONG).setAction("Save", null).show();
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_black_24dp));
                    flag = false;

                } else if (flag == false) {
                    Snackbar.make(view, "Cancel", Snackbar.LENGTH_LONG).setAction("cancel", null).show();
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border_black_24dp));
                    flag = true;

                }
            }
        });
    }
//    @android.annotation.SuppressLint("StaticFieldLeak")
//    public class ambilURLYoutube extends AsyncTask<Void, Void, JSONObject> {
//
//        String movieID;
//        JSONArray hasil;
//
//        public ambilURLYoutube(String id)
//        {
//            movieID = id;
//        }
//
//        @Override
//        protected void onPreExecute()
//        {
//            //kasih loading
//        }
//
//        @Override
//        protected JSONObject doInBackground(Void... params) {
//            JSONObject jsonObject;
//            try {
//
//                String url= "https://api.themoviedb.org/3/movie/" + this.movieID + "/videos?api_key=9f880a971182565a05cacbaae6e9195b&language=en-US";
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
//            if (jsonObject == null)
//            {
//               //Tidak ada trailer
//            }
//            else if (jsonObject != null) {
//                try {
//                    hasil = jsonObject.getJSONArray("results");
//
//                    if (hasil == null || hasil.length() <= 0)
//                    {
//                        //Tidak ada trailer
//                    } else
//                    {
//                        String key = "";
//
//                        for (int i = 0; i < hasil.length(); i++)
//                        {
//                            if (hasil.getJSONObject(i).getString("key") != null || !hasil.getJSONObject(i).getString("key").equals(""))
//                            {
//                                key = hasil.getJSONObject(i).getString("key");
//                                break;
//                            }
//                        }
//
//                        if (!key.equals("") || key != null)
//                        {
//                            trailer = "https://www.youtube.com/watch?v=" + key;
//                        } else
//                        {
//                            //Tidak ada trailer
//                        }
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }
}