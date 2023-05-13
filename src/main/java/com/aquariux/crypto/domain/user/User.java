package com.aquariux.crypto.domain.user;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.aquariux.crypto.infrastructure.object.AbstractEntity;

import lombok.Data;

@Entity
@Table(name = "m_user")
@Data
public class User extends AbstractEntity {
    
    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false)
    @NotEmpty
    private String userName;

    @Column(nullable = false)
    @NotEmpty
    private String userEmail;

    @Column(nullable = false)
    @NotEmpty
    private String userPassword;

    public User() {
        this.userId = UUID.randomUUID().toString();
    }
}
