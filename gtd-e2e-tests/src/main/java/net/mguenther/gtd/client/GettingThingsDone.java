package net.mguenther.gtd.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

import java.util.List;

public interface GettingThingsDone {

    @RequestLine("POST /items")
    @Headers("Content-Type: application/json")
    Response createItem(CreateItem payload);

    @RequestLine("PUT /items/{itemId}")
    @Headers("Content-Type: application/json")
    Response updateItem(@Param("itemId") String itemId, UpdateItem payload);

    @RequestLine("GET /items/{itemId}")
    @Headers("Accept: application/json")
    Item getItem(@Param("itemId") String itemId);

    @RequestLine("GET /items")
    @Headers("Accept: application/json")
    List<Item> getItems();
}
