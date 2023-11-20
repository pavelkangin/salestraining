package com.st.app.dao;

import com.st.app.dto.PagingInfo;
import com.st.app.model.Script;
import com.st.app.repository.ScriptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ScriptService {


    Logger logger = LoggerFactory.getLogger(ScriptService.class);


    @Autowired
    private ScriptRepository repository;

    public Page<Script> fetch(PagingInfo info){
        //TODO implement fetching scripts with paging, assume page size is 20 rows

        return null;
    }

}
