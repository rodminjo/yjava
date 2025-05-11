package com.yjava.support;

public class MacLibSupporter implements NativeLibSupporter {

  private static final String LIB_NAME = "libyrs.dylib";

  @Override
  public boolean support() {
    return System.getProperty("os.name").toLowerCase().contains("mac");
  }

  @Override
  public String getLibPath() {
    return NativeLibSupporter.loadFromResources(LIB_NAME);
  }
}
