package munchhunt.munchhuntproject.Objects;

import java.util.ArrayList;

public class History {
    public String name = "";
    public String id = "";
    public String category = "";

    public History() {
    }

    public History(String name, String id, String category) {
        this.name = name;
        this.id = id;
        this.category = category;
    }

    public String getName()
    {
        return name;
    }
    public String getId()
    {
        return id;
    }
    public String getCategory()
    {
        return category;
    }

}
