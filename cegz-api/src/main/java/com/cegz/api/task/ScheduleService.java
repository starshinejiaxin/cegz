package com.cegz.api.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cegz.api.service.PublishAdverService;

/**
 * task
 * 
 * @author lijiaxin
 * @date 2018年8月1日
 */
@Component
public class ScheduleService {
	 @Autowired
	 private PublishAdverService publishService;
	 @Scheduled(cron = "0 0 4 * * *")
//	 @Scheduled(cron = "0/5 * * * * *")
	 public void scheduled(){
		 System.out.println("task");
		 publishService.updatePublishRecord(new Date());
	 }
	 
	 
}
  