public class MetricsException extends Exception{
	private static final long serialVersionUID = 7024625504788659986L;
	
	public MetricsException(String consoleOutput) {
		super(consoleOutput);
	}
}