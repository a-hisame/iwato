package net.ahisame.iwato.callback;

import net.ahisame.iwato.function.TaskProcedureResult;
import net.ahisame.iwato.function.TaskResultCode;

public class Callbacks<T> {

	private static Initializer nullInitializer() {
		return new Initializer() {
			@Override
			public void initialize() {
				// nothing to do
			}
		};
	}

	private static <T> SuccessFinalizer<T> nullSuccessFinalizer() {
		return new SuccessFinalizer<T>() {
			@Override
			public void callback(TaskProcedureResult<T> result) {
				// nothing to do
			}
		};
	}

	private static FailedFinalizer nullFailedFinalizer() {
		return new FailedFinalizer() {
			@Override
			public void callback(TaskResultCode result) {
				// nothing to do
			}
		};
	}

	public static class Builder<T> {
		private Initializer initializer;
		private SuccessFinalizer<T> successFinalizer;
		private FailedFinalizer failedFinalizer;

		public Builder() {
			this.initializer = nullInitializer();
			this.successFinalizer = nullSuccessFinalizer();
			this.failedFinalizer = nullFailedFinalizer();
		}

		public Builder<T> initializer(Initializer initializer) {
			this.initializer = initializer;
			return this;
		}

		public Builder<T> setSuccessFinalizer(SuccessFinalizer<T> successFinalizer) {
			this.successFinalizer = successFinalizer;
			return this;
		}

		public Builder<T> setFailedFinalizer(FailedFinalizer failedFinalizer) {
			this.failedFinalizer = failedFinalizer;
			return this;
		}

		public Callbacks<T> build() {
			return new Callbacks<T>(initializer, successFinalizer, failedFinalizer);
		}

	}

	public static <T> Callbacks<T> empty() {
		return new Builder<T>().build();
	}

	private final Initializer initializer;
	private final SuccessFinalizer<T> successFinalizer;
	private final FailedFinalizer failedFinalizer;

	private Callbacks(Initializer initializer, SuccessFinalizer<T> successFinalizer, FailedFinalizer failedFinalizer) {
		super();
		this.initializer = initializer;
		this.successFinalizer = successFinalizer;
		this.failedFinalizer = failedFinalizer;
	}

	public Initializer getInitializer() {
		return initializer;
	}

	public SuccessFinalizer<T> getSuccessFinalizer() {
		return successFinalizer;
	}

	public FailedFinalizer getFailedFinalizer() {
		return failedFinalizer;
	}

}
