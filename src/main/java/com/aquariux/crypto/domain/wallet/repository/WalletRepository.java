package com.aquariux.crypto.domain.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aquariux.crypto.domain.wallet.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, String> {

    Wallet findByWalletId(String walletId);
    Wallet findByUserId(String userId);
}
