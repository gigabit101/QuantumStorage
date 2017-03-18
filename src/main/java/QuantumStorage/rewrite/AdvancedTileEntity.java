package QuantumStorage.rewrite;

import QuantumStorage.QuantumStorage;
import QuantumStorage.reborncore.VanillaPacketDispatcher;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import reborncore.client.guibuilder.GuiBuilder;

import java.util.List;

/**
 * Created by Gigabit101 on 17/03/2017.
 */
public abstract class AdvancedTileEntity extends TileEntity
{
    public abstract String getName();

    public ItemStackHandler inv = new ItemStackHandler(getInvSize());

    public abstract int getInvSize();

    public boolean hasInv()
    {
        if(getInvSize() != 0)
        {
            return true;
        }
        return false;
    }

    public ItemStackHandler getInv() { return this.inv; }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inv);
    }

    //Gui
    @SideOnly(Side.CLIENT)
    public GuiBuilder builder = new GuiBuilder(GuiBuilder.defaultTextureSheet);

    @SideOnly(Side.CLIENT)
    public GuiBuilder getBuilder()
    {
        return this.builder;
    }

    @SideOnly(Side.CLIENT)
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY, int guiLeft, int guiTop, int xSize, int ySize, GuiContainer gui)
    {
        builder.drawDefaultBackground(gui, guiLeft, guiTop, xSize, ySize);
        builder.drawPlayerSlots(gui, guiLeft + xSize / 2, guiTop + 93, true);
        if (getSlots() != null)
        {
            for (Slot s : getSlots())
            {
                builder.drawSlot(gui, guiLeft + s.xPos - 1, guiTop + s.yPos - 1);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY, GuiContainer gui, int guiLeft, int guiTop) {}

    //Container
    public abstract List<Slot> getSlots();

    //Block
    public abstract TileEntity createNewTileEntity(World world, int meta);

    public abstract boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ);

    public void openGui(EntityPlayer player, AdvancedTileEntity machine)
    {
        if(!player.isSneaking())
        {
            player.openGui(QuantumStorage.INSTANCE, 0, machine.world, machine.pos.getX(), machine.pos.getY(), machine.pos.getZ());
        }
    }

    //NBT
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 0, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
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
        tagCompound.merge(getInv().serializeNBT());
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
}
