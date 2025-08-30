package com.example.Service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.Model.CompressionResponse;

public interface CompressionService {
	CompressionResponse compressImage(MultipartFile file, float quality) throws IOException;

    byte[] getCompressedFile(String filename) throws IOException;

}
