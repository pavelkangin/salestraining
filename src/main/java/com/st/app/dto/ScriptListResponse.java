package com.st.app.dto;

import com.st.app.model.Script;

import java.util.ArrayList;
import java.util.List;

public class ScriptListResponse extends DefaultResponse{

    private List<Script> data=new ArrayList<>();
    private int totalRows;

    public List<Script> getData() {
        return data;
    }

    public void setData(List<Script> data) {
        this.data = data;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }
}
