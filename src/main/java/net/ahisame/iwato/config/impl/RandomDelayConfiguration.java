package net.ahisame.iwato.config.impl;

import java.util.Random;

import net.ahisame.iwato.config.DelayConfiguration;
import net.ahisame.iwato.config.WaitTime;

/**
 * It is a configuration with randomization retryWaitSeconds. <br>
 * getRetryWaitSeconds method returns between (retryWaitSeconds - deltaSeconds)
 * and (retryWaitSeconds + deltaSeconds).
 * 
 * @author a-hisame
 *
 */
public final class RandomDelayConfiguration implements DelayConfiguration {

	private final int maxRetryCount;
	private final WaitTime firstDelayTime;
	private final WaitTime retryWaitTime;
	private final WaitTime deltaTime;
	private final Random random;

	public RandomDelayConfiguration(int maxRetryCount, WaitTime firstDelayTime, WaitTime retryWaitTime,
			WaitTime deltaTime) {
		super();
		this.maxRetryCount = maxRetryCount;
		this.firstDelayTime = firstDelayTime;
		this.retryWaitTime = retryWaitTime;
		this.deltaTime = deltaTime;
		this.random = new Random();
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
		long diff = random.nextInt(2 * (int) deltaTime.toMilliseconds() + 1) - deltaTime.toMilliseconds();
		return this.retryWaitTime.add(WaitTime.milliseconds(diff));
	}

	@Override
	public String toString() {
		return "RandomDelayConfiguration [maxRetryCount=" + maxRetryCount + ", firstDelayTime=" + firstDelayTime
				+ ", retryWaitTime=" + retryWaitTime + ", deltaTime=" + deltaTime + ", random=" + random + "]";
	}

}
