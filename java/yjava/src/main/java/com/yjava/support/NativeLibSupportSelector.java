package com.yjava.support;

import java.util.List;

public class NativeLibSupportSelector {
  private static final List<NativeLibSupporter> SUPPORTERS = List.of(
      new MacLibSupporter(),
      new WindowsLibSupporter(),
      new LinuxLibSupporter()
  );

  public static String support() {
    return SUPPORTERS.stream()
        .filter(NativeLibSupporter::support)
        .findFirst()
        .orElseThrow(() -> new UnsupportedOperationException("Unsupported OS"))
        .getLibPath();
  }
}
