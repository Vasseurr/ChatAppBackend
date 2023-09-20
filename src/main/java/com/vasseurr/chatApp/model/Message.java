package com.vasseurr.chatApp.model;

import com.vasseurr.chatApp.model.base.AbstractAuditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MESSAGE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Message extends AbstractAuditable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Override
    public String toString() {
        return "\nMessage{" +
                "\n\tid='" + getId() + '\'' +
                "\n\tsender=" + sender +
                ", \n\treceiver=" + receiver +
                ", \n\tcontent='" + content + '\'' +
                ", \n\tmodifiedDate='" + getModifiedDate() + '\'' +
                "\n}";
    }
}
