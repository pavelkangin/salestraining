package com.st.app.dao;

import com.st.app.model.AnalyticsEntry;
import com.st.app.model.Script;
import com.st.app.model.User;
import com.st.app.repository.AnalyticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class AnalyticsService {

    @Autowired
    private AnalyticsRepository repository;

    public List<AnalyticsEntry> fetch(Script script, User user, Date startDate,Date endDate){
        //TODO implement fetch list of all records by user and/or script if specified, and/or specified interval
        return null;
    }

}
