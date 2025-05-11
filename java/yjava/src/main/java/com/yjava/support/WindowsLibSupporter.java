package com.yjava.support;

public class WindowsLibSupporter implements NativeLibSupporter {

  private static final String LIB_NAME = "yrs.dll";

  @Override
  public boolean support() {
    return System.getProperty("os.name").toLowerCase().contains("win");
  }

  @Override
  public String getLibPath() {
    return NativeLibSupporter.loadFromResources(LIB_NAME);
  }
}

