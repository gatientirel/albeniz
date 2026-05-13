package com.theodo.albeniz.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theodo.albeniz.services.messaging.MessagingService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController()
@RequestMapping("/message")
@Hidden()
public class MessagingController {
    private final MessagingService messagingService;

    public MessagingController(@Qualifier("slackMessagingService") MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    @GetMapping("/send")
    public void sendMessage() {
        messagingService.sendMessage();
    }

}
