package com.group3.license;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import static android.media.CamcorderProfile.get;
import static android.net.Uri.parse;

public class LicenseActivity extends AppCompatActivity {

    private long backPressedTime;
    private  Toast backToast;

    TextView fnameRes,lnameRes,mnameRes,brgyRes,cityRes,munRes,provRes,heightRes,weightRes,sexRes,nationalityRes,bdayRes,expire,idNum;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
//License no. generator
        Random random = new Random();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        StringBuilder sb = new StringBuilder();
        int length = 4;
        for(int i = 0; i < length; i++){
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);

            sb.append(randomChar);
        }
        char[] chars1 ="0123456789".toCharArray();
        Random rand1 = new Random();
        StringBuilder sb1 = new StringBuilder((10 + rand1.nextInt(90))+"-");
        for(int i = 0; i < 5; i++)
        {sb1.append(chars1[rand1.nextInt(chars1.length)]);}

        //get current date
        Calendar calendar = Calendar.getInstance();
        //add 10 years from current date, as expiration
        calendar.add(Calendar.YEAR, 10);
        //Date expireDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
        String expiration = dateFormat.format(calendar.getTime());
        //License no.
        UUID uniqueKey = UUID.randomUUID();

        //Declare image view
        ImageView id = findViewById(R.id.idContainer);



        //get image
       if(getIntent().getExtras() != null){
           imageUri = Uri.parse(getIntent().getStringExtra("uri"));
           id.setImageURI(imageUri);
       }

//Intent to get the data from main activity
        Intent info = getIntent();
//Variable declaration and assigning its value by "keyword" from mainactivity
        String FirstName = info.getStringExtra("FName");
        String LastName = info.getStringExtra("LName");
        String MiddleName = info.getStringExtra("MName");
        String brgyVal = info.getStringExtra("Brgy");
        String Municipality = info.getStringExtra("Mun");
        String City = info.getStringExtra("City");
        String Province = info.getStringExtra("Prov");
        String Height = info.getStringExtra("Height");
        String Weight = info.getStringExtra("Weight");
        String Nationality = info.getStringExtra("Nationality");
        String Sex = info.getStringExtra("Sex");
        String Birthday = info.getStringExtra("Bday");


//assigning
        fnameRes = findViewById(R.id.fnameContainer);
        lnameRes = findViewById(R.id.lnameContainer);
        mnameRes = findViewById(R.id.mnameContainer);
        brgyRes = findViewById(R.id.brgyContainer);
        cityRes = findViewById(R.id.cityContainer);
        munRes = findViewById(R.id.munContainer);
        provRes = findViewById(R.id.provContainer);
        heightRes = findViewById(R.id.heightContainer);
        weightRes = findViewById(R.id.weightContainer);
        sexRes = findViewById(R.id.sexContainer);
        nationalityRes = findViewById(R.id.nationalitytContainer);
        bdayRes = findViewById(R.id.bdayContainer);
        expire = findViewById(R.id.expireContainer);
        idNum = findViewById(R.id.licenseContainer);

//setting text to display for names
        fnameRes.setText(FirstName);
        lnameRes.setText(LastName);
        mnameRes.setText(MiddleName);
//setting text to display for address
        brgyRes.setText(brgyVal);
        munRes.setText(Municipality);
        cityRes.setText(City);
        provRes.setText(Province);
//setting text to display for height and weight
        heightRes.setText(Height);
        weightRes.setText(Weight);


//setting text to display for nationality/sex/bday/expiration of license/License no.
        nationalityRes.setText(Nationality);
        sexRes.setText(Sex);
        bdayRes.setText(Birthday);
        expire.setText(expiration);
        idNum.setText(sb.toString()+"-"+sb1.toString());


    }

    @Override
    public void onBackPressed() {

        if(backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }else{
            backToast = Toast.makeText(getBaseContext(),"Press Back Again to Exit",Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();

    }
}