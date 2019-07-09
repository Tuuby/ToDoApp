package com.example.netlab.todotest;

import java.io.Serializable;

public class LoginItem  implements Serializable {
    private static final long serialVersionUID = 15435325348607253L;

    private String username;
    private String password;

    public LoginItem(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginItem() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
