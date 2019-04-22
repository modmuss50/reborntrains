package me.modmuss50.trains.block;

import me.modmuss50.trains.api.TrainTrigger;
import me.modmuss50.trains.entity.LocomotiveEntity;
import me.modmuss50.trains.sounds.TrainSounds;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.RailPlacementHelper;
import net.minecraft.sound.SoundCategory;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;

public class SwitchBlock extends Block implements TrainTrigger {

	public SwitchBlock() {
		super(FabricBlockSettings.of(Material.METAL).build());
	}

	@Override
	public void onPass(LocomotiveEntity entity, BlockPos pos) {
		if (entity.world.isClient) {
			return;
		}
		BlockPos railPos = pos.up(); //TODO dont do this
		BlockState blockState = entity.world.getBlockState(railPos);
		if (!blockState.matches(BlockTags.RAILS)) {
			return;
		}

		BlockState state = new RailPlacementHelper(entity.world, railPos, blockState).updateBlockState(true, false).getBlockState();
		entity.world.setBlockState(railPos, state);

		entity.world.playSound(null, railPos.getX(), railPos.getY(), railPos.getZ(), TrainSounds.CLANK, SoundCategory.BLOCKS, 0.3F, 2F);

		entity.queue.queue(20, entity1 -> {
			BlockState state1 = new RailPlacementHelper(entity1.world, railPos, blockState).updateBlockState(false, false).getBlockState();
			entity1.world.setBlockState(railPos, state1);
			entity1.world.playSound(null, railPos.getX(), railPos.getY(), railPos.getZ(), TrainSounds.CLANK, SoundCategory.BLOCKS, 0.3F, 2F);
		});
	}
}
