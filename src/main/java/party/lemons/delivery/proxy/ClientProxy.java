package party.lemons.delivery.proxy;

import net.minecraft.client.renderer.entity.RenderFallingBlock;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import party.lemons.delivery.Delivery;
import party.lemons.delivery.store.block.EntityFallingBlockExt;
import party.lemons.delivery.store.block.RenderFallingBlockExt;

/**
 * Created by Sam on 7/11/2018.
 */
public class ClientProxy implements IProxy
{
    public static KeyBinding KEY_STORE = new KeyBinding("key." + Delivery.MODID+ ".store", 48, "key.categories.gameplay");

    @Override
    public void preInitSided(FMLPreInitializationEvent event)
    {
        ClientRegistry.registerKeyBinding(KEY_STORE);
        RenderingRegistry.registerEntityRenderingHandler(EntityFallingBlockExt.class, RenderFallingBlockExt::new);
    }
}
