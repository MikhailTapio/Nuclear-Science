package nuclearscience.common.inventory.container;

import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import electrodynamics.prefab.inventory.container.slot.UpgradeSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.tile.TileGasCentrifuge;

public class ContainerGasCentrifuge extends GenericContainer<TileGasCentrifuge> {

	public ContainerGasCentrifuge(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(6), new SimpleContainerData(3));
	}

	public ContainerGasCentrifuge(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(DeferredRegisters.CONTAINER_GASCENTRIFUGE.get(), id, playerinv, inventory, inventorydata);
	}

	public ContainerGasCentrifuge(MenuType<?> type, int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(type, id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 127, 13));
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 127, 36));
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 101, 54));
		addSlot(new UpgradeSlot(inv, nextIndex(), 153, 14, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed));
		addSlot(new UpgradeSlot(inv, nextIndex(), 153, 34, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed));
		addSlot(new UpgradeSlot(inv, nextIndex(), 153, 54, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed));
	}
}
