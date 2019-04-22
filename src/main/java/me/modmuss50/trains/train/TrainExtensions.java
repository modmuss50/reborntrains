package me.modmuss50.trains.train;

import net.minecraft.entity.vehicle.AbstractMinecartEntity;

public interface TrainExtensions {

	void setEngineCart(AbstractMinecartEntity engineCart);

	void setTowingCart(AbstractMinecartEntity towingCart);

}
