package party.lemons.delivery.store.deliverymethod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.delivery.store.Trade;

import javax.annotation.Nonnull;

/**
 * Created by Sam on 7/11/2018.
 */
public interface IDeliveryMethod
{
    void doDelivery(@Nonnull Trade trade, @Nonnull EntityPlayer player, @Nonnull World world, @Nonnull BlockPos pos);
}
