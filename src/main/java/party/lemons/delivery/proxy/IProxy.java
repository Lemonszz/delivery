package party.lemons.delivery.proxy;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by Sam on 7/11/2018.
 */
public interface IProxy
{
    default void preInitSided(FMLPreInitializationEvent event){}
}
