package com.st.app.controllers;

import com.st.app.dao.ScriptService;
import com.st.app.dto.PagingInfo;
import com.st.app.dto.ScriptListResponse;
import com.st.app.model.Script;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScriptsController {

    @Autowired
    private ScriptService scriptService;

    Logger logger = LoggerFactory.getLogger(ScriptsController.class);

    @PostMapping("/api/scripts/fetch")
    public ScriptListResponse fetch(@RequestBody PagingInfo info){
        Page<Script> scripts = scriptService.fetch(info);
        ScriptListResponse response = new ScriptListResponse();
        response.setData(scripts.getContent());
        response.setTotalRows(scripts.getTotalElements());
        return response;
    }


}
