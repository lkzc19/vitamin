package com.example.scheduler

import org.quartz.Scheduler
import org.quartz.impl.StdSchedulerFactory

class JobSchedulerManager() {
  
  private val scheduler: Scheduler = StdSchedulerFactory.getDefaultScheduler()
  
  fun startScheduler() {
    scheduler.start()
  }
}