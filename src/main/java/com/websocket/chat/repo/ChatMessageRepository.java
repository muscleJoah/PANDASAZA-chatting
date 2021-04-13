package com.websocket.chat.repo;


import com.websocket.chat.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ChatMessageRepository {


    private ListOperations<String, ChatMessage> opsListChatMessage;
    private final RedisTemplate<String, ChatMessage> redisTemplate;
    private static final String Chat_Message = "Chat_Message";


    //TODO -- 채팅방삭제.
    //권민준사랑해
    @PostConstruct
    public void init() { opsListChatMessage = redisTemplate.opsForList(); }

    public List<ChatMessage> findMessageByRoomId(String roomId) {
         return opsListChatMessage.range(roomId,0,opsListChatMessage.size(roomId)-1);
    }

    public ChatMessage createChatMessage(ChatMessage Message) {
            opsListChatMessage.rightPush(Message.getRoomId(),Message);
            return Message;

    }
}
