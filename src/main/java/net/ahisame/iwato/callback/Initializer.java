package net.ahisame.iwato.callback;

/**
 * {@link Initializer#initialize()} is called before each delayTask execution on
 * same Thread (If you use ThreadLocal Parameters, should reset those values by
 * this implementations).
 * 
 * @author a-hisame
 *
 */
public interface Initializer {
	void initialize();
}
