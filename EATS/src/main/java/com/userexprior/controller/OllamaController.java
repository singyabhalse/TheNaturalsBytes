package com.userexprior.controller;

import com.userexprior.ai.rca.RCAService;
import org.springframework.ai.chat.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ollama")
public class OllamaController {
    private final ChatClient chatClient;
    private final RCAService rcaService;

    public OllamaController(ChatClient chatClient, RCAService rcaService) {
        this.chatClient = chatClient;
        this.rcaService = rcaService;
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String prompt) {
        return chatClient.call(prompt);
    }

    @GetMapping("/log")
    public String log() {
        return rcaService.analyzeLogs();
    }

}
