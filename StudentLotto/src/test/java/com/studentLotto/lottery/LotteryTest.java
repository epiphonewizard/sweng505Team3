package com.studentLotto.lottery;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.studentLotto.university.University;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class LotteryTest {
	@Mock
	private LotteryRepository lotteryRepositoryMock;
	
	@Mock
	private List<Lottery> lotteryListMock;
	
	@Test
	public void shouldInitializeProperly() {
		lotteryRepositoryMock.save(new Lottery());
		lotteryRepositoryMock.save(new Lottery());
		// assert
		verify(lotteryRepositoryMock, times(2)).save(any(Lottery.class));
	}
	
	@Test
	public void shouldGetAllLotteries(){
		University uni1 = new University();
		Lottery lottery1 = new Lottery();
		lottery1.setUniversity(uni1);
		University uni2 = new University();
		Lottery lottery2 = new Lottery();
		lottery1.setUniversity(uni2);
		Lottery lottery3 = new Lottery();
		lottery1.setUniversity(uni2);
		lotteryListMock.add(lottery1);
		lotteryListMock.add(lottery2);
		lotteryListMock.add(lottery3);
		when(lotteryRepositoryMock.findAll()).thenReturn(lotteryListMock);
		
		assertThat(lotteryRepositoryMock.findAll().contains(lottery2));
	}

		
}
