package info.androidhive.navigationdrawer.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.database.DatabaseHandler;
import info.androidhive.navigationdrawer.model.User;

/**
 * Created by zeesh on 12/22/2016.
 */

public class UserDetail extends AppCompatActivity {

    Context context;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        User user_id = (User)getIntent().getExtras().getSerializable("user");

        context =this;


        DatabaseHandler db = new DatabaseHandler(this);
        User user = db.getUser(user_id);
        Log.e(getClass().getName(), " " + user);
        db.close();


        TextView name = (TextView)findViewById(R.id.name);
        name.setText(user.getName());

        TextView hometown = (TextView)findViewById(R.id.hometown);
        hometown.setText(user.getHometown());

        TextView userName = (TextView)findViewById(R.id.username);
        userName.setText(user.getUsername());


        TextView dob = (TextView)findViewById(R.id.dateofbirth);
        dob.setText(user.getDob());

        TextView age = (TextView) findViewById(R.id.age);
        age.setText(user.getAge());

        TextView phone = (TextView)findViewById(R.id.phone);
        phone.setText(user.getPhone());

        TextView fphone = (TextView)findViewById(R.id.fphone);
        fphone.setText(user.getFphone());

        TextView tv = (TextView)findViewById(R.id.tv);
        tv.setText(user.getTv());

        de.hdodenhof.circleimageview.CircleImageView image = (de.hdodenhof.circleimageview.CircleImageView)findViewById(R.id.imageView);


        String filePath = user.getImageUri();

        if(filePath == null){
            image.getDrawable();
        }else {
            Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);

            Drawable d = new BitmapDrawable(yourSelectedImage);

            image.setImageDrawable(d);
            context = this;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit) {

            //Context context;
            User user_id = (User)getIntent().getExtras().getSerializable("user");


            DatabaseHandler db = new DatabaseHandler(this);
            User user = db.getUser(user_id);

            db.close();
            // context = this;
            Intent intent = new Intent(context, UserUpdate.class);
            intent.putExtra("user", user);

            context.startActivity(intent);



            return true;
        }
        if (id == R.id.delete) {

            User user_id = (User)getIntent().getExtras().getSerializable("user");


            DatabaseHandler db = new DatabaseHandler(this);
            User user = db.getUser(user_id);
            db.delete(user);

            db.close();

            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
            Toast.makeText(getApplication(), "Deleted Successfully" ,Toast.LENGTH_SHORT).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

