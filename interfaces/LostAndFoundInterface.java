package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import model.Item;

public interface LostAndFoundInterface extends Remote {
    String reportLost(Item item) throws RemoteException;

    String reportFound(Item item) throws RemoteException;

    List<Item> searchItem(String itemName) throws RemoteException;
}
