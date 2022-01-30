package id.ac.unsyiah.android.oculus;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import id.ac.unsyiah.android.oculus.data.DataSoal;
import id.ac.unsyiah.android.oculus.data.Soal;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_GALLERY = 999;
    DataSoal dataSoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void tentang(View view) {
        Intent intent = new Intent(MainActivity.this, TentangActivity.class);
        startActivity(intent);
    }

    public void petunjuk(View view) {
        Intent intent = new Intent(MainActivity.this, PetunjukActiviy.class);
        startActivity(intent);
    }

    public void tes(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Mulai tes");
        builder.setMessage("Anda akan memulai tes buta warna, anda tidak dapat mengganti jawaban yang telah disimpan di soal sebelumnya. Mulai tes ?");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("MULAI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startTest();
            }
        });
        builder.show();
    }

    private void startTest(){
        Intent resultIntent = new Intent(this, TestActivity.class);
        resultIntent.putExtra("HASIL", "Anda menderita buta warna total");
        startActivityForResult(resultIntent, 1001);
    }
}
