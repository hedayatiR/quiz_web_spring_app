package ir.maktab.model.account;

import ir.maktab.model.base.BaseEntity;
import ir.maktab.model.role.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "Accounts")
public class Account extends BaseEntity<Long> implements UserDetails {

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    @Transient
    private String repeatPassword;

    //    private UserStatusEnum status;
    @Column(nullable = false)
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Accounts_roles",
            joinColumns = @JoinColumn(name = "Account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    protected Collection<Role> roles;


    public Account(String username, String password, Collection<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.enabled = false;
//        this.status = UserStatusEnum.INACTIVATED;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
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

//    @Override
//    public boolean isEnabled() {
//        return (this.status == UserStatusEnum.ACTIVATED);
//    }

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
