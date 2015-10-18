package net.ahisame.iwato.function;

public interface Mapper<A, B> {
	B convert(A a);
}
