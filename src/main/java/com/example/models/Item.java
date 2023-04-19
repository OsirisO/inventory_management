package com.example.models;

public class Item {
    private String id;
    private String name;
    private int quantity;
    private String supplier;
    private float unitCost;
    private String marketingDescription;


    // constructors
    public Item(String id, String name, int quantity, String supplier, float unitCost, String marketingDescription) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.supplier = supplier;
        this.unitCost = unitCost;
        this.marketingDescription = marketingDescription;
    }

    public Item() {
    }

    // getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public float getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(float unitCost) {
        this.unitCost = unitCost;
    }

    public String getMarketingDescription() {
        return marketingDescription;
    }

    public void setMarketingDescription(String marketingDescription) {
        this.marketingDescription = marketingDescription;
    }
}
