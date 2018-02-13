package performanceTestRepresentation;

import java.util.Vector;

/**
 * Classe que representa um Plano de Teste de Desempenho ou Estresse  
 * @author Ismayle de Sousa Santos
 */
public class TestPlan {

	private int concurrentUsers, rampUpTime, loopCount, timeout,
			constantTimer, randomTimer;
	private Vector<URLAccess> pages;
	private boolean infiniteLoop;
	private String csvVariablesNames;
	private String csvFileName;

	/**
	 * Construtor Completo
	 * @param concurrentUsers N�mero de usu�rios virtuais
	 * @param rampUpTime Frequ�ncia de lan�amento dos usu�rios virtuais
	 * @param loopCount N�meros de vezes que o Teste vai executar
	 * @param maxTimeout Tempo m�ximo de resposta
	 * @param constantTimer Tempo m�nimo entre as requisi��es
	 * @param randomTimer Tempo m�ximo entre as requisi��es
	 * @param infiniteLoop Indica se a execu��o do  Teste � infita ou n�o
	 */
	public TestPlan(int concurrentUsers, int rampUpTime, int loopCount,
			int maxTimeout, int constantTimer, int randomTimer,
			boolean infiniteLoop) {
		super();
		this.concurrentUsers = concurrentUsers;
		this.rampUpTime = rampUpTime;
		this.loopCount = loopCount;
		this.timeout = maxTimeout;
		this.infiniteLoop = infiniteLoop;
		this.constantTimer = constantTimer;
		this.randomTimer = randomTimer;
		this.csvFileName = "";
		this.csvVariablesNames = "";
		pages = new Vector<URLAccess>();
	}

	/**
	 * Construtor que cria o TestPlan j� com alguns requisitos pr�-definidos
	 */
	public TestPlan() {
		this(0, 0, 0, 8000, 0, 0, false);
	}

	/**
	 * M�todo para obter o n�mero de usu�rios virtuais
	 * @return n�mero de usu�rios
	 */
	public int getConcurrentUsers() {
		return concurrentUsers;
	}

	/**
	 * M�todo para alterar o n�mero de usu�rios virtuais do Plano de Teste
	 * @param concurrentUsers novo n�mero de usu�rios
	 */
	public void setConcurrentUsers(int concurrentUsers) {
		this.concurrentUsers = concurrentUsers;
	}

	/**
	 * M�todo para saber se o Teste vai ser executado infinitamente ou finitamente
	 * @return true se executa infinitamente e false caso seja finito
	 */
	public boolean isInfiniteLoop() {
		return infiniteLoop;
	}

	/**
	 * M�todo para alterar a execu��o do Teste de infinito para finito ou de finito para infinito
	 * @param infiniteLoop true para executar inifinitamente e false para a execu��o ser finita 
	 */
	public void setInfiniteLoop(boolean infiniteLoop) {
		this.infiniteLoop = infiniteLoop;
	}

	/**
	 * M�todo para obter o n�mero de execu��es do Plano de Teste
	 * @return n�mero de execu��es
	 */
	public int getLoopCount() {
		return loopCount;
	}
	
	/**
	 * M�todo para alterar o numero de execu��es do Plano de Teste
	 * @param loopCount novo n�mero de execu��es
	 */
	public void setLoopCount(int loopCount) {
		this.loopCount = loopCount;
	}

	/**
	 * M�todo para obter o tempo de resposta do Plano de Teste
	 * @return tempo de resposta
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * M�todo para alterar o tempo de resposta do Plano de Teste
	 * @param timeout novo tempo de resposta
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * M�todo para obter o tempo m�nimo de parada entre as requisi��es de um usu�rio virtual
	 * @return tempo m�nimo
	 */
	public int getConstantTimer() {
		return constantTimer;
	}

	/**
	 * M�todo para alterar o tempo m�nimo de parada entre as requisi��es de um usu�rio virtual
	 * @param constantTimer novo tempo m�nimo
	 */
	public void setConstantTimer(int constantTimer) {
		this.constantTimer = constantTimer;
	}

	/**
	 * M�todo para obter o limite m�ximo de um n�mero aleat�rio que ser� gerado e somado ao tempo m�nimo para formar o tempo de parada entre as requisi��es
	 * @return limite m�ximo do n�mero gerado
	 */
	public int getRandomTimer() {
		return randomTimer;
	}

	/**
	 * M�todo para altera o limite m�ximo de um n�mero aleat�rio que ser� gerado e somado ao tempo m�nimo 
	 * @param randomTimer novo limite m�ximo  
	 */
	public void setRandomTimer(int randomTimer) {
		this.randomTimer = randomTimer;
	}

	/**
	 * M�todo para obter a lista de p�ginas do TestPlan
	 * @return lista de p�ginas
	 */
	public Vector<URLAccess> getPages() {
		return pages;
	}

	/**
	 * M�todo para alterar a lista de P�ginas do TestPlan
	 * @param pages nova lista de P�ginas
	 */
	public void setPages(Vector<URLAccess> pages) {
		this.pages = pages;
	}

	/**
	 * M�todo para obter a frequencia de lan�amento dos usu�rios Virtuais
	 * @return frequencia de lan�amento
	 */
	public int getRampUpTime() {
		return rampUpTime;
	}

	/**
	 * M�todo para alterar a frequencia de lan�amento dos usu�rios Virtuais
	 * @param rampUpTime nova frequencia de lan�amento
	 */
	public void setRampUpTime(int rampUpTime) {
		this.rampUpTime = rampUpTime;
	}

	/**
	 * M�todo para adicionar uma p�gina a lista de p�ginas do Plano de Teste
	 * @param page p�gina a ser adicionada
	 * @return
	 */
	public boolean addPage(URLAccess page) {
		return pages.add(page);
	}

	/**
	 * M�todo para remover uma p�gina do Plano de Teste
	 * @param page p�gina a ser removida
	 * @return
	 */
	public boolean removePage(URLAccess page) {
		return pages.remove(page);
	}
	
	/**
	 * M�todo para remover uma p�gina do Plano de Teste
	 * @param pos posi��o da p�gina a ser removida
	 */
	public void remove(int pos){
		pages.remove(pos);
	}
	
	public boolean hasCSV(){
		return (csvVariablesNames.length() > 0);
	}
	
	public String getCsvVariablesNames() {
		return csvVariablesNames;
	}

	public void setCsvVariablesNames(String csvVariablesNames) {
		this.csvVariablesNames = csvVariablesNames;
	}

	public String getCsvFileName() {
		return csvFileName;
	}

	public void setCsvFileName(String csvFileName) {
		this.csvFileName = csvFileName;
	}

	
	@Override
	public String toString() {
		StringBuffer s = new StringBuffer("Test Plan:");

		s.append("\nConcurrent Users: ");
		s.append(concurrentUsers);

		s.append("\nRamp-Up Time: ");
		s.append(rampUpTime);

		s.append("\nLoop Count: ");
		s.append(loopCount);

		s.append("\nMaximum Response Time: ");
		s.append(timeout);

		s.append("\nInfinite Loop: ");
		s.append(infiniteLoop);

		s.append("\n\nPage Sequence: ");
		for (URLAccess url : pages) {
			s.append("\n" + url.toString());
		}

		return s.toString();

	}

}
