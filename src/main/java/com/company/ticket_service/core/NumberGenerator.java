package com.company.ticket_service.core;

import lombok.experimental.UtilityClass;

import java.util.concurrent.atomic.AtomicLong;

@UtilityClass
public class NumberGenerator {
  private static final ThreadLocal<AtomicLong> SEQUENCE =
      ThreadLocal.withInitial(() -> new AtomicLong(0));

  public static Long generateUniqueNumber() {
    long timestamp = System.currentTimeMillis();
    long threadId = Thread.currentThread().threadId();
    long seq = SEQUENCE.get().getAndIncrement();

    return timestamp + threadId + seq;
  }
}