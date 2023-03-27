package com.example.controller;

import com.example.facade.InventoryFacade;
import com.example.models.Item;
import com.example.models.ItemRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the inventory with path "/inventory"
 *
 * @author Osiris O.
 */
@Singleton
@Controller("/inventory")
public class InventoryController {
    private final InventoryFacade inventoryFacade;

    @Inject
    public InventoryController(InventoryFacade inventoryFacade) {
        this.inventoryFacade = inventoryFacade;
    }

    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Item> getItem(@PathVariable String id) {
        try {
            Item item = inventoryFacade.getItem(id);
            return HttpResponse.ok(item);
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<ArrayList<Item>> getItems() {
        try {
            ArrayList<Item> items = inventoryFacade.getItems();
            return HttpResponse.ok(items);
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Get("/export")
    @Produces(MediaType.APPLICATION_XML)
    public HttpResponse<String> getItemsXML() {
        try {
            List<Item> items = inventoryFacade.getItems();
            String xml = toXml(items);
            return HttpResponse.ok(xml).contentType(MediaType.APPLICATION_XML);
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public String toXml(List<Item> items) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.writeValueAsString(items);
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public HttpResponse<Item> addItem(@Body ItemRequest req) {
        try {
            Item item = inventoryFacade.addItem(req.getName(), req.getQuantity(), req.getSupplier(), req.getUnitCost(), req.getMarketingDescription());
            return HttpResponse.ok(item);
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Put()
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public HttpResponse<Item> updateItem(@Body Item item) {
       try {
           boolean updated = inventoryFacade.updateItem(item);
           if (updated) {
               return HttpResponse.ok(item);
           } else {
               return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update item");
           }
       } catch (Exception e) {
           e.printStackTrace();
           return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
       }
    }

    @Delete("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public HttpResponse<String> deleteItem(@PathVariable String id) {
        try {
            boolean deleted = inventoryFacade.deleteItem(id);
            if (deleted) {
                return HttpResponse.ok("Item deleted");
            } else {
                return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete item");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
