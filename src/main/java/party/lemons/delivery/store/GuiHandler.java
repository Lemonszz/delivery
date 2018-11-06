package party.lemons.delivery.store;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import party.lemons.delivery.store.block.ContainerStore;
import party.lemons.delivery.store.block.GuiStore;

import javax.annotation.Nullable;

/**
 * Created by Sam on 7/11/2018.
 */
public class GuiHandler implements IGuiHandler
{
    //yolo
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return new ContainerStore(player, ID);
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return new GuiStore(new ContainerStore(player, ID));
    }
}