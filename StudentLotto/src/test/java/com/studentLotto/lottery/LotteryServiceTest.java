package com.studentLotto.lottery;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.mockito.Mock;


public class LotteryServiceTest {
	
	@Mock
	private LotteryRepository lotteryRepositoryMock;
	
	@Mock
	private LotteryService lotteryServiceMock;
	
	@Test
	public void drawNumbersTest() {
		//arrange
		List<Lottery> lotteryList = lotteryRepositoryMock.findAll();
		Lottery lottery = lotteryList.get(0);
		lottery.setFullMatchGuaranteed(true);
		lottery.setNumberOfBallsPicked(6);
		
		//act
		lotteryServiceMock.drawWinningNumbers(lottery);
		
		// assert
		assertThat(lottery.getWinningNumber1().intValue() > 0);
		assertThat(lottery.getWinningNumber2().intValue() > 0);
		assertThat(lottery.getWinningNumber3().intValue() > 0);
		assertThat(lottery.getWinningNumber4().intValue() > 0);
		assertThat(lottery.getWinningNumber5().intValue() > 0);
		assertThat(lottery.getWinningNumber6().intValue() > 0);
	}

}
