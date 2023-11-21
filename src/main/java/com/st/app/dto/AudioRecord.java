package com.st.app.dto;

import java.sql.Date;

public class AudioRecord {

    private Date date;
    private String file;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
