package com.senpure.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class IPGetter {
    private String ip;

    public String getIp() {
        return ip;
    }
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public void checkIP() {
        URL url;
        try {
            url = new URL("http://pv.sohu.com/cityjson?ie=utf-8");
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), StandardCharsets.UTF_8));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String result = sb.toString();
            int i = result.indexOf("\"cip\": \"");
            int j = result.indexOf("\"", i + 8);
            ip = result.substring(i + 8, j);
            logger.debug("本机IP is {}", ip);
        } catch (IOException e) {
          logger.error("",e);
        }

    }
}
