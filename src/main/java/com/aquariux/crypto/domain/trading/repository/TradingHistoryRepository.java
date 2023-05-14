package com.aquariux.crypto.domain.trading.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aquariux.crypto.domain.trading.TradingHistory;

@Repository
public interface TradingHistoryRepository extends JpaRepository<TradingHistory, String> {
    
    List<TradingHistory> findByCreateUserOrderByUpdateDateDesc(String createUser);
    List<TradingHistory> findByCreateUserAndTradingTypeOrderByUpdateDateDesc(String createUser, int tradingType, Pageable pageable);
	List<TradingHistory> findByCreateUserAndTradingPairOrderByUpdateDateDesc(String createUser, String tradingPair, Pageable pageable);
}
