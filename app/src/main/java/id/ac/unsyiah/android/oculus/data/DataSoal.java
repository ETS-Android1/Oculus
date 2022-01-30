package id.ac.unsyiah.android.oculus.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataSoal {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    Context context;

    public DataSoal(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void buka() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void tutup() {
        dbHelper.close();
    }

    public void addSoal(Soal soal){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.KEY_SOAL, soal.getSoal());
        cv.put(DatabaseHelper.KEY_GAMBAR, soal.getGambar());
        cv.put(DatabaseHelper.KEY_A, soal.getJawabanA());
        cv.put(DatabaseHelper.KEY_B, soal.getJawabanB());
        cv.put(DatabaseHelper.KEY_C, soal.getJawabanC());
        cv.put(DatabaseHelper.KEY_D, soal.getJawabanD());
        cv.put(DatabaseHelper.KEY_BENAR, soal.getJawabanBenar());
        database.insert(dbHelper.TABLE, null, cv);
    }
    public Soal getSoal(int kode){
        String query = "SELECT * FROM "+DatabaseHelper.TABLE+
                " WHERE "+DatabaseHelper.KEY_ID+" = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(kode)});
        Soal soal = new Soal();
        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            soal.setSoal(cursor.getString(2));
            soal.setGambar(cursor.getBlob(1));
            soal.setJawabanA(cursor.getString(3));
            soal.setJawabanB(cursor.getString(4));
            soal.setJawabanC(cursor.getString(5));
            soal.setJawabanD(cursor.getString(6));
            soal.setJawabanBenar(cursor.getString(7));
        }else{
            soal = null;
        }
        cursor.close();
        return soal;
    }

}
