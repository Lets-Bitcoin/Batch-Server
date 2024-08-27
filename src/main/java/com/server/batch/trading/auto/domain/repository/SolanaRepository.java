package com.server.batch.trading.auto.domain.repository;

import com.server.batch.trading.auto.domain.entity.coin.Solana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolanaRepository extends JpaRepository<Solana, Long> {
}
