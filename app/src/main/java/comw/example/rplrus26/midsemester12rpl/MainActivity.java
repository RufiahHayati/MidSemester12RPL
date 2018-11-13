package comw.example.rplrus26.midsemester12rpl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int MAIN_ACTIVITY_REQUEST_CODE;
    EditText tusername, password;
    Button btnLogin;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tusername = findViewById(R.id.edt_username);
        password = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_Login);

        SharedPreferences.Editor editor;
        pref = getSharedPreferences("testapp", MODE_PRIVATE);
        editor = pref.edit();
        editor.putString("login", "true");
        editor.commit();


        btnLogin.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tusername.getText().toString().equals("rufiah") && password.getText().toString().equals("rufiah")){

                    Toast.makeText(getApplicationContext(),"Sucess", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,Home.class);

                    String username = tusername.getText().toString();
                    SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.commit();
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Ups!!Ada Yang Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
//        boolean stateValue = sharedPreferences.getBoolean("", false);
//
//        if (requestCode == MAIN_ACTIVITY_REQUEST_CODE) {
//
//            if (!stateValue){
//                finish();
//            }else {
//                updateLoginState(false);
//                super.onActivityResult(requestCode, resultCode, data);
//            }
//        }else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }
//
//    private void updateLoginState(boolean b) {
//    }
}
