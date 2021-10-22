package com.example.resto.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    private int id, age;
    private String login, password, email, name, firstname;

//    //region serialized
//    @SerializedName("id")
//    @Expose
//    private int id;
//
//    @SerializedName("login")
//    @Expose
//    private String login;
//
//    @SerializedName("password")
//    @Expose
//    private String password;
//
//    @SerializedName("email")
//    @Expose
//    private String email;
//
//    @SerializedName("name")
//    @Expose
//    private String name;
//
//    @SerializedName("firstname")
//    @Expose
//    private String firstname;
//
//    @SerializedName("age")
//    @Expose
//    private int age;
//    //endregion

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
}
