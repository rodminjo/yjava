# YJava: JNI Bindings for Y-CRDT

This project integrates [y-crdt](https://github.com/y-crdt/y-crdt) into a Java environment using FFI and JNI. The following native methods are bound and made accessible from Java:

```java
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
```

## Features

- ✅ JNI bindings for core `y-crdt` operations via FFI  
- ✅ `YProtocol` implementation to handle sync/update logic between clients  
- 🔄 This project is under active development and will continue to evolve