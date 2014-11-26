package com.studentLotto.lottery;

import static org.assertj.core.api.Assertions.assertThat;





import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.studentLotto.student.PurchaseTicketRepo;
import com.studentLotto.university.University;

@RunWith(MockitoJUnitRunner.class)
public class LotteryServiceTest {

	private LotteryService lotteryService;
	
	@Test
	public void drawNumbersTest() {
		//arrange
		Lottery lottery = new Lottery(new Date(), true, 5.0, 15, 25, 4, new Date(), new Date(), 0.45, new University(), 100.0);
		lottery.setId(12);
		
		//act
		//lotteryService.drawWinningNumbers(lottery);
		
		// assert
		assertThat(lottery.getWinningNumber1() > 0);
		assertThat(lottery.getWinningNumber2() > 0);
	}

}
