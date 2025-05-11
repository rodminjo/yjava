package com.yjava.support;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public interface NativeLibSupporter {

  boolean support();
  String getLibPath();

  static String loadFromResources(String libName) {
    try {
      URL resource = NativeLibSupporter.class.getClassLoader().getResource("native" + File.separator + libName);
      if (resource == null) {
        throw new FileNotFoundException("Native library not found in classpath: " + libName);
      }

      // 개발 중: 실제 파일 시스템 경로인 경우 그대로 사용
      if ("file".equals(resource.getProtocol())) {
        return Paths.get(resource.toURI()).toAbsolutePath().toString();
      }

      // JAR 내부: 리소스를 temp 디렉토리에 복사
      try (InputStream in = resource.openStream()) {
        Path tempFile = Files.createTempFile("native_", "_" + libName);
        tempFile.toFile().deleteOnExit();
        Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
        return tempFile.toAbsolutePath().toString();
      }

    } catch (Exception e) {
      throw new RuntimeException("Failed to load native library: " + libName, e);
    }
  }
}
