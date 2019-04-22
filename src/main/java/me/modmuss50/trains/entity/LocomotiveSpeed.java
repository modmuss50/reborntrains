package me.modmuss50.trains.entity;

public enum LocomotiveSpeed {
	STOP(0),
	SLOW(0.01D),
	FAST(0.06D);

	public double speed;

	LocomotiveSpeed(double speed) {
		this.speed = speed;
	}
}
