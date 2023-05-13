package com.aquariux.crypto.domain.wallet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.aquariux.crypto.domain.wallet.Wallet;
import com.aquariux.crypto.domain.wallet.repository.WalletRepository;
import com.aquariux.crypto.domain.wallet.service.WalletService;

public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Wallet findByWalletId(String walletId) {
        return walletRepository.findByWalletId(walletId);
    }

    @Override
    public Wallet findByUserId(String userId) {
        return walletRepository.findByUserId(userId);
    }

    @Override
    public Wallet saveWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }
    
}
