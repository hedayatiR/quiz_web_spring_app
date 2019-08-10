package ir.maktab.model.account;

import ir.maktab.model.base.BaseEntity;
import ir.maktab.model.role.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "accounts")
public class Account extends BaseEntity<Long> implements UserDetails {

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    @Transient
    private String repeatPassword;

    @Column(nullable = false)
    private boolean enabled;

    @ManyToOne
    protected Role role;


    public Account(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = false;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<Role> collection = new ArrayList<>();
        collection.add(role);
        return collection;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

//    @PrePersist
//    public void prePersist() {
//        if(this.status == null)
//            this.status = UserStatusEnum.INACTIVATED;
//    }


    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
