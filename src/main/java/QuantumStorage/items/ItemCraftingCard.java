package QuantumStorage.items;

import QuantumStorage.QuantumStorage;
import QuantumStorage.client.GuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCraftingCard extends ItemQuantumStorage
{
	public ItemCraftingCard()
	{
		setUnlocalizedName("quantumCraftingCard");
	}
	
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) 
    {
        player.openGui(QuantumStorage.INSTANCE, GuiHandler.crafingcard, world, (int) player.posX, (int) player.posY, (int) player.posY);
        return itemStack;
    }
}
