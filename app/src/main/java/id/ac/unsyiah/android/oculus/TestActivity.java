package id.ac.unsyiah.android.oculus;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import id.ac.unsyiah.android.oculus.data.DataSoal;
import id.ac.unsyiah.android.oculus.data.Soal;

public class TestActivity extends AppCompatActivity {
    DataSoal dataSoal;
    ImageView imageView;
    Button btnA, btnB, btnC, btnD, btnNext;
    ArrayList<Integer> soal = new ArrayList<Integer>();
    int randomNumber;
    String result;
    HashMap<Integer, String> jawabanBenar = new HashMap<>();
    HashMap<Integer, String> jawabanPengguna = new HashMap<>();
    public static final String HASIL = "hasil";
    public static final int DETAIL_REQUEST_CODE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tes);
        imageView = (ImageView) findViewById(R.id.gambarIv);
        btnA = findViewById(R.id.buttonA);
        btnB = findViewById(R.id.buttonB);
        btnC = findViewById(R.id.buttonC);
        btnD = findViewById(R.id.buttonD);
        btnNext = findViewById(R.id.nextBtn);
        dataSoal = new DataSoal(this);
        //insertData();
        setSoal();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(jawabanPengguna.size() == 22){
                    btnNext.setBackground( getResources().getDrawable(R.drawable.done));
                    setSoal();
                }else if(jawabanPengguna.size() == 23){
                    countResult();
                }else{
                    setSoal();
                }
            }
        });


    }
    private void countResult() {
        Toast.makeText(TestActivity.this,"Data "+jawabanPengguna.get(23) +" " + jawabanBenar.get(23), Toast.LENGTH_LONG).show();
        int hijau=0;
        int kuning=0;
        List<Integer> merah = Arrays.asList( 2, 3, 4, 5, 6, 7, 14, 15, 16, 17, 18, 19, 20, 22, 23);
        List<Integer> biru = Arrays.asList(16, 17, 18, 21);

        ArrayList<Integer> merahHijau = new ArrayList<>();
        ArrayList<Integer> biruKuning = new ArrayList<>();

        //Copy all items from list 1 to list 2
        merahHijau.addAll(merah);
        biruKuning.addAll(biru);

        int benar = 0;
        if (!jawabanBenar.get(1).equals(jawabanPengguna.get(1)) || !jawabanBenar.get(24).equals(jawabanPengguna.get(24))) {
            result = "BUTA MENDERITA BUTA WARNA TOTAL";
        }else {
            for (int i = 1; i <= 17; i++) {
                if (jawabanBenar.get(i).equals(jawabanPengguna.get(i))) {
                    benar++;
                }
            }
            if (benar == 17){
                result = "ANDA MEMILIKI PENGLIHATAN NORMAL";
            }else{
                benar =0;
                for (int i = 1; i <= 23; i++) {
                    if (jawabanBenar.get(i).equals(jawabanPengguna.get(i))) {
                        benar++;
                    }
                }
                if (benar == 13){
                    result = "ANDA NORMAL !!!!";
                }else {
                    for(int i=1; i<=23; i++){
                        if (!jawabanBenar.get(i).equals(jawabanPengguna.get(i))) {
                            if(i == 16){
                                if(jawabanPengguna.get(i).equals("6")){
                                    hijau++;
                                } else if(jawabanPengguna.get(i).equals("2")){
                                    kuning++;
                                }
                            }else if(i == 17){
                                if(jawabanPengguna.get(i).equals("2")){
                                    hijau++;
                                } else if(jawabanPengguna.get(i).equals("4")){
                                    kuning++;
                                }
                            }else if(i == 18){
                                if(jawabanPengguna.get(i).equalsIgnoreCase("Garis merah")){
                                    kuning++;
                                } else if(jawabanPengguna.get(i).equalsIgnoreCase("Garis ungu")){
                                    hijau++;
                                }
                            }else{
                                if(merahHijau.contains(i)){
                                    hijau++;
                                }else if(biruKuning.contains(i)){
                                    kuning++;
                                }
                            }

                        }
                    }
                    if(kuning/4 > hijau/15){
                        result = "Anda menderita buta warna biru kuning";
                    }else{
                        result = "Anda menderita buta warna Merah Hijau";

                    }
                }
            }
        }
    showResult();

    }

    private void setSoal(){
        Random r=new Random();
        boolean loop = false;
        randomNumber=r.nextInt(25);
        do {
            if (!soal.contains(randomNumber) && !(randomNumber == 0)) {
                soal.add(randomNumber);
                Toast.makeText(TestActivity.this,"Data "+randomNumber , Toast.LENGTH_LONG).show();
                getById(randomNumber);
                loop=true;
            } else {
                randomNumber = r.nextInt(25);
            }
        }while(loop == false);
    }

    private void insertData(){
        Soal sp = new Soal();
        sp.setSoal("Apa yang anda lihat ?");
        sp.setJawabanA("42");
        sp.setJawabanB("12");
        sp.setJawabanC("4");
        sp.setJawabanD("2");
        sp.setJawabanBenar("42");
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.gambar17);

        Soal sp1 = new Soal();
        sp1.setSoal("Apa yang anda lihat ?");
        sp1.setJawabanA("Garis ungu");
        sp1.setJawabanB("Garis merah dan ungu");
        sp1.setJawabanC("Garis merah");
        sp1.setJawabanD("Tidak ada apapun");
        sp1.setJawabanBenar("Garis merah dan ungu");
        Bitmap bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.gambar18);

        Soal sp2 = new Soal();
        sp2.setSoal("Apa yang anda lihat ?");
        sp2.setJawabanA("Garis merah muda");
        sp2.setJawabanB("Tidak ada apapun");
        sp2.setJawabanC("Garis merah");
        sp2.setJawabanD("Garis hijau");
        sp2.setJawabanBenar("Tidak ada apapun");
        Bitmap bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.gambar19);

        Soal sp3 = new Soal();
        sp3.setSoal("Apa yang anda lihat ?");
        sp3.setJawabanA("Garis hijau dan kuning");
        sp3.setJawabanB("Tidak ada apapun");
        sp3.setJawabanC("Garis merah dan kuning");
        sp3.setJawabanD("Garis hijau");
        sp3.setJawabanBenar("Garis hijau");
        Bitmap bm3 = BitmapFactory.decodeResource(getResources(), R.drawable.gambar20);

        Soal sp4 = new Soal();
        sp4.setSoal("Apa yang anda lihat ?");
        sp4.setJawabanA("Garis hijau");
        sp4.setJawabanB("Garis orange");
        sp4.setJawabanC("Garis merah");
        sp4.setJawabanD("Garis biru");
        sp4.setJawabanBenar("Garis orange");
        Bitmap bm4 = BitmapFactory.decodeResource(getResources(), R.drawable.gambar21);

        Soal sp5 = new Soal();
        sp5.setSoal("Apa yang anda lihat ?");
        sp5.setJawabanA("Garis hijau dan kuning");
        sp5.setJawabanB("Garis hijau dan merah");
        sp5.setJawabanC("Garis kuning dan ungu");
        sp5.setJawabanD("Garis hijau dan ungu");
        sp5.setJawabanBenar("Garis hijau dan kuning");
        Bitmap bm5 = BitmapFactory.decodeResource(getResources(), R.drawable.gambar22);

        Soal sp6 = new Soal();
        sp6.setSoal("Apa yang anda lihat ?");
        sp6.setJawabanA("Garis ungu dan orange");
        sp6.setJawabanB("Garis merah dan hijau");
        sp6.setJawabanC("Garis ungu dan hijau");
        sp6.setJawabanD("Tidak ada apapun");
        sp6.setJawabanBenar("Garis ungu dan orange");
        Bitmap bm6 = BitmapFactory.decodeResource(getResources(), R.drawable.gambar23);

        Soal sp7 = new Soal();
        sp7.setSoal("Apa yang anda lihat ?");
        sp7.setJawabanA("Garis orange");
        sp7.setJawabanB("Garis hijau");
        sp7.setJawabanC("Garis biru");
        sp7.setJawabanD("Tidak ada apapun");
        sp7.setJawabanBenar("Garis orange");
        Bitmap bm7 = BitmapFactory.decodeResource(getResources(), R.drawable.gambar24);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
        ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
        ByteArrayOutputStream stream4 = new ByteArrayOutputStream();
        ByteArrayOutputStream stream5 = new ByteArrayOutputStream();
        ByteArrayOutputStream stream6 = new ByteArrayOutputStream();
        ByteArrayOutputStream stream7 = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bm1.compress(Bitmap.CompressFormat.PNG, 100, stream1);
        bm2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
        bm3.compress(Bitmap.CompressFormat.PNG, 100, stream3);
        bm4.compress(Bitmap.CompressFormat.PNG, 100, stream4);
        bm5.compress(Bitmap.CompressFormat.PNG, 100, stream5);
        bm6.compress(Bitmap.CompressFormat.PNG, 100, stream6);
        bm7.compress(Bitmap.CompressFormat.PNG, 100, stream7);
        byte[] byteArray = stream.toByteArray();
        byte[] byteArray1 = stream1.toByteArray();
        byte[] byteArray2 = stream2.toByteArray();
        byte[] byteArray3 = stream3.toByteArray();
        byte[] byteArray4 = stream4.toByteArray();
        byte[] byteArray5 = stream5.toByteArray();
        byte[] byteArray6 = stream6.toByteArray();
        byte[] byteArray7 = stream7.toByteArray();
        sp.setGambar(byteArray);
        sp1.setGambar(byteArray1);
        sp2.setGambar(byteArray2);
        sp3.setGambar(byteArray3);
        sp4.setGambar(byteArray4);
        sp5.setGambar(byteArray5);
        sp6.setGambar(byteArray6);
        sp7.setGambar(byteArray7);
        dataSoal.buka();
        dataSoal.addSoal(sp);
        dataSoal.addSoal(sp1);
        dataSoal.addSoal(sp2);
        dataSoal.addSoal(sp3);
        dataSoal.addSoal(sp4);
        dataSoal.addSoal(sp5);
        dataSoal.addSoal(sp6);
        dataSoal.addSoal(sp7);
        dataSoal.tutup();
        Toast.makeText(TestActivity.this,"Data Berhasil Disimpan", Toast.LENGTH_LONG).show();
    }

    public void getById(int id) {
        dataSoal.buka();
        Soal soal = dataSoal.getSoal(id);

        dataSoal.tutup();
        if (soal != null) {
            Bitmap bms = BitmapFactory.decodeByteArray(soal.getGambar(), 0, soal.getGambar().length);
            imageView.setImageBitmap(bms);
            jawabanBenar.put(id, soal.getJawabanBenar());
            btnA.setText(soal.getJawabanA());
            btnB.setText(soal.getJawabanB());
            btnC.setText(soal.getJawabanC());
            btnD.setText(soal.getJawabanD());
            btnNext.setEnabled(false);
            btnA.setBackground(getResources().getDrawable(R.drawable.btnjawab));
            btnB.setBackground(getResources().getDrawable(R.drawable.btnjawab));
            btnC.setBackground(getResources().getDrawable(R.drawable.btnjawab));
            btnD.setBackground(getResources().getDrawable(R.drawable.btnjawab));
        } else {
            Toast.makeText(this, "Data tidak berhasil ditemukan", Toast.LENGTH_LONG).show();
        }

    }

    public void pilihJawaban(View view) {
        switch (view.getId()) {
            case R.id.buttonA:
                jawabanPengguna.remove(randomNumber);
                jawabanPengguna.put(randomNumber, btnA.getText().toString());
                btnA.setBackground(getResources().getDrawable(R.drawable.mulaibtn));
                btnB.setBackground(getResources().getDrawable(R.drawable.btnjawab));
                btnC.setBackground(getResources().getDrawable(R.drawable.btnjawab));
                btnD.setBackground(getResources().getDrawable(R.drawable.btnjawab));
                break;

            case R.id.buttonB:
                jawabanPengguna.remove(randomNumber);
                jawabanPengguna.put(randomNumber, btnB.getText().toString());
                btnB.setBackground(getResources().getDrawable(R.drawable.mulaibtn));
                btnA.setBackground(getResources().getDrawable(R.drawable.btnjawab));
                btnC.setBackground(getResources().getDrawable(R.drawable.btnjawab));
                btnD.setBackground(getResources().getDrawable(R.drawable.btnjawab));
                break;

            case R.id.buttonC:
                jawabanPengguna.remove(randomNumber);
                jawabanPengguna.put(randomNumber, btnC.getText().toString());
                btnC.setBackground(getResources().getDrawable(R.drawable.mulaibtn));
                btnA.setBackground(getResources().getDrawable(R.drawable.btnjawab));
                btnB.setBackground(getResources().getDrawable(R.drawable.btnjawab));
                btnD.setBackground(getResources().getDrawable(R.drawable.btnjawab));
                break;

            case R.id.buttonD:
                jawabanPengguna.remove(randomNumber);
                jawabanPengguna.put(randomNumber, btnD.getText().toString());
                btnD.setBackground(getResources().getDrawable(R.drawable.mulaibtn));
                btnA.setBackground(getResources().getDrawable(R.drawable.btnjawab));
                btnB.setBackground(getResources().getDrawable(R.drawable.btnjawab));
                btnC.setBackground(getResources().getDrawable(R.drawable.btnjawab));
                break;
        }
        Toast.makeText(TestActivity.this,"halooooooooooooooo" +jawabanPengguna.size(), Toast.LENGTH_LONG).show();
        btnNext.setEnabled(true);
    }

    public void showResult(){
        Intent resultIntent = new Intent(this, ResultActivity.class);
        resultIntent.putExtra(HASIL, result);
        startActivityForResult(resultIntent, 1001);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DETAIL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String msg = data.getStringExtra("kirimBalik");
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            }
        }
    }

}
