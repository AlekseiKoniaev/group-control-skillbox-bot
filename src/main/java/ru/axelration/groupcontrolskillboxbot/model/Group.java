package ru.axelration.groupcontrolskillboxbot.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.axelration.groupcontrolskillboxbot.model.enums.Role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "_group")
@Getter
@Setter
@RequiredArgsConstructor
public class Group {

    @Setter(AccessLevel.NONE)
    @Id
    @Column(nullable = false)
    private final Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id")
    private Group group;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (!title.equals(group.title)) return false;
        return role == group.role;
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }
}
