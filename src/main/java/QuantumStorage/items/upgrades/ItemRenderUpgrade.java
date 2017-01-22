package QuantumStorage.items.upgrades;

import QuantumStorage.items.ItemQuantumStorage;
import QuantumStorage.lib.ModInfo;
import QuantumStorage.tile.TileQuantumDsu;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Gigabit101 on 28/12/2016.
 */
public class ItemRenderUpgrade extends ItemQuantumStorage
{
    public static String NBT_KEY = ModInfo.MOD_ID.toLowerCase() + ".render";
    public ItemRenderUpgrade()
    {
        setUnlocalizedName(ModInfo.MOD_ID.toLowerCase() + ".renderupgrade");
        setRegistryName("renderupgrade");
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(worldIn.getTileEntity(pos) != null && worldIn.getTileEntity(pos) instanceof TileQuantumDsu)
        {
            TileQuantumDsu tileQuantumDsu = (TileQuantumDsu) worldIn.getTileEntity(pos);
            if(!tileQuantumDsu.getTileData().hasKey(NBT_KEY))
            {
                tileQuantumDsu.getTileData().setBoolean(NBT_KEY, true);
                return EnumActionResult.SUCCESS;
            }
            else
            {
                tileQuantumDsu.getTileData().removeTag(NBT_KEY);
                return EnumActionResult.SUCCESS;
            }
        }
        return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
    {
        list.add("Shift Right click on QuantumStorage Unit to add");
        list.add("a render to the QuantumStorage Unit to display the stored item above the block");
    }
}
