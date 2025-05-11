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
    int originLen = origin != null ? origin.length() : 0;
    long txnPtr = NativeBridge.getInstance().ydocWriteTransaction(ptr, originLen, origin);
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

