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
public class Member {

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

        Member member = (Member) o;

        if (!name.equals(member.name)) return false;
        return Objects.equals(nick, member.nick);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (nick != null ? nick.hashCode() : 0);
        return result;
    }
}
