package QuantumStorage.blocks;

import QuantumStorage.client.CreativeTabQuantumStorage;
import QuantumStorage.tiles.AdvancedTileEntity;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

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
        if(advancedTileEntity != null)
        {
            return advancedTileEntity.getRenderType(state);
        }
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        if(advancedTileEntity != null)
        {
            return advancedTileEntity.getBoundingBox(state, source, pos);
        }
        return super.getBoundingBox(state, source, pos);
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
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
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
        else
        {
            super.harvestBlock(world, player, pos, state, te, stack);
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        AdvancedTileEntity adva = (AdvancedTileEntity) worldIn.getTileEntity(pos);

        if(stack.hasTagCompound())
            adva.readFromNBTWithoutCoords(stack.getTagCompound().getCompoundTag("tileEntity"));

        adva.setFacing(placer.getHorizontalFacing().getOpposite());

        worldIn.notifyBlockUpdate(pos, state, state, 3);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        super.onBlockAdded(worldIn, pos, state);
        worldIn.notifyBlockUpdate(pos, state, state, 3);
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

    private static final EnumFacing[] validRotationAxes = new EnumFacing[] { EnumFacing.UP, EnumFacing.DOWN };

    @Override
    public EnumFacing[] getValidRotations(World worldObj, BlockPos pos)
    {
        return validRotationAxes;
    }

    @Override
    public boolean rotateBlock(World worldObj, BlockPos pos, EnumFacing axis)
    {
        if (worldObj.isRemote)
        {
            return false;
        }
        if (axis == EnumFacing.UP || axis == EnumFacing.DOWN)
        {
            TileEntity tileEntity = worldObj.getTileEntity(pos);
            if (tileEntity instanceof AdvancedTileEntity)
            {
                AdvancedTileEntity icte = (AdvancedTileEntity) tileEntity;
                icte.rotateAround();
            }
            return true;
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
        if(advancedTileEntity != null)
        {
            advancedTileEntity.addInformation(stack, player, tooltip, advanced);
        }
        super.addInformation(stack, player, tooltip, advanced);
    }
}
