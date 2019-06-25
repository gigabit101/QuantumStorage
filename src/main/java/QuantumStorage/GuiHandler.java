package QuantumStorage;

import QuantumStorage.api.QuantumStorageAPI;
import QuantumStorage.client.AdvancedGui;
import QuantumStorage.client.GuiQuantumBag;
import QuantumStorage.inventory.AdvancedContainer;
import QuantumStorage.inventory.ContainerQuantumBag;
import QuantumStorage.tiles.AdvancedTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

/**
 * Created by Gigabit101 on 27/01/2017.
 */
public class GuiHandler implements IGuiHandler
{
    public static final int BAG_ID = 0;
    public static final int BAG_ID_PACKET = -1;
    public static final int MULTI_BASEPAGE = 1;
    
    @Override
    public Object getServerGuiElement(int ID, PlayerEntity player, World world, int x, int y, int z)
    {
//        if (ID >= MULTI_BASEPAGE)
//        {
//            if (getMultiBlock(world, x, y, z) != null)
//            {
//                return new ContainerMultiBlockStorage(player, getMultiBlock(world, x, y, z), ID - MULTI_BASEPAGE + 1);
//            }
//        }
        if (world.getTileEntity(new BlockPos(x, y, z)) != null && world.getTileEntity(new BlockPos(x, y, z)) instanceof AdvancedTileEntity)
        {
            AdvancedTileEntity machine = (AdvancedTileEntity) world.getTileEntity(new BlockPos(x, y, z));
            return new AdvancedContainer(player, machine);
        }
//        else if (ID == BAG_ID)
//        {
//            DyeColor color = getColour(player);
//            IItemHandlerModifiable inventory = (IItemHandlerModifiable) player.getCapability(QuantumStorageAPI.QUANTUM_BAG_PROVIDER_CAPABILITY, null).getBag(color);
//            return new ContainerQuantumBag(player, inventory);
//        }
//        else if (ID == BAG_ID_PACKET)
//        {
//            IItemHandlerModifiable inventory = (IItemHandlerModifiable) player.getCapability(QuantumStorageAPI.QUANTUM_BAG_PROVIDER_CAPABILITY, null).getBag(EnumDyeColor.byMetadata(z));
//            return new ContainerQuantumBag(player, inventory);
//        }
        return null;
    }
    
    @Override
    public Object getClientGuiElement(int ID, PlayerEntity player, World world, int x, int y, int z)
    {
//        if (ID >= MULTI_BASEPAGE)
//        {
//            if (getMultiBlock(world, x, y, z) != null)
//            {
//                return new GuiMultiStorage(player, getMultiBlock(world, x, y, z), ID - MULTI_BASEPAGE + 1, new BlockPos(x, y, z));
//            }
//        }
        if (world.getTileEntity(new BlockPos(x, y, z)) != null && world.getTileEntity(new BlockPos(x, y, z)) instanceof AdvancedTileEntity)
        {
            AdvancedTileEntity machine = (AdvancedTileEntity) world.getTileEntity(new BlockPos(x, y, z));
            return new AdvancedGui(player, machine);
        }
//        else if (ID == BAG_ID)
//        {
//            DyeColor color = getColour(player);
//            IItemHandlerModifiable inventory = (IItemHandlerModifiable) player.getCapability(QuantumStorageAPI.QUANTUM_BAG_PROVIDER_CAPABILITY, null).getBag(color);
//            return new GuiQuantumBag(player, inventory);
//        }
//        else if (ID == BAG_ID_PACKET)
//        {
//            IItemHandlerModifiable inventory = (IItemHandlerModifiable) player.getCapability(QuantumStorageAPI.QUANTUM_BAG_PROVIDER_CAPABILITY, null).getBag(EnumDyeColor.byMetadata(z));
//            return new GuiQuantumBag(player, inventory);
//        }
        return null;
    }
    
//    public DyeColor getColour(PlayerEntity player)
//    {
//        if (!player.getHeldItem(Hand.MAIN_HAND).isEmpty() && player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof ItemQuantumBag)
//        {
//            return DyeColor.byMetadata(player.getHeldItem(Hand.MAIN_HAND).getItemDamage());
//        }
//        else if (!player.getHeldItem(Hand.OFF_HAND).isEmpty() && player.getHeldItem(Hand.OFF_HAND).getItem() instanceof ItemQuantumBag)
//        {
//            return DyeColor.byMetadata(player.getHeldItem(Hand.OFF_HAND).getItemDamage());
//        }
//        return DyeColor.WHITE;
//    }
    
//    public MultiBlockStorage getMultiBlock(World world, int x, int y, int z)
//    {
//        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
//        if (tileEntity instanceof TileMultiStorage)
//        {
//            return (MultiBlockStorage) ((TileMultiStorage) tileEntity).getMultiblockController();
//        }
//        return null;
//    }
}
