package ru.axelration.groupcontrolskillboxbot.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.axelration.groupcontrolskillboxbot.model.enums.UserRole;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "joining")
@Getter
@Setter
@RequiredArgsConstructor
public class Joining {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Enumerated
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private LocalDateTime entryTime;

    @Column(nullable = false)
    private Integer serialNumber;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Joining joining = (Joining) o;

        if (!user.equals(joining.user)) return false;
        return group.equals(joining.group);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + group.hashCode();
        return result;
    }
}
