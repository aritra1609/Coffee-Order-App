package com.example.android.coffeeorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public  class MainActivity extends AppCompatActivity {
    TextView txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9;
    int variable = 0;
    int c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
                .detectLeakedClosableObjects()
                .build());
        txt1= findViewById(R.id.name_text);
        txt2 =findViewById(R.id.email);
        txt3 =findViewById(R.id.topp);
        txt4 =findViewById(R.id.whipcr);
        txt5 =findViewById(R.id.chocolate1);
        txt6 =findViewById(R.id.cart);
        txt7 =findViewById(R.id.summary);
        txt8 =findViewById(R.id.sendbtn);
        txt9 = findViewById(R.id.change_lang);
        loadLocal();

    }


    public void onclick1(View view){
        findViewById(R.id.sendbtn).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {


                String subject="Your Coffee Order";
                EditText etext1 = findViewById(R.id.name_text);
                String etext2= etext1.getText().toString();

                EditText etext3 = findViewById(R.id.email);
                String etext4= etext3.getText().toString();

                CheckBox checkBoxValue = findViewById(R.id.whipcr);
                boolean check1 = checkBoxValue.isChecked();
                if (checkBoxValue.isChecked()){
                    c= (variable*5)+(variable*3);
                } else {
                    c=variable*5;
                }
                CheckBox checkBoxValue1 = findViewById(R.id.chocolate1);
                boolean check2 = checkBoxValue1.isChecked();
                if (checkBoxValue1.isChecked()){
                    c= c+(variable*5);
                } else {
                    c= c;
                }

                String priceMessage;
                priceMessage = (String) getString(R.string.msgtxt1)+ etext2 + "\r\n" + getString(R.string.msgtxt2) + check1+"\r\n" + getString(R.string.msgtxt3) + check2 + "\r\n" + getString(R.string.msgtxt4) + variable + "\r\n" + getString(R.string.msgtxt5) + c + "\r\n" + getString(R.string.msgtxt6);
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                String[] addresses = etext4.split(",");
                intent.putExtra(Intent.EXTRA_EMAIL,addresses);
                intent.putExtra(Intent.EXTRA_SUBJECT,subject);
                intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
                intent.setPackage("com.google.android.gm");
                try {
                    startActivity(Intent.createChooser(intent, "Send"));
                }
                catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MainActivity.this, "f", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }



    public void submitOrder1 (View view){

        if(variable>=10) {
            variable = 10;
            Toast.makeText(this, "Maximum Limit Reached", Toast.LENGTH_SHORT).show();
        }
        else variable++;
        display(variable);


    }
    public void submitOrder2 (View view){
        if (variable<=0) {
            variable=0;
            Toast.makeText(this, "No Items on Cart", Toast.LENGTH_SHORT).show();
        }
        else variable--;
        display(variable);

    }

    public void loadLocal() {
        SharedPreferences preferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language;
        language = preferences.getString("My_lang"," ");
        setLocale(language);
    }
    private void setLocale(String language) {
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = new Locale(language);
        resources.updateConfiguration(configuration,metrics);
        onConfigurationChanged(configuration);

        SharedPreferences.Editor editor;
        editor = getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_lang",language);
        editor.apply();
    }


    public void display ( int number)
    {
        TextView text_view = (TextView) findViewById(R.id.price_a);
        text_view.setText(String.valueOf(+number));

    }
    public void displayPrice ( int coffee){

        TextView priceTextView = (TextView) findViewById(R.id.price_b);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(coffee));
    }
    private void displayMessage (String message , Boolean check1 , boolean check2){
        TextView priceTextView = (TextView) findViewById(R.id.price_b);
        priceTextView.setText(message);
    }
    public void clang(View view){
        AlertDialog.Builder mbuilder= new AlertDialog.Builder(this);
        String[] language = {"हिन्दी","English","اردو","বাংলা"};
        mbuilder.setTitle("Choose Language");
        mbuilder.setSingleChoiceItems(language, -1, new DialogInterface.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    String language = "hi";
                    setLocale("hi");
                } else if (i == 1) {
                    setLocale(" ") ;
                } else if(i==2){
                    setLocale("ur");
                }else if (i==3){
                    setLocale("bn");
                }
            }

        });

        mbuilder.create();
        mbuilder. show();
    }
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        txt1.setHint(R.string.full_name);
        txt2.setHint(R.string.email_id);
        txt3.setText(R.string.toppings);
        txt4.setText(R.string.wipped_cream);
        txt5.setText(R.string.chocolate);
        txt6.setText(R.string.add_to_cart);
        txt7.setText(R.string.order_summary);
        txt8.setText(R.string.order);
        txt9.setText(R.string.lang);

    }
}
