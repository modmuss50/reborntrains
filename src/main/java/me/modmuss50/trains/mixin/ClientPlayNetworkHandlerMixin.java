package me.modmuss50.trains.mixin;

import me.modmuss50.trains.Mod;
import me.modmuss50.trains.entity.LocomotiveEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.packet.EntitySpawnS2CPacket;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

	@Shadow
	private ClientWorld world;

	//Make a PR for this shit
	@Inject(method = "onEntitySpawn", at = @At("RETURN"))
	private void onEntitySpawn(EntitySpawnS2CPacket packet, CallbackInfo info) {
		if (packet.getEntityTypeId() == Mod.locomotiveEntityType) {
			LocomotiveEntity entity = new LocomotiveEntity(this.world, packet.getX(), packet.getY(), packet.getZ());

			int int_1 = packet.getId();
			entity.method_18003(packet.getX(), packet.getY(), packet.getZ());
			entity.pitch = (float) (packet.getPitch() * 360) / 256.0F;
			entity.yaw = (float) (packet.getYaw() * 360) / 256.0F;
			entity.setEntityId(int_1);
			entity.setUuid(packet.getUuid());
			this.world.addEntity(int_1, entity);
		}
	}

}
