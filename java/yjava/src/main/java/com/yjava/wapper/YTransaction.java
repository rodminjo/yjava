package com.yjava.wapper;

import com.yjava.bridge.NativeBridge;

public class YTransaction {

  private final long ptr;

  public YTransaction(long ptr) {
    this.ptr = ptr;
  }

  public byte[] getStateVector() {
    return NativeBridge.getInstance().ytransactionStateVectorV1(ptr);
  }

  public byte[] getStateDiff(byte[] stateVector) {
    return NativeBridge.getInstance().ytransactionStateDiffV1(ptr, stateVector, stateVector.length);
  }

  public byte applyUpdate(byte[] update) {
    return NativeBridge.getInstance().ytransactionApply(ptr, update, update.length);
  }

  public void commit() {
    NativeBridge.getInstance().ytransactionCommit(ptr);
  }
}
