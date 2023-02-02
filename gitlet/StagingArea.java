package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.*;

import java.io.Serializable;
import java.util.HashMap;

public class StagingArea implements Serializable {
    TreeMap<String, String> stage;
    ArrayList<String> listToRemove;
    byte[] contentsBytes;
    String contentsSerial;
    public StagingArea() {
        stage = new TreeMap<>();
        listToRemove = new ArrayList<>();
    }

    public void add(File file) {
        String fileName = file.getName();
        if (!file.exists()) {
            System.out.println("File does not exist.");
            return;
        }
        contentsBytes = Utils.readContents(file);
        contentsSerial = Utils.sha1(contentsBytes);
        stage.put(fileName, contentsSerial);
        /**if (serialize == null) {
            serialize = new HashMap<>();
            serialize.put(fileName, serialized);
        }
        else {
            if (serialize.containsValue(serialized)) {
                serialize.remove(fileName);
            }
            else {
                serialize.put(fileName, serialized);
            }
        }*/
    }
    public void clear() {
        stage.clear();
        listToRemove.clear();
    }
    public void remove(File file) {
        stage.remove(file.getName());
    }
    public ArrayList<String> getListToRemove() {
        return listToRemove;
    }
    public TreeMap<String, String> getAdded() {
        if (stage == null) {
            stage = new TreeMap<>();
        }
        return stage;
    }
    public String serailizedContents() {
        return contentsSerial;
    }
    public byte[] contentsAsBytes() {
        return contentsBytes;
    }

}
