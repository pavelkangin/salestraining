package com.st.app.dto;

import java.sql.Date;

public class AnalyticsFilter {

    private int user;
    private int script;

    private Date startDate;

    private Date endDate;


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getScript() {
        return script;
    }

    public void setScript(int script) {
        this.script = script;
    }
}
