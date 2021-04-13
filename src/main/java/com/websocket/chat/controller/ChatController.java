package com.websocket.chat.controller;

import com.websocket.chat.model.ChatMessage;
import com.websocket.chat.pubsub.RedisPublisher;
import com.websocket.chat.repo.ChatMessageRepository;
import com.websocket.chat.repo.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class ChatController {

    private final RedisPublisher redisPublisher;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

     //websocket "/pub/chat/message"로 들어오는 메시징을 처리

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            chatRoomRepository.enterChatRoom(message.getRoomId());
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        }
        // Websocket에 발행된 메시지를 redis로 발행한다(publish)
        chatMessageRepository.createChatMessage(message);
        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);

        System.out.println("테스트되나?");
    }

    @GetMapping("/chat/messageList/{roomId}")
    @ResponseBody
    public List<ChatMessage> findMessage(@PathVariable String roomId){
        return chatMessageRepository.findMessageByRoomId(roomId);
    }
}