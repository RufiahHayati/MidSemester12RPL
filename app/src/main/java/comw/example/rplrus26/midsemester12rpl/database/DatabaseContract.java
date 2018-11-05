package comw.example.rplrus26.midsemester12rpl.database;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_NAME = "table_mahasiswa";

    static final class MahasiswaColumns implements BaseColumns {

        // Mahasiswa nama
        static String NAMA = "nama";
        // Mahasiswa nim
        static String NIM = "nim";
        static String URL = "url";
        static String TANGGAL = "tanggal";

    }
}
