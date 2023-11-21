package com.st.app.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.sql.Date;

@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private Boolean active;
    @Column
    private Date expiry_date;
    @Column
    private String reset_code;
    @Column
    private Timestamp reset_expiry;
    @Column
    private int role_id;
    @Column
    private int wrongPasswordCounter;
    @Column
    private boolean hiddenUser;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(Date expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getReset_code() {
        return reset_code;
    }

    public void setReset_code(String reset_code) {
        this.reset_code = reset_code;
    }

    public Timestamp getReset_expiry() {
        return reset_expiry;
    }

    public void setReset_expiry(Timestamp reset_expiry) {
        this.reset_expiry = reset_expiry;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getWrongPasswordCounter() {
        return wrongPasswordCounter;
    }
    public void setWrongPasswordCounter(int wrongPasswordCounter) {
        this.wrongPasswordCounter = wrongPasswordCounter;
    }

    public boolean isHiddenUser() {
        return hiddenUser;
    }
    public void setHiddenUser(boolean hiddenUser) {
        this.hiddenUser = hiddenUser;
    }
}
