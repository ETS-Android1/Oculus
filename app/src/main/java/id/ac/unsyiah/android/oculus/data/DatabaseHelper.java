package id.ac.unsyiah.android.oculus.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "oculusss.db";

    public static final String TABLE = "tb_soal";

    public static final String KEY_ID = "id";
    public static final String KEY_GAMBAR = "gambar";
    public static final String KEY_SOAL = "soal";
    public static final String KEY_A = "pilihanA";
    public static final String KEY_B = "pilihanB";
    public static final String KEY_C = "pilihanC";
    public static final String KEY_D = "pilihanD";
    public static final String KEY_BENAR = "pilihanBenar";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_MAHASISWA = "CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_GAMBAR + " VARCHAR,"
                + KEY_SOAL + " VARCHAR," +KEY_A + " VARCHAR," +KEY_B + " VARCHAR,"  +KEY_C + " VARCHAR,"
                +KEY_D + " VARCHAR," +KEY_BENAR + " VARCHAR)";
        db.execSQL(CREATE_TABLE_MAHASISWA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop tabel jika sudah pernah ada
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);

        //create table again
        onCreate(db);
    }

}
