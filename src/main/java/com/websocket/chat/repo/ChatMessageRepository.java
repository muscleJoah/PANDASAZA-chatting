package com.websocket.chat.repo;


import com.websocket.chat.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ChatMessageRepository {

private HashOperations<String, String, ChatMessage> opsHashChatMessage;
private final RedisTemplate<String, Object> redisTemplate;
private static final String Chat_Messages = "ChatMessage";
// 해시를 <Chat_Messages, sender(), roomid())git p

@PostConstruct
public void init(){
    opsHashChatMessage = redisTemplate.opsForHash();
}
public ChatMessage createChatMessage(ChatMessage Message){
    opsHashChatMessage.put(Chat_Messages, Message.getRoomId(), Message);
    return Message;
}
public List<ChatMessage> findAllMessage() {return opsHashChatMessage.values(Chat_Messages);}
public ChatMessage findMessageByRoomId(String roomId) {return opsHashChatMessage.get(Chat_Messages,roomId);}
}
