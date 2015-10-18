package net.ahisame.iwato.config.impl;

import net.ahisame.iwato.config.DelayConfiguration;
import net.ahisame.iwato.config.WaitTime;

/**
 * Using simple exponential-backoff strategy.
 * 
 * @author a-hisame
 *
 */
public final class ExponentialBackoffDelayConfiguration implements DelayConfiguration {

	private final int maxRetryCount;
	private final WaitTime firstDelayTime;
	private final WaitTime retryWaitTime;

	public ExponentialBackoffDelayConfiguration(int maxRetryCount, WaitTime firstDelayTime, WaitTime retryWaitTime) {
		super();
		this.maxRetryCount = maxRetryCount;
		this.firstDelayTime = firstDelayTime;
		this.retryWaitTime = retryWaitTime;
	}

	@Override
	public int getMaxRetryCount() {
		return this.maxRetryCount;
	}

	@Override
	public WaitTime getFirstDelayTime() {
		return this.firstDelayTime;
	}

	@Override
	public WaitTime getRetryWaitTime(int trialCount) {
		int k = 1;
		for (int i = 0; i < trialCount; i++) {
			k = k * 2;
		}
		return this.retryWaitTime.times(k);
	}

	@Override
	public String toString() {
		return "ExponentialBackoffDelayConfiguration [maxRetryCount=" + maxRetryCount + ", firstDelayTime="
				+ firstDelayTime + ", retryWaitTime=" + retryWaitTime + "]";
	}

}
