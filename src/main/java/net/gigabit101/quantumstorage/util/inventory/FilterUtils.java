package net.gigabit101.quantumstorage.util.inventory;

import java.util.HashMap;

public class FilterUtils
{
    private static HashMap<String, AbstractFilter> filters = new HashMap<>();
    public static void register(AbstractFilter abstractFilter)
    {
        filters.put(abstractFilter.getIdentifier(), abstractFilter);
    }
}
