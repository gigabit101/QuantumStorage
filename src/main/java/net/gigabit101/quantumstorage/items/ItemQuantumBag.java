package net.gigabit101.quantumstorage.items;

import net.gigabit101.quantumstorage.api.IColorable;
import net.gigabit101.quantumstorage.client.CreativeTabQuantumStorage;
import net.gigabit101.quantumstorage.items.prefab.ItemBase;
import com.google.common.collect.Maps;
import net.minecraft.item.*;
import java.util.Map;

public class ItemQuantumBag extends ItemBase implements IColorable
{
    private static final Map<DyeColor, ItemQuantumBag> COLOR_DYE_ITEM_MAP = Maps.newEnumMap(DyeColor.class);
    private final DyeColor dyeColor;
    protected final int colour;

    public ItemQuantumBag(DyeColor dyeColor)
    {
        super(new Item.Properties().group(CreativeTabQuantumStorage.INSTANCE).maxStackSize(1));
        setRegistryName("quantum_bag_" + dyeColor.getName());
        this.dyeColor = dyeColor;
        float[] vals = dyeColor.getColorComponentValues();
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++)
            rgb[i] = (int) (255 * vals[i]);
        int value = ((255 & 0xFF) << 24) | ((rgb[0] & 0xFF) << 16) | ((rgb[1] & 0xFF) << 8) | ((rgb[2] & 0xFF) << 0);
        colour = value;
    }

    public DyeColor getDyeColor()
    {
        return this.dyeColor;
    }

    public static ItemQuantumBag getItem(DyeColor color)
    {
        return (ItemQuantumBag) COLOR_DYE_ITEM_MAP.get(color);
    }

    @Override
    public int getColor(ItemStack stack, int tint)
    {
        return colour;
    }

//    @Override
//    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
//    {
//        if (!worldIn.isRemote)
//        {
//            playerIn.openGui(QuantumStorage.INSTANCE, GuiHandler.BAG_ID, worldIn, 0, 0, 0);
//        }
//        return super.onItemRightClick(worldIn, playerIn, handIn);
//    }
}
