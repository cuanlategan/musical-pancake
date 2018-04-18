package com.lightbend.akka.sample;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.lightbend.akka.sample.Printer.Greeting;

import java.util.ArrayList;
import java.util.List;

public class Greeter extends AbstractActor {
  static public Props props() {
    return Props.create(Greeter.class, () -> new Greeter());
  }

  static public class CreateChild {
    public final String name;

    public CreateChild(String name) {
        this.name = name;
    }
  }

  static public class GetChildren {
    public GetChildren() {}
  }

  static public class Children {
    final public List<ActorRef> children;

    public Children(List<ActorRef> children) {
      this.children = children;
    }
  }


  @Override
  public Receive createReceive() {
    return receiveBuilder()
        .match(CreateChild.class, m -> {
          ActorRef printerChild = this.getContext().actorOf(Printer.props(), m.name);
          printerChild.tell(new Greeting("hello "+m.name), self());
        })
        .match(GetChildren.class, x -> {
          List<ActorRef> children = new ArrayList<>();
          this.getContext().getChildren().forEach(children::add);
          sender().tell(new Children(children), getSelf());
        })
        .build();
  }
}
