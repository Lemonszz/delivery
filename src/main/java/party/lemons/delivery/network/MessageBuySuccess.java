package party.lemons.delivery.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import party.lemons.delivery.block.tileentity.GuiStore;

/**
 * Created by Sam on 7/11/2018.
 */
public class MessageBuySuccess implements IMessage
{
    public MessageBuySuccess(){}


    @Override
    public void fromBytes(ByteBuf buf){
    }

    @Override
    public void toBytes(ByteBuf buf)
    {

    }

    public static class Handler implements IMessageHandler<MessageBuySuccess, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageBuySuccess message, final MessageContext ctx)
        {
            Minecraft.getMinecraft().addScheduledTask(()->
            {
                if(Minecraft.getMinecraft().currentScreen instanceof GuiStore)
                {
                    ((GuiStore)Minecraft.getMinecraft().currentScreen).setButtonDisabled();
                }
            });
            return null;
        }
    }

}
