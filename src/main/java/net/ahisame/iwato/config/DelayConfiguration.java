package net.ahisame.iwato.config;

public interface DelayConfiguration {
	public int getMaxRetryCount();

	public WaitTime getFirstDelayTime();

	public WaitTime getRetryWaitTime(int trialCount);
}
