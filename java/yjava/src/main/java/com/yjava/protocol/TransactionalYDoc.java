package com.yjava.protocol;

import com.yjava.wapper.YDoc;
import com.yjava.wapper.YTransaction;
import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;

public class TransactionalYDoc {

  private final YDoc yDoc;
  private LocalDateTime lastEditTime;
  private final ReentrantLock transactionLock = new ReentrantLock();

  public TransactionalYDoc() {
    this.yDoc = new YDoc();
    this.lastEditTime = LocalDateTime.now();
  }

  public boolean isTimeExceeded(long minutes) {
    if (lastEditTime == null) {
      return true;
    }
    return lastEditTime.plusMinutes(minutes).isBefore(LocalDateTime.now());
  }

  public boolean applyUpdate(byte[] diff) {
    transactionLock.lock(); // 트랜잭션 잠금
    try {
      YTransaction yTransaction = yDoc.getWriteTransaction();
      byte result = yTransaction.applyUpdate(diff);
      yTransaction.commit();

      boolean isSuccess = result == 0;
      if (isSuccess) {
        updateLastEditTime();
      }

      return isSuccess;
    } finally {
      transactionLock.unlock(); // 항상 잠금 해제
    }
  }

  public byte[] encodeStateAsUpdate() {
    return encodeStateAsUpdate(new byte[1]);
  }

  public byte[] encodeStateAsUpdate(byte[] update) {
    transactionLock.lock();
    try {
      YTransaction yTransaction = yDoc.getReadTransaction();
      byte[] diff = yTransaction.getStateDiff(update);
      yTransaction.commit();
      return diff;
    } finally {
      transactionLock.unlock();
    }
  }

  public byte[] encodeStateVector() {
    transactionLock.lock();
    try {
      YTransaction yTransaction = yDoc.getReadTransaction();
      byte[] vector = yTransaction.getStateVector();
      yTransaction.commit();
      return vector;
    } finally {
      transactionLock.unlock();
    }
  }


  public void destroy(){
    transactionLock.lock();
    try {
      yDoc.destroy();

    } finally {
      transactionLock.unlock();
    }
  }

  private void updateLastEditTime(){
    this.lastEditTime = LocalDateTime.now();
  }
}
