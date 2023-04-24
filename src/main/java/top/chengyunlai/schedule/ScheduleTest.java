package top.chengyunlai.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @ClassName
 * @Description
 * @Author:chengyunlai
 * @Date
 * @Version 1.0
 **/
@Service
public class ScheduleTest {
    @Scheduled(cron = "0/1 * * * * *")
    public void test() {
        System.out.println("测试");
    }
}