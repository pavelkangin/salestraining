package com.st.app.controllers;

import com.st.app.dao.AnalyticsService;
import com.st.app.dto.AnalyticsFilter;
import com.st.app.dto.AnalyticsResponse;
import com.st.app.dto.AudioRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class AnalyticsController {

    Logger logger = LoggerFactory.getLogger(AnalyticsController.class);

    @Autowired
    private AnalyticsService analyticsService;


    @PostMapping("/api/analytics/fetch")
    public AnalyticsResponse fetch(@RequestBody AnalyticsFilter filter, HttpSession session){
        //TODO return list of the analytics record depending on selected filters
        return null;
    }


    @GetMapping("/api/analytics/records/{scriptId}")
    public List<AudioRecord> records(@PathVariable("scriptId")Integer scriptId, HttpSession session){
        //TODO return list of audio records related to specific analytics record
        return null;
    }

    @GetMapping("/api/analytics/play/{recordId}")
    public void play(@PathVariable("recordId")Integer recordId, HttpServletResponse response,HttpSession session){
        //TODO stream audio to the browser
    }




}
