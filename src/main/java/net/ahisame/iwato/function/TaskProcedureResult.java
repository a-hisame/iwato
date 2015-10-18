package net.ahisame.iwato.function;

public abstract class TaskProcedureResult<T> {

	private final T value;
	private final TaskResultCode code;

	private TaskProcedureResult(T value, TaskResultCode code) {
		super();
		this.value = value;
		this.code = code;
	}

	public static TaskProcedureResult<Unit> success() {
		return new Just<Unit>(Unit.VOID, TaskResultCode.SUCCESS);
	}

	public static <T> TaskProcedureResult<T> success(T value) {
		return new Just<T>(value, TaskResultCode.SUCCESS);
	}

	public static <T> TaskProcedureResult<T> retry() {
		return new Nothing<T>(TaskResultCode.RETRY);
	}

	public static <T> TaskProcedureResult<T> failWithRetry() {
		return new Nothing<T>(TaskResultCode.FAIL_WITH_RETRY);
	}

	public static <T> TaskProcedureResult<T> failWithCancel() {
		return new Nothing<T>(TaskResultCode.FAIL_WITH_CANCEL);
	}

	public abstract T getValue();

	public abstract <B> TaskProcedureResult<B> map(Mapper<T, B> mapper);

	public abstract boolean isJust();

	public abstract <B> TaskProcedureResult<B> convertNothing();

	public TaskResultCode getCode() {
		return code;
	}

	private static class Just<T> extends TaskProcedureResult<T> {

		private Just(T value, TaskResultCode code) {
			super(value, code);
		}

		@Override
		public T getValue() {
			return super.value;
		}

		@Override
		public boolean isJust() {
			return true;
		}

		@Override
		public <B> TaskProcedureResult<B> map(Mapper<T, B> mapper) {
			return TaskProcedureResult.success(mapper.convert(getValue()));
		}

		@Override
		public <B> TaskProcedureResult<B> convertNothing() {
			throw new IllegalStateException("Just cannot convert to Nothing");
		}

		@Override
		public String toString() {
			return "Just [value=" + getValue() + ", code=" + super.code + "]";
		}

	}

	private static class Nothing<T> extends TaskProcedureResult<T> {

		private Nothing(TaskResultCode code) {
			super(null, code);
		}

		@Override
		public T getValue() {
			throw new IllegalStateException("Nothing has no value");
		}

		@Override
		public boolean isJust() {
			return false;
		}

		@Override
		public <B> TaskProcedureResult<B> map(Mapper<T, B> mapper) {
			return new Nothing<B>(super.code);
		}

		@Override
		public <B> TaskProcedureResult<B> convertNothing() {
			return new Nothing<B>(super.code);
		}

		@Override
		public String toString() {
			return "Nothing[code=" + super.code + "]";
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskProcedureResult other = (TaskProcedureResult) obj;
		if (code != other.code)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
