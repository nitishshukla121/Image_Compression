package com.example.Util;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class LossyImageCompressor {

    public byte[] compressToJpeg(BufferedImage image, float quality) throws IOException {
        if (image == null) {
            return new byte[0];
        }

        ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("JPEG").next();
        ImageWriteParam jpgParam = jpgWriter.getDefaultWriteParam();
        jpgParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        
        jpgParam.setCompressionQuality(quality);

        ByteArrayOutputStream compressedStream = new ByteArrayOutputStream();
        ImageOutputStream outputStream = ImageIO.createImageOutputStream(compressedStream);
        jpgWriter.setOutput(outputStream);
        jpgWriter.write(null, new javax.imageio.IIOImage(image, null, null), jpgParam);
        
        outputStream.close();
        jpgWriter.dispose();
        
        return compressedStream.toByteArray();
    }
}