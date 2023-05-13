package com.aquariux.crypto.domain.wallet.service;

import com.aquariux.crypto.domain.wallet.Wallet;

public interface WalletService {
    Wallet findByWalletId(String walletId);
    Wallet findByUserId(String userId);
    Wallet save(Wallet wallet);
}
