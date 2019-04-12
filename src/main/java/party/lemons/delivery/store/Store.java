package party.lemons.delivery.store;


import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class Store
{
	private String name;
	private ItemStack stack = new ItemStack(Items.EMERALD);

	public Store(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Store)
		{
			return ((Store) obj).getName().equalsIgnoreCase(this.getName());
		}

		return super.equals(obj);
	}

	public ItemStack getItemStack()
	{
		return stack;
	}

	public void setItemStack(ItemStack itemStack)
	{
		this.stack = itemStack;
	}
}
