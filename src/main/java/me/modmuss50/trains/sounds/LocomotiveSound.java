package me.modmuss50.trains.sounds;

import me.modmuss50.trains.entity.LocomotiveEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.audio.MovingSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.MathHelper;

public class LocomotiveSound extends MovingSoundInstance {

	private final LocomotiveEntity locomotive;
	private float distance = 0.0F;

	private LocomotiveSound(LocomotiveEntity locomotive, SoundEvent soundEvent, float pitch) {
		super(soundEvent, SoundCategory.NEUTRAL);
		this.locomotive = locomotive;
		this.repeat = false;
		this.repeatDelay = 0;
		this.volume = 0.0F;
		this.pitch = pitch;
		this.x = (float) locomotive.x;
		this.y = (float) locomotive.y;
		this.z = (float) locomotive.z;
	}

	@Override
	public boolean shouldAlwaysPlay() {
		return true;
	}

	@Override
	public void tick() {
		if (this.locomotive.removed) {
			this.done = true;
		} else {
			this.x = (float) this.locomotive.x;
			this.y = (float) this.locomotive.y;
			this.z = (float) this.locomotive.z;
			float float_1 = MathHelper.sqrt(Entity.squaredHorizontalLength(this.locomotive.getVelocity()));
			if ((double) float_1 >= 0.01D) {
				this.distance = MathHelper.clamp(this.distance + 0.0025F, 0.0F, 1.0F);
				this.volume = MathHelper.lerp(MathHelper.clamp(float_1, 0.0F, 0.5F), 0.0F, 0.7F);
			} else {
				this.distance = 0.0F;
				this.volume = 0.0F;
			}
		}
	}

	public static void play(LocomotiveEntity entity, SoundEvent event, float pitch) {
		MinecraftClient.getInstance().getSoundManager().play(new LocomotiveSound(entity, event, pitch));
	}

}