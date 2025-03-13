package com.aig.backend.services;

public interface IChatService {
    public String getResponse(String prompt);
    public String getResponseOptions(String prompt);
}
