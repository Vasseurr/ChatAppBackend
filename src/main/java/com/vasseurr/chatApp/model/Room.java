package com.vasseurr.chatApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vasseurr.chatApp.model.base.AbstractAuditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ROOM")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Room extends AbstractAuditable {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "room_users",
            joinColumns = { @JoinColumn(name = "room_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<User> users = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    /*@JoinTable(name = "room_messages",
            joinColumns = { @JoinColumn(name = "room_id") },
            inverseJoinColumns = { @JoinColumn(name = "message_id") })*/
    private Set<Message> messages = new HashSet<>();

    @Override
    public String toString() {
        return "Room {" +
                "\n\tid=" + getId() +
                "\n\tusers=" + users +
                "\n}";
    }
}
