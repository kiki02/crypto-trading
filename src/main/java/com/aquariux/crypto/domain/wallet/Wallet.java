package com.aquariux.crypto.domain.wallet;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.aquariux.crypto.domain.user.User;
import com.aquariux.crypto.infrastructure.object.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "m_wallet")
@Data
public class Wallet extends AbstractEntity {
    
    @Id
    @Column(name = "wallet_id", nullable = false)
    private String walletId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column
    private BigDecimal usdt;

    @Column
    private BigDecimal ethusdt;

    @Column
    private BigDecimal btcusdt;

    public Wallet() {
        this.walletId = UUID.randomUUID().toString();
    }

    // @JsonIgnore
    // @OneToOne(optional=false)
    // @JoinColumn(name="user_id",referencedColumnName="user_id", insertable=false, updatable=false)
    // private User walletUser;
}
