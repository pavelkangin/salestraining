package com.st.app.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class SynthService {

    Logger logger = LoggerFactory.getLogger(SynthService.class);

    public InputStream synth(String sentence){
        //TODO implement call API for synth audio from the text

        return null;
    }

}
