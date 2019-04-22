package me.modmuss50.trains;

import me.modmuss50.trains.block.HornBlock;
import me.modmuss50.trains.block.SwitchBlock;
import me.modmuss50.trains.entity.LocomotiveEntity;
import me.modmuss50.trains.gui.LocomotiveContainer;
import me.modmuss50.trains.gui.LocomotiveScreen;
import me.modmuss50.trains.item.LocomotiveItem;
import me.modmuss50.trains.render.LocomotiveRender;
import net.fabricmc.fabric.api.client.render.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class Mod {

	public static EntityType<LocomotiveEntity> locomotiveEntityType;
	public static LocomotiveItem locomotiveItem;
	public static HornBlock hornBlock;
	public static SwitchBlock switchBlock;

	@SuppressWarnings("unused")
	public static void init() {
		locomotiveEntityType = Registry.register(Registry.ENTITY_TYPE, new Identifier("reborntrains", "locomotive"),
		                                         FabricEntityTypeBuilder.create(EntityCategory.MISC, (EntityType.EntityFactory<LocomotiveEntity>) LocomotiveEntity::new)
			                                         .size(new EntitySize(1, 1, true))
			                                         .trackable(64, 1, true)
			                                         .build());



		EntityRendererRegistry.INSTANCE.register(LocomotiveEntity.class, (entityRenderDispatcher, context) -> new LocomotiveRender(entityRenderDispatcher));

		locomotiveItem = new LocomotiveItem();
		Registry.register(Registry.ITEM, new Identifier("reborntrains", "locomotive_item"), locomotiveItem);

		ContainerProviderRegistry.INSTANCE.registerFactory(new Identifier("reborntrains", "locomotive_gui"), (syncID, identifier, playerEntity, packetByteBuf) -> {
			LocomotiveEntity entity = (LocomotiveEntity) playerEntity.world.getEntityById(packetByteBuf.readInt());
			return new LocomotiveContainer(syncID, playerEntity, entity);
		});

		ScreenProviderRegistry.INSTANCE.registerFactory(new Identifier("reborntrains", "locomotive_gui"), LocomotiveScreen::new);

		hornBlock = new HornBlock();
		Registry.register(Registry.BLOCK, new Identifier("reborntrains", "horn"), hornBlock);
		Registry.register(Registry.ITEM, new Identifier("reborntrains", "horn"), new BlockItem(hornBlock, new Item.Settings().itemGroup(ItemGroup.TRANSPORTATION)));

		switchBlock = new SwitchBlock();
		Registry.register(Registry.BLOCK, new Identifier("reborntrains", "switch"), switchBlock);
		Registry.register(Registry.ITEM, new Identifier("reborntrains", "switch"), new BlockItem(switchBlock, new Item.Settings().itemGroup(ItemGroup.TRANSPORTATION)));
	}

}
