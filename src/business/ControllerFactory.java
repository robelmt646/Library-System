package business;

public class ControllerFactory {
	private static ControllerInterface controller = new SystemController();
	public static ControllerInterface of() {
		return controller;
	}
}
