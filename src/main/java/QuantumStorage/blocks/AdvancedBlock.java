package QuantumStorage.blocks;

import QuantumStorage.client.CreativeTabQuantumStorage;
import QuantumStorage.tiles.AdvancedTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Gigabit101 on 17/03/2017.
 */
public class AdvancedBlock extends BlockContainer
{
    public AdvancedTileEntity advancedTileEntity;
    public static PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static PropertyBool ACTIVE = PropertyBool.create("active");

    public AdvancedBlock(AdvancedTileEntity advancedTileEntity)
    {
        super(Material.IRON);
        this.advancedTileEntity = advancedTileEntity;
        setCreativeTab(CreativeTabQuantumStorage.INSTANCE);
        setHardness(2.0F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, false));
    }

    protected BlockStateContainer createBlockState()
    {
        FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
        ACTIVE = PropertyBool.create("active");
        return new BlockStateContainer(this, FACING, ACTIVE);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        if (advancedTileEntity != null)
        {
            return advancedTileEntity.getRenderType(state);
        }
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        if (advancedTileEntity != null)
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
        if (advancedTileEntity != null)
        {
            return advancedTileEntity.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
    {
        if (advancedTileEntity != null)
        {
            advancedTileEntity.onBlockClicked(worldIn, pos, playerIn);
        }
        super.onBlockClicked(worldIn, pos, playerIn);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        if (te instanceof AdvancedTileEntity)
        {
            float xOffset = world.rand.nextFloat() * 0.8F + 0.1F;
            float yOffset = world.rand.nextFloat() * 0.8F + 0.1F;
            float zOffset = world.rand.nextFloat() * 0.8F + 0.1F;

            ItemStack stacknbt = ((AdvancedTileEntity) te).getDropWithNBT();
            int amountToDrop = Math.min(world.rand.nextInt(21) + 10, stacknbt.getCount());

            EntityItem entityitem = new EntityItem(world, pos.getX() + xOffset, pos.getY() + yOffset, pos.getZ() + zOffset, stacknbt.splitStack(amountToDrop));
            world.spawnEntity(entityitem);
        } else
        {
            super.harvestBlock(world, player, pos, state, te, stack);
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        AdvancedTileEntity adva = (AdvancedTileEntity) worldIn.getTileEntity(pos);

        if (stack.hasTagCompound())
        {
            adva.readFromNBTWithoutCoords(stack.getTagCompound().getCompoundTag("tileEntity"));
        }

        setFacing(placer.getHorizontalFacing().getOpposite(), worldIn, pos);
        adva.setFacing(placer.getHorizontalFacing().getOpposite());

        worldIn.notifyBlockUpdate(pos, state, state, 3);
    }

    public void setFacing(EnumFacing facing, World world, BlockPos pos)
    {
        world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, facing));
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        super.onBlockAdded(worldIn, pos, state);
        worldIn.notifyBlockUpdate(pos, state, state, 3);
        this.setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            IBlockState sate = worldIn.getBlockState(pos.north());
            Block block = sate.getBlock();
            IBlockState state1 = worldIn.getBlockState(pos.south());
            Block block1 = state1.getBlock();
            IBlockState state2 = worldIn.getBlockState(pos.west());
            Block block2 = state2.getBlock();
            IBlockState state3 = worldIn.getBlockState(pos.east());
            Block block3 = state3.getBlock();
            EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && block.isFullBlock(state) && !block1.isFullBlock(state1))
            {
                enumfacing = EnumFacing.SOUTH;
            } else if (enumfacing == EnumFacing.SOUTH && block1.isFullBlock(state1) && !block.isFullBlock(state))
            {
                enumfacing = EnumFacing.NORTH;
            } else if (enumfacing == EnumFacing.WEST && block2.isFullBlock(state2) && !block3.isFullBlock(state2))
            {
                enumfacing = EnumFacing.EAST;
            } else if (enumfacing == EnumFacing.EAST && block3.isFullBlock(state3) && !block2.isFullBlock(state2))
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }

    public EnumFacing getSideFromint(int i)
    {
        if (i == 0)
        {
            return EnumFacing.NORTH;
        } else if (i == 1)
        {
            return EnumFacing.SOUTH;
        } else if (i == 2)
        {
            return EnumFacing.EAST;
        } else if (i == 3)
        {
            return EnumFacing.WEST;
        }
        return EnumFacing.NORTH;
    }

    public int getSideFromEnum(EnumFacing facing)
    {
        if (facing == EnumFacing.NORTH)
        {
            return 0;
        } else if (facing == EnumFacing.SOUTH)
        {
            return 1;
        } else if (facing == EnumFacing.EAST)
        {
            return 2;
        } else if (facing == EnumFacing.WEST)
        {
            return 3;
        }
        return 0;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int facingInt = getSideFromEnum(state.getValue(FACING));
        int activeInt = state.getValue(ACTIVE) ? 0 : 4;
        return facingInt + activeInt;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        boolean active = false;
        int facingInt = meta;
        if (facingInt > 4)
        {
            active = true;
            facingInt = facingInt - 4;
        }
        EnumFacing facing = getSideFromint(facingInt);
        return this.getDefaultState().withProperty(FACING, facing).withProperty(ACTIVE, active);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        if(advancedTileEntity != null)
        {
            return advancedTileEntity.getBlockLayer();
        }
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return side == EnumFacing.UP || side == EnumFacing.DOWN;
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
    {
        return layer == BlockRenderLayer.CUTOUT_MIPPED || layer == BlockRenderLayer.TRANSLUCENT;
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

    private static final EnumFacing[] validRotationAxes = new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN};

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

    public EnumFacing getFacing(IBlockState state)
    {
        return state.getValue(FACING);
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced)
    {
        if (advancedTileEntity != null)
        {
            advancedTileEntity.addInformation(stack, world, tooltip, advanced);
        }
        super.addInformation(stack, world, tooltip, advanced);
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion)
    {
        return 100.0f;
    }
}
