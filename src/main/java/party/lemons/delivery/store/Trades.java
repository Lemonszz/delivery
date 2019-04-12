package party.lemons.delivery.store;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by Sam on 7/11/2018.
 */
@ZenRegister
@ZenClass("mods.Delivery.Store")
public class Trades
{
	public static Map<Store, List<Trade>> trades = new LinkedHashMap<>();

	@ZenMethod
	public static void clear()
	{
		trades.clear();
	}

	@ZenMethod
	public static Trade addTrade(IItemStack result, IIngredient... inputs)
	{
		return addTrade(new Trade(CraftTweakerMC.getItemStack(result), getIngredients(inputs)), "");
	}

	@ZenMethod
	public static Trade addTrade(String gamestage, IItemStack result, IIngredient... inputs)
	{
		return addTrade(new Trade(gamestage, CraftTweakerMC.getItemStack(result), getIngredients(inputs)), "");
	}

	@ZenMethod
	public static Trade addTrade(IItemStack result)
	{
		return addTrade(new Trade(CraftTweakerMC.getItemStack(result)), "");
	}

	@ZenMethod
	public static Trade addTrade(String gamestage, IItemStack result)
	{
		return addTrade(new Trade(gamestage, CraftTweakerMC.getItemStack(result)), "");
	}

	@ZenMethod
	public static Trade addTrade(IItemStack result, String store, IIngredient... inputs)
	{
		return addTrade(new Trade(CraftTweakerMC.getItemStack(result), getIngredients(inputs)), store);
	}

	@ZenMethod
	public static Trade addTrade(String gamestage, IItemStack result, String store, IIngredient... inputs)
	{
		return addTrade(new Trade(gamestage, CraftTweakerMC.getItemStack(result), getIngredients(inputs)), store);
	}

	@ZenMethod
	public static Trade addTrade(IItemStack result, String store)
	{
		return addTrade(new Trade(CraftTweakerMC.getItemStack(result)), store);
	}

	@ZenMethod
	public static Trade addTrade(String gamestage, IItemStack result, String store)
	{
		return addTrade(new Trade(gamestage, CraftTweakerMC.getItemStack(result)), store);
	}

	private static Trade addTrade(Trade trade, String storeName)
	{
		if(storeName.isEmpty())
		{
			storeName = "_store";
		}

		if(!trades.containsKey(getStore(storeName)))
		{
			Store s = new Store(storeName);
			trades.put(s, new ArrayList<>());
		}

		trades.get(getStore(storeName)).add(trade);

		return trade;
	}

	@ZenMethod
	public static void setStoreIcon(String storeName, IItemStack storeIcon)
	{
		if(!trades.containsKey(getStore(storeName)))
		{
			Store s = new Store(storeName);
			trades.put(s, new ArrayList<>());
		}

		Store s = getStore(storeName);
		s.setItemStack(CraftTweakerMC.getItemStack(storeIcon));
	}

	public static Ingredient[] getIngredients(IIngredient... inputs)
	{
		Ingredient[] ingreds = new Ingredient[inputs.length];
		for(int i = 0; i < inputs.length; i++)
		{
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
		return getTrades(player, "_store");
	}

	public static List<Trade> getTrades(EntityPlayer player, String store)
	{
		List<Trade> entries = trades.get(getStore(store));
		if(entries == null)
		{
			return Collections.emptyList();
		}

		return entries.stream().filter(t->t.isUnlocked(player)).collect(Collectors.toList());
	}

	public static Store getStore(String name)
	{
		for(Store store : trades.keySet())
		{
			if(store.getName().equalsIgnoreCase(name)) return store;
		}

		return null;
	}

	public static int getStoreIndex(String store)
	{
		int i = 0;
		for(Store s : trades.keySet())
		{
			if(s.getName().equalsIgnoreCase(store)) return i;

			i++;
		}

		return -1;
	}

	public static String getStoreById(int data)
	{
		int i = 0;
		for(Store s : trades.keySet())
		{
			if(i == data) return s.getName();

			i++;
		}

		return "_store";
	}

	public static int transformStoreIndex(int index)
	{
		if(index < 0)
		{
			return trades.size() - 1;
		}

		if(index >= trades.size())
		{
			return 0;
		}

		return index;

	}

	public static void sort()
	{
	}
}