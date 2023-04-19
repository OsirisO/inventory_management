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
    final int MIN_STOCK = 5;

    @Inject
    public InventoryController(InventoryFacade inventoryFacade) {
        this.inventoryFacade = inventoryFacade;
    }

    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Item> getItem(@Header String authorization, @PathVariable String id) {
        if (authorization == null || !authorization.startsWith("Basic adminInventory")) {
            return HttpResponse.status(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        try {
            Item item = inventoryFacade.getItem(id);
            if (item == null) {
                return HttpResponse.status(HttpStatus.NOT_FOUND, "Item not found");
            }
            return HttpResponse.ok(item);
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<ArrayList<Item>> getItems(@Header String authorization) {
        if (authorization == null || !authorization.startsWith("Basic adminInventory")) {
            return HttpResponse.status(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

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
    public HttpResponse<String> getItemsXML(@Header String authorization) {
        if (authorization == null || !authorization.startsWith("Basic adminInventory")) {
            return HttpResponse.status(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        try {
            List<Item> items = inventoryFacade.getItems();
            String xml = toXml(items);
            return HttpResponse.ok(xml).contentType(MediaType.APPLICATION_XML);
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private String toXml(List<Item> items) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.writeValueAsString(items);
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public HttpResponse<Item> addItem(@Header String authorization, @Body ItemRequest req) {
        if (authorization == null || !authorization.startsWith("Basic adminInventory")) {
            return HttpResponse.status(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        // check quantity is not less than the minimum reorder point
        if (req.getQuantity() <= MIN_STOCK) {
            return HttpResponse.status(HttpStatus.BAD_REQUEST, "Quantity must be greater than 5");
        }

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
    public HttpResponse<Item> updateItem(@Header String authorization, @Body Item item) {
        if (authorization == null || !authorization.startsWith("Basic adminInventory")) {
            return HttpResponse.status(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

       try {
           boolean updated = inventoryFacade.updateItem(item);
           // if item quantity is negative, return bad request
              if (item.getQuantity() < 0) {
                return HttpResponse.status(HttpStatus.BAD_REQUEST, "Quantity must be greater than 0");
              }

           if (updated) {
               return HttpResponse.ok(item);
           } else {
               return HttpResponse.status(HttpStatus.NOT_FOUND, "Item not found");
           }
       } catch (Exception e) {
           e.printStackTrace();
           return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
       }
    }

    @Delete("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public HttpResponse<String> deleteItem(@Header String authorization, @PathVariable String id) {
        if (authorization == null || !authorization.startsWith("Basic adminInventory")) {
            return HttpResponse.status(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        try {
            boolean deleted = inventoryFacade.deleteItem(id);
            if (deleted) {
                return HttpResponse.ok("Item deleted");
            } else {
                return HttpResponse.status(HttpStatus.NOT_FOUND, "Item not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
