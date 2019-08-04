package ir.maktab.model.user;

import ir.maktab.model.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "users")
public class User extends BaseEntity<Long> {

    @Column(unique = true)
    private String userName;

    private String password;

    private UserStatusEnum status;


    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.status = UserStatusEnum.INACTIVATED;
    }
}
