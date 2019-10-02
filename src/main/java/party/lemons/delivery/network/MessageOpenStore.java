package party.lemons.delivery.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import party.lemons.delivery.Delivery;
import party.lemons.delivery.DeliveryConfig;
import party.lemons.delivery.store.Trades;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sam on 7/11/2018.
 */
public class MessageOpenStore implements IMessage
{
	public int page;
	public boolean keybind;
	public String store;
	public String profile;

	public MessageOpenStore()
	{
	}

	public MessageOpenStore(int page, String store, String profile, boolean keybind)
	{
		this.page = page;
		this.keybind = keybind;
		this.store = store;
		this.profile = profile;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.page = buf.readInt();
		this.keybind = buf.readBoolean();
		this.store = ByteBufUtils.readUTF8String(buf);
		this.profile = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(page);
		buf.writeBoolean(keybind);
		ByteBufUtils.writeUTF8String(buf, store);
		ByteBufUtils.writeUTF8String(buf, profile);
	}

	public static class Handler implements IMessageHandler<MessageOpenStore, IMessage>
	{
		@Override
		public IMessage onMessage(final MessageOpenStore message, final MessageContext ctx)
		{
			final EntityPlayerMP player = ctx.getServerHandler().player;
			final WorldServer world = player.getServerWorld();

			world.addScheduledTask(()->
			{
				boolean dimOk = true;
				for(int i = 0; i < DeliveryConfig.dimensionBlacklist.length; i++)
				{
					if(world.provider.getDimension() == DeliveryConfig.dimensionBlacklist[i])
					{
						dimOk = false;
						break;
					}
				}

				if(dimOk && (!message.keybind || (message.keybind && DeliveryConfig.useKey)))
				{
					int storeID = Trades.getStoreIndex(message.store, message.profile);
					player.openGui(Delivery.INSTANCE, message.page, world, storeID, Trades.getProfileIndex(message.profile), 0);
				}
			});
			return null;
		}
	}
}