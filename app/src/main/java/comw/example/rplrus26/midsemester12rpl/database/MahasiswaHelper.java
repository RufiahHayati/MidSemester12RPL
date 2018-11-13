package comw.example.rplrus26.midsemester12rpl.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static comw.example.rplrus26.midsemester12rpl.database.DatabaseContract.MahasiswaColumns.NAMA;
import static comw.example.rplrus26.midsemester12rpl.database.DatabaseContract.MahasiswaColumns.NIM;
import static comw.example.rplrus26.midsemester12rpl.database.DatabaseContract.MahasiswaColumns.TANGGAL;
import static comw.example.rplrus26.midsemester12rpl.database.DatabaseContract.MahasiswaColumns.URL;
import static comw.example.rplrus26.midsemester12rpl.database.DatabaseContract.TABLE_NAME;


public class MahasiswaHelper {

    private Context context;
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public MahasiswaHelper(Context context) {
        this.context = context;
    }

    public MahasiswaHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    /**
     * Gunakan method ini untuk cari NIM berdasarkan nama mahasiswa
     *
     * @param nama nama yang dicari
     * @return NIM dari mahasiswa
     */
    public ArrayList<MahasiswaModel> getDataByName(String nama) {
        String result = "";
        Cursor cursor = database.query(TABLE_NAME, null, NAMA + " LIKE ?", new String[]{nama + "%"}, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<MahasiswaModel> arrayList = new ArrayList<>();
        MahasiswaModel mahasiswaModel;
        if (cursor.getCount() > 0) {
            do {
                mahasiswaModel = new MahasiswaModel();
                mahasiswaModel.setId(cursor.getString(cursor.getColumnIndexOrThrow(_ID)));
                mahasiswaModel.setName(cursor.getString(cursor.getColumnIndexOrThrow(NAMA)));
                mahasiswaModel.setNim(cursor.getString(cursor.getColumnIndexOrThrow(NIM)));
                mahasiswaModel.setUrl(cursor.getString(cursor.getColumnIndexOrThrow(URL)));
                mahasiswaModel.settanggal(cursor.getString(cursor.getColumnIndexOrThrow(TANGGAL)));

                arrayList.add(mahasiswaModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }


    /**
     * Gunakan method ini untuk mendapatkan semua data mahasiswa
     *
     * @return hasil query mahasiswa model di dalam arraylist
     */
    public ArrayList<MahasiswaModel> getAllData() {
        //Cursor cursor = database.query(TABLE_NAME,null,null,null,null,null,_ID+ " ASC",null);

        database.beginTransaction();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        cursor.moveToFirst();
        ArrayList<MahasiswaModel> arrayList = new ArrayList<>();
        MahasiswaModel mahasiswaModel;
        if (cursor.getCount() > 0) {
            do {
                mahasiswaModel = new MahasiswaModel();
                mahasiswaModel.setId(cursor.getString(cursor.getColumnIndexOrThrow(_ID)));
                mahasiswaModel.setName(cursor.getString(cursor.getColumnIndexOrThrow(NAMA)));
                mahasiswaModel.setNim(cursor.getString(cursor.getColumnIndexOrThrow(NIM)));
                mahasiswaModel.setUrl(cursor.getString(cursor.getColumnIndexOrThrow(URL)));
                mahasiswaModel.settanggal(cursor.getString(cursor.getColumnIndexOrThrow(TANGGAL)));
                arrayList.add(mahasiswaModel);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());
        }
        cursor.close();
        database.endTransaction();
        return arrayList;
    }

    /**
     * Gunakan method ini untuk memulai sesi query transaction
     */
    public void beginTransaction() {
        database.beginTransaction();
    }

    /**
     * Gunakan method ini jika query transaction berhasil, jika error jangan panggil method ini
     */
    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    /**
     * Gunakan method ini untuk mengakhiri sesi query transaction
     */
    public void endTransaction() {
        database.endTransaction();
    }

    /**
     * Gunakan method ini untuk query insert di dalam transaction
     *
     * @param mahasiswaModel inputan model mahasiswa
     */
    public void insertTransaction(MahasiswaModel mahasiswaModel) {
        String sql = "INSERT INTO " + TABLE_NAME + " (" + NAMA + ", " + NIM + ", " + URL + ", " + TANGGAL + ") VALUES (?, ? , ? , ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, mahasiswaModel.getName());
        stmt.bindString(2, mahasiswaModel.getNim());
        stmt.bindString(3, mahasiswaModel.getUrl());
        stmt.bindString(4, mahasiswaModel.gettanggal());
        stmt.execute();
        stmt.clearBindings();
        Log.d("sukses", "insertTransaction: ");
    }

    public int delete(String uname) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        String[] whereArgs = {uname};

        int count = db.delete(TABLE_NAME, _ID + " = ?", whereArgs);
        return count;
    }
/*
    static class database_sql2 extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "my_movie";    // Database Name
        private static final String TABLE_NAME = "my_table_movie";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        //private static final String UID = "_id";     // Column I (Primary Key)
        private static final String title = "title";    //Column II
        private static final String overview = "overview";    // Column III
        private static final String poster_path = "poster_path";    // Column III
        //        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
//                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + title + "  VARCHAR(255) ," + overview + " VARCHAR(225)," + poster_path + " VARCHAR(225))";
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + title + "  VARCHAR(255) PRIMARY KEY ," + overview + " VARCHAR(225)," + poster_path + " VARCHAR(225))";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private Context context;

        public database_sql2(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context = context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
//                Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (Exception e) {
//                Message.message(context,""+e);
            }
        }
    }
*/
}