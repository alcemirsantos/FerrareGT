package performanceTestGenerator;

import performanceTestRepresentation.TestPlan;

/**
 * Classe abstract dos geradores das P�ginas - HTTPSAMPLER
 * @author Ismayle de Sousa Santos
 */
public abstract class BaseScripter {

	public abstract boolean parse(TestPlan testPlan, String path);

}
