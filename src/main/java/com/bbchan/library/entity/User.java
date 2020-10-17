package com.bbchan.library.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class User {
    @Id
    private String username;
    private String password;
    private Integer identity;
    private String email;
    private Integer borrow_num;
    private Integer reserve_num;

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    public void setBorrow_num(Integer borrow_num) {
        this.borrow_num = borrow_num;
    }

    public Integer getReserve_num() {
        return reserve_num;
    }

    public void setReserve_num(Integer reserve_num) {
        this.reserve_num = reserve_num;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", identity=" + identity +
                ", email='" + email + '\'' +
                ", borrow_num=" + borrow_num +
                ", reserve_num=" + reserve_num +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIdentity() {
        return identity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getBorrow_num() {
        return borrow_num;
    }

    public void setBorrow_num(int borrow_num) {
        this.borrow_num = borrow_num;
    }
}
