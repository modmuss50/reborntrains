package me.modmuss50.trains.gui;

import me.modmuss50.trains.entity.LocomotiveEntity;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;

public class LocomotiveContainer extends Container {

	public final PlayerInventory playerInventory;
	public final LocomotiveEntity locomotiveEntity;

	public LocomotiveContainer(int syncID, PlayerEntity playerEntity, LocomotiveEntity entity) {
		super(null, syncID);
		playerInventory = playerEntity.inventory;
		this.locomotiveEntity = entity;
	}

	@Override
	public boolean canUse(PlayerEntity var1) {
		return true;
	}
}
