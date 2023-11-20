package com.st.app.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class RecognitionService {

    Logger logger = LoggerFactory.getLogger(RecognitionService.class);

    public String recognize(InputStream stream){
        //TODO implement call google api for speech recognition
        return null;
    }
}
