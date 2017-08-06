package randomizer.fates.singletons;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import randomizer.Randomizer;
import randomizer.common.enums.ItemType;
import randomizer.common.structures.FEItem;
import randomizer.common.structures.Job;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FatesItems {
    private static FatesItems instance;
    private List<FEItem> items;

    private String[] debugIids = {
            "IID_青銅の剣", "IID_青銅の槍", "IID_青銅の斧", "IID_青銅の暗器",
            "IID_青銅の弓", "IID_ファイアー", "IID_ライブ", "IID_獣石",
            "IID_乱拳", "IID_岩塊"
    };

    private FatesItems() {
        Type itemType = new TypeToken<List<FEItem>>() {}.getType();

        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new BufferedReader(new InputStreamReader(Randomizer.class
                    .getResourceAsStream("data/json/FatesItems.json"))));
            items = gson.fromJson(reader, itemType);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FatesItems getInstance() {
        if (instance == null)
            instance = new FatesItems();
        return instance;
    }

    List<FEItem> getWeapons() {
        List<FEItem> weapons = new ArrayList<>();
        for(FEItem i : items) {
            if(i.getType() != ItemType.Treasure)
                weapons.add(i);
        }
        return weapons;
    }

    public List<FEItem> getSelectedItems(ItemType type) {
        List<FEItem> selected = new ArrayList<>();
        List<FEItem> weapons = getWeapons();
        boolean[] selectedItems = FatesGui.getInstance().getSelectedItems();
        for(int x = 0; x < weapons.size(); x++) {
            if(weapons.get(x).getType() == type && selectedItems[x])
                selected.add(weapons.get(x));
        }
        return selected;
    }

    public FEItem generateEligibleItem(ItemType type, int maxLength) {
        List<FEItem> eligible = new ArrayList<>();
        try {
            for(FEItem i : getSelectedItems(type)) {
                if(i.getIid().getBytes("shift-jis").length <= maxLength)
                    eligible.add(i);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Random random = new Random();
        return eligible.get(random.nextInt(eligible.size()));
    }

    public FEItem generateItem(Job j) {
        Random random = new Random();
        List<FEItem> items = getSelectedItems(j.getItemType());
        return items.get(random.nextInt(items.size()));
    }

    public FEItem generateDebugItem(Job j) {
        Random random = new Random();
        List<FEItem> weapons = getDebugItems();
        List<FEItem> selected = new ArrayList<>();
        for (FEItem weapon : weapons) {
            if (weapon.getType() == j.getItemType())
                selected.add(weapon);
        }
        return selected.get(random.nextInt(selected.size()));
    }

    private List<FEItem> getDebugItems() {
        List<FEItem> eligible = new ArrayList<>();
        for(String s : debugIids)
            eligible.add(getByIid(s));
        return  eligible;
    }

    private FEItem getByIid(String iid) {
        for(FEItem i : getWeapons()) {
            if(i.getIid().equals(iid))
                return i;
        }
        return null;
    }

    public List<FEItem> getItems() {
        return items;
    }
}
