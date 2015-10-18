package net.ahisame.iwato.config.impl;

import net.ahisame.iwato.config.DelayConfiguration;
import net.ahisame.iwato.config.WaitTime;

/**
 * Return fixed delay configurations
 * 
 * @author a-hisame
 *
 */
public final class FixedDelayConfiguration implements DelayConfiguration {

	private final int maxRetryCount;
	private final WaitTime firstDelayTime;
	private final WaitTime retryWaitTime;

	public FixedDelayConfiguration(int maxRetryCount, WaitTime firstDelayTime, WaitTime retryWaitTime) {
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
		return this.retryWaitTime;
	}

	@Override
	public String toString() {
		return "FixedDelayConfiguration [maxRetryCount=" + maxRetryCount + ", firstDelayTime=" + firstDelayTime
				+ ", retryWaitTime=" + retryWaitTime + "]";
	}

}
