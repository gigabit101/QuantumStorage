package QuantumStorage.upgrades;

/**
 * Created by Gigabit101 on 31/07/2017.
 */
@Deprecated
public enum EnumUpgradeType
{
    RENDER("render"),
    VOID ("void"),
    CREATIVE ("creative"),
    WATER ("water");

    private final String name;

    EnumUpgradeType(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
