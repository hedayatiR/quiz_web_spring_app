package ir.maktab.model.role;

import ir.maktab.model.base.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "roles")
public class Role extends BaseEntity<Long> {

    @Column(unique = true)
    private RoleEnum name;

    public Role(RoleEnum name) {
        this.name = name;
    }

}
