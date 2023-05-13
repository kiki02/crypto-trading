package com.aquariux.crypto.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aquariux.crypto.domain.wallet.Wallet;
import com.aquariux.crypto.domain.wallet.service.WalletService;
import com.aquariux.crypto.infrastructure.utils.Constants;

@RestController
@RequestMapping("/api/wallet")
public class WalletAPIController {
    
    @Autowired
    private WalletService walletService;

    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    public ResponseEntity<Wallet> getBalance() {
        // we assumed that the user has already authenticated and authorised to access the APIs, so we will have the user_id to do this
        Wallet wallet = walletService.findByUserId(Constants.DEFAULT_USER_ID);
        return new ResponseEntity<>(wallet, HttpStatus.OK);
    }
}
