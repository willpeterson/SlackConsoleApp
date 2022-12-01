package will.peterson.slackconsole;

import java.util.ArrayList;
import java.util.List;

public class ItemList {
    private List items = new ArrayList<Item>();

    public List<Item> getItems() { return items; }

    public void setItems(List<Item> value) { this.items = value; }

    public List<Item> getMembers() { return items; }

    public void setMembers(List<Item> value) { this.items = value; }

    public List<Item> getChannels() { return items; }

    public void setChannels(List<Item> value) { this.items = value; }

    public List<Item> getMessages() { return items; }

    public void setMessages(List<Item> value) { this.items = value; }
}

