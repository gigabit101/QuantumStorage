package QuantumStorage.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

/**
 * Created by Gigabit101 on 10/05/2017.
 */
public class RecipeQuantumCrafter
{
    Object input;
    ItemStack output;
    int time;

    public RecipeQuantumCrafter(Object input, ItemStack output, int time)
    {
        this.input = input;
        this.output = output;
        this.time = time;
    }

    public static ItemStack getOutputFrom(ItemStack input)
    {
        if (input != null)
        {
            for (RecipeQuantumCrafter recipe : QuantumStorageAPI.RECIPES)
            {
                if (recipe.matches(input))
                {
                    ItemStack output = recipe.getOutput().copy();
                    return output;
                }
            }
        }
        return null;
    }

    public static int getTimeFromStack(ItemStack input)
    {
        if (input != null)
        {
            for (RecipeQuantumCrafter recipe : QuantumStorageAPI.RECIPES)
            {
                if (recipe.matches(input))
                {
                    return recipe.getTime();
                }
            }
        }
        return 0;
    }

    public boolean matches(ItemStack stack)
    {
        if (input instanceof ItemStack)
        {
            ItemStack inputCopy = ((ItemStack) input).copy();
            if (inputCopy.getItemDamage() == Short.MAX_VALUE)
            {
                inputCopy.setItemDamage(stack.getItemDamage());
            }
            return stack.isItemEqual(inputCopy);
        }

        if (input instanceof String)
        {
            List<ItemStack> validStacks = OreDictionary.getOres((String) input);

            for (ItemStack ostack : validStacks)
            {
                ItemStack cstack = ostack.copy();
                if (cstack.getItemDamage() == Short.MAX_VALUE)
                {
                    cstack.setItemDamage(stack.getItemDamage());
                }
                if (stack.isItemEqual(cstack))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public Object getInput()
    {
        return input;
    }

    public ItemStack getOutput()
    {
        return output;
    }

    public int getTime()
    {
        return time;
    }
}
