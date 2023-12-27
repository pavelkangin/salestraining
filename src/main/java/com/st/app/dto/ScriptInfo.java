package com.st.app.dto;

import com.st.app.model.Script;

public class ScriptInfo {

    private Script script;
    private String message;


    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
