package me.modmuss50.trains.entity;

import me.modmuss50.trains.Mod;
import me.modmuss50.trains.api.TrainTrigger;
import me.modmuss50.trains.sounds.LocomotiveSound;
import me.modmuss50.trains.sounds.TrainSounds;
import me.modmuss50.trains.utils.DataTracking;
import me.modmuss50.trains.utils.TickQueue;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Arrays;

public class LocomotiveEntity extends AbstractMinecartEntity {

	public TickQueue<LocomotiveEntity> queue = new TickQueue<>(this);
	private BlockPos lastTriggerCheck = BlockPos.ORIGIN;

	private static final TrackedData<Double> TARGET_SPEED = DataTracker.registerData(LocomotiveEntity.class, DataTracking.DOUBLE);

	private boolean backwards = false;

	public LocomotiveEntity(EntityType<?> entityType_1, World world_1) {
		super(Mod.locomotiveEntityType, world_1);
	}

	public LocomotiveEntity(World world_1, double x, double y, double z) {
		super(Mod.locomotiveEntityType, world_1, x, y, z);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(TARGET_SPEED, 0D);
	}

	@Override
	public Type getMinecartType() {
		return Type.FURNACE;
	}

	@Override
	public void tick() {
		super.tick();
		queue.tick();

		if (world.isClient) {

			if (dataTracker.get(TARGET_SPEED) > 0) {
				int smoke = 4;
				if (dataTracker.get(TARGET_SPEED) >= LocomotiveSpeed.FAST.speed) {
					smoke = 1;
				}
				if (world.getTime() % smoke == 0) {
					smoke();
				}
			}

			if (world.getTime() % 18 == 0) {
				LocomotiveSound.play(this, TrainSounds.MOVE, 1F);
			}

		}

		//Prevents it running every tick on the same blocks
		if (!lastTriggerCheck.equals(getBlockPos())) {
			lastTriggerCheck = getBlockPos();
			checkTrigger();
		}

	}

	public void checkTrigger() {
		final LocomotiveEntity entity = this;
		Arrays.stream(Direction.values()).forEach(direction -> {
			BlockState state = world.getBlockState(getBlockPos().offset(direction));
			if (state.getBlock() instanceof TrainTrigger) {
				((TrainTrigger) state.getBlock()).onPass(entity, getBlockPos().offset(direction));
			}
		});

	}

	public void smoke() {
		world.addImportantParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, true, x + Math.cos(yaw * Math.PI / 180) * 0.3, y + 1, z + Math.sin(yaw * Math.PI / 180) * 0.3, 0.0D, 0.02D, 0.0D);
	}

	@Override
	public boolean interact(PlayerEntity playerEntity, Hand hand) {
		if (!playerEntity.world.isClient) {
			ContainerProviderRegistry.INSTANCE.openContainer(new Identifier("reborntrains", "locomotive_gui"), playerEntity, packetByteBuf -> packetByteBuf.writeInt(getEntityId()));
		}
		return super.interact(playerEntity, hand);
	}

	@Override
	public boolean startRiding(Entity entity_1) {
		return false; //Nope
	}

	public void setTargetSpeed(LocomotiveSpeed speed) {
		dataTracker.set(TARGET_SPEED, speed.speed);
	}

	public double getTargetSpeed(){
		return dataTracker.get(TARGET_SPEED);
	}

	@Override
	protected void method_7513(BlockPos blockPos, BlockState blockState) {
		super.method_7513(blockPos, blockState);

		double targetSpeed = dataTracker.get(TARGET_SPEED);

		AbstractRailBlock currentRail = (AbstractRailBlock) blockState.getBlock();
		RailShape railShape = blockState.get(currentRail.getShapeProperty());

		Vec3d velocity = this.getVelocity();
		double speed = Math.sqrt(squaredHorizontalLength(velocity));
		if (speed > 0.01D) {
			if (targetSpeed == 0) {

				//TODO bring it to a stop?
			} else {
				this.setVelocity(velocity.add(velocity.x / speed * targetSpeed, 0.0D, velocity.z / speed * targetSpeed));
			}

		} else if (targetSpeed > 0) {
			velocity = this.getVelocity();
			double velocityX = velocity.x;
			double velocityY = velocity.z;
			if (railShape == RailShape.EAST_WEST) {
				//TODO check these values are correct
				if (backwards) {
					velocityX = -0.02D;
				} else {
					velocityX = 0.02D;
				}
			} else {
				if (railShape != RailShape.NORTH_SOUTH) {
					return;
				}
				if (backwards) {
					velocityY = 0.02D;
				} else {
					velocityY = -0.02D;
				}
			}

			this.setVelocity(velocityX, velocity.y, velocityY);
		}

	}

}
