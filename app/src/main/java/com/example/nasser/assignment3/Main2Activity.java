package com.example.nasser.assignment3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView txtinfo =(TextView) findViewById(R.id.txtinfo);
        Bundle b = getIntent().getExtras();
        String fname = b.getString("first name");
        String lname = b.getString("last name");
        String number = b.getString("my number");
        String mail = b.getString("mail");
        String Gender = b.getString("Gender");
        String msg = b.getString("msg");

        txtinfo.setText("Hey my name is " +
                fname + " " +
                lname +"\n"+
                "and my phone number is: " +number+ " " +
                "u can talk to me on whatsapp, " +"\n"+
                "Email: " + mail + "\n"+
                "for sure i am " + Gender +"\n" +
                "_____________"+ "\n" +
                "this is my message to you mr zamel : "+"\n"+msg);







    }
}
