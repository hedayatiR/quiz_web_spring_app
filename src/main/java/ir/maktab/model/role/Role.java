package ir.maktab.model.role;

import ir.maktab.model.base.BaseEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "roles")
public class Role extends BaseEntity<Long> implements GrantedAuthority {

    @Column(unique = true)
    private String name;

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
