package party.lemons.delivery.store;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.delivery.store.deliverymethod.DeliveryMethodDrop;
import party.lemons.delivery.store.deliverymethod.DeliveryMethodGive;
import party.lemons.delivery.store.deliverymethod.IDeliveryMethod;

import javax.annotation.Nonnull;

/**
 * Created by Sam on 7/11/2018.
 */
public enum DeliveryType
{
	SKY_DROP(new DeliveryMethodDrop()), INVENTORY(new DeliveryMethodGive());

	private final IDeliveryMethod method;

	DeliveryType(IDeliveryMethod method)
	{
		this.method = method;
	}

	public void doDelivery(Trade trade, @Nonnull EntityPlayer player, @Nonnull World world, @Nonnull BlockPos pos)
	{
		method.doDelivery(trade, player, world, pos);
	}
}
