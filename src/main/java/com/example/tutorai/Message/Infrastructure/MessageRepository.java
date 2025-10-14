package com.example.tutorai.Message.Infrastructure;

import com.example.tutorai.Message.Domain.Message;
import com.example.tutorai.Message.Domain.MessageId;
import com.example.tutorai.Topic.Domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, MessageId> {

    int countByTopic(Topic topic);

    List<Message> findByTopicOrderByIdMessageNumberAsc(Topic topic);
}
