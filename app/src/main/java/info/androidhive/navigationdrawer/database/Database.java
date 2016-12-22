package info.androidhive.navigationdrawer.database;

import java.util.ArrayList;

import info.androidhive.navigationdrawer.model.User;

/**
 * Created by zeesh on 12/22/2016.
 */

public class Database {

    private static ArrayList<User> arrayOfUsers = new ArrayList<User>();

    public static void initUsers() {
        // arrayOfUsers.add(new User("Ali", "Karachi", "", "", "","", ""));
        //arrayOfUsers.add(new User("Khalid", "Pindi", "", "", "","", ""));
        //arrayOfUsers.add(new User("Rashid", "RawalPindi", "", "", "","", ""));
    }

    public static void addUsers(User user) {
        arrayOfUsers.add(user);
    }
    public static ArrayList<User> getAllUsers() {
        return arrayOfUsers;
    }

}
