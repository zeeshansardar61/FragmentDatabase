package info.androidhive.navigationdrawer.activity;

import android.app.Activity;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.database.DatabaseHandler;
import info.androidhive.navigationdrawer.model.User;

/**
 * Created by zeesh on 4/1/2016.
 */
public class UserUpdate extends Activity {

    Context context;
    Uri uri;
    String img;
    ImageView newImage;
    private static final int SELECTED_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        final User user_id = (User)getIntent().getExtras().getSerializable("user");

        DatabaseHandler db = new DatabaseHandler(this);
        final User user = db.getUser(user_id);
        db.close();

        final EditText name = (EditText)findViewById(R.id.name);
        name.setText(user.getName());

        final EditText hometown = (EditText)findViewById(R.id.hometown);
        hometown.setText(user.getHometown());

        final EditText userName = (EditText)findViewById(R.id.username);
        userName.setText(user.getUsername());
        userName.setEnabled(false);

        final EditText dob = (EditText)findViewById(R.id.dateofbirth);
        dob.setText(user.getDob());

        final EditText age = (EditText) findViewById(R.id.age);
        age.setText(user.getAge());

        final EditText phone = (EditText)findViewById(R.id.phone);
        phone.setText(user.getPhone());

        final EditText fphone = (EditText)findViewById(R.id.fphone);
        fphone.setText(user.getFphone());

        final EditText tv = (EditText)findViewById(R.id.tv);
        tv.setText(user.getTv());

        newImage = (ImageView)findViewById(R.id.imageView2);
        String filePath = user.getImageUri();

        if(filePath == null){
            newImage.getDrawable();
        }else{
        Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);

        Drawable d = new BitmapDrawable(yourSelectedImage);

        newImage.setImageDrawable(d);
        }


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
                EditText dob = (EditText) findViewById(R.id.dateofbirth);

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
                new DatePickerDialog(UserUpdate.this, date, cal
                        .get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
                        .get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        newImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, SELECTED_PICTURE);
            }
        });

        Button save = (Button) findViewById(R.id.button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().length() != 0 && hometown.getText().toString().length() != 0 && userName.getText().toString().length() != 0 && dob.getText().toString().length() != 0 && age.getText().toString().length() != 0 && phone.getText().toString().length() != 0 && fphone.getText().toString().length() != 0 ) {

                    DatabaseHandler db = new DatabaseHandler(context);
                   // db.createUser(new User(name.getText().toString(), hometown.getText().toString(), userName.getText().toString(), dob.getText().toString(), age.getText().toString(), phone.getText().toString()));

                    db.update(new User(user.getId() ,name.getText().toString(), hometown.getText().toString(), userName.getText().toString(), dob.getText().toString(), age.getText().toString(), phone.getText().toString(), img, fphone.getText().toString(), tv.getText().toString() ));
                    //Log.d(getClass().getName(), ""+value);

                    db.close();

                    Toast.makeText(getApplication(), "Updated Successfully" , Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);

                }


                else
                    Toast.makeText(context, "Please fill all fields or username is already exist", Toast.LENGTH_SHORT).show();
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECTED_PICTURE:
                if (resultCode == RESULT_OK) {
                     uri = data.getData();


                    Log.e("Image path is ", "" + uri);
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

                    newImage.setImageDrawable(d);
                    Toast.makeText(getApplicationContext(), filePath,
                            Toast.LENGTH_SHORT).show();



                }
                break;

            default:
                break;
        }
    }
}
