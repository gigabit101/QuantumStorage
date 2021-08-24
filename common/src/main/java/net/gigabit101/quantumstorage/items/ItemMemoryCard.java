package net.gigabit101.quantumstorage.items;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemMemoryCard extends Item
{
    public ItemMemoryCard()
    {
        super(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).tab(QuantumStorage.CREATIVE_TAB));
    }

    @Override
    public boolean isFoil(ItemStack itemStack)
    {
        return itemStack.hasTag();
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag)
    {
        if(!itemStack.isEmpty() && itemStack.hasTag())
        {
            if(itemStack.getTag().getCompound("BlockEntityTag") != null)
            {
                ListTag listTag = itemStack.getTag().getCompound("BlockEntityTag").getList("Items", 10);
                int count = listTag.getCompound(1).getInt("SizeSpecial");

                for (int i = 0; i < listTag.size(); i++)
                {
                    count += ItemStack.of(listTag.getCompound(i)).getCount();
                }
                list.add(new TranslatableComponent(ChatFormatting.GOLD + "Storing: " +  ChatFormatting.GREEN + count + " " + ChatFormatting.WHITE + ItemStack.of(listTag.getCompound(2)).getDisplayName().getString()));
            }
        }
        super.appendHoverText(itemStack, level, list, tooltipFlag);
    }
}
