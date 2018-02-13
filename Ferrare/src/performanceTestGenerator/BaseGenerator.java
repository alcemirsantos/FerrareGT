package performanceTestGenerator;

import performanceTestRepresentation.TestPlan;
import functionalTestRepresentation.TestBattery;

/**
 * Classe abstract do Gerador de Testes de Desempenho e Estresse
 * @author Ismayle de Sousa Santos
 */
public abstract class BaseGenerator {

	protected TestBattery battery;

	/**
	 * Construtor Completo
	 * @param battery TestBattery
	 */
	public BaseGenerator(TestBattery battery) {
		super();
		this.battery = battery;
	}

	public BaseGenerator() {
		super();
		this.battery = null;
	}

	/**
	 * Método para obter a bateria de Testes
	 * @return bateria de Testes
	 */
	public TestBattery getBattery() {
		return battery;
	}

	/**
	 * Método para alterar a bateria de Testes
	 * @param battery nova bateria de Testes
	 */
	public void setBattery(TestBattery battery) {
		this.battery = battery;
	}

	public abstract TestPlan generateDefaultTestPlan();

	public abstract TestPlan generateTestPlan(int concurrentUsers,
			int rampUpTime, int loopCount, int maxTimeout, int constantTimer,
			int randomTimer, boolean infiniteLoop);

}
