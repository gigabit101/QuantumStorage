package QuantumStorage;

import QuantumStorage.api.QuantumStorageAPI;
import QuantumStorage.client.AdvancedGui;
import QuantumStorage.inventory.ContainerQuantumBag;
import QuantumStorage.client.GuiQuantumBag;
import QuantumStorage.inventory.AdvancedContainer;
import QuantumStorage.items.ItemQuantumBag;
import QuantumStorage.multiblock.ContainerMultiBlockCrate;
import QuantumStorage.multiblock.GuiMultiBlockCrate;
import QuantumStorage.multiblock.MultiBlockCrate;
import QuantumStorage.multiblock.TileCrate;
import QuantumStorage.tiles.AdvancedTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

/**
 * Created by Gigabit101 on 27/01/2017.
 */
public class GuiHandler implements IGuiHandler
{
    public static int BAG_ID = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (getMultiBlock(world, x, y, z) != null)
        {
            return new ContainerMultiBlockCrate(player, getMultiBlock(world, x, y, z), world.getTileEntity(new BlockPos(x, y, z)));
        }
        else if (world.getTileEntity(new BlockPos(x, y, z)) != null && world.getTileEntity(new BlockPos(x, y, z)) instanceof AdvancedTileEntity)
        {
            AdvancedTileEntity machine = (AdvancedTileEntity) world.getTileEntity(new BlockPos(x, y, z));
            return new AdvancedContainer(player, machine);
        }
        else if(ID == BAG_ID)
        {
            EnumDyeColor color = getColour(player);
            IItemHandlerModifiable inventory = (IItemHandlerModifiable) player.getCapability(QuantumStorageAPI.QUANTUM_BAG_PROVIDER_CAPABILITY, null).getBag(color);
            return new ContainerQuantumBag(player, inventory);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (getMultiBlock(world, x, y, z) != null)
        {
            return new GuiMultiBlockCrate(player, getMultiBlock(world, x, y, z), new BlockPos(x, y, z));
        }
        else if (world.getTileEntity(new BlockPos(x, y, z)) != null && world.getTileEntity(new BlockPos(x, y, z)) instanceof AdvancedTileEntity)
        {
            AdvancedTileEntity machine = (AdvancedTileEntity) world.getTileEntity(new BlockPos(x, y, z));
            return new AdvancedGui(player, machine);
        }
        else if(ID == BAG_ID)
        {
            EnumDyeColor color = getColour(player);
            IItemHandlerModifiable inventory = (IItemHandlerModifiable) player.getCapability(QuantumStorageAPI.QUANTUM_BAG_PROVIDER_CAPABILITY, null).getBag(color);
            return new GuiQuantumBag(player, inventory);
        }
        return null;
    }

    public EnumDyeColor getColour(EntityPlayer player)
    {
        if(!player.getHeldItem(EnumHand.MAIN_HAND).isEmpty() && player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemQuantumBag)
        {
            return EnumDyeColor.byMetadata(player.getHeldItem(EnumHand.MAIN_HAND).getItemDamage());
        }
        else if(!player.getHeldItem(EnumHand.OFF_HAND).isEmpty() && player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemQuantumBag)
        {
            return EnumDyeColor.byMetadata(player.getHeldItem(EnumHand.OFF_HAND).getItemDamage());
        }
        return EnumDyeColor.WHITE;
    }

    public MultiBlockCrate getMultiBlock(World world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if (tileEntity instanceof TileCrate)
        {
            return (MultiBlockCrate) ((TileCrate) tileEntity).getMultiblockController();
        }
        return null;
    }
}
