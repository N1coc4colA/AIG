package com.aig.backend.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.image.ImageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.aig.backend.services.IChatService;
import com.aig.backend.services.IImageService;
import com.aig.backend.services.IRecipeService;

import java.io.IOException;
import java.util.List;

@RestController
public class AigController {
    private final IChatService chatService;
    private final IImageService imageService;
    private final IRecipeService recipeService;

    public AigController(IChatService chatService, IImageService imageService, IRecipeService recipeService) {
        assert(chatService != null);
        assert(imageService != null);
        assert(recipeService != null);

        this.chatService = chatService;
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("chat")
    public String getResponse(@RequestParam String prompt) {
        return chatService.getResponse(prompt);
    }

    @GetMapping("chat-options")
    public String getResponseOptions(@RequestParam String prompt) {
        return chatService.getResponseOptions(prompt);
    }

    @GetMapping("image")
    public List<String> generateImages(HttpServletResponse response,
                                       @RequestParam String prompt,
                                       @RequestParam(defaultValue = "hd") String quality,
                                       @RequestParam(defaultValue = "1") int n,
                                       @RequestParam(defaultValue = "1024") int width,
                                       @RequestParam(defaultValue = "1024") int height) throws IOException {
        try {
            ImageResponse imageResponse = imageService.generateImage(prompt, quality, n, width, height);
            // Streams to get urls from ImageResponse
            List<String> imageUrls = imageResponse.getResults().stream()
                    .map(result -> result.getOutput().getUrl())
                    .toList();

            return imageUrls;
        } catch (Exception e) {
            // Set the HTTP response status to 500 (Internal Server Error) or another appropriate status
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            // Return an HTML error message
            response.setContentType("text/plain");
            response.getWriter().write("Error generating images: " + e.getMessage());
            return null; // Return null since the response is already written
        }
    }

    @GetMapping("recipe")
    public String recipeCreator(@RequestParam String ingredients,
                                @RequestParam(defaultValue = "any") String cuisine,
                                @RequestParam(defaultValue = "") String dietaryRestriction) {
        return recipeService.createRecipe(ingredients, cuisine, dietaryRestriction);
    }
}
