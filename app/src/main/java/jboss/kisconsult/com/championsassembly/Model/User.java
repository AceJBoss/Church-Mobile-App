package jboss.kisconsult.com.championsassembly.Model;

import java.io.Serializable;

/**
 * Created by JBoss on 7/20/2016.
 */
public class User implements Serializable {

    private String fullname, username, mobile, image, level, state, gender, category, password, email,age;
   // private double height;
   // private int age;


    public User() {

    }

    public void setState(String bg) {
        state = bg;
    }

    public String getBgroup() {
        return state;
    }

    public void setGender(String sex) {
        gender = sex;
    }

    public String getGender() {
        return gender;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setAge(String height) {
        this.age = height;
    }

    public String getAge() {
        return age;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setLevel(String mobile) {
        this.level= mobile;
    }

    public String getLevel() {
        return this.level;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return this.image;
    }
}
