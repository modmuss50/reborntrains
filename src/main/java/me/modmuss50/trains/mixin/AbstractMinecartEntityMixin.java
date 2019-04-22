package me.modmuss50.trains.mixin;

import me.modmuss50.trains.entity.LocomotiveEntity;
import me.modmuss50.trains.entity.LocomotiveSpeed;
import me.modmuss50.trains.train.TrainExtensions;
import me.modmuss50.trains.utils.RailUtils;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractMinecartEntity.class)
public abstract class AbstractMinecartEntityMixin extends Entity implements TrainExtensions {

	public AbstractMinecartEntityMixin(EntityType<?> entityType_1, World world_1) {
		super(entityType_1, world_1);
	}

	private AbstractMinecartEntity towingCart = null;
	private AbstractMinecartEntity engineCart = null;

//	@Inject(method = "method_7513", at = @At("RETURN"))
//	private void moveOnTracks(BlockPos blockPos_1, BlockState blockState_1, CallbackInfo info) {
//		if (engineCart != null) {
//
//			LocomotiveEntity locomotiveEntity = (LocomotiveEntity) engineCart;
//			double targetSpeed = locomotiveEntity.getTargetSpeed();
//			Vec3d velocity = getVelocity();
//			Vec3d engineCartVelocity = engineCart.getVelocity();
//			double engineSpeed = Math.sqrt(squaredHorizontalLength(engineCartVelocity));
//			double speed = Math.sqrt(squaredHorizontalLength(engineCartVelocity));
//
//			AbstractRailBlock currentRail = (AbstractRailBlock) blockState_1.getBlock();
//			RailShape railShape = blockState_1.get(currentRail.getShapeProperty());
//
//			AbstractRailBlock engineRail = (AbstractRailBlock) world.getBlockState(engineCart.getBlockPos()).getBlock();
//			RailShape engineRailShape = world.getBlockState(engineCart.getBlockPos()).get(engineRail.getShapeProperty());
//
//			System.out.println(engineCart.getPos().distanceTo(getPos()));
//
//			if (!RailUtils.isCurve(engineRailShape) && engineRailShape == railShape) {
//				if (engineCart.getPos().distanceTo(getPos()) > 1.4) {
//					if (engineSpeed < 0.01D) {
//						//TODO move towards the engine
//						this.setVelocity(0.02D, 0, 0);
//					} else {
//						this.setVelocity(engineCartVelocity.multiply(1.2));
//					}
//
//				} else {
//					//
//					this.setVelocity(velocity.add(velocity.x / 0.01D * targetSpeed, 0.0D, velocity.z / 0.01D * targetSpeed));
//				}
//			} else if (engineSpeed > 0.01D){
//				if(RailUtils.isCurve(railShape)){
//
//				} else {
//					this.setVelocity(velocity.multiply(0.2));
//				}
//
//			}
//
//		}
//	}

	@Override
	public void setEngineCart(AbstractMinecartEntity engineCart) {
		this.engineCart = engineCart;
	}

	@Override
	public void setTowingCart(AbstractMinecartEntity towingCart) {
		this.towingCart = towingCart;
	}
}
