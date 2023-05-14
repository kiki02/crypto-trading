package com.aquariux.crypto.domain.trading.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aquariux.crypto.domain.trading.TradingHistory;
import com.aquariux.crypto.domain.trading.repository.TradingHistoryRepository;
import com.aquariux.crypto.domain.trading.service.TradingHistoryService;

@Service
public class TradingHistoryServiceImpl implements TradingHistoryService {

    @Autowired
    private TradingHistoryRepository tradingHistoryRepository;

    @Override
    public TradingHistory save(TradingHistory tradingHistory) {
        return tradingHistoryRepository.save(tradingHistory);
    }

    @Override
    public TradingHistory findByTradingId(String tradingId) {
        return tradingHistoryRepository.findById(tradingId).orElse(null);
    }

    @Override
    public List<TradingHistory> findByCreateUser(String createUser) {
        return tradingHistoryRepository.findByCreateUserOrderByUpdateDateDesc(createUser);
    }

    @Override
    public List<TradingHistory> findByCreateUserAndTradingType(String createUser, int tradingType, Pageable pageable) {
        return tradingHistoryRepository.findByCreateUserAndTradingTypeOrderByUpdateDateDesc(createUser, tradingType, pageable);
    }

    @Override
    public List<TradingHistory> findByCreateUserAndTradingPair(String createUser, String tradingPair, Pageable pageable) {
        return tradingHistoryRepository.findByCreateUserAndTradingPairOrderByUpdateDateDesc(createUser, tradingPair, pageable);
    }

    
    
}
