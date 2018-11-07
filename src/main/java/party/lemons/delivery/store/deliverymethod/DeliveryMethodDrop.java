package party.lemons.delivery.store.deliverymethod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.delivery.DeliveryConfig;
import party.lemons.delivery.store.Trade;
import party.lemons.delivery.store.block.DeliveryBlocks;
import party.lemons.delivery.store.block.EntityFallingBlockExt;
import party.lemons.delivery.store.block.TileEntityCrate;

import javax.annotation.Nonnull;

/**
 * Created by Sam on 7/11/2018.
 */
public class DeliveryMethodDrop implements IDeliveryMethod
{
    @Override
    public void doDelivery(Trade trade, @Nonnull EntityPlayer player, @Nonnull World world, @Nonnull BlockPos pos)
    {
        float xPos = pos.getX() + 0.5F;
        float zPos = pos.getZ() + 0.5F;

        if(DeliveryConfig.dropRadius > 0)
        {
            xPos += (-player.getRNG().nextInt(DeliveryConfig.dropRadius) + player.getRNG().nextInt(DeliveryConfig.dropRadius * 2));
            zPos += (-player.getRNG().nextInt(DeliveryConfig.dropRadius) + player.getRNG().nextInt(DeliveryConfig.dropRadius * 2));
        }
        EntityFallingBlockExt block = new EntityFallingBlockExt(world, xPos, player.world.getHeight() + player.getRNG().nextInt(25), zPos, DeliveryBlocks.CRATE.getDefaultState());

        TileEntityCrate crateTe = new TileEntityCrate();
        crateTe.setStack(trade.result.copy());
        block.tileEntityData = crateTe.saveToNbt(new NBTTagCompound());
        world.spawnEntity(block);
        block.fallTime = 1;
    }
}
