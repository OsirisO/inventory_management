package com.example.facade;

import com.example.models.Item;
import com.example.repository.InventoryRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.UUID;

@Singleton
public class InventoryFacade {
    private final InventoryRepository inventoryRepository;


    @Inject
    public InventoryFacade(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Item addItem(String name, int quantity, String supplier, float unitCost, String marketingDescription) {
        // Build item
        String id = UUID.randomUUID().toString();
        Item item = new Item(id, name, quantity, supplier, unitCost, marketingDescription);

        // Send item to database so it's added
        boolean itemAdded = inventoryRepository.addItem(item);
        if (!itemAdded) {
            return null;
        }

        return item;
    }

    public Item getItem(String id) {
        return inventoryRepository.getItem(id);
    }

    public ArrayList<Item> getItems() {
        return inventoryRepository.getItems();
    }

    public boolean updateItem(Item item) {
        return inventoryRepository.updateItem(item);
    }

    public boolean deleteItem(String id) {
        return inventoryRepository.deleteItem(id);
    }
}
