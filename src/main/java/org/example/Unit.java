package org.example;

public class Unit {
    private final int id;
    private final String key;
    private final String category;
    private final String symbol;
    private final String name;
    private final String description;

    public Unit(int id, String key, String category, String symbol,
                String name, String description) {
        this.id = id;
        this.key = key;
        this.category = category;
        this.symbol = symbol;
        this.name = name;
        this.description = description;
    }

    public int getId() { return id; }
    public String getKey() { return key; }
    public String getCategory() { return category; }
    public String getSymbol() { return symbol; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return name + " (" + symbol + ")";
    }
}
