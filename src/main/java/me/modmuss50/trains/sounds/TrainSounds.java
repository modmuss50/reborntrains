package me.modmuss50.trains.sounds;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TrainSounds {

	public static SoundEvent HORN;
	public static SoundEvent MOVE;
	public static SoundEvent CLANK;

	@SuppressWarnings("unused")
	public static void init() {
		HORN = register(new Identifier("reborntrains", "horn"));
		MOVE = register(new Identifier("reborntrains", "move"));
		CLANK = register(new Identifier("reborntrains", "clank"));
	}

	private static SoundEvent register(Identifier identifier) {
		return Registry.register(Registry.SOUND_EVENT, identifier, new SoundEvent(identifier));
	}
}
