package QuantumStorage.multiblock;

import QuantumStorage.QuantumStorage;
import QuantumStorage.client.CreativeTabQuantumStorage;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import reborncore.common.multiblock.BlockMultiblockBase;
import reborncore.common.util.ChatUtils;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Gigabit101 on 12/05/2017.
 */
public class BlockCrate extends BlockMultiblockBase
{
    public BlockCrate()
    {
        super(Material.IRON);
        setCreativeTab(CreativeTabQuantumStorage.INSTANCE);
        setUnlocalizedName(QuantumStorage.MOD_ID + ".crate");
        setHardness(2.0F);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileCrate();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileCrate crate = (TileCrate) worldIn.getTileEntity(pos);
        if (crate.getMultiblockController() != null)
        {
            if (!crate.getMultiblockController().isAssembled())
            {
                if (crate.getMultiblockController().getLastValidationException() != null)
                {
                    if (worldIn.isRemote)
                    {
                        ChatUtils.sendNoSpamMessages(42, new TextComponentString(crate.getMultiblockController().getLastValidationException().getMessage()));
                    }
                    return false;
                }
            } else
            {
                playerIn.openGui(QuantumStorage.INSTANCE, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
                return true;
            }
            return true;
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
        tooltip.add("WIP Do not use yet");

        super.addInformation(stack, player, tooltip, advanced);
    }
}
