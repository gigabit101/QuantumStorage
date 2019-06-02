package QuantumStorage.command;

import QuantumStorage.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandBuildMultiBlock extends CommandBase
{
    @Override
    public String getName() {
        return "qsmblock";
    }
    
    @Override
    public String getUsage(ICommandSender sender) {
        return "qsmblock <size>";
    }
    
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        int size = Integer.parseInt(args[0]);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    int meta = 1;
                    if (x == 0 || z == 0 || x == size - 1 || z == size - 1) {
                        if ((x == 0 && z == 0) || (x == 0 && z == size - 1) || (x == size - 1 && z == 0) || (x == size - 1 && z == size - 1)) {
                            meta = 0;
                        } else {
                            if (y == 0 || y == size - 1) {
                                meta = 0;
                            } else {
                                meta = 1;
                            }
                            
                        }
                        
                    } else {
                        if (y == 0 || y == size - 1) {
                            meta = 1;
                        } else {
                            meta = 2;
                        }
                    }
                    
                    IBlockState state = ModBlocks.MULTIBLOCK_STORAGE.getStateFromMeta(meta);
                    sender.getEntityWorld().setBlockState(sender.getPosition().add(x, y, z), state);
                }
            }
        }
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 4;
    }
}
