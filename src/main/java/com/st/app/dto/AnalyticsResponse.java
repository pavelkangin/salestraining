package com.st.app.dto;

import com.st.app.model.Script;
import com.st.app.model.User;

import java.sql.Date;

public class AnalyticsResponse {

    private User user;

    private Script script;

    private int tries;

    private String avgDuration;

    private Date lastDate;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public String getAvgDuration() {
        return avgDuration;
    }

    public void setAvgDuration(String avgDuration) {
        this.avgDuration = avgDuration;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }
}
