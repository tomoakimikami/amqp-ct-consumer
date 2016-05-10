package com.example.service.impl;

import com.example.dto.Sample;
import com.example.repository.RabbitMqReservationRepository;
import com.example.repository.entity.RabbitMqReservation;
import com.example.service.ConsumerService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Map;
import java.util.Optional;

import spring.support.amqp.rabbit.ExactlyOnceDeliveryAdvice.ExactlyOnceDelivery;
import spring.support.amqp.rabbit.ExactlyOnceDeliveryProducer;

/**
 * メッセージ受信サービス実装.
 *
 * @author Tomoaki Mikami
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {
  /**
   * 予約リポジトリ
   */
  @Autowired
  private RabbitMqReservationRepository reservationRepository;

  /**
   * {@inheritDoc}.
   */
  @Transactional
  @ExactlyOnceDelivery
  @RabbitListener(queues = "default.queue", containerFactory = "requeueRejectContainerFactory")
  @Override
  public void receive(Map<String, String> headers, Sample data) {
    // 受信したメッセージに応じて予約登録する.
    RabbitMqReservation reservation = new RabbitMqReservation();
    Optional<String> mutexOptional = Optional.of(headers.get(ExactlyOnceDeliveryProducer.MUTEX));
    String mutex = mutexOptional.orElseThrow(
        () -> new IllegalArgumentException(ExactlyOnceDeliveryProducer.MUTEX + " is required"));
    reservation.setMutex(Long.valueOf(mutex));
    reservation.setName(data.getName());
    reservation.setReservedAt(Calendar.getInstance().getTime());
    reservationRepository.save(reservation);
  }
}
