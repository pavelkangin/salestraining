package com.st.app.dao;

import com.st.app.controllers.ScriptsController;
import com.st.app.model.Constants;
import com.twelvemonkeys.imageio.plugins.jpeg.JPEGImageWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;


@Service
public class ImageService {

    Logger logger = LoggerFactory.getLogger(ImageService.class);


    public File resize(InputStream file,String contentType, int width, int height){
        //TODO implement resize of jpeg and png images using canvas 2d
        String fileName=String.valueOf(new Date().getTime());
        File img=new File(Constants.avatarPath+"/"+fileName);
        if (resize(file,img,width,height,0.8f)){
            return img;
        }
        return null;
    }


    private boolean resize(InputStream source, File dest, int width,int height,float quality)  {
        try {
            BufferedImage sourceImage = ImageIO.read(source);
            int h=0;
            int w=0;
            if (sourceImage.getWidth()>sourceImage.getHeight()){
                h=width*sourceImage.getHeight()/sourceImage.getWidth();
                w=width;
            }
            else {
                h=height;
                w=height*sourceImage.getWidth()/sourceImage.getHeight();
            }

            logger.info("wh: " + w + " " + h);
            Image scaled = sourceImage.getScaledInstance(w, h, Image.SCALE_AREA_AVERAGING);
            BufferedImage bufferedScaled = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bufferedScaled.createGraphics();

            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);



            g2d.drawImage(scaled, width/2-w/2, height/2-h/2, w, h, null);
            BufferedImage image=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
            Graphics2D bGr = image.createGraphics();
            bGr.drawImage(scaled, 0, 0, null);
            bGr.dispose();

            g2d.setTransform(new AffineTransform());
            int crop_w=w;
            int crop_h=h;
            if (crop_w>crop_h){
                writeJpeg(bufferedScaled.getSubimage(0,height/2-crop_h/2,w,crop_h), dest.getCanonicalPath(), quality);
            }
            else {
                writeJpeg(bufferedScaled.getSubimage(width/2-crop_w/2,0,crop_w,crop_h), dest.getCanonicalPath(), quality);
            }
            g2d.dispose();

            return true;
        } catch (IOException e){
            logger.error(e.getMessage(),e);
        }

        return false;

    }

    private void writeJpeg(BufferedImage image, String destFile, float quality) throws IOException{

            // Image writer
            JPEGImageWriter imageWriter =  (JPEGImageWriter) ImageIO.getImageWritersBySuffix("jpeg").next();
            ImageOutputStream ios = ImageIO.createImageOutputStream(new File(destFile));
            imageWriter.setOutput(ios);

            // Compression
            JPEGImageWriteParam jpegParams = (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
            jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
            jpegParams.setCompressionQuality(quality);

            // Metadata (dpi)
            IIOMetadata data = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image), jpegParams);
            Element tree = (Element)data.getAsTree("javax_imageio_jpeg_image_1.0");
            Element jfif = (Element)tree.getElementsByTagName("app0JFIF").item(0);
            jfif.setAttribute("Xdensity", Integer.toString(300));
            jfif.setAttribute("Ydensity", Integer.toString(300));
            jfif.setAttribute("resUnits", "1"); // density is dots per inch
            data.setFromTree("javax_imageio_jpeg_image_1.0", tree);
            // Write and clean up



            imageWriter.write(null, new IIOImage(image, null, data), jpegParams);
            ios.close();
            imageWriter.dispose();



    }



}
