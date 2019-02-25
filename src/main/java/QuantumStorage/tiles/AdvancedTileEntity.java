package QuantumStorage.tiles;

import QuantumStorage.QuantumStorage;
import QuantumStorage.client.AdvancedGui;
import QuantumStorage.client.GuiBuilderQuantumStorage;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import reborncore.common.network.VanillaPacketDispatcher;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Gigabit101 on 17/03/2017.
 */
public abstract class AdvancedTileEntity extends TileEntity
{
    private EnumFacing facing;
    public float prevLidAngle;
    public float lidAngle;
    public ItemStackHandler inv = null;

    protected AdvancedTileEntity()
    {
        super();
        this.facing = EnumFacing.NORTH;
    }

    public EnumFacing getFacing()
    {
        return facing;
    }

    public void setFacing(EnumFacing facing)
    {
        this.facing = facing;
    }

    public void rotateAround()
    {
        this.setFacing(this.getFacing().rotateY());
        this.world.addBlockEvent(this.pos, getBlock(), 2, this.getFacing().ordinal());
    }

    public abstract String getName();

    public ItemStackHandler getInv()
    {
        return this.inv;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if (inv != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (inv != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inv);
        }
        return super.getCapability(capability, facing);
    }

    @SideOnly(Side.CLIENT)
    private GuiBuilderQuantumStorage builder;

    @SideOnly(Side.CLIENT)
    public GuiBuilderQuantumStorage getBuilder()
    {
        if(builder == null)
        {
            //Builder is set here as it cannot be set when the class is loaded due to the field not being present
            builder = new GuiBuilderQuantumStorage();
        }
        return builder;
    }

    public int getXSize()
    {
        return 176;
    }

    public int getYsize()
    {
        return 176;
    }

    @SideOnly(Side.CLIENT)
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY, int guiLeft, int guiTop, int xSize, int ySize, AdvancedGui gui)
    {
        getBuilder().drawDefaultBackground(gui, guiLeft, guiTop, xSize, ySize);
        getBuilder().drawPlayerSlots(gui, guiLeft + xSize / 2, guiTop + 93, true);
        if (getSlots() != null)
        {
            for (Slot s : getSlots())
            {
                getBuilder().drawSlot(gui, guiLeft + s.xPos - 1, guiTop + s.yPos - 1);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY, GuiContainer gui, int guiLeft, int guiTop) {}

    //Container
    public abstract List<Slot> getSlots();

    public int inventoryOffsetX()
    {
        return 8;
    }

    public int inventoryOffsetY()
    {
        return 94;
    }

    //Block
    public abstract TileEntity createNewTileEntity(World world, int meta);

    public abstract boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ);

    public void openGui(EntityPlayer player, AdvancedTileEntity machine)
    {
        if (!player.isSneaking())
        {
            player.openGui(QuantumStorage.INSTANCE, 0, machine.world, machine.pos.getX(), machine.pos.getY(), machine.pos.getZ());
        }
    }

    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    public static final AxisAlignedBB FULL_BLOCK_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return FULL_BLOCK_AABB;
    }

    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {}

    //NBT
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound = super.writeToNBT(compound);
        compound.setByte("facing", (byte) getFacing().ordinal());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.facing = EnumFacing.VALUES[compound.getByte("facing")];
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 0, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
    {
        super.onDataPacket(net, packet);
        this.readFromNBT(packet.getNbtCompound());
    }

    public void sync()
    {
        VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
    }

    public void writeToNBTWithoutCoords(NBTTagCompound tagCompound)
    {
        tagCompound = super.writeToNBT(tagCompound);
        if(getInv() != null)
        {
            tagCompound.merge(getInv().serializeNBT());
        }
    }

    public void readFromNBTWithoutCoords(NBTTagCompound compound)
    {
        getInv().deserializeNBT(compound);
    }

    public abstract Block getBlock();

    public ItemStack getDropWithNBT()
    {
        NBTTagCompound tileEntity = new NBTTagCompound();
        ItemStack dropStack = new ItemStack(getBlock(), 1);
        writeToNBTWithoutCoords(tileEntity);
        dropStack.setTagCompound(new NBTTagCompound());
        dropStack.getTagCompound().setTag("tileEntity", tileEntity);
        return dropStack;
    }

    public abstract void addRecipe();

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced) {}

    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.SOLID;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
        if (oldState.getBlock() != newSate.getBlock())
        {
            return true;
        }
        return false;
    }
}
