package com.ljl.opweSettlementService.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Liu Jialin
 * @Date 2024/6/5 22:58
 * @PackageName com.ljl.opweSettlementService.config
 * @ClassName mqConfig
 * @Description TODO
 * @Version 1.0.0
 */
@Configuration
public class mqConfig {
    /**
     * declare an exchange
     * @return Direct exchange
     */
    @Bean
    public DirectExchange settlementExchange(){
        return ExchangeBuilder.directExchange("opwe.settlement.direct").build();
    }

    /**
     * declare a delayed exchange
     * @return
     */
    @Bean
    public DirectExchange delayExchange(){
        return ExchangeBuilder
                .directExchange("opwe.settlement.orderStatusCheck") // 指定交换机类型和名称
                .delayed() // 设置delay的属性为true
                .durable(true) // 持久化
                .build();
    }

    /**
     * order queue one
     */
    @Bean
    public Queue orderQueue1(){
        return new Queue("opwe.settlement.orderQueue1");
    }

    /**
     * order queue two
     */
    @Bean
    public Queue orderQueue2(){
        return new Queue("opwe.settlement.orderQueue2");
    }

    /**
     * orderStatusCheckQueue1
     */
    @Bean
    public Queue orderStatusCheckQueue1(){
        return new Queue("opwe.settlement.orderStatusCheckQueue1");
    }

    /**
     * binding exchange and queue
     */
    @Bean
    public Binding bindingQueue1WithConfirm(){
        return BindingBuilder.bind(orderQueue1()).to(settlementExchange()).with("orderConfirm");
    }

    /**
     * binding exchange and queue
     */
    @Bean
    public Binding bindingQueue2WithConfirm(){
        return BindingBuilder.bind(orderQueue2()).to(settlementExchange()).with("orderConfirm");
    }

    /**
     * binding exchange and queue
     */
    @Bean
    public Binding bindingOrderStatusCheckQueue1(){
        return BindingBuilder.bind(orderStatusCheckQueue1()).to(delayExchange()).with("orderStatusCheck");
    }


    @Bean
    public MessageConverter messageConverter(){
        // 1.定义消息转换器
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        // 2.配置自动创建消息id，用于识别不同消息，也可以在业务中基于ID判断是否是重复消息
        jackson2JsonMessageConverter.setCreateMessageIds(true);
        return jackson2JsonMessageConverter;
    }
}
