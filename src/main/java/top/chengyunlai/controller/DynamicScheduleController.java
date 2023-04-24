package top.chengyunlai.controller;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.chengyunlai.schedule.SimpleJob;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @ClassName
 * @Description
 * @Author:chengyunlai
 * @Date
 * @Version 1.0
 **/
@RestController
public class DynamicScheduleController {

    @Autowired
    private Scheduler scheduler;

    @GetMapping("/addSchedule")
    public String addSchedule() throws SchedulerException {
        int random = ThreadLocalRandom.current().nextInt(1000);
        // 1. 创建JobDetail，指定定时任务实现类的类型
        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("test-schedule" + random, "test-group").build();
        // 2. 创建Trigger，并指定每3秒执行一次
        CronScheduleBuilder cron = CronScheduleBuilder.cronSchedule("0/3 * * * * ?");

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("test-trigger" + random, "test-trigger-group")
                .withSchedule(cron).build();

        // 3. 调度任务
        scheduler.scheduleJob(jobDetail, trigger);
        return "success";
    }

    // 暂停
    @GetMapping("/pauseSchedule")
    public String pauseSchedule(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        // 获取定时任务
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return "error";
        }
        scheduler.pauseJob(jobKey);
        return "success";
    }

    // 恢复
    @GetMapping("/remuseSchedule")
    public String remuseSchedule(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        // 获取定时任务
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return "error";
        }
        scheduler.resumeJob(jobKey);
        return "success";
    }

    // 删除定时任务
    @GetMapping("/removeSchedule")
    public String removeSchedule(String jobName, String jobGroup, String triggerName, String triggerGroup) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

        Trigger trigger = scheduler.getTrigger(triggerKey);
        if (trigger == null) {
            return "error";
        }
        // 停止触发器
        scheduler.pauseTrigger(triggerKey);
        // 移除触发器
        scheduler.unscheduleJob(triggerKey);
        // 删除任务
        scheduler.deleteJob(jobKey);
        return "success";
    }
}
