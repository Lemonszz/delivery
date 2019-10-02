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
	public static final String DEFAULT_STORE = "_store";
	public static final String DEFAULT_PROFILE = "_default";


	public static Map<String, Map<Store, List<Trade>>> trades = new LinkedHashMap<>();
	private static String profile = "_default";

	@ZenMethod
	public static void clear()
	{
		trades.clear();
	}

	@ZenMethod
	public static void setProfile(String profile)
	{
		Trades.profile = profile;
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

		if(!trades.containsKey(profile))
		{
			trades.put(profile, new LinkedHashMap<>());
		}
		Map<Store, List<Trade>> profileTrades = trades.get(profile);

		if(!profileTrades.containsKey(getStore(storeName, profile)))
		{
			Store s = new Store(storeName);
			profileTrades.put(s, new ArrayList<>());
		}

		profileTrades.get(getStore(storeName, profile)).add(trade);

		return trade;
	}

	@ZenMethod
	public static void setStoreIcon(String storeName, IItemStack storeIcon)
	{
		if(!trades.containsKey(profile))
		{
			trades.put(profile, new LinkedHashMap<>());
		}
		Map<Store, List<Trade>> profileTrades = trades.get(profile);

		if(!profileTrades.containsKey(getStore(storeName, profile)))
		{
			Store s = new Store(storeName);
			profileTrades.put(s, new ArrayList<>());
		}

		Store s = getStore(storeName, profile);
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

	public static List<Trade> getTrades(EntityPlayer player, String store, String profile)
	{
		if(!trades.containsKey(profile))
		{
			trades.put(profile, new LinkedHashMap<>());
		}
		Map<Store, List<Trade>> profileTrades = trades.get(profile);

		List<Trade> entries = profileTrades.get(getStore(store, profile));
		if(entries == null)
		{
			return Collections.emptyList();
		}

		return entries.stream().filter(t->t.isUnlocked(player)).collect(Collectors.toList());
	}

	public static Store getStore(String name, String profile)
	{
		if(!trades.containsKey(profile))
		{
			trades.put(profile, new LinkedHashMap<>());
		}

		Map<Store, List<Trade>> profileTrades = trades.get(profile);

		for(Store store : profileTrades.keySet())
		{
			if(store.getName().equalsIgnoreCase(name)) return store;
		}

		return null;
	}

	public static int getStoreIndex(String store, String profile)
	{
		if(!trades.containsKey(profile))
		{
			trades.put(profile, new LinkedHashMap<>());
		}

		Map<Store, List<Trade>> profileTrades = trades.get(profile);

		int i = 0;
		for(Store s : profileTrades.keySet())
		{
			if(s.getName().equalsIgnoreCase(store)) return i;

			i++;
		}

		return -1;
	}

	public static String getStoreById(int data, String profile)
	{
		if(!trades.containsKey(profile))
		{
			trades.put(profile, new LinkedHashMap<>());
		}
		Map<Store, List<Trade>> profileTrades = trades.get(profile);

		int i = 0;
		for(Store s : profileTrades.keySet())
		{
			if(i == data) return s.getName();

			i++;
		}

		return DEFAULT_STORE;
	}

	public static String getProfileById(int data)
	{
		int i = 0;
		for(String s : trades.keySet())
		{
			if(i == data)
				return s;
			i++;
		}

		return DEFAULT_PROFILE;
	}

	public static int getProfileIndex(String profile)
	{
		int i = 0;
		for(String s : trades.keySet())
		{
			if(s.equalsIgnoreCase(profile)) return i;

			i++;
		}

		return -1;
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