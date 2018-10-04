package com.ehp.clase1.Model;

public class Usuario {
    String id;
    String email;
    String name;
    String username;
    String pass;

    public Usuario(String id, String email, String name, String username, String pass) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.username = username;
        this.pass = pass;
    }

    public Usuario() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
