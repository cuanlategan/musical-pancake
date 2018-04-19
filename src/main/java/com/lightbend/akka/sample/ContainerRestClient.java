package com.lightbend.akka.sample;

import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ContainerRestClient {
  static ContainerStatusResource getStatus(String uri) {
    Client client = ClientBuilder.newClient();

    return client
        .target(uri)
        .path("/")
        .request(MediaType.APPLICATION_JSON)
        .get(ContainerStatusResource.class);
  }
}
