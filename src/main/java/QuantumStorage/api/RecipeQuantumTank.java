package QuantumStorage.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

@Deprecated
public class RecipeQuantumTank
{
    Object input;
    ItemStack output;
    FluidStack fluidStack;
    
    public RecipeQuantumTank(Object input, ItemStack output, FluidStack fluidStack)
    {
        this.input = input;
        this.output = output;
        this.fluidStack = fluidStack;
    }
    
//    public static ItemStack getOutputFrom(ItemStack input)
//    {
//        if (input != null)
//        {
//            for (RecipeQuantumTank recipe : QuantumStorageAPI.TANK_RECIPES)
//            {
//                if (recipe.matches(input))
//                {
//                    ItemStack output = recipe.getOutput().copy();
//                    return output;
//                }
//            }
//        }
//        return null;
//    }
//
//    public static FluidStack getFluidFromStack(ItemStack input)
//    {
//        if (input != null)
//        {
//            for (RecipeQuantumTank recipe : QuantumStorageAPI.TANK_RECIPES)
//            {
//                if (recipe.matches(input))
//                {
//                    return recipe.getFluidStack();
//                }
//            }
//        }
//        return null;
//    }
//
//    public boolean matches(ItemStack stack)
//    {
//        if (input instanceof ItemStack)
//        {
//            ItemStack inputCopy = ((ItemStack) input).copy();
//            if (inputCopy.getItemDamage() == Short.MAX_VALUE)
//            {
//                inputCopy.setItemDamage(stack.getItemDamage());
//            }
//            return stack.isItemEqual(inputCopy);
//        }
//
//        if (input instanceof String)
//        {
//            List<ItemStack> validStacks = OreDictionary.getOres((String) input);
//
//            for (ItemStack ostack : validStacks)
//            {
//                ItemStack cstack = ostack.copy();
//                if (cstack.getItemDamage() == Short.MAX_VALUE)
//                {
//                    cstack.setItemDamage(stack.getItemDamage());
//                }
//                if (stack.isItemEqual(cstack))
//                {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
    
    public Object getInput()
    {
        return input;
    }
    
    public ItemStack getOutput()
    {
        return output;
    }
    
    public FluidStack getFluidStack()
    {
        return fluidStack;
    }
}
