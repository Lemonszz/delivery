package party.lemons.delivery.store;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IngredientOreDict;
import crafttweaker.mc1120.oredict.MCOreDictEntry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import net.minecraftforge.oredict.OreIngredient;
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
    public static Trade addTrade(IItemStack result, IIngredient... inputs)
    {
        return addTrade(new Trade(CraftTweakerMC.getItemStack(result), getIngredients(inputs)));
    }

    @ZenMethod
    public static Trade addTrade(String gamestage, IItemStack result, IIngredient... inputs)
    {
        return addTrade(new Trade(gamestage, CraftTweakerMC.getItemStack(result), getIngredients(inputs)));
    }

    @ZenMethod
    public static Trade addTrade(IItemStack result)
    {
        return addTrade(new Trade(CraftTweakerMC.getItemStack(result)));
    }

    @ZenMethod
    public static Trade addTrade(String gamestage, IItemStack result)
    {
        return addTrade(new Trade(gamestage, CraftTweakerMC.getItemStack(result)));
    }

    private static Trade addTrade(Trade trade)
    {
        trades.add(trade);

        return trade;
    }

    public static Ingredient[] getIngredients(IIngredient... inputs)
    {
        Ingredient[] ingreds = new Ingredient[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            ingreds[i] = CraftTweakerMC.getIngredient(inputs[i]);
        }

        return ingreds;
    }

    public static Ingredient getMergedIngredient(IIngredient... inputs)
    {
        ItemStack[] items = new ItemStack[inputs.length];
        for(int i = 0; i < inputs.length; i++)
        {
            items[i] = CraftTweakerMC.getItemStack(inputs[i]);
        }

        return Ingredient.fromStacks(items);
    }

    public static List<Trade> getTrades(EntityPlayer player)
    {
        return trades.stream().filter(t -> t.isUnlocked(player)).collect(Collectors.toList());
    }
}