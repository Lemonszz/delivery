package party.lemons.delivery.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import party.lemons.delivery.DeliveryConfig;

/**
 * Created by Sam on 7/11/2018.
 */
public class MessageCloseGui implements IMessage
{
	public MessageCloseGui()
	{
	}


	@Override
	public void fromBytes(ByteBuf buf)
	{
	}

	@Override
	public void toBytes(ByteBuf buf)
	{

	}

	public static class Handler implements IMessageHandler<MessageCloseGui, IMessage>
	{
		@Override
		public IMessage onMessage(final MessageCloseGui message, final MessageContext ctx)
		{
			if(DeliveryConfig.closeGui) Minecraft.getMinecraft().player.closeScreen();
			return null;
		}
	}

}
