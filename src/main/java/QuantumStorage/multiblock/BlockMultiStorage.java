package QuantumStorage.multiblock;

import QuantumStorage.GuiHandler;
import QuantumStorage.QuantumStorage;
import QuantumStorage.client.CreativeTabQuantumStorage;
import QuantumStorage.network.PacketGui;
import com.google.common.collect.Lists;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import reborncore.common.blocks.PropertyString;
import reborncore.common.multiblock.BlockMultiblockBase;
import reborncore.common.network.NetworkManager;
import reborncore.common.util.ArrayUtils;
import reborncore.common.util.ChatUtils;

import java.util.List;

public class BlockMultiStorage extends BlockMultiblockBase
{
    public static final String[] types = new String[]{"frame", "heat", "storage", "io"};
    private static final List<String> typesList = Lists.newArrayList(ArrayUtils.arrayToLowercase(types));
    
    public static final PropertyString VARIANTS = new PropertyString("type", types);
    
    public BlockMultiStorage()
    {
        super(Material.IRON);
        setCreativeTab(CreativeTabQuantumStorage.INSTANCE);
        setUnlocalizedName(QuantumStorage.MOD_ID + ".multistorage");
        this.setDefaultState(this.getStateFromMeta(0));
        setHardness(2F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        IBlockState state = this.getStateFromMeta(meta);
        
        if (state.getBlock() instanceof BlockMultiStorage && state.getValue(BlockMultiStorage.VARIANTS).equals("io"))
        {
            return new TileIoPort();
        }
        else if (state.getBlock() instanceof BlockMultiStorage && !state.getValue(BlockMultiStorage.VARIANTS).equals("io"))
        {
            return new TileMultiStorage(getStateFromMeta(meta).getValue(VARIANTS));
        }
        return null;
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileMultiStorage tile = (TileMultiStorage) worldIn.getTileEntity(pos);
        if (tile.getMultiblockController() != null)
        {
            if (!tile.getMultiblockController().isAssembled())
            {
                if (tile.getMultiblockController().getLastValidationException() != null)
                {
                    if (worldIn.isRemote && playerIn.getHeldItem(EnumHand.MAIN_HAND).isEmpty())
                    {
                        ChatUtils.sendNoSpamMessages(42, new TextComponentString(tile.getMultiblockController().getLastValidationException().getMessage()));
                    }
                    return false;
                }
            }
            else if (worldIn.isRemote)
            {
                playerIn.openGui(QuantumStorage.INSTANCE, GuiHandler.MULTI_BASEPAGE, worldIn, pos.getX(), pos.getY(), pos.getZ());
                NetworkManager.sendToServer(new PacketGui(0, pos));
                return true;
            }
            return true;
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, side, hitX, hitY, hitZ);
    }
    
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getBlockState().getBaseState().withProperty(VARIANTS, typesList.get(meta));
    }
    
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return typesList.indexOf(state.getValue(VARIANTS));
    }
    
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, VARIANTS);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs creativeTabs, NonNullList<ItemStack> list)
    {
        for (int meta = 0; meta < types.length; meta++)
        {
            list.add(new ItemStack(this, 1, meta));
        }
    }
    
    @Override
    public int damageDropped(IBlockState state)
    {
        return getMetaFromState(state);
    }
    
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(this, 1, getMetaFromState(state));
    }
    
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        
        if (tileentity instanceof TileMultiStorage)
        {
            TileMultiStorage tile = (TileMultiStorage) tileentity;
            if (tile.inv == null)
            {
                super.breakBlock(worldIn, pos, state);
                return;
            }
            dropInventoryItems(worldIn, pos, tile.inv);
        }
        super.breakBlock(worldIn, pos, state);
    }
    
    public static void dropInventoryItems(World world, BlockPos pos, IItemHandler inventory)
    {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity == null)
        {
            return;
        }
        for (int i = 0; i < inventory.getSlots(); i++)
        {
            ItemStack itemStack = inventory.getStackInSlot(i);
            
            if (itemStack.isEmpty())
            {
                continue;
            }
            if (itemStack.getCount() > 0)
            {
                if (itemStack.getItem() instanceof ItemBlock)
                {
                    if (((ItemBlock) itemStack.getItem()).getBlock() instanceof BlockFluidBase || ((ItemBlock) itemStack.getItem()).getBlock() instanceof BlockStaticLiquid || ((ItemBlock) itemStack.getItem()).getBlock() instanceof BlockDynamicLiquid)
                    {
                        continue;
                    }
                }
            }
            net.minecraft.inventory.InventoryHelper.spawnItemStack(world, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), itemStack);
        }
    }
}
