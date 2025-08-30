package com.example.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompressionResponse {
	private String fileName;
	private long originalSize;
	private long compressedSize;
	private double compressionPercentage;
	private String compressedFileName; 

}
