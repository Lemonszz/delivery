package party.lemons.delivery.store;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.Ingredient;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by Sam on 7/11/2018.
 */
@ZenRegister
@ZenClass("mods.Delivery.Store")
public class Trades
{
    public static List<Trade> trades = new ArrayList<>();

    @ZenMethod
    public static void clear()
    {
        trades.clear();
    }

    @ZenMethod
    public static void addTrade(IItemStack result, IIngredient... inputs)
    {
        addTrade(new Trade(CraftTweakerMC.getItemStack(result), getIngredients(inputs)));
    }

    @ZenMethod
    public static void addTrade(String gamestage, IItemStack result, IIngredient... inputs)
    {
        addTrade(new Trade(gamestage, CraftTweakerMC.getItemStack(result), getIngredients(inputs)));
    }

    private static void addTrade(Trade trade)
    {
        trades.add(trade);
    }

    private static Ingredient[] getIngredients(IIngredient... inputs)
    {
        Ingredient[] ingreds = new Ingredient[inputs.length];
        for (int i = 0; i < inputs.length; i++)
            ingreds[i] = CraftTweakerMC.getIngredient(inputs[i]);

        return ingreds;
    }

    public static List<Trade> getTrades(EntityPlayer player)
    {
        return trades.stream().filter(t -> t.isUnlocked(player)).collect(Collectors.toList());
    }
}