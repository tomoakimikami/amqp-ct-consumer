package com.example.service;

import com.example.dto.Sample;

import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

/**
 * メッセージ受信サービスインタフェース.
 *
 * @author Tomoaki Mikami
 */
public interface ConsumerService {
  /**
   * メッセージ受信.
   * HeadersおよびPayloadアノテーションは実装ではなくインタフェース側に必要
   *
   * @param headers ヘッダ
   * @param dto 受信DTO
   */
  void receive(@Headers Map<String, String> headers, @Payload Sample data);
}
