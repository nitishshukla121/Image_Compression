package com.example.Util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class RLEImageCompressor {
	public byte[] compress(BufferedImage image) throws IOException {
        if (image == null) {
            return new byte[0];
        }

        ByteArrayOutputStream compressedStream = new ByteArrayOutputStream();
        int width = image.getWidth();
        int height = image.getHeight();

        compressedStream.write(ByteBuffer.allocate(4).putInt(width).array());
        compressedStream.write(ByteBuffer.allocate(4).putInt(height).array());

        for (int y = 0; y < height; y++) {
            int x = 0;
            while (x < width) {
                int currentPixel = image.getRGB(x, y);
                int count = 1;
                int j = x + 1;
                while (j < width && image.getRGB(j, y) == currentPixel) {
                    count++;
                    j++;
                }

                compressedStream.write(ByteBuffer.allocate(4).putInt(count).array());
                compressedStream.write(ByteBuffer.allocate(4).putInt(currentPixel).array());
                x = j;
            }
        }
        return compressedStream.toByteArray();
    }

}
