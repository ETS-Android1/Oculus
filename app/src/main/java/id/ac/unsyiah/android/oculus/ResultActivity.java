package id.ac.unsyiah.android.oculus;

import android.Manifest;
import android.content.Intent;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ResultActivity extends AppCompatActivity{
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    EditText nama;
    TextView result;
    String hasil ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);
        result = findViewById(R.id.resultTv);
        nama = findViewById(R.id.nama);
        verifyStoragePermissions(this);
        hasil = getIntent().getStringExtra(TestActivity.HASIL);
        result.setText(hasil);

    }

    public Image getWatermarkedImage(PdfContentByte cb, Image img, String watermark, String hasil)
            throws DocumentException, java.io.IOException {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        System.out.println(formatter.format(date));

        BaseFont bebas_neue = BaseFont.createFont("assets/BebasNeue-Regular.ttf", "UTF-8",BaseFont.EMBEDDED);
        BaseFont lato = BaseFont.createFont("assets/Lato-Semibold.ttf", "UTF-8",BaseFont.EMBEDDED);
        Font f = new Font(bebas_neue, 28.0f, Font.NORMAL, new BaseColor(154, 68, 107));
        Font f2 = new Font(lato, 14.0f, Font.NORMAL, new BaseColor(154, 68, 107));
        Font f3 = new Font(lato, 17.0f, Font.NORMAL, new BaseColor(154, 68, 107));
        float width = img.getScaledWidth();
        float height = img.getScaledHeight();

        PdfTemplate template = cb.createTemplate(width, height);
        String [] dateParts = formatter.format(date).split("-");
        String day = dateParts[0];
        String month = new SimpleDateFormat("MMMM").format(date);
        String year = dateParts[2];

        ColumnText.showTextAligned(template, Element.ALIGN_CENTER,
                new Phrase(watermark, f ), width / 2 - 20 , height / 2, 0);
        ColumnText.showTextAligned(template, Element.ALIGN_CENTER,
                new Phrase(day + " " +month + " " +year, f2 ), width / 2 - 20, height / 6 - 36, 0);
        ColumnText.showTextAligned(template, Element.ALIGN_CENTER,
                new Phrase(hasil, f3 ), width / 2 - 20, height / 2 - 100, 0);
        return Image.getInstance(template);
    }
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    private void createPdf(String sometext){
        // Create output PDF
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/oculus.pdf";
        Document document = new Document(PageSize.A4.rotate());
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.oculus);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(directory_path));
            document.open();
            PdfContentByte cb = writer.getDirectContentUnder();
            Image img = Image.getInstance(byteArray);
            img.setAbsolutePosition(0f, 0f);
            img.scaleToFit(PageSize.A4.rotate().getWidth(), PageSize.A4.rotate().getHeight());
            document.add(img);
            document.add(getWatermarkedImage(cb, img, sometext, hasil));
            document.close();

        } catch (DocumentException | IOException e) {
            Log.e("main", "error "+e.toString());
            Toast.makeText(this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
        }


    }

    public void down(View view) {
        String name = nama.getText().toString();
        createPdf(name);
        Toast.makeText(this, "Sertifikat anda tersimpan di memori internal dengan nama oculus.pdf",  Toast.LENGTH_LONG).show();
    }

    public void selesai(View view) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.putExtra("Hasil", "tes");
        startActivityForResult(mainIntent, 1001);
    }
}
