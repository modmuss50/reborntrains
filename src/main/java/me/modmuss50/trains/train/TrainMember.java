package me.modmuss50.trains.train;

import net.minecraft.entity.vehicle.AbstractMinecartEntity;

import javax.annotation.Nullable;

public class TrainMember {

	private final AbstractMinecartEntity entity;

	@Nullable
	TrainMember parent;

	@Nullable
	TrainMember child;

	public TrainMember(AbstractMinecartEntity entity) {
		this.entity = entity;
	}

	public AbstractMinecartEntity getEntity() {
		return entity;
	}

	@Nullable
	public TrainMember getParent() {
		return parent;
	}

	public void setParent(@Nullable TrainMember parent) {
		this.parent = parent;
	}

	@Nullable
	public TrainMember getChild() {
		return child;
	}

	public void setChild(@Nullable TrainMember child) {
		this.child = child;
	}

	public boolean isMaster(){
		return getParent() == null;
	}

	public boolean isValid(){
		return getChild() != null && !getChild().getEntity().removed && !getEntity().removed;
	}


}