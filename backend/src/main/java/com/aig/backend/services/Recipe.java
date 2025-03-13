package com.aig.backend.services;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.model.Media;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class Recipe implements IRecipeService {
    private final ChatModel chatModel;

    public Recipe(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String createRecipe(String ingredients, String cuisine, String dietaryRestrictions) {
        var template = """
            I want to create a recipe using the following ingredients: {ingredients}.
            The cuisine type I prefer is {cuisine}.
            Please consider the following dietary restrictions: {dietaryRestrictions}.
            Please provide me with a detailed recipe including title, list of ingredients, and cooking instructions
        """;

        PromptTemplate promptTemplate = new PromptTemplate(template);
        Map<String, Object> params = Map.of(
                "ingredients",ingredients,
                "cuisine", cuisine,
                "dietaryRestrictions", dietaryRestrictions
        );

        final Prompt prompt = promptTemplate.create(params);

        return utils.mediaToString(chatModel.call(prompt).getResult().getOutput().getMedia());
    }
}
