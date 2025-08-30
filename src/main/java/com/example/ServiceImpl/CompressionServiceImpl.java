package com.example.ServiceImpl;

import com.example.Model.CompressionResponse;
import com.example.Service.CompressionService;
import com.example.Util.LossyImageCompressor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class CompressionServiceImpl implements CompressionService {

    private final LossyImageCompressor lossy = new LossyImageCompressor();
    private final String tempDir = "compressed_temp";

    public CompressionServiceImpl() throws IOException {
        Files.createDirectories(Path.of(tempDir));
    }
   
    public byte[] getCompressedFile(String filename) throws IOException {
        Path filePath = Path.of(tempDir, filename);
        return Files.readAllBytes(filePath);
    }

	@Override
	public CompressionResponse compressImage(MultipartFile file, float quality) throws IOException {
		 long originalSize = file.getSize();
	        BufferedImage originalImage = ImageIO.read(file.getInputStream());
	        
	        if (originalImage == null) {
	            throw new IOException("Could not read image data. The file format may be invalid.");
	        }

	        if (originalImage.getType() != BufferedImage.TYPE_INT_RGB) {
	            BufferedImage rgbImage = new BufferedImage(
	                originalImage.getWidth(),
	                originalImage.getHeight(),
	                BufferedImage.TYPE_INT_RGB);
	            rgbImage.createGraphics().drawImage(originalImage, 0, 0, null);
	            originalImage = rgbImage;
	        }

	        byte[] compressedData = lossy.compressToJpeg(originalImage, quality);
	        long compressedSize = compressedData.length;

	        String compressedFileName = UUID.randomUUID() + ".jpg";
	        Path outputPath = Path.of(tempDir, compressedFileName);
	        Files.write(outputPath, compressedData);

	        return new CompressionResponse(
	            file.getOriginalFilename(),
	            originalSize,
	            compressedSize,
	            (double) (originalSize - compressedSize) / originalSize * 100,
	            compressedFileName
	        );
	}
}