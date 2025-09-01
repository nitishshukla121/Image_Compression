package com.example.Controller;

import com.example.Model.CompressionResponse;
import com.example.Service.CompressionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Base64;

@Controller
public class CompressionController {

    private final CompressionService compressionService;

    public CompressionController(CompressionService compressionService) {
        this.compressionService = compressionService;
    }

    @PostMapping("/compress")
    public String compressImage(@RequestParam("file") MultipartFile file,
                                @RequestParam("quality") float quality,
                                Model model) throws IOException {
    	
    	// ... inside the @PostMapping("/compress") method
    	byte[] originalData = file.getBytes();
    	String base64OriginalImage = Base64.getEncoder().encodeToString(originalData);
    	String originalImageSrc = "data:" + file.getContentType() + ";base64," + base64OriginalImage;

    	// Add this to your model
    	model.addAttribute("originalImageSrc", originalImageSrc);
    	// ... rest of your code

        // Use the new service method to get the compressed data directly for the preview
        byte[] compressedData = compressionService.compressImageAndReturnData(file, quality);
        
        // Use the original service method to get the metadata and save the file for download
        CompressionResponse response = compressionService.compressImage(file, quality);

        // Base64 encode the compressed image data for in-line display
        String base64Image = Base64.getEncoder().encodeToString(compressedData);
        String imageSrc = "data:image/jpeg;base64," + base64Image;

        // Add both the response object and the image source to the model
        model.addAttribute("response", response);
        model.addAttribute("imageSrc", imageSrc);

        return "result";
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadImage(@RequestParam("filename") String filename) throws IOException {
        byte[] compressedData = compressionService.getCompressedFile(filename);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentDispositionFormData("attachment", filename);

        return ResponseEntity.ok().headers(headers).body(compressedData);
    }
}
