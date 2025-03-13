package com.aig.backend.services;

import org.springframework.ai.image.ImageResponse;

public interface IImageService {
    public ImageResponse generateImage(String prompt, String quality, int n, int width, int height);
}
