package com.aquariux.crypto.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aquariux.crypto.domain.wallet.service.WalletService;

@RestController
@RequestMapping("/api/wallet")
public class WalletAPIController {
    
    @Autowired
    private WalletService walletService;

    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    public ResponseEntity<Object> getProduct() {
       return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
