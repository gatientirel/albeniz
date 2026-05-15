package com.theodo.albeniz.services.messaging;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service("emailMessagingService")
@Slf4j()
public class EmailMessagingService implements MessagingService {

    @Override
    public void sendMessage() {
        log.info("I just sent something via mail !");
        ;
    }

}
