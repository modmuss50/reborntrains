package me.modmuss50.trains;

import me.modmuss50.trains.entity.LocomotiveEntity;
import me.modmuss50.trains.entity.LocomotiveSpeed;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.util.Identifier;

public class ModPackets {

	public static void init() {
		ServerSidePacketRegistry.INSTANCE.register(new Identifier("reborntrains", "set_speed"), (packetContext, packetByteBuf) -> {
			LocomotiveSpeed speed = LocomotiveSpeed.values()[packetByteBuf.readInt()];
			int entityID = packetByteBuf.readInt();
			packetContext.getTaskQueue().execute(() -> {
				LocomotiveEntity entity = (LocomotiveEntity) packetContext.getPlayer().world.getEntityById(entityID);
				entity.setSpeed(speed);
			});
		});
	}
}
