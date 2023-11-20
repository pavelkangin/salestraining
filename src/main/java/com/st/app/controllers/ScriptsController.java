package com.st.app.controllers;

import com.st.app.dao.ScriptService;
import com.st.app.dto.AuthInfo;
import com.st.app.dto.DefaultResponse;
import com.st.app.dto.PagingInfo;
import com.st.app.dto.ScriptListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class ScriptsController {

    @Autowired
    private ScriptService scriptService;

    Logger logger = LoggerFactory.getLogger(ScriptsController.class);

    @PostMapping("/api/scripts/fetch")
    public ScriptListResponse fetch(@RequestBody PagingInfo info, HttpSession session){
        //TODO return list of all scripts depending on the paging info

        return null;
    }


}
