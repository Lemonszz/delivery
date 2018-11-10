package party.lemons.delivery.store;

import crafttweaker.api.item.IItemStack;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.items.ItemStackHandler;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;

/**
 * Created by Sam on 7/11/2018.
 */
@ZenClass
public class Trade
{
    public ItemStack result = ItemStack.EMPTY;
    public Ingredient[] cost;
    public String gameStage;

    public Trade(ItemStack result, Ingredient... cost)
    {
        this("", result, cost);
    }

    public Trade(String gamestage, ItemStack result, Ingredient... cost)
    {
        this.result = result;
        this.cost = cost;
        this.gameStage = gamestage;
    }

    @ZenMethod
    public Trade addOre(String dict, int size)
    {
        SizedOreIngredient ingredient = new SizedOreIngredient(dict, size);
        return addIngredient(ingredient);
    }

    @ZenMethod
    public Trade addItem(IItemStack... stacks)
    {
        return addIngredient(Trades.getMergedIngredient(stacks));
    }

    @ZenMethod
    public Trade addIngredient(Ingredient ingredient)
    {
        int ind = cost.length;
        cost = Arrays.copyOf(cost, cost.length + 1);
        cost[ind] = ingredient;

        return this;
    }

    public boolean canPurchase(EntityPlayer player)
    {
        if(!isUnlocked(player))
            return false;

        for(Ingredient ingredient : cost)
        {
            boolean found = false;
            for (ItemStack stack : player.inventory.mainInventory)
            {
                if(ingredient.apply(stack) && stack.getCount() >= ingredient.getMatchingStacks()[0].getCount())
                {
                    found = true;
                    break;
                }
            }

            if(!found)
                return false;
        }
        return true;
    }

    public void takeCost(EntityPlayer player)
    {
        for(Ingredient ingredient : cost)
        {
            for (ItemStack stack : player.inventory.mainInventory)
            {
                if (ingredient.apply(stack)  && stack.getCount() >= ingredient.getMatchingStacks()[0].getCount())
                {
                    stack.shrink(ingredient.getMatchingStacks()[0].getCount());
                    break;
                }
            }
        }

        player.inventoryContainer.detectAndSendChanges();
    }

    public boolean isUnlocked(EntityPlayer player)
    {
        if(Loader.isModLoaded("gamestages") && !gameStage.isEmpty())
        {
            return GameStageHelper.hasStage(player, gameStage);
        }

        return true;
    }

    public ItemStackHandler getResultAsInventory()
    {
        ItemStackHandler handler = new ItemStackHandler(1);
        handler.setStackInSlot(0, result);

        return handler;
    }

    public ItemStackHandler getCostAsInventory()
    {
        ItemStackHandler handler =new ItemStackHandler(cost.length);
        for(int i = 0; i < handler.getSlots(); i++)
        {
            ItemStack stack = cost[i].getMatchingStacks()[0];
            handler.setStackInSlot(i, stack.copy());
        }

        return handler;
    }
}
