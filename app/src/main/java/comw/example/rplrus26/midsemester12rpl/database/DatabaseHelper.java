package comw.example.rplrus26.midsemester12rpl.database;

import android.content.ClipData;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.view.menu.MenuBuilder;

import static android.provider.BaseColumns._ID;
import static comw.example.rplrus26.midsemester12rpl.database.DatabaseContract.MahasiswaColumns.NAMA;
import static comw.example.rplrus26.midsemester12rpl.database.DatabaseContract.MahasiswaColumns.NIM;
import static comw.example.rplrus26.midsemester12rpl.database.DatabaseContract.MahasiswaColumns.TANGGAL;
import static comw.example.rplrus26.midsemester12rpl.database.DatabaseContract.MahasiswaColumns.URL;
import static comw.example.rplrus26.midsemester12rpl.database.DatabaseContract.TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbmahasiswa";

    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_MAHASISWA = "create table "+TABLE_NAME+
            " ("+_ID+" integer primary key autoincrement, " +
            NAMA+" text not null, " +
            URL+" text not null, " +
            TANGGAL+" text not null, " +
            NIM+" text not null);";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MAHASISWA);
    }

    /*
    Method onUpgrade akan di panggil ketika terjadi perbedaan versi
    Gunakan method onUpgrade untuk melakukan proses migrasi data
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        Drop table tidak dianjurkan ketika proses migrasi terjadi dikarenakan data user akan hilang,
         */
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void clearDatabase() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String clearDBQuery = "DELETE FROM "+TABLE_NAME;
        sqLiteDatabase.execSQL(clearDBQuery);
    }

    public void deleteEntry(long id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, _ID + "=" + id, null);
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + _ID + " = " + id + ";");
    }

}
