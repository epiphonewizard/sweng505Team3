package com.studentLotto.lottery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LotteryRepository extends JpaRepository<Lottery, Long> {
		
}
