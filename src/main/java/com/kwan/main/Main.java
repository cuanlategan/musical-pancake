package com.kwan.main;

import java.io.IOException;


public class Main {
  public static void main(String[] args) throws IOException {

    System.out.println(ContainerRestClient.getStatus("http://localhost:8080").getStatus());

    System.out.println(">>> Press ENTER to exit <<<");
    int ignored = System.in.read();

  }
}
