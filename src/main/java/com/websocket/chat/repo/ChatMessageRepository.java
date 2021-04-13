package com.websocket.chat.repo;


import com.websocket.chat.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Repository
public class ChatMessageRepository {


    private ListOperations<String, ChatMessage> opsListChatMessage;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String Chat_Message = "Chat_Message";

    // 해시를 <Chat_Messages, sender(), roomid())git p
    @PostConstruct
    public void init() { opsListChatMessage = redisTemplate.opsForList(); }

    public void findMessageByRoomId(String roomId) {

         System.out.println(opsListChatMessage.leftPop(roomId).toString());
    }

    public ChatMessage createChatMessage(ChatMessage Message) {
            opsListChatMessage.rightPush(Message.getRoomId(),Message);
            return Message;

    }
}
