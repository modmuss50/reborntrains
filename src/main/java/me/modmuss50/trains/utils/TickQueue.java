package me.modmuss50.trains.utils;

import net.minecraft.entity.Entity;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class TickQueue<T extends Entity> {

	T entity;

	public TickQueue(T entity) {
		this.entity = entity;
	}

	private final Map<Long, List<Consumer<T>>> queue = new HashMap<>();

	public void queue(long time, Consumer<T> consumer) {
		Validate.isTrue(time > 0, "time must be more than 0 ticks");
		time = entity.world.getTime() + time;
		if (entity.removed) {
			throw new RuntimeException("Cannot add to queue on removed entity");
		}
		if (!queue.containsKey(time)) {
			queue.put(time, new ArrayList<>());
		}
		queue.get(time).add(consumer);
	}

	public void tick() {
		if (entity.removed) {
			return;
		}
		long time = entity.world.getTime();
		if (queue.containsKey(time)) {
			queue.get(time).forEach(tConsumer -> tConsumer.accept(entity));
			queue.remove(time);
		}
	}

}
