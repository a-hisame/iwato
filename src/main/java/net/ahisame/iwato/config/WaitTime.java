package net.ahisame.iwato.config;

import java.util.concurrent.TimeUnit;

public final class WaitTime {

	private final long duration;
	private final TimeUnit unit;

	private WaitTime(long duration, TimeUnit unit) {
		this.duration = duration;
		this.unit = unit;
	}

	public static WaitTime seconds(long duration) {
		return new WaitTime(duration, TimeUnit.SECONDS);
	}

	public static WaitTime milliseconds(long duration) {
		return new WaitTime(duration, TimeUnit.MICROSECONDS);
	}

	public WaitTime add(WaitTime t) {
		if (this.unit == t.unit) {
			return new WaitTime(this.duration + t.duration, this.unit);
		}
		return new WaitTime(this.toMilliseconds() + t.toMilliseconds(), TimeUnit.MILLISECONDS);
	}

	public WaitTime times(long v) {
		return new WaitTime(this.duration * v, this.unit);
	}

	public long toMilliseconds() {
		return this.unit.toMillis(this.duration);
	}

	@Override
	public String toString() {
		return "WaitTime [duration=" + duration + ", unit=" + unit + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (duration ^ (duration >>> 32));
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WaitTime other = (WaitTime) obj;
		if (duration != other.duration)
			return false;
		if (unit != other.unit)
			return false;
		return true;
	}

}
