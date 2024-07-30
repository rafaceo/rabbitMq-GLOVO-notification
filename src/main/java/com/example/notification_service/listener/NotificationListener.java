package com.example.notification_service.listener;

import com.example.notification_service.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.Exchange;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationListener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "almatyOrder"),
            exchange = @Exchange(value = "${mq.order.topic.exchange}", type = ExchangeTypes.TOPIC),
            key = "order.#"))
    public void AlmatyOrders(OrderDto orderDto){
        log.info("Received order - ORDER:{}", orderDto);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "notificationsCostumer"),
            exchange = @Exchange(value = "${mq.order.fanout.exchange}", type = ExchangeTypes.FANOUT),
            key = ""
    ))
    public void CostumerOrderNotification(OrderDto orderDto){
        log.info("Customer notification - ORDER:{} status updated: {}", orderDto, orderDto.getStatus());
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("notificationsCourier"),
            exchange = @Exchange(value = "${mq.order.fanout.exchange}", type = ExchangeTypes.FANOUT),
            key = ""
    ))
    public void CourierNotification(OrderDto orderDto){
        log.info("Courier notification - ORDER:{} status updated: {}", orderDto, orderDto.getStatus());
    }

}
