package QuantumStorage.tiles;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Gigabit101 on 17/03/2017.
 */
public abstract class AdvancedTileEntity extends TileEntity
{
    private Direction facing;
    public ItemStackHandler inv = null;
    
    public AdvancedTileEntity(TileEntityType<?> tileEntityTypeIn)
    {
        super(tileEntityTypeIn);
        this.facing = Direction.NORTH;
    }
    
    public Direction getFacing()
    {
        return facing;
    }
    
    public void setFacing(Direction facing)
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

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability)
    {
        if (inv != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
//            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inv);
        }
        return super.getCapability(capability, facing);
    }
    
//    @SideOnly(Side.CLIENT)
//    private GuiBuilderQuantumStorage builder;
    
//    @SideOnly(Side.CLIENT)
//    public GuiBuilderQuantumStorage getBuilder()
//    {
//        if (builder == null)
//        {
//            builder = new GuiBuilderQuantumStorage();
//        }
//        return builder;
//    }
    
    public int getXSize()
    {
        return 176;
    }
    
    public int getYsize()
    {
        return 176;
    }
    
//    @SideOnly(Side.CLIENT)
//    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY, int guiLeft, int guiTop, int xSize, int ySize, AdvancedGui gui)
//    {
//        getBuilder().drawDefaultBackground(gui, guiLeft, guiTop, xSize, ySize);
//        getBuilder().drawPlayerSlots(gui, guiLeft + xSize / 2, guiTop + 93, true);
//        if (getSlots() != null)
//        {
//            for (Slot s : getSlots())
//            {
//                getBuilder().drawSlot(gui, guiLeft + s.xPos - 1, guiTop + s.yPos - 1);
//            }
//        }
//    }
    
//    @SideOnly(Side.CLIENT)
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY, ContainerScreen gui, int guiLeft, int guiTop)
    {
    }
    
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
    public abstract TileEntity createNewTileEntity(IBlockReader world);
    
    public abstract boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult);
    
    public void openGui(PlayerEntity player, AdvancedTileEntity machine)
    {
        if (!player.isSneaking())
        {
//            player.openGui(QuantumStorage.INSTANCE, 0, machine.world, machine.pos.getX(), machine.pos.getY(), machine.pos.getZ());
        }
    }

    public static final AxisAlignedBB FULL_BLOCK_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    
    //NBT
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        compound = super.write(compound);
        return compound;
    }
    
    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
    }

//    @Override
//    public SPacketUpdateTileEntity getUpdatePacket()
//    {
//        return new SPacketUpdateTileEntity(this.pos, 0, this.getUpdateTag());
//    }
    
    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet)
    {
        super.onDataPacket(net, packet);
        this.read(packet.getNbtCompound());
    }
    
    public void sync()
    {
//        VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
    }
    
    public void writeToNBTWithoutCoords(CompoundNBT tagCompound)
    {
        tagCompound = super.write(tagCompound);
        if (getInv() != null)
        {
            tagCompound.merge(getInv().serializeNBT());
        }
    }
    
    public void readFromNBTWithoutCoords(CompoundNBT compound)
    {
        getInv().deserializeNBT(compound);
    }
    
    public abstract Block getBlock();
    
    public ItemStack getDropWithNBT()
    {
        CompoundNBT tileEntity = new CompoundNBT();
        ItemStack dropStack = new ItemStack(getBlock(), 1);
        writeToNBTWithoutCoords(tileEntity);
        dropStack.setTag(new CompoundNBT());
        dropStack.setTagInfo("tileEntity", tileEntity);
        return dropStack;
    }
    
    public abstract void addRecipe();
    
    public void addInformation(ItemStack stack, @Nullable IBlockReader world, List<ITextComponent> tooltip, ITooltipFlag advanced) {}
    
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.SOLID;
    }
    
//    @Override
//    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
//    {
//        if (oldState.getBlock() != newSate.getBlock())
//        {
//            return true;
//        }
//        return false;
//    }
}
