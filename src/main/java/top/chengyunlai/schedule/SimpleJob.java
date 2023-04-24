package top.chengyunlai.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * @ClassName
 * @Description 自定义定时任务，某个机制下触发，可以是使用controller
 * @Author:chengyunlai
 * @Date
 * @Version 1.0
 **/
public class SimpleJob implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        System.out.println("自定义任务");
    }
}