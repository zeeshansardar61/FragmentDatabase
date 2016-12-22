package info.androidhive.navigationdrawer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.UserDetail;
import info.androidhive.navigationdrawer.model.User;

/**
 * Created by zeesh on 12/22/2016.
 */

public class UsersAdapter extends ArrayAdapter<User> {

    private Context context;
    private Fragment fragment;

    public UsersAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
        this.context = context;
   }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final User user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_layout, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        tvName.setText(user.name);

        TextView userName = (TextView)convertView.findViewById(R.id.userName);
        //Log.e(getClass().getName(), user.username);
        userName.setText(user.username);

        TextView DOB = (TextView)convertView.findViewById(R.id.dob);
        DOB.setText(user.dob);

        de.hdodenhof.circleimageview.CircleImageView imageUser = (de.hdodenhof.circleimageview.CircleImageView)convertView.findViewById(R.id.imageView3);

        String filePath = user.tv;

        if(filePath == null){
            imageUser.getDrawable();
        }else {
            Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);

            Drawable d = new BitmapDrawable(yourSelectedImage);

            imageUser.setImageDrawable(d);
        }

        final int pos = position;

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, UserDetail.class);
                intent.putExtra("user", user);
                getContext().startActivity(intent);
            }
        });


        return convertView;
    }
}
