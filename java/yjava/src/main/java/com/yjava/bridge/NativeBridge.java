package com.yjava.bridge;

import com.yjava.support.NativeLibSupportSelector;

public class NativeBridge {

  private static final NativeBridge singleTon;

  static {
    singleTon = new NativeBridge();
    System.load(NativeLibSupportSelector.support());
  }

  private NativeBridge() {}

  public static NativeBridge getInstance() {
    return singleTon;
  }

  public native long ydocNew();
  public native long ydocWriteTransaction(long doc_ptr, int origin_len, String origin);
  public native long ydocReadTransaction(long doc_ptr);
  public native void ytransactionCommit(long txn_ptr);
  public native byte[] ytransactionStateVectorV1(long txn_ptr);
  public native byte[] ytransactionStateDiffV1(long txn_ptr, byte[] diff_arr, int diff_arr_len);
  public native byte ytransactionApply(long txn_ptr, byte[] diff_arr, int diff_len);
  public native void ydocDestroy(long doc_ptr);
  public native long ymap(long doc_ptr, String name_str);
  public native String ymapGetJson(long map_ptr, long txn_ptr, String key_str);
//  public native void ymapInsert(long map_ptr, long txn_ptr, String key_str, byte[] input_arr);
//  public native String ymapGet(long map_ptr, long txn_ptr, String key_str);
//  public native byte ymapRemove(long map_ptr, long txn_ptr, String key_str);
//  public native byte[] yinputString(String str);

}