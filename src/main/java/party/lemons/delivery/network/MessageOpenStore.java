package party.lemons.delivery.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import party.lemons.delivery.Delivery;
import party.lemons.delivery.DeliveryConfig;

/**
 * Created by Sam on 7/11/2018.
 */
public class MessageOpenStore implements IMessage
{
    public int page;
    public boolean keybind;

    public MessageOpenStore(){}

    public MessageOpenStore(int page, boolean keybind)
    {
        this.page = page;
        this.keybind = keybind;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.page = buf.readInt();
        this.keybind = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(page);
        buf.writeBoolean(keybind);
    }

    public static class Handler implements IMessageHandler<MessageOpenStore, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageOpenStore message, final MessageContext ctx)
        {
            final EntityPlayerMP player = ctx.getServerHandler().player;
            final WorldServer world = player.getServerWorld();

            world.addScheduledTask(() ->
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
                    player.openGui(Delivery.INSTANCE, message.page, world, 0, 0, 0);
                }
            });
            return null;
        }
    }

}