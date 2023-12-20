package com.st.app.dto;

import com.st.app.model.DialogueEntry;

public class DialogueProgressInfo {

    private String sentence;
    private int scriptId;
    private int sentenceId;


    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public int getScriptId() {
        return scriptId;
    }

    public void setScriptId(int scriptId) {
        this.scriptId = scriptId;
    }

    public int getSentenceId() {
        return sentenceId;
    }

    public void setSentenceId(int sentenceId) {
        this.sentenceId = sentenceId;
    }
}
