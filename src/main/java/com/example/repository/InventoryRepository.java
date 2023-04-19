package com.example.repository;

import com.example.models.Item;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Singleton
public class InventoryRepository {
    private final Connection conn;

    @Inject
    public InventoryRepository(DBConnect connection) {
        this.conn = connection.getConnection();
    }

    /**
     * Insert a row into the inventory table
     *
     * @param item Item to insert
     */
    public boolean addItem(Item item) {
        String sql = "INSERT INTO inventory(id, name, quantity, supplier, unitCost, marketingDescription)" +
                " VALUES(?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, item.getId());
            statement.setString(2, item.getName());
            statement.setInt(3, item.getQuantity());
            statement.setString(4, item.getSupplier());
            statement.setFloat(5, item.getUnitCost());
            statement.setString(6, item.getMarketingDescription());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Get a row from the inventory table
     *
     * @param id item Id
     */
    public Item getItem(String id) {
        String sql = "SELECT id, name, quantity, supplier, unitCost, marketingDescription" +
                " FROM inventory WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, id);
            ResultSet result = statement.executeQuery();

            Item item = null;
            while (result.next()) {
                item = new Item(
                        result.getString("id"),
                        result.getString("name"),
                        result.getInt("quantity"),
                        result.getString("supplier"),
                        result.getFloat("unitCost"),
                        result.getString("marketingDescription")
                );
            }

            statement.close();
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Item> getItems() {
        String sql = "SELECT id, name, quantity, supplier, unitCost, marketingDescription" +
                " FROM inventory";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();

            ArrayList<Item> items = new ArrayList<>();
            while (result.next()) {
                Item item = new Item(
                        result.getString("id"),
                        result.getString("name"),
                        result.getInt("quantity"),
                        result.getString("supplier"),
                        result.getFloat("unitCost"),
                        result.getString("marketingDescription")
                );
                items.add(item);
            }
            statement.close();
            return items;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateItem(Item item) {
        String sql = "UPDATE inventory SET name = ?, quantity = ?, supplier = ?, unitCost = ?, marketingDescription = ?" +
                " WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, item.getName());
            statement.setInt(2, item.getQuantity());
            statement.setString(3, item.getSupplier());
            statement.setFloat(4, item.getUnitCost());
            statement.setString(5, item.getMarketingDescription());
            statement.setString(6, item.getId());
            int result = statement.executeUpdate();

            if (result == 0) {
                // Item not found
                return false;
            }

            statement.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteItem(String id) {
        String sql = "DELETE FROM inventory WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, id);
            int result = statement.executeUpdate();

            if (result == 0) {
                // Item not found
                return false;
            }

            statement.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
