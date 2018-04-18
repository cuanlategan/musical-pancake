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
  public static void main(String[] args) {
    final ActorSystem system = ActorSystem.create("helloakka");
    Children children = null;
    try {
      /*final ActorRef printerActor =
        system.actorOf(Printer.props(), "printerActor");

      final ActorRef howdyGreeter =
        system.actorOf(Greeter.props("Howdy", printerActor), "howdyGreeter");
      final ActorRef helloGreeter = 
        system.actorOf(Greeter.props("Hello", printerActor), "helloGreeter");
      final ActorRef goodDayGreeter = 
        system.actorOf(Greeter.props("Good day", printerActor), "goodDayGreeter");

      howdyGreeter.tell(new WhoToGreet("Akka"), ActorRef.noSender());
      howdyGreeter.tell(new Greet(), ActorRef.noSender());

      howdyGreeter.tell(new WhoToGreet("Lightbend"), ActorRef.noSender());
      howdyGreeter.tell(new Greet(), ActorRef.noSender());

      helloGreeter.tell(new WhoToGreet("Java"), ActorRef.noSender());
      helloGreeter.tell(new Greet(), ActorRef.noSender());

      goodDayGreeter.tell(new WhoToGreet("Play"), ActorRef.noSender());
      goodDayGreeter.tell(new Greet(), ActorRef.noSender());
*/

      List<Integer> generator = new ArrayList<>();
      final ActorRef greeterActor = system.actorOf(Greeter.props(), "greeter");
      for(int i = 0; i < 1_000_000; i++) {
        generator.add(i);
      }

      generator.parallelStream()
               .forEach(i -> {
                 greeterActor.tell(new Greeter.CreateChild("child"+i), ActorRef.noSender());
                 if(i %21 == 0) {
                   Timeout timeout = new Timeout(5000, TimeUnit.MILLISECONDS);
                   Future<Object> f =  Patterns.ask(greeterActor, new Greeter.GetChildren(), timeout);
                   Children children1;
                   try {
                     children1 =  (Children) Await.result(f, Duration.create(5000L, TimeUnit.MILLISECONDS));
                   } catch (Exception e) {
                     System.out.println("time out!!!!!!!!!!!!!!!!");
                     e.printStackTrace();
                     throw new RuntimeException();
                   }
                 }
               });

      Timeout timeout = new Timeout(5000, TimeUnit.MILLISECONDS);
      Future<Object> f =  Patterns.ask(greeterActor, new Greeter.GetChildren(), timeout);

      try {
        children =  (Children) Await.result(f, Duration.create(5000L, TimeUnit.MILLISECONDS));
      } catch (Exception e) {
        System.out.println("time out!!!!!!!!!!!!!!!!");
        e.printStackTrace();
      }

      System.out.println("ressult size = " + children.children.size());


      System.out.println(">>> Press ENTER to exit <<<");
      System.in.read();
    } catch (IOException ioe) {
    } finally {
      System.out.println("ressult size = " + children.children.size());
      system.terminate();
    }
  }
}
