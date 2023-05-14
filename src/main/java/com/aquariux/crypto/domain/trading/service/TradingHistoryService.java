package com.aquariux.crypto.domain.trading.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.aquariux.crypto.domain.trading.TradingHistory;

public interface TradingHistoryService {

    TradingHistory save(TradingHistory tradingHistory);
    List<TradingHistory> findByCreateUser(String createUser);
    TradingHistory findByTradingId(String tradingId);
    List<TradingHistory> findByCreateUserAndTradingType(String createUser, int tradingType, Pageable pageable);
	List<TradingHistory> findByCreateUserAndTradingPair(String createUser, String tradingPair, Pageable pageable);
}
