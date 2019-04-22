package me.modmuss50.trains.block;

import me.modmuss50.trains.api.TrainTrigger;
import me.modmuss50.trains.entity.LocomotiveEntity;
import me.modmuss50.trains.sounds.LocomotiveSound;
import me.modmuss50.trains.sounds.TrainSounds;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;

import java.util.function.Consumer;

public class HornBlock extends Block implements TrainTrigger {

	public HornBlock() {
		super(FabricBlockSettings.of(Material.METAL).build());
	}

	@Override
	public void onPass(LocomotiveEntity entity, BlockPos pos) {
		if (entity.world.isClient) {
			entity.queue.queue(1, playSound(TrainSounds.HORN, 1F));
			entity.queue.queue(15, playSound(TrainSounds.HORN, 1F));
			entity.queue.queue(40, playSound(TrainSounds.HORN, 0.5F));
		}
	}

	private Consumer<LocomotiveEntity> playSound(SoundEvent event, float pitch) {
		return entity -> {
			if (entity.world.isClient) {
				int time = 4;
				if (pitch < 1) {
					time = 10;
				}
				for (int i = 1; i < time; i++) {
					entity.queue.queue(i, entity1 -> {
						for (int i1 = 0; i1 < 20; i1++) {
							entity1.world.addImportantParticle(ParticleTypes.LARGE_SMOKE, true, entity1.x, entity1.y + 0.1, entity1.z, 0.1D, 0.01D, 0.1D);
						}
					});
				}
				LocomotiveSound.play(entity, event, pitch);
			}
		};
	}
}
