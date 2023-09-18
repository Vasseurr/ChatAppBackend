package com.vasseurr.chatApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vasseurr.chatApp.model.base.AbstractAuditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "USER", uniqueConstraints = { @UniqueConstraint(columnNames = { "userName", "email" }) })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends AbstractAuditable {

    private String firstName;

    private String lastName;

    private String userName;

    private String password;

    private String email;

    private String photoLink;

   /* @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "users")
    @JsonIgnore
    private Set<Room> rooms = new HashSet<>();*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) &&
                Objects.equals(userName, user.userName) && Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) && Objects.equals(photoLink, user.photoLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, userName, password, email, photoLink);
    }

    @Override
    public String toString() {
        return "\nUser {" +
                "\n\tid='" + getId() + '\'' +
                "\n\tfirstName='" + firstName + '\'' +
                ", \n\tlastName='" + lastName + '\'' +
                ", \n\tuserName='" + userName + '\'' +
                ", \n\tpassword='" + password + '\'' +
                ", \n\temail='" + email + '\'' +
                ", \n\tphotoLink='" + photoLink + '\'' +
                ", \n\tmodifiedDate='" + getModifiedDate() + '\'' +
                "\n}";
    }
}
