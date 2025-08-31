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
        
        CompressionResponse response = compressionService.compressImage(file, quality);
        model.addAttribute("response", response);
        
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
    
//    @GetMapping("/display")
//    public ResponseEntity<byte[]> displayImage(@RequestParam("filename") String filename) throws IOException {
//        Path imagePath = Paths.get(COMPRESSED_IMAGE_PATH + filename);
//
//        logger.info("Attempting to load image for display from path: " + imagePath.toAbsolutePath());
//
//        try {
//            if (Files.exists(imagePath) && Files.isReadable(imagePath)) {
//                byte[] imageBytes = Files.readAllBytes(imagePath);
//                HttpHeaders headers = new HttpHeaders();
//
//                String fileExtension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
//                if (fileExtension.equals("jpg") || fileExtension.equals("jpeg")) {
//                    headers.setContentType(MediaType.IMAGE_JPEG);
//                } else if (fileExtension.equals("png")) {
//                    headers.setContentType(MediaType.IMAGE_PNG);
//                }
//                
//                headers.setContentLength(imageBytes.length);
//                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
//            } else {
//                logger.error("File not found or not readable: " + imagePath.toAbsolutePath());
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        } catch (NoSuchFileException e) {
//            logger.error("File not found: " + imagePath.toAbsolutePath());
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } catch (IOException e) {
//            logger.error("Error reading file: " + imagePath.toAbsolutePath(), e);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    
}