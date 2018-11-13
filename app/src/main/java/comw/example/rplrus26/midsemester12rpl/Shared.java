package comw.example.rplrus26.midsemester12rpl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Shared extends AppCompatActivity {

    public static final String data = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences(data, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        if (username == "") {
            Intent intent = new Intent(Shared.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        else if (username == username){
            Intent intent = new Intent(Shared.this, Home.class);
            startActivity(intent);
            finish();
        }
    }
}