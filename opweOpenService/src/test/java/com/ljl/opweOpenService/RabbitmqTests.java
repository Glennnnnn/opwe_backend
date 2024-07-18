package com.ljl.opweOpenService;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author Liu Jialin
 * @Date 2024/5/28 20:36
 * @PackageName com.ljl.opweProcess
 * @ClassName RabbitmqTests
 * @Description TODO
 * @Version 1.0.0
 */
@SpringBootTest(classes = OpweOpenServiceApplication.class)
public class RabbitmqTests {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void simpleRabbitQueue(){
        String queueName = "dailyBlogQueue";
        String message = "this is a test msg";

        rabbitTemplate.convertAndSend(queueName, message);
    }
}
