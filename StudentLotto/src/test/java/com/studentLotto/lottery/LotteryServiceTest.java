package com.studentLotto.lottery;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LotteryServiceTest {
	
	@Mock
	private LotteryRepository lotteryRepositoryMock;
	
	@Mock
	private LotteryService lotteryServiceMock;
	
	@Test
	public void drawNumbersTest() {
		//arrange
		Lottery lottery = new Lottery();
		lottery.setFullMatchGuaranteed(true);
		lottery.setNumberOfBallsPicked(6);
		lottery.setNumberOfBallsAvailable(18);
		
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
