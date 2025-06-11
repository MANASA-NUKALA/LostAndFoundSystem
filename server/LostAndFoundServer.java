package server;

import interfaces.LostAndFoundInterface;
import model.Item;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class LostAndFoundServer implements LostAndFoundInterface {

    public String reportLost(Item item) {
        String sql = "INSERT INTO lost_items (lost_by_name, phone, item_name, place, date, description) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getPhone());
            stmt.setString(3, item.getItemName());
            stmt.setString(4, item.getPlace());
            stmt.setDate(5, new java.sql.Date(item.getDate().getTime())); // FIXED: use java.sql.Date
            stmt.setString(6, item.getDescription());
            stmt.executeUpdate();
            return "✅ Lost item reported by " + item.getName();
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Error reporting lost item: " + e.getMessage();
        }
    }

    public String reportFound(Item item) {
        String sql = "INSERT INTO found_items (found_by_name, phone, item_name, place, date, description) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getPhone());
            stmt.setString(3, item.getItemName());
            stmt.setString(4, item.getPlace());
            stmt.setDate(5, new java.sql.Date(item.getDate().getTime())); // FIXED: use java.sql.Date
            stmt.setString(6, item.getDescription());
            stmt.executeUpdate();
            return "✅ Found item reported by " + item.getName();
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Error reporting found item: " + e.getMessage();
        }
    }

    public List<Item> searchItem(String itemName) {
        List<Item> list = new ArrayList<>();
        String sql = "SELECT found_by_name, phone, item_name, place, date, description FROM found_items WHERE item_name LIKE ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + itemName + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Item i = new Item(
                        rs.getString("found_by_name"),
                        rs.getString("phone"),
                        rs.getString("item_name"),
                        rs.getString("place"),
                        rs.getDate("date"), // FIXED: use getDate
                        rs.getString("description"));
                list.add(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        try {
            LostAndFoundServer obj = new LostAndFoundServer();
            LostAndFoundInterface stub = (LostAndFoundInterface) UnicastRemoteObject.exportObject(obj, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("LostAndFoundService", stub);
            System.out.println("Server ready");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
