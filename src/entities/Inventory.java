package entities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Inventory {
    private final int capacity = 10;
    private final int itemCapacity = 10;
    private List<Crop> list = new ArrayList<>(capacity);
    private ObservableList<Crop> crops = FXCollections.observableList(list);
    private Map<String, Crop> cropNames = new HashMap();
    private List<Item> list2 = new ArrayList<>(itemCapacity);
    private ObservableList<Item> items = FXCollections.observableList(list2);
    private boolean fullCapacity = false;

    public boolean isAtCapacity() {
        return fullCapacity;
    }

    public boolean addCrop(Crop newCrop) {
        if (!fullCapacity) {
            crops.add(newCrop);
            cropNames.put(newCrop.getName(), newCrop);
            if (crops.size() == capacity) {
                fullCapacity = true;
            }
            return true;
        }
        return false;
    }

    public Crop removeCrop(String cropName) {
        if (cropNames.containsKey(cropName)) {
            crops.remove(cropNames.get(cropName));
            fullCapacity = false;
            if (!crops.contains(cropNames.get(cropName))) {
                return cropNames.remove(cropName).clone();
            }
            return cropNames.get(cropName).clone();
        }
        return null;
    }

    public boolean addItem(Item newItem) {
        if (items.size() < itemCapacity) {
            items.add(newItem);
            return true;
        }
        return false;
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    public ObservableList<Crop> getCrops() {
        return crops;
    }

    public int getCapacity() {
        return capacity;
    }

    public Map<String, Crop> getCropNames() {
        return cropNames;
    }

    public ObservableList<Item> getItems() {
        return items;
    }

    public void clear() {
        crops.clear();
        cropNames.clear();
    }
}
