package me.modmuss50.trains.train;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.world.WorldTickCallback;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.item.Items;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class TrainManager {

	@SuppressWarnings("unused")
	public static void init(){
//		UseEntityCallback.EVENT.register((playerEntity, world, hand, entity, entityHitResult) -> {
//			if(playerEntity.getEquippedStack(EquipmentSlot.HAND_MAIN).getItem() == Items.DIAMOND){
//				if(entity instanceof AbstractMinecartEntity){
//					rightClick(playerEntity, (AbstractMinecartEntity) entity);
//					return ActionResult.SUCCESS;
//				}
//			}
//			return ActionResult.PASS;
//		});
//
//		WorldTickCallback.EVENT.register(TrainManager::update);
	}

	static Map<PlayerEntity, AbstractMinecartEntity> onGoingLinks = new HashMap<>();
	static List<TrainMember> trains = new ArrayList<>();

	private static void rightClick(PlayerEntity player, AbstractMinecartEntity minecartEntity){
		if(player.world.isClient){
			return;
		}
		if(onGoingLinks.containsKey(player)){
			if(onGoingLinks.get(player).equals(minecartEntity)){
				onGoingLinks.remove(player);
				player.sendMessage(new StringTextComponent("Canceling cart linking"));
			} else {
				AbstractMinecartEntity parent = onGoingLinks.get(player);
				AbstractMinecartEntity child = minecartEntity;
				if(createLink(parent, child)){
					player.sendMessage(new StringTextComponent("Carts linked"));
				} else {
					player.sendMessage(new StringTextComponent("Failed to link carts"));
				}
				onGoingLinks.remove(player);
			}
		} else {
			onGoingLinks.put(player, minecartEntity);
			player.sendMessage(new StringTextComponent("Starting cart linking"));
		}
	}

	private static boolean createLink(AbstractMinecartEntity parent, AbstractMinecartEntity child){
		TrainMember parentMember = new TrainMember(parent);
		TrainMember childMember = new TrainMember(child);
		parentMember.setChild(childMember);

		if(trains.stream().anyMatch(trainMember -> trainMember.getEntity() == parent)){
			return false;
		}

		trains.add(parentMember);

		return true;
	}

	public static void update(World world){
		for(TrainMember train : trains){
			if(!train.isMaster() || !train.isValid()){
				continue;
			}
			if(train.getEntity().world != world || world.isClient){
				continue;
			}

			AbstractMinecartEntity parent = train.getEntity();
			AbstractMinecartEntity child = train.getChild().getEntity();

			TrainExtensions parentExtensions = (TrainExtensions) parent;
			TrainExtensions childExtensions = (TrainExtensions) child;


			parentExtensions.setTowingCart(child);
			childExtensions.setEngineCart(parent);
		}
	}



}
