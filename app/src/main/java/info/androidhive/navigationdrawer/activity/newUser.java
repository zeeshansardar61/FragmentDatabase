package info.androidhive.navigationdrawer.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.database.DatabaseHandler;
import info.androidhive.navigationdrawer.model.User;

/**
 * Created by zeesh on 12/22/2016.
 */

public class newUser extends AppCompatActivity {

    private static final int SELECTED_PICTURE = 1;
    Context context;
    EditText name;
    EditText hometown, username, dob, age, phone, fphone, tv;
    Button save;
    DatabaseHandler dbHandler;
    String img;
    de.hdodenhof.circleimageview.CircleImageView imageUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        name = (EditText) findViewById(R.id.name);
        hometown = (EditText) findViewById(R.id.hometown);
        username = (EditText) findViewById(R.id.username);
        dob = (EditText) findViewById(R.id.dateofbirth);
        age = (EditText) findViewById(R.id.age);
        phone = (EditText) findViewById(R.id.phone);
        fphone = (EditText) findViewById(R.id.fphone);
        tv = (EditText)findViewById(R.id.tv);
        imageUser = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.imageView2);


        context = this;

        final Calendar cal = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                // TODO Auto-generated method stub
                String dateFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat,
                        Locale.US);
                dob.setText(sdf.format(cal.getTime()));
            }
        };

        dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(newUser.this, date, cal
                        .get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
                        .get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        imageUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, SELECTED_PICTURE);
            }
        });

        save = (Button) findViewById(R.id.button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db = new DatabaseHandler(context);


                if(db.CheckIsDataAlreadyInDBorNot(username.getText().toString())==true){
                    //Toast.makeText(getApplicationContext(), "Username already exist", Toast.LENGTH_SHORT).show();
                    username.setError("Username already Exitst");

                }
                if (db.CheckIsDataAlreadyInDBorNot(username.getText().toString())== false && name.getText().toString().length() != 0 && hometown.getText().toString().length() != 0 && username.getText().toString().length() != 0 && dob.getText().toString().length() != 0 && age.getText().toString().length() != 0 && phone.getText().toString().length() != 0 && fphone.getText().toString().length() != 0 ) {


                    db.createUser(new User(name.getText().toString(), hometown.getText().toString(), username.getText().toString(), dob.getText().toString(), age.getText().toString(), phone.getText().toString(), img ,fphone.getText().toString() , tv.getText().toString() ));

                    db.close();

                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);

                }

                else
                    Toast.makeText(context, "Please fill all fields or Username already Exist", Toast.LENGTH_SHORT).show();
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECTED_PICTURE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();

                    Log.e("Image path is ", "" + uri.toString() + " \n ");
                    String[] projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri, projection,
                            null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filePath = cursor.getString(columnIndex);
                    String path = filePath;
                    img = path;
                    cursor.close();

                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);

                    Drawable d = new BitmapDrawable(yourSelectedImage);

                    imageUser.setImageDrawable(d);

                    Toast.makeText(getApplicationContext(), filePath,
                            Toast.LENGTH_SHORT).show();



                }
                break;

            default:
                break;
        }
    }
}
