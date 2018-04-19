package com.lightbend.akka.sample;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.lightbend.akka.sample.Greeter.*;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class AkkaQuickstart {
  public static void main(String[] args) throws IOException {

    System.out.println(ContainerRestClient.getStatus("http://localhost:8080").getStatus());

    System.out.println(">>> Press ENTER to exit <<<");
    System.in.read();

  }
}
