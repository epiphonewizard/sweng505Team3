package com.studentLotto.lottery;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LotteryController {

	private LotteryRepository lotteryRepository;
	
	@Autowired
	public LotteryController(LotteryRepository lotteryRepository){
		this.lotteryRepository = lotteryRepository;
	}
	
	@RequestMapping(value="lottery/create", method=RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String create(Principal principal){
		return "error/NotYetImplemented";
	}
}
