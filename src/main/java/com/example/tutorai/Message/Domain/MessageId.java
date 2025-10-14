package com.example.tutorai.Message.Domain;

import com.example.tutorai.Topic.Domain.TopicId;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;



@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MessageId implements Serializable {
    private TopicId topicId;
    private Integer messageNumber;
}
