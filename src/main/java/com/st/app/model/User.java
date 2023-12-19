package com.st.app.model;

import com.st.app.dto.RegisterInfo;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private boolean active;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @Column(name = "reset_code")
    private String resetCode;

    @Column(name = "reset_expiry")
    private Timestamp resetExpiry;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "wrong_pass_count")
    private Integer wrongPassCount;

    @Column(name = "avatar_file")
    private String avatarFile;

    @Column(name = "avatar_content_type")
    private String avatarContentType;

    public User () {}

    public User(RegisterInfo info) {
        this.email = info.getEmail();
        this.name = info.getName();
        this.password = info.getPassword();
        this.active = false;
        this.expiryDate = Date.valueOf(LocalDate.now().plusDays(1));
        this.wrongPassCount = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getResetCode() {
        return resetCode;
    }

    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Timestamp getResetExpiry() {
        return resetExpiry;
    }

    public void setResetExpiry(Timestamp resetExpiry) {
        this.resetExpiry = resetExpiry;
    }

    public Integer getWrongPassCount() {
        return wrongPassCount;
    }

    public void setWrongPassCount(Integer wrongPassCount) {
        this.wrongPassCount = wrongPassCount;
    }

    public String getAvatarFile() {
        return avatarFile;
    }

    public void setAvatarFile(String avatarFile) {
        this.avatarFile = avatarFile;
    }

    public String getAvatarContentType() {
        return avatarContentType;
    }

    public void setAvatarContentType(String avatarContentType) {
        this.avatarContentType = avatarContentType;
    }
}
