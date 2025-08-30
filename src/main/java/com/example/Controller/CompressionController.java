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
}