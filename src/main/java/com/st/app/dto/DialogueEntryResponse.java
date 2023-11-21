package com.st.app.dto;

import com.st.app.model.DialogueEntry;

public class DialogueEntryResponse extends DefaultResponse {

    private DialogueEntry entry;
    private boolean hasNext=false;

    public DialogueEntry getEntry() {
        return entry;
    }

    public void setEntry(DialogueEntry entry) {
        this.entry = entry;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
}
