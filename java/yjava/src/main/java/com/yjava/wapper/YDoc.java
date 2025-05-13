package com.yjava.wapper;

import com.yjava.bridge.NativeBridge;

public class YDoc {

  private final long ptr;

  public YDoc() {
    this.ptr = NativeBridge.getInstance().ydocNew();
  }

  public YTransaction getWriteTransaction() {
    return this.getWriteTransaction(null);
  }

  public YTransaction getWriteTransaction(String origin) {
    long txnPtr = NativeBridge.getInstance().ydocWriteTransaction(ptr, origin);
    return txnPtr != 0L ? new YTransaction(txnPtr) : null;
  }

  public YTransaction getReadTransaction() {
    long txnPtr = NativeBridge.getInstance().ydocReadTransaction(ptr);
    return txnPtr != 0L ? new YTransaction(txnPtr) : null;
  }

  public void destroy() {
    NativeBridge.getInstance().ydocDestroy(ptr);
  }

}

