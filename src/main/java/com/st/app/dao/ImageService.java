package com.st.app.dao;

import com.st.app.controllers.ScriptsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

@Service
public class ImageService {

    Logger logger = LoggerFactory.getLogger(ImageService.class);


    public File resize(InputStream file,String contentType, int width, int height){
        //TODO implement resize of jpeg and png images using canvas 2d

        return null;
    }

}
