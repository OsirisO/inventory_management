package com.example.models;

public class ItemRequest {
    private String name;
    private int quantity;
    private String supplier;
    private float unitCost;
    private String marketingDescription;

    // constructors
    public ItemRequest() {
    }

    public ItemRequest(String name, int quantity, String supplier, float unitCost, String marketingDescription) {
        this.name = name;
        this.quantity = quantity;
        this.supplier = supplier;
        this.unitCost = unitCost;
        this.marketingDescription = marketingDescription;
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
