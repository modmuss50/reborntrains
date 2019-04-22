package me.modmuss50.trains.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import io.netty.buffer.Unpooled;
import me.modmuss50.trains.entity.LocomotiveSpeed;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ContainerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.server.network.packet.CustomPayloadC2SPacket;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

import java.util.Arrays;
import java.util.stream.Collectors;

public class LocomotiveScreen extends ContainerScreen {

	private static final Identifier BG_TEXTURE = new Identifier("reborntrains", "textures/gui/locomotive.png");

	LocomotiveContainer locomotiveContainer;

	public LocomotiveScreen(LocomotiveContainer container) {
		super(container, container.playerInventory, new StringTextComponent("Locomotive"));
		containerWidth = 176;
		containerHeight = 166;
		this.locomotiveContainer = container;
	}

	@Override
	protected void init() {
		super.init();

		buttons.addAll(Arrays.stream(LocomotiveSpeed.values()).map(locomotiveSpeed -> new ButtonWidget(left + 10, top + 10 + (locomotiveSpeed.ordinal() * 25), 40, 20, locomotiveSpeed.name().toLowerCase(), var1 -> setSpeed(locomotiveSpeed))).collect(Collectors.toList()));
	}

	@Override
	public boolean mouseClicked(double double_1, double double_2, int int_1) {
		buttons.forEach(abstractButtonWidget -> abstractButtonWidget.mouseClicked(double_1, double_2, int_1));
		return super.mouseClicked(double_1, double_2, int_1);
	}

	@Override
	protected void drawBackground(float var1, int var2, int var3) {
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		minecraft.getTextureManager().bindTexture(BG_TEXTURE);
		this.blit(left, top, 0, 0, containerWidth, containerHeight);
	}

	private void setSpeed(LocomotiveSpeed speed) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeInt(speed.ordinal());
		buf.writeInt(locomotiveContainer.locomotiveEntity.getEntityId());

		MinecraftClient.getInstance().getNetworkHandler().sendPacket(new CustomPayloadC2SPacket(new Identifier("reborntrains", "set_speed"), buf));
	}
}
