package com.yjava.wapper;

import com.yjava.bridge.NativeBridge;

public class YMap {
  private final long mapPtr;

  public YMap(long docPtr, String name) {
    this.mapPtr = NativeBridge.getInstance().ymap(docPtr, name);
    if (mapPtr == 0) {
      throw new IllegalStateException("Failed to create YMap");
    }
  }

  public String getJson(long txnPtr, String key) {
    return NativeBridge.getInstance().ymapGetJson(mapPtr, txnPtr, key);
  }
}

