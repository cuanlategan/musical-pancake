package com.kwan.main;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

public class ContainerRestClient {
  static ContainerStatusRepresentation getStatus(String uri) {
    Client client = ClientBuilder.newClient();

    return client
        .target(uri)
        .path("/")
        .request(MediaType.APPLICATION_JSON)
        .get(ContainerStatusRepresentation.class);
  }
}
