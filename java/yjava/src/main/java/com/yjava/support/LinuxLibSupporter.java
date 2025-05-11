package com.yjava.support;

public class LinuxLibSupporter implements NativeLibSupporter {

  private static final String LIB_NAME = "libyrs.so";

  @Override
  public boolean support() {
    String os = System.getProperty("os.name").toLowerCase();
    return os.contains("nux") || os.contains("nix");
  }

  @Override
  public String getLibPath() {
    return NativeLibSupporter.loadFromResources(LIB_NAME);
  }
}

