package com.vasseurr.chatApp.model;

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
@EqualsAndHashCode
public class Room extends AbstractAuditable {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "room_users",
            joinColumns = { @JoinColumn(name = "room_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<User> users = new HashSet<>();

    @Override
    public String toString() {
        return "Room {" +
                "\n\tid=" + getId() +
                "\n\tusers=" + users +
                "\n}";
    }
}
