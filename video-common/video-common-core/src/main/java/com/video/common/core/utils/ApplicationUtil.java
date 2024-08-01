package com.video.common.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.util.Objects;

public final class ApplicationUtil {
  private static final Logger log = LoggerFactory.getLogger(ApplicationUtil.class);
  
  private ApplicationUtil() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }
  
  public static void startPrintInformation(Environment env) {
    String protocol = "http";
    String ssl = "server.ssl.key-store";
    if (env.getProperty(ssl) != null) {
      protocol = "https";
    }
    String hostAddress = "localhost";
    try {
      hostAddress = InetAddress.getLocalHost().getHostAddress();
    } catch (Exception e) {
      log.warn("The host name could not be determined, using `localhost` as fallback");
    } 
    log.info("\n----------------------------------------------------------\n\tApplication '{}' is running! Access URLs:\n\tLocal: \t\t{}://localhost:{}\n\tExternal: \t{}://{}:{}\n\tDoc: \t{}://{}:{}{}/doc.html\n\tProfile(s): \t{}\n----------------------------------------------------------", new Object[] { 
          env.getProperty("spring.application.name"),protocol,
          env.getProperty("server.port"),protocol,hostAddress,
          env.getProperty("server.port"),protocol,hostAddress,
          env.getProperty("server.port"),
          Objects.nonNull(env.getProperty("server.servlet.context-path"))?env.getProperty("server.servlet.context-path"):"",
          env.getActiveProfiles() });
    String configServerStatus = env.getProperty("configserver.status");
    log.info("\n----------------------------------------------------------\n\tConfig Server: \t{}\n----------------------------------------------------------", (configServerStatus == null) ? "Not found or not setup for this application" : configServerStatus);
  }
}
