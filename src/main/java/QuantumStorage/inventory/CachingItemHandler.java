package QuantumStorage.inventory;

import net.minecraftforge.items.ItemStackHandler;

public class CachingItemHandler extends ItemStackHandler
{
    private int firstAvailable = 0;
    private int lastUsed = -1;
    
    public CachingItemHandler(int size) {
        super(size);
    }
    
    @Override
    protected void onLoad() {
        super.onLoad();
        firstAvailable = getSlots();
        lastUsed = -1;
        for (int i = 0; i < getSlots(); i++) {
            if (getStackInSlot(i).isEmpty()) {
                firstAvailable = Integer.min(firstAvailable, i);
            } else {
                lastUsed = Integer.max(lastUsed, i);
            }
        }
    }
    
    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        for (int i = slot; i < firstAvailable && i >= 0 && getStackInSlot(i).isEmpty(); i--) {
            firstAvailable = i;
        }
        for (int i = slot; i == firstAvailable && i < getSlots() && !getStackInSlot(i).isEmpty(); i++) {
            firstAvailable = i + 1;
        }
        for (int i = slot; i > lastUsed && i < getSlots() && !getStackInSlot(i).isEmpty(); i++) {
            lastUsed = i;
        }
        for (int i = slot; i == lastUsed && i >= 0 && getStackInSlot(i).isEmpty(); i--) {
            lastUsed = i - 1;
        }
    }
    
    public int getFirstAvailable() {
        return firstAvailable;
    }
    
    public int getLastUsed() {
        return lastUsed;
    }
    
    public boolean isEmpty() {
        return lastUsed == -1;
    }
    
    public boolean isFull() {
        return firstAvailable == getSlots();
    }
}
