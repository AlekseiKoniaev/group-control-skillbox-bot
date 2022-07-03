package ru.axelration.groupcontrolskillboxbot.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "_user")
@Getter
@Setter
@RequiredArgsConstructor
public class User {

    @Setter(AccessLevel.NONE)
    @Id
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String nick;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!name.equals(user.name)) return false;
        return Objects.equals(nick, user.nick);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (nick != null ? nick.hashCode() : 0);
        return result;
    }
}
