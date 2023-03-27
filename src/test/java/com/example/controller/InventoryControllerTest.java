package com.example.controller;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class InventoryControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    public void testInventory() {
        HttpRequest<String> request = HttpRequest.GET("/inventory");
        String body = client.toBlocking().retrieve(request);
        assertTrue(true);
        assertNotNull(body);
    }
}