package me.modmuss50.trains.render;

import net.minecraft.client.model.Cuboid;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class LocomotiveModel<T extends Entity> extends EntityModel<T> {

	private List<Cuboid> list = new ArrayList<>();

	public LocomotiveModel() {
		list.add(create(-10.0F, -1.0F, -8.0F, 20, 1, 16, 0.0F));
		list.add(create(-9.0F, -9.0F, -7.0F, 18, 8, 14, 0.0F));
		list.add(create(4.0F, -15.0F, -2.0F, 4, 6, 4, 0.0F));

		list.add(create(-7.0F, 0.0F, -7.0F, 2, 1, 1, 0.0F));
		list.add(create(-7.0F, 0.0F, 6.0F, 2, 1, 1, 0.0F));
		list.add(create(5.0F, 0.0F, 6.0F, 2, 1, 1, 0.0F));
		list.add(create(5.0F, 0.0F, -7.0F, 2, 1, 1, 0.0F));
	}

	@Override
	public void render(T entity_1, float float_1, float float_2, float float_3, float float_4, float float_5, float float_6) {
		list.forEach(cuboid -> cuboid.render(float_6));
	}

	private Cuboid create(float f1, float f2, float f3, int i4, int i5, int i6, float f7) {
		Cuboid cuboid = new Cuboid(this, 0, 0);
		//cuboid.setRotationPoint(rotationPointX, rotationPointY, rotationPointZ);
		cuboid.addBox(f1, f2, f3, i4, i5, i6, f7);
		return cuboid;
	}
}
