package com.example.nasser.assignment3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView imageView1 ,imageView2;
    static final int PICK_IMAGE = 100;
    static final int PICK_CONTACT=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void btnCameraClick(View v )
    {
        imageView1 = (ImageView) findViewById(R.id.imageView);
        Intent myintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(myintent,0);
    }
    public void openGallery(View v)
    {
        imageView2 = (ImageView) findViewById(R.id.imageView);
        Intent gallery = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 0 :
                Bitmap bitmap1 =(Bitmap) data.getExtras().get("data");
                imageView1.setImageBitmap(bitmap1);
                break;
            case PICK_IMAGE :
                Bitmap bitmap2 =(Bitmap) data.getExtras().get("data");
                imageView2.setImageBitmap(bitmap2);
                break;
            case PICK_CONTACT :
                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c =  managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {


                        String id =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
                                    null, null);
                            phones.moveToFirst();
                          String  cNumber = phones.getString(phones.getColumnIndex("data"));
                            System.out.println("number is:"+cNumber);
                        }
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));


                    }
                }
                break;
        }
    }


    protected void onActivityResult1(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri imageuri = data.getData();
        imageView1.setImageURI(imageuri);
    }

    public void btnWhatsappClick (View v)
    {
        EditText txtFname = (EditText) findViewById(R.id.txtfirstname);
        EditText txtLname = (EditText) findViewById(R.id.txtlastname);
        EditText txt = (EditText) findViewById(R.id.txtphonenumber);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "hey i am "+ txtFname.getText().toString()
                    +  " "     + txtLname.getText().toString()
                    + ", i am using WhatsApp, you can text me on it" );

        sendIntent.setType("text/plain");
       // sendIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.com.WhatsApp"));
        //startActivity(Intent.createChooser(sendIntent, ""));
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);

    }

    public void btnSignupclick (View v )
        {
        String Gender;
        EditText txtFname = (EditText) findViewById(R.id.txtfirstname);
        EditText txtLname = (EditText) findViewById(R.id.txtlastname);
       // RadioButton chmale = (RadioButton) findViewById(R.id.chmale);
    //    RadioButton chfemale = (RadioButton) findViewById(R.id.chfemale);
        EditText txtnumber = (EditText) findViewById(R.id.txtphonenumber);
        EditText txtmail = (EditText) findViewById(R.id.txtEmail);
        EditText txtmsg = (EditText) findViewById(R.id.txtmsg);

    //    if (chmale.isChecked())
    //    {Gender = "male";
       // } else{Gender="female";}

        Intent myintent = new Intent(this,Main2Activity.class);
        myintent.putExtra("first name",txtFname.getText().toString());
        myintent.putExtra("last name",txtLname.getText().toString());
        myintent.putExtra("my number",txtnumber.getText().toString());
        //myintent.putExtra("male",txtFname.getText().toString());
       // myintent.putExtra("female",txtFname.getText().toString());
        myintent.putExtra("mail",txtmail.getText().toString());
        myintent.putExtra("msg",txtmsg.getText().toString());
     //   myintent.putExtra("Gender",txt.getText().toString());

        startActivity(myintent);

    }

    public void btnNumberClick (View v )
    {
        EditText txt = (EditText) findViewById(R.id.txtphonenumber);


        if (! txt.getText().toString().startsWith("01") )
        {
            txt.setText("invalid");
        }

        if(txt.getText().length() != 11)
        {
            txt.setHint("invalid");
        }

        else
        {
            startActivity(new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+txt.getText())));
        }

    }

    public void btnSendClick (View v )
    {
        EditText txt = (EditText) findViewById(R.id.txtEmail);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // The intent does not have a URI, so declare the "text/plain" MIME type
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"jon@example.com"}); // recipients
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text");
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
        emailIntent.setPackage("com.google.android.gm");


        if(txt.getText().toString().endsWith("@gmail.com"))
        {
            startActivity(emailIntent);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"invalid email",Toast.LENGTH_LONG).show();
        }
    }

    public void btnClickContacts(View v)
    {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }




}
