package net.gigabit101.quantumstorage.util.inventory;

public abstract class AbstractFilter
{
    private final String identifier;

    public AbstractFilter(String identifier)
    {
        this.identifier = identifier;
        register();
    }

    private void register() {
        FilterUtils.register(this);
    }

    public String getIdentifier() {
        return identifier;
    }
}
