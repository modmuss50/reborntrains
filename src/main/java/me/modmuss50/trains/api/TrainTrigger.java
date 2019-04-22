package me.modmuss50.trains.api;

import me.modmuss50.trains.entity.LocomotiveEntity;
import net.minecraft.util.math.BlockPos;

public interface TrainTrigger {

	void onPass(LocomotiveEntity entity, BlockPos pos);

}
