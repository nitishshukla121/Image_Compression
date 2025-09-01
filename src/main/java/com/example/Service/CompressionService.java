package com.example.Service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.Model.CompressionResponse;

public interface CompressionService {
    // This method handles compression and metadata, but does not return the data
    CompressionResponse compressImage(MultipartFile file, float quality) throws IOException;

    // A separate method to get the compressed file's byte array from disk (for download)
    byte[] getCompressedFile(String filename) throws IOException;

    // A new method to perform compression and return the data directly
    byte[] compressImageAndReturnData(MultipartFile file, float quality) throws IOException;
}
