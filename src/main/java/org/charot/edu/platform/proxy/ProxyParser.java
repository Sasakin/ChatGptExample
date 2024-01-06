package org.charot.edu.platform.proxy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProxyParser {
    public static void main(String[] args) {
        String fileName = "proxies.txt";
        String currentDirectory = System.getProperty("user.dir") + "/proxybrocker/data/" + fileName;
        List<String> proxies = parseProxies(currentDirectory);
        for (String proxy : proxies) {
            String[] proxyInfo = proxy.split(" ")[4].split(":");
            String host = proxyInfo[0];
            String port = proxyInfo[1];
            System.out.println("Host: " + host + ", Port: " + port);
        }
    }

    public static List<String> parseProxies() {
        String fileName = "proxies.txt";
        String currentDirectory = System.getProperty("user.dir") + "/proxybrocker/data/" + fileName;
        return parseProxies(currentDirectory);
    }

    private static List<String> parseProxies(String fileName) {
        List<String> proxies = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                proxies.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return proxies;
    }
}
