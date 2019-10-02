package party.lemons.delivery.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import party.lemons.delivery.DeliveryClient;
import party.lemons.delivery.store.Trades;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Sam on 7/11/2018.
 */
public class ItemStore extends Item
{
	public static String getProfile(ItemStack stack)
	{
		if(stack.hasTagCompound())
		{
			String profile = stack.getTagCompound().getString("_profile");
			return profile;
		}

		return Trades.DEFAULT_PROFILE;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		super.getSubItems(tab, items);
		if(isInCreativeTab(tab))
		{
			for(String profile : Trades.trades.keySet())
			{
				NBTTagCompound tag = new NBTTagCompound();
				tag.setString("_profile", profile);

				ItemStack stack = new ItemStack(this);
				stack.setTagCompound(tag);

				items.add(stack);
			}
		}
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if(stack.hasTagCompound())
		{
			NBTTagCompound tags = stack.getTagCompound();
			if(tags.hasKey("_profile"))
			{
				tooltip.add(TextFormatting.GRAY + tags.getString("_profile"));
			}
		}

		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);

		if(worldIn.isRemote)
		{
			DeliveryClient.sendStoreMessage(getProfile(stack), false);
		}

		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}
}
