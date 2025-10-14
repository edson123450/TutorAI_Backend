
package com.example.tutorai.Message.Domain;


        import com.example.tutorai.Topic.Domain.Topic;
        import com.example.tutorai.Topic.Domain.TopicId;
        import jakarta.persistence.*;
        import lombok.*;

@Entity
@Table(
        name="messages",
        uniqueConstraints = {
                @UniqueConstraint(
                        name="uk_messages_topic_msgnum",
                        columnNames = {"classroom_id","course_id","topic_number","message_number"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    @EmbeddedId
    private MessageId id;

    @MapsId("topicId")
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name="classroom_id", referencedColumnName = "classroom_id"),
            @JoinColumn(name="course_id", referencedColumnName = "course_id"),
            @JoinColumn(name="topic_number", referencedColumnName = "topic_number")
    })
    private Topic topic;

    @Enumerated(EnumType.STRING)
    @Column(name="role",nullable = false,length = 16)
    private MessageRole role;

    @Lob
    @Column(name="content",nullable = false)
    private String content;

    public TopicId getTopicId(){
        return id!=null ? id.getTopicId() : null;
    }

    public Integer getMessageNumber(){
        return id!=null ? id.getMessageNumber() : null;
    }

}