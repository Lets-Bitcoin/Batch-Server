package com.server.batch.trading.auto.domain.repository;

import com.server.batch.trading.auto.domain.entity.coin.Bitcoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BitcoinRepository extends JpaRepository<Bitcoin, Long> {
}
