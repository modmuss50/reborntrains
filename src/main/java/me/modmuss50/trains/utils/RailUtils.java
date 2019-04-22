package me.modmuss50.trains.utils;

import net.minecraft.block.enums.RailShape;

public class RailUtils {

	public static boolean isCurve(RailShape shape){
		return shape != RailShape.NORTH_SOUTH && shape != RailShape.EAST_WEST;
	}

}
