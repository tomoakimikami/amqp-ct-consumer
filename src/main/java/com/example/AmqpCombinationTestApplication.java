package com.example;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication(scanBasePackages = {"com.example", "spring.support.amqp.rabbit"})
@EnableBatchProcessing
@Slf4j
public class AmqpCombinationTestApplication implements CommandLineRunner {
  private static CountDownLatch latch = new CountDownLatch(1);

  /**
   * エントリーポイント.
   *
   * @param args 引数
   */
  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(AmqpCombinationTestApplication.class);
    application.setWebEnvironment(false);
    ApplicationContext context = application.run(args);
    SpringApplication.exit(context);
  }

  /**
   * {@inheritDoc}.
   * 
   * @throws InterruptedException
   */
  @Override
  public void run(String... args) throws InterruptedException {
    log.info("Start!");
    latch.await(10000, TimeUnit.MILLISECONDS);
    log.info("Done!");
  }
}
