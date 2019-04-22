package me.modmuss50.trains.utils;

import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.util.PacketByteBuf;

public class DataTracking {

	public static TrackedDataHandler<Double> DOUBLE;

	static {
		DOUBLE = new TrackedDataHandler<Double>() {

			@Override
			public void write(PacketByteBuf var1, Double var2) {
				var1.writeDouble(var2);
			}

			@Override
			public Double read(PacketByteBuf var1) {
				return var1.readDouble();
			}

			@Override
			public Double copy(Double var1) {
				return var1;
			}
		};
		TrackedDataHandlerRegistry.register(DOUBLE);
	}

}
