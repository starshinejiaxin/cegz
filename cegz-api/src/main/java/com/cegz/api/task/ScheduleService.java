package com.cegz.api.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * task
 * 
 * @author lijiaxin
 * @date 2018年8月1日
 */
@Component
public class ScheduleService {
	 @Scheduled(cron = "0 0 4 * * *")
	 public void scheduled(){
		 System.out.println("task");
	 }
}
