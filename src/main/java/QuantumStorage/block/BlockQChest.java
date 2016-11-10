package QuantumStorage.block;

import QuantumStorage.QuantumStorage;
import QuantumStorage.client.GuiHandler;
import QuantumStorage.tile.chest.TileDiamondChest;
import QuantumStorage.tile.chest.TileGoldChest;
import QuantumStorage.tile.chest.TileIronChest;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

/**
 * Created by Gigabit101 on 01/11/2016.
 */
public class BlockQChest extends BlockQuantumStorage
{
    public static final String[] types = new String[]{"iron", "gold", "diamond"};

    public PropertyInteger METADATA;

    public BlockQChest()
    {
        setUnlocalizedName("qchest");
        this.setDefaultState(this.getDefaultState().withProperty(METADATA, 0));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!player.isSneaking())
        {
            player.openGui(QuantumStorage.INSTANCE, GuiHandler.chest, world, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
        return false;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list)
    {
        for (int meta = 0; meta < types.length; meta++)
        {
            list.add(new ItemStack(item, 1, meta));
        }
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return getMetaFromState(state);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        if (meta > types.length)
        {
            meta = 0;
        }
        return this.getDefaultState().withProperty(METADATA, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(METADATA);
    }

    protected BlockStateContainer createBlockState()
    {
        METADATA = PropertyInteger.create("type", 0, types.length - 1);
        return new BlockStateContainer(this, METADATA);
    }


    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
//        if(meta == 0)
//            return new TileQChest(52);
//        if(meta == 1)
//            return new TileQChest(65);
//        return new TileQChest(78);
        if(meta == 0)
        {
            return new TileIronChest();
        }
        if(meta == 1)
        {
            return new TileGoldChest();
        }
        return new TileDiamondChest();
    }
}
