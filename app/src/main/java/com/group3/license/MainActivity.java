package com.group3.license;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;



/*
John Kenneth Mina
John Vincent Micu
Rob Yves  Lacaste

Sherwin Comesario
 */

public class MainActivity extends AppCompatActivity {

    //declaration prerequisite to image selector
    int SELECT_PHOTO = 1;
    ImageView imageView;
    Uri uri;


    //declaration prerequisite to registration form
    Button register;
    private Button next;
    private EditText firstname,lastname,midname,barangay,city,municipality,province,height,weight,nationality,sex;
    TextView bdayText;
    ImageView etBday;
    private int mDate,mMonth,mYear;
    static  String Birthdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button choose = findViewById(R.id.picSelect);
        imageView = findViewById(R.id.idpic);

        // image selector when"select picture" button is clicked
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,SELECT_PHOTO);

            }
        });


        //Button
        register = findViewById(R.id.regBtn);
        //edit text
        firstname = findViewById(R.id.fname);
        lastname = findViewById(R.id.lname);
        midname = findViewById(R.id.mname);
        etBday = findViewById(R.id.bday);
        barangay = findViewById(R.id.brgy);
        municipality = findViewById(R.id.mun);
        city = findViewById(R.id.city);
        province = findViewById(R.id.province);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        nationality = findViewById(R.id.nationality);
        sex = findViewById(R.id.sex);
        bdayText = findViewById(R.id.bdayPicked);




        etBday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDate = calendar.get(Calendar.DATE);


                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this, android.R.style.Theme_DeviceDefault_Dialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int date) {
                                String bdate = year+"/"+month+"/"+date;
                                  bdayText .setText(bdate);
                                Birthdate = bdate;


                            }
                        }, mYear,mMonth,mDate);
                datePickerDialog.show();

            }
        });




        //onClick Listener for register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Variable declaration and assigning its value from input of the user
                String lname = lastname.getText().toString();
                String fname = firstname.getText().toString();
                String mname = midname.getText().toString();
                String birthday = Birthdate;
                String brgyVal = barangay.getText().toString();
                String munVal = municipality.getText().toString();
                String cityVal = city.getText().toString();
                String provVal = province.getText().toString();
                String heightVal = height.getText().toString();
                String weightVal = weight.getText().toString();
                String sexVal = sex.getText().toString();
                String nationalityVal = nationality.getText().toString();

//Condition to check if there is a null value in registration form
                if (imageView.getDrawable() == null){
                    Snackbar snackbar = Snackbar.make(v, "Please Select Image", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else if(lname.isEmpty() || fname.isEmpty()  || mname.isEmpty()  || brgyVal.isEmpty()
                        || nationalityVal.isEmpty() || munVal.isEmpty() || cityVal.isEmpty() || provVal.isEmpty() || heightVal.isEmpty() || weightVal.isEmpty() || sexVal.isEmpty()){
                    Snackbar snackbar = Snackbar.make(v, "Please Fill up required form", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else if(birthday == null){
                    Snackbar snackbar = Snackbar.make(v, "Please Pick your Birthday, just click the calendar icon", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else{

//If registration is completely filled up, we pass the data and used  "keyword" to access or get it later in License Activity
                    Intent intent = new Intent(MainActivity.this,LicenseActivity.class);
                    intent.putExtra("FName", fname);
                    intent.putExtra("LName", lname);
                    intent.putExtra("MName", mname);
                    intent.putExtra("Brgy", brgyVal);
                    intent.putExtra("Mun", munVal);
                    intent.putExtra("City", cityVal);
                    intent.putExtra("Prov", provVal);
                    intent.putExtra("Height", heightVal);
                    intent.putExtra("Weight", weightVal);
                    intent.putExtra("Sex", sexVal);
                     intent.putExtra("Bday", birthday);
                    intent.putExtra("Nationality", nationalityVal);
                    Intent passImg = new Intent(MainActivity.this, LicenseActivity.class);
                    intent.putExtra("uri", uri.toString());
                    startActivity(intent);
                    finish();

                }

            }
        });
    }

    //assigning the image to the "imageView" variable
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null ){
            uri = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}