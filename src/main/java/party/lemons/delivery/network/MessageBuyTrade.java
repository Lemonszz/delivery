package party.lemons.delivery.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import party.lemons.delivery.Delivery;
import party.lemons.delivery.DeliveryConfig;
import party.lemons.delivery.store.Trade;
import party.lemons.delivery.store.Trades;
import party.lemons.delivery.store.block.ContainerStore;

/**
 * Created by Sam on 7/11/2018.
 */
public class MessageBuyTrade implements IMessage
{
    public int index;

    public MessageBuyTrade(){}

    public MessageBuyTrade(int index)
    {
        this.index = index;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.index = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(index);
    }

    public static class Handler implements IMessageHandler<MessageBuyTrade, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageBuyTrade message, final MessageContext ctx)
        {
            final EntityPlayerMP player = ctx.getServerHandler().player;
            final WorldServer world = player.getServerWorld();

            world.addScheduledTask(() ->{
                Trade trade = Trades.getTrades(player).get(message.index);
                String sound = DeliveryConfig.purchaceFailSound;

                if(player.openContainer instanceof ContainerStore && trade.canPurchase(player))
                {
                    trade.takeCost(player);
                    player.closeScreen();
                    Delivery.NETWORK.sendTo(new MessageCloseGui(), player);

                    DeliveryConfig.deliveryType.doDelivery(trade, player, world, player.getPosition());

                    sound = DeliveryConfig.purchaceSuccessSound;

                }
                else
                {
                }
                player.connection.sendPacket(new SPacketCustomSound(sound, SoundCategory.MASTER, player.posX, player.posY, player.posZ, 0.5F, 1F));
            });
            return null;
        }
    }

}