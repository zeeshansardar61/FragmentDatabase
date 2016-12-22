package info.androidhive.navigationdrawer.model;

import java.io.Serializable;

/**
 * Created by zeesh on 12/22/2016.
 */

public class User implements Serializable {

    int id;
    public static String name;
    public static String hometown;
    public static String username;
    public static String dob;
    public static String age;
    public static String phone;
    public static String imageUri;
    public static String fphone;
    public static String tv;

    public User(String name, String hometown, String username, String dob, String age, String phone, String imageUri, String fphone, String tv) {

        this.name = name;
        this.hometown = hometown;
        this.username = username;
        this.dob = dob;
        this.age = age;
        this.phone = phone;
        this.imageUri = imageUri;
        this.fphone = fphone;
        this.tv = tv;


    }

    public User(int id, String name, String hometown, String username, String dob, String age, String phone, String imageUri, String fphone, String tv) {
        this.id = id;
        this.name = name;
        this.hometown = hometown;
        this.username = username;
        this.dob = dob;
        this.age = age;
        this.phone = phone;
        this.imageUri = imageUri;
        this.fphone = fphone;
        this.tv = tv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getFphone() {
        return fphone;
    }

    public String getTv() {
        return tv;
    }
}
