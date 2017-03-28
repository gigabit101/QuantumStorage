package QuantumStorage.blocks;

import QuantumStorage.client.CreativeTabQuantumStorage;
import QuantumStorage.tiles.AdvancedTileEntity;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Gigabit101 on 17/03/2017.
 */
public class AdvancedBlock extends BlockContainer
{
    public AdvancedTileEntity advancedTileEntity;

    public AdvancedBlock(AdvancedTileEntity advancedTileEntity)
    {
        super(Material.IRON);
        this.advancedTileEntity = advancedTileEntity;
        setCreativeTab(CreativeTabQuantumStorage.INSTANCE);
        setHardness(2.0F);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return advancedTileEntity.createNewTileEntity(worldIn, meta);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(advancedTileEntity != null)
        {
            return advancedTileEntity.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
    {
        if(advancedTileEntity != null)
        {
            advancedTileEntity.onBlockClicked(worldIn, pos, playerIn);
        }
        super.onBlockClicked(worldIn, pos, playerIn);
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {}

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity te = world.getTileEntity(pos);
        if(te instanceof AdvancedTileEntity)
        {
            float xOffset = world.rand.nextFloat() * 0.8F + 0.1F;
            float yOffset = world.rand.nextFloat() * 0.8F + 0.1F;
            float zOffset = world.rand.nextFloat() * 0.8F + 0.1F;

            ItemStack stacknbt = ((AdvancedTileEntity) te).getDropWithNBT();
            int amountToDrop = Math.min(world.rand.nextInt(21) + 10, stacknbt.getCount());

            EntityItem entityitem = new EntityItem(world, pos.getX() + xOffset, pos.getY() + yOffset, pos.getZ() + zOffset, stacknbt.splitStack(amountToDrop));
            world.spawnEntity(entityitem);
        }
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
}
