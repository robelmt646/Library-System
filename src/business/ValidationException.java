package business;

import java.io.Serializable;

public class ValidationException extends Exception implements Serializable {

	public ValidationException() {
		super();
	}
	public ValidationException(String msg) {
		super(msg);
	}
	public ValidationException(Throwable t) {
		super(t);
	}
	private static final long serialVersionUID = 8978723266036027364L;
	
}
