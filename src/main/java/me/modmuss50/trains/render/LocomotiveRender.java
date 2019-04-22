package me.modmuss50.trains.render;

import com.mojang.blaze3d.platform.GlStateManager;
import me.modmuss50.trains.entity.LocomotiveEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class LocomotiveRender extends EntityRenderer<LocomotiveEntity> {

	protected final EntityModel<LocomotiveEntity> model = new LocomotiveModel<>();

	public LocomotiveRender(EntityRenderDispatcher entityRenderDispatcher_1) {
		super(entityRenderDispatcher_1);
		this.field_4673 = 0.7F; //Magic numbers are fun
	}

	@Override
	public void render(LocomotiveEntity locomotive, double double_1, double double_2, double double_3, float float_1, float float_2) {
		GlStateManager.pushMatrix();
		this.bindEntityTexture(locomotive);
		long long_1 = (long) locomotive.getEntityId() * 493286711L;
		long_1 = long_1 * long_1 * 4392167121L + long_1 * 98761L;
		float float_3 = (((float) (long_1 >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		float float_4 = (((float) (long_1 >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		float float_5 = (((float) (long_1 >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		GlStateManager.translatef(float_3, float_4 - 0.3F, float_5);
		double double_4 = MathHelper.lerp((double) float_2, locomotive.prevRenderX, locomotive.x);
		double double_5 = MathHelper.lerp((double) float_2, locomotive.prevRenderY, locomotive.y);
		double double_6 = MathHelper.lerp((double) float_2, locomotive.prevRenderZ, locomotive.z);
		Vec3d vec3d_1 = locomotive.method_7508(double_4, double_5, double_6);
		float float_6 = MathHelper.lerp(float_2, locomotive.prevPitch, locomotive.pitch);
		if (vec3d_1 != null) {
			Vec3d vec3d_2 = locomotive.method_7505(double_4, double_5, double_6, 0.30000001192092896D);
			Vec3d vec3d_3 = locomotive.method_7505(double_4, double_5, double_6, -0.30000001192092896D);
			if (vec3d_2 == null) {
				vec3d_2 = vec3d_1;
			}

			if (vec3d_3 == null) {
				vec3d_3 = vec3d_1;
			}

			double_1 += vec3d_1.x - double_4;
			double_2 += (vec3d_2.y + vec3d_3.y) / 2.0D - double_5;
			double_3 += vec3d_1.z - double_6;
			Vec3d vec3d_4 = vec3d_3.add(-vec3d_2.x, -vec3d_2.y, -vec3d_2.z);
			if (vec3d_4.length() != 0.0D) {
				vec3d_4 = vec3d_4.normalize();
				float_1 = (float) (Math.atan2(vec3d_4.z, vec3d_4.x) * 180.0D / 3.141592653589793D);
				float_6 = (float) (Math.atan(vec3d_4.y) * 73.0D);
			}
		}

		GlStateManager.translatef((float) double_1, (float) double_2 + 0.375F, (float) double_3);
		GlStateManager.rotatef(180.0F - float_1, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotatef(-float_6, 0.0F, 0.0F, 1.0F);
		float float_7 = (float) locomotive.method_7507() - float_2;
		float float_8 = locomotive.method_7521() - float_2;
		if (float_8 < 0.0F) {
			float_8 = 0.0F;
		}

		if (float_7 > 0.0F) {
			GlStateManager.rotatef(MathHelper.sin(float_7) * float_7 * float_8 / 10.0F * (float) locomotive.method_7522(), 1.0F, 0.0F, 0.0F);
		}

		int int_1 = locomotive.getBlockOffset();
		if (this.renderOutlines) {
			GlStateManager.enableColorMaterial();
			GlStateManager.setupSolidRenderingTextureCombine(this.getOutlineColor(locomotive));
		}

		GlStateManager.scalef(-1.0F, -1.0F, 1.0F);
		model.render(locomotive, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GlStateManager.popMatrix();
		if (this.renderOutlines) {
			GlStateManager.tearDownSolidRenderingTextureCombine();
			GlStateManager.disableColorMaterial();
		}

		super.render(locomotive, double_1, double_2, double_3, float_1, float_2);
	}

	@Override
	protected Identifier getTexture(LocomotiveEntity var1) {
		return new Identifier("textures/block/iron_block.png");
	}
}
