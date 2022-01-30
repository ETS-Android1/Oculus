package id.ac.unsyiah.android.oculus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TentangActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);
    }

    public void gejala1(View view) {
        Intent intent = new Intent(TentangActivity.this, GejalaMerahActivity.class);
        startActivity(intent);
    }

    public void gejala2(View view) {
        Intent intent = new Intent(TentangActivity.this, GejalaBiruActivity.class);
        startActivity(intent);
    }

    public void gejala3(View view) {
        Intent intent = new Intent(TentangActivity.this, GejalaTotalActivity.class);
        startActivity(intent);
    }
}
