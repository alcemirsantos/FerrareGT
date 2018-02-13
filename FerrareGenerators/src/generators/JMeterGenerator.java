package generators;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import functionalTestRepresentation.Field;

/**
 * Classe que gera o arquivo .jmx (arquivo que contém o Script do teste de
 * Desempenho para o JMeter)
 * @author Ismayle de Sousa Santos
 */
public class JMeterGenerator {
	private int users, rampUpTime, loopCount, maxTimeout, constantTimer,
			randomTimer;
	private boolean infiniteLoop;
	private Vector<HTTPSampler> pages;
	private String csvVariablesNames;
	private String csvFileName;

	/**
	 * Construtor Completo
	 * 
	 * @param users
	 *            Número de usuários virtuais
	 * @param rampUpTime
	 *            Frequência de lançamento dos usuários virtuais
	 * @param loopCount
	 *            Número de execuções do Teste
	 * @param maxTimeout
	 *            Tempo máximo de Resposta de uma requisição
	 * @param constantTimer
	 *            Tempo mínimo entre as requisições
	 * @param randomTimer
	 *            Tempo máximo entre as requisições
	 * @param infiniteLoop
	 *            Indica se o Teste vai executar infinitamente ou não
	 */
	public JMeterGenerator(int users, int rampUpTime, int loopCount,
			int maxTimeout, int constantTimer, int randomTimer,
			boolean infiniteLoop) {
		super();
		this.users = users;
		this.rampUpTime = rampUpTime;
		this.loopCount = loopCount;
		this.maxTimeout = maxTimeout;
		this.constantTimer = constantTimer;
		this.randomTimer = randomTimer;
		this.infiniteLoop = infiniteLoop;
		this.csvVariablesNames = "";
		this.csvFileName = "";
		pages = new Vector<HTTPSampler>();
	}
	
	public JMeterGenerator() {
		this(1, 0, -1, 8000, 0, 0, false);
	}

	/**
	 * Método para obter o número de execuções de um Script de Teste de
	 * Desempenho
	 * 
	 * @return número de execuções
	 */
	public int getLoopCount() {
		return loopCount;
	}

	/**
	 * Método para alterar o número e execuções de um Script de Teste de
	 * Desempenho
	 * 
	 * @param loopCount
	 *            novo número de execuções
	 */
	public void setLoopCount(int loopCount) {
		this.loopCount = loopCount;
	}

	/**
	 * Método para obter o tempo de resposta geral (válido para todas as
	 * páginas)
	 * 
	 * @return tempo de resposta
	 */
	public int getMaxTimeout() {
		return maxTimeout;
	}

	/**
	 * Método para alterar o tempo de de resposta geral (válido para todas as
	 * páginas)
	 * 
	 * @param maxTimeout
	 *            novo tempo de resposta
	 */
	public void setMaxTimeout(int maxTimeout) {
		this.maxTimeout = maxTimeout;
	}

	/**
	 * Método para obter a lista de Páginas usada no Script de Desempenho
	 * 
	 * @return lista de páginas
	 */
	public Vector<HTTPSampler> getPages() {
		return pages;
	}

	/**
	 * Método para alterar a lista de Páginas usadas no Script de Desempenho
	 * 
	 * @param pages
	 *            nova lista de páginas
	 */
	public void setPages(Vector<HTTPSampler> pages) {
		this.pages = pages;
	}

	/**
	 * Método para obter a frequencia de lançamento dos usuários
	 * 
	 * @return frequencia de lançamento
	 */
	public int getRampUpTime() {
		return rampUpTime;
	}

	/**
	 * Método para alterar a frequencia de lançamento dos usuários
	 * 
	 * @param rampUpTime
	 *            nova frequencia de lançamentos
	 */
	public void setRampUpTime(int rampUpTime) {
		this.rampUpTime = rampUpTime;
	}

	/**
	 * Método para obter o tempo mínimo entre o lançamento das requisições de um
	 * usuário
	 * 
	 * @return tempo mínimo
	 */
	public int getConstantTimer() {
		return constantTimer;
	}

	/**
	 * Método para alterar o tempo mínimo entre o lançamneto das requisições de
	 * um usuário
	 * 
	 * @param constantTimer
	 *            novo tempo mínimo
	 */
	public void setConstantTimer(int constantTimer) {
		this.constantTimer = constantTimer;
	}

	/**
	 * Método para obter o limite máximo de um número aleatório que será gerado
	 * e somado ao tempo mínimo para formar o tempo de parada entre requisições
	 * 
	 * @return tempo máximo
	 */
	public int getRandomTimer() {
		return randomTimer;
	}

	/**
	 * Método para alterar o limite máximo de um número aleatório que será
	 * gerado para ser somado ao tempo mínimo
	 * 
	 * @param randomTimer
	 *            novo tempo máximo
	 */
	public void setRandomTimer(int randomTimer) {
		this.randomTimer = randomTimer;
	}

	/**
	 * Método para saber se o Teste vai executar infinitamente ou não
	 * 
	 * @return true se infinitamente, e false do contrário
	 */
	public boolean isinfiniteLoop() {
		return this.infiniteLoop;
	}

	/**
	 * Método para alterar a execução do teste de finito para infinito ou
	 * vice-versa
	 * 
	 * @param infiniteLoop
	 *            true para torná-la infinita e false para finita
	 */
	public void setinfiniteLoop(boolean infiniteLoop) {
		this.infiniteLoop = infiniteLoop;
	}

	/**
	 * Método que pega o número de usuários virtuais que serão criados no teste
	 * de Desempenho
	 * 
	 * @return número de usuários
	 */
	public int getUsers() {
		return users;
	}

	/**
	 * Método que altera o número de usuários virtuais criados
	 * 
	 * @param users
	 *            novo número de usuários
	 */
	public void setUsers(int users) {
		this.users = users;
	}

	/**
	 * Método que adiciona páginas ao Script de Desempenho
	 * 
	 * @param page
	 *            pagina para ser adicionada
	 */
	public void addPage(HTTPSampler page) {
		pages.add(page);
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
	
	
	/**
	 * Método que gera o script de teste de Desempenho para o JMeter
	 * 
	 * @param filename
	 *            caminho do arquivo a ser gerado
	 * @return
	 * @throws IOException
	 */
	public boolean parse(String filename) throws IOException {
		File jmx = new File(filename);

		jmx.createNewFile();
		StringBuffer code = new StringBuffer();
		String urlBase, path;
		urlBase = pages.get(0).getURL();
		if(pages.get(0).getPath().contains("/"))
			path = pages.get(0).getPath().substring(0,
				pages.get(0).getPath().lastIndexOf('/'));
		else if (pages.get(0).getPath().equals("regex[]")){
			path = "";
		}else{
			path = pages.get(0).getPath();
		}
		code
				.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
						+ "<jmeterTestPlan version=\"1.2\" properties=\"1.8\">\r\n"
						+ "  <hashTree>\r\n"
						+ "    <TestPlan guiclass=\"TestPlanGui\" testclass=\"TestPlan\" testname=\"Test Plan\" enabled=\"true\">\r\n"
						+ "      <stringProp name=\"TestPlan.comments\">Plano de Teste gerado pela FERRARE 1.0</stringProp>\r\n"
						+ "      <boolProp name=\"TestPlan.functional_mode\">false</boolProp>\r\n"
						+ "      <boolProp name=\"TestPlan.serialize_threadgroups\">false</boolProp>\r\n"
						+ "      <elementProp name=\"TestPlan.user_defined_variables\" elementType=\"Arguments\" guiclass=\"ArgumentsPanel\" testclass=\"Arguments\" testname=\"User Defined Variables\" enabled=\"true\">\r\n"
						+ "        <collectionProp name=\"Arguments.arguments\">\r\n"
						+ "          <elementProp name=\"urlBase\" elementType=\"Argument\">\r\n"
						+ "            <stringProp name=\"Argument.name\">urlBase</stringProp>\r\n"
						+ "            <stringProp name=\"Argument.value\">"
						+ urlBase
						+ "</stringProp>\r\n"
						+ "            <stringProp name=\"Argument.metadata\">=</stringProp>\r\n"
						+ "          </elementProp>\r\n"
						+ "          <elementProp name=\"path\" elementType=\"Argument\">\r\n"
						+ "            <stringProp name=\"Argument.name\">path</stringProp>\r\n"
						+ "            <stringProp name=\"Argument.value\">"
						+ path
						+ "</stringProp>\r\n"
						+ "            <stringProp name=\"Argument.metadata\">=</stringProp>\r\n"
						+ "          </elementProp>\r\n"
						+ "        </collectionProp>\r\n"
						+ "      </elementProp>\r\n"
						+ "      <stringProp name=\"TestPlan.user_define_classpath\"></stringProp>\r\n"
						+ "    </TestPlan>\r\n"
						+ "    <hashTree>\r\n"
						+ "      <ThreadGroup guiclass=\"ThreadGroupGui\" testclass=\"ThreadGroup\" testname=\"Thread Group\" enabled=\"true\">\r\n"
						+ "        <elementProp name=\"ThreadGroup.main_controller\" elementType=\"LoopController\" guiclass=\"LoopControlPanel\" testclass=\"LoopController\" testname=\"Loop Controller\" enabled=\"true\">\r\n");
		if (infiniteLoop == false) {
			code
					.append("          <boolProp name=\"LoopController.continue_forever\">false</boolProp>\r\n");
		} else {
			code
					.append("          <boolProp name=\"LoopController.continue_forever\">true</boolProp>\r\n");
			loopCount = -1;
		}

		code
				.append("          <stringProp name=\"LoopController.loops\">"
						+ loopCount
						+ "</stringProp>\r\n"
						+ "        </elementProp>\r\n"
						+ "        <stringProp name=\"ThreadGroup.num_threads\">"
						+ users
						+ "</stringProp>\r\n"
						+ "        <stringProp name=\"ThreadGroup.ramp_time\">"
						+ rampUpTime
						+ "</stringProp>\r\n"
						+ "        <longProp name=\"ThreadGroup.start_time\">1196273094000</longProp>\r\n"
						+ "        <longProp name=\"ThreadGroup.end_time\">1196273094000</longProp>\r\n"
						+ "        <boolProp name=\"ThreadGroup.scheduler\">false</boolProp>\r\n"
						+ "        <stringProp name=\"ThreadGroup.on_sample_error\">continue</stringProp>\r\n"
						+ "        <stringProp name=\"ThreadGroup.duration\"></stringProp>\r\n"
						+ "        <stringProp name=\"ThreadGroup.delay\"></stringProp>\r\n"
						+ "        <stringProp name=\"TestPlan.comments\">Gerado pela FERRARE 1.0</stringProp>\r\n"
						+ "      </ThreadGroup>\r\n" 
						+ "      <hashTree>\r\n");

		String pathAnterior = "";		
		int contador = 0;
		int pos = 0;
		for (HTTPSampler page : pages) {
			System.out.println(page.getPath());
			System.out.println(page.getHiddenFieldsList().size());
			System.out.println(page.getCapture());
			
			if(page.getCapture() > 0 ){
				if(page.getCapture() == 1)
					contador++;
				//----
				//Captura URL e os campos hidden
				//----
						code
							.append("        <HTTPSampler guiclass=\"HttpTestSampleGui\" testclass=\"HTTPSampler\" testname=\"/"
									+ page.getPath()
									+ "\" enabled=\"true\">\r\n"
									+ "          <elementProp name=\"HTTPsampler.Arguments\" elementType=\"Arguments\" guiclass=\"HTTPArgumentsPanel\" testclass=\"Arguments\" testname=\"User Defined Variables\" enabled=\"true\">\r\n");

						if (page.getHiddenFieldsList().isEmpty()) {							
							code
								.append("            <collectionProp name=\"Arguments.arguments\"/>\r\n");
						} else {
							code
								.append("            <collectionProp name=\"Arguments.arguments\">\r\n");
							
							
							Iterator<?> fields = page.getHmpFieldInput().keySet().iterator();
							
							while (fields.hasNext()) {
								Field field = (Field) fields.next();
								String value = page.getHmpFieldInput().get(field).getValue();
								if(field.getReferenceToCSV().length() > 0){
									value = "${"+field.getReferenceToCSV()+"}";
								}
								code
									.append("              <elementProp name=\"\" elementType=\"HTTPArgument\">\r\n"
										+ "                <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\r\n"
										+ "                <stringProp name=\"Argument.name\">"
										+ field.getName() 
										+ "</stringProp>\r\n"
										+ "                <stringProp name=\"Argument.value\">"
										+ value  
										+ "</stringProp>\r\n"
										+ "                <stringProp name=\"Argument.metadata\">=</stringProp>\r\n"
										+ "                <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\r\n"
										+ "              </elementProp>\r\n");
		
							}
							code.append("            </collectionProp>\r\n");
						}
						
						code
							.append("          </elementProp>\r\n"
								+ "          <stringProp name=\"HTTPSampler.domain\"></stringProp>\r\n"
								+ "          <stringProp name=\"HTTPSampler.port\">"
								+ page.getPort()
								+ "</stringProp>\r\n"
								+ "          <stringProp name=\"HTTPSampler.protocol\"></stringProp>\r\n"
								+ "          <stringProp name=\"HTTPSampler.method\">"
								+ page.getMethod()
								+ "</stringProp>\r\n"
								+ "          <stringProp name=\"HTTPSampler.contentEncoding\"></stringProp>\r\n"
								+ "          <stringProp name=\"HTTPSampler.path\">/"
								+ page.getPath()
								+ "</stringProp>\r\n"
								+ "          <boolProp name=\"HTTPSampler.follow_redirects\">true</boolProp>\r\n"
								+ "          <boolProp name=\"HTTPSampler.auto_redirects\">false</boolProp>\r\n"
								+ "          <boolProp name=\"HTTPSampler.use_keepalive\">true</boolProp>\r\n"
								+ "          <boolProp name=\"HTTPSampler.DO_MULTIPART_POST\">false</boolProp>\r\n"
								+ "          <stringProp name=\"HTTPSampler.mimetype\"></stringProp>\r\n"
								+ "          <stringProp name=\"HTTPSampler.FILE_NAME\"></stringProp>\r\n"
								+ "          <stringProp name=\"HTTPSampler.FILE_FIELD\"></stringProp>\r\n"
								+ "          <stringProp name=\"HTTPSampler.monitor\">false</stringProp>\r\n"
								+ "          <stringProp name=\"HTTPSampler.embedded_url_re\"></stringProp>\r\n"
								+ "          <stringProp name=\"TestPlan.comments\">Gerado pela FERRARE 1.0</stringProp>\r\n"
								+ "        </HTTPSampler>\r\n"
								+ "        <hashTree>\r\n");
								// No caso de ter regex
								if(page.getCapture() == 1 ){
									code.append("          <RegexExtractor guiclass=\"RegexExtractorGui\" testclass=\"RegexExtractor\" testname=\"url"+contador+"\" enabled=\"true\">\r\n"
				          			+ "    		   <stringProp name=\"RegexExtractor.useHeaders\">false</stringProp>\r\n"
				            		+ "	   		   <stringProp name=\"RegexExtractor.refname\">url"+contador+"</stringProp>\r\n"
				            		+ "    		   <stringProp name=\"RegexExtractor.regex\">action=\\&quot;(.+?)\\&quot;&gt;</stringProp>\r\n"
				            		+ "			   <stringProp name=\"RegexExtractor.template\">$1$</stringProp>\r\n"
				            		+ "			   <stringProp name=\"RegexExtractor.default\">1</stringProp>\r\n"
				            		+ "			   <stringProp name=\"RegexExtractor.match_number\">1</stringProp>\r\n"
				            		+ "			   <stringProp name=\"TestPlan.comments\">Gerado pela FERRARE 1.0</stringProp>\r\n"
				          			+ "          </RegexExtractor>\r\n"
				          			+ "		     <hashTree/>\r\n");
								}
								if((!page.getHiddenFieldsList().isEmpty()) &&(pos < pages.size()-1)){
								    pages.get(pos+1).setHiddenFieldsList(page.getHiddenFieldsList());
									Iterator<?> camposHidden = pages.get(pos).getHiddenFieldsList().iterator();								
									while (camposHidden.hasNext()) {
										String campoH = (String) camposHidden.next();
										code.append(""//"        <hashTree>\r\n" // adicionado
										+ "          <RegexExtractor guiclass=\"RegexExtractorGui\" testclass=\"RegexExtractor\" testname=\""+campoH+"\" enabled=\"true\">\r\n"
							            + "		       <stringProp name=\"RegexExtractor.useHeaders\">false</stringProp>\r\n"
							            + "		       <stringProp name=\"RegexExtractor.refname\">V"+campoH+"</stringProp>\r\n"
							            + "		       <stringProp name=\"RegexExtractor.regex\">&lt;input.*name=&quot;"+campoH+"&quot;.*value=\\&quot;(\\w.*)\\&quot;.*&gt;</stringProp>\r\n"
							            + "		       <stringProp name=\"RegexExtractor.template\">$1$</stringProp>\r\n"
							            + "		       <stringProp name=\"RegexExtractor.default\"></stringProp>\r\n"
							            + "		       <stringProp name=\"RegexExtractor.match_number\">1</stringProp>\r\n"
							            + "		       <stringProp name=\"TestPlan.comments\">Gerado pela FERRARE 1.0</stringProp>\r\n"
							            + "		     </RegexExtractor>\r\n"
							            + "		     <hashTree/>\r\n");
									}													
								}
						pathAnterior = page.getPath();
			}else{
				//---
				//Não captura campos
				//---
				
						if (page.getPath().contains("regex")) {
		
							//
							//Tratamento das requisições com dados e com "submit"
							//
							
							code.append ("		   <GenericController guiclass=\"LogicControllerGui\" testclass=\"GenericController\" testname=\"Controle url"+contador+"\" enabled=\"true\"/>\r\n"
									+ "		   <hashTree>\r\n"
									+ "		     <IfController guiclass=\"IfControllerPanel\" testclass=\"IfController\" testname=\"Nova url"+contador+"\" enabled=\"true\">\r\n"
									+ "		       <stringProp name=\"IfController.condition\">&quot;${url"+contador+"}&quot; != &quot;1&quot;</stringProp>\r\n"
									+ "		     </IfController>\r\n"
									+ "		     <hashTree>\r\n"
									+ "          <HTTPSampler guiclass=\"HttpTestSampleGui\" testclass=\"HTTPSampler\" testname=\"${url"+contador+"_g1}\" enabled=\"true\">\r\n"
									+ "            <elementProp name=\"HTTPsampler.Arguments\" elementType=\"Arguments\" guiclass=\"HTTPArgumentsPanel\" testclass=\"Arguments\" testname=\"User Defined Variables\" enabled=\"true\">\r\n");

							if ((page.getHmpFieldInput().isEmpty())&&(page.getHiddenFieldsList().isEmpty())) {
								code
										.append("              <collectionProp name=\"Arguments.arguments\"/>\r\n");
							}else{
								code
								.append("              <collectionProp name=\"Arguments.arguments\">\r\n");						
							}
							if (page.getHmpFieldInput().size() > 0){
								
								Iterator<?> fields = page.getHmpFieldInput().keySet().iterator();
								
								while (fields.hasNext()) {
									Field field = (Field)fields.next();
									String value = page.getHmpFieldInput().get(field).getValue();
									if(field.getReferenceToCSV().length() > 0){
										value = "${"+field.getReferenceToCSV()+"}";
									}
									code
											.append("              <elementProp name=\"\" elementType=\"HTTPArgument\">\r\n"
													+ "                <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\r\n"
													+ "                <stringProp name=\"Argument.name\">"
													+ field.getName()
													+ "</stringProp>\r\n"
													+ "                <stringProp name=\"Argument.value\">"
													+ value
													+ "</stringProp>\r\n"
													+ "                <stringProp name=\"Argument.metadata\">=</stringProp>\r\n"
													+ "                <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\r\n"
													+ "            </elementProp>\r\n");										
								}								
							}
							if (page.getHiddenFieldsList().size() > 0) {								
								Iterator<?> camposHidden = page.getHiddenFieldsList().iterator();
								while (camposHidden.hasNext()) {
									String campoH = (String) camposHidden.next();
									code
											.append("              <elementProp name=\"\" elementType=\"HTTPArgument\">\r\n"
													+ "                <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\r\n"
													+ "                <stringProp name=\"Argument.name\">"
													+ campoH
													+ "</stringProp>\r\n"
													+ "                <stringProp name=\"Argument.value\">"
													+ "${V"+campoH+"}"
													+ "</stringProp>\r\n"
													+ "                <stringProp name=\"Argument.metadata\">=</stringProp>\r\n"
													+ "                <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\r\n"
													+ "            </elementProp>\r\n");		
								}								
							}
							if((page.getHmpFieldInput().size() > 0) || (page.getHiddenFieldsList().size()> 0)){
								code.append("          </collectionProp>\r\n");
										
							}
							
							code
									.append("        </elementProp>\r\n"
											+ "            <stringProp name=\"HTTPSampler.domain\"></stringProp>\r\n"
											+ "            <stringProp name=\"HTTPSampler.port\"></stringProp>\r\n"
											+ "            <stringProp name=\"HTTPSampler.protocol\"></stringProp>\r\n"
											+ "            <stringProp name=\"HTTPSampler.contentEncoding\"></stringProp>\r\n"
											+ "            <stringProp name=\"HTTPSampler.path\">"
											+ "${url"+contador+"_g1}"
											+ "</stringProp>\r\n"
											+ "            <stringProp name=\"HTTPSampler.method\">"
											+ page.getMethod()
											+ "</stringProp>\r\n"
											+ "            <boolProp name=\"HTTPSampler.follow_redirects\">true</boolProp>\r\n"
											+ "            <boolProp name=\"HTTPSampler.auto_redirects\">false</boolProp>\r\n"
											+ "            <boolProp name=\"HTTPSampler.use_keepalive\">true</boolProp>\r\n"
											+ "            <boolProp name=\"HTTPSampler.DO_MULTIPART_POST\">false</boolProp>\r\n"
											+ "            <stringProp name=\"HTTPSampler.FILE_NAME\"></stringProp>\r\n"
											+ "            <stringProp name=\"HTTPSampler.FILE_FIELD\"></stringProp>\r\n"
											+ "            <stringProp name=\"HTTPSampler.mimetype\"></stringProp>\r\n"
											+ "            <stringProp name=\"HTTPSampler.monitor\">false</stringProp>\r\n"
											+ "            <stringProp name=\"HTTPSampler.embedded_url_re\"></stringProp>\r\n"
											+ "            <stringProp name=\"TestPlan.comments\">Gerado pela FERRARE 1.0</stringProp>\r\n"
											+ "          </HTTPSampler>\r\n"
											+ "          <hashTree/>\r\n"
											+ "        </hashTree>\r\n"
											+ "		     <IfController guiclass=\"IfControllerPanel\" testclass=\"IfController\" testname=\"Mesma url"+contador+"\" enabled=\"true\">\r\n"
											+ "		       <stringProp name=\"IfController.condition\">&quot;${url"+contador+"}&quot; == &quot;1&quot;</stringProp>\r\n"
											+ "		     </IfController>\r\n"
											+ "		     <hashTree>\r\n"
											+ "          <HTTPSampler guiclass=\"HttpTestSampleGui\" testclass=\"HTTPSampler\" testname=\""+pathAnterior+"\" enabled=\"true\">\r\n"
											+ "            <elementProp name=\"HTTPsampler.Arguments\" elementType=\"Arguments\" guiclass=\"HTTPArgumentsPanel\" testclass=\"Arguments\" testname=\"User Defined Variables\" enabled=\"true\">\r\n");

							if ((page.getHmpFieldInput().isEmpty())&&(page.getHiddenFieldsList().isEmpty())) {
								code
										.append("              <collectionProp name=\"Arguments.arguments\"/>\r\n");
							}else{
								code
								.append("              <collectionProp name=\"Arguments.arguments\">\r\n");						
							}
							if (page.getHmpFieldInput().size() > 0){
								
								Iterator<?> fields = page.getHmpFieldInput().keySet().iterator();
								
								while (fields.hasNext()) {
									Field field = (Field)fields.next();
									String value = page.getHmpFieldInput().get(field).getValue();
									if(field.getReferenceToCSV().length() > 0){
										value = "${"+field.getReferenceToCSV()+"}";
									}
									code
											.append("              <elementProp name=\"\" elementType=\"HTTPArgument\">\r\n"
													+ "                <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\r\n"
													+ "                <stringProp name=\"Argument.name\">"
													+ field.getName()
													+ "</stringProp>\r\n"
													+ "                <stringProp name=\"Argument.value\">"
													+ value
													+ "</stringProp>\r\n"
													+ "                <stringProp name=\"Argument.metadata\">=</stringProp>\r\n"
													+ "                <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\r\n"
													+ "            </elementProp>\r\n");										
								}								
							}
							if (page.getHiddenFieldsList().size() > 0) {								
								Iterator<?> camposHidden = page.getHiddenFieldsList().iterator();
								while (camposHidden.hasNext()) {
									String campoH = (String) camposHidden.next();
									code
											.append("              <elementProp name=\"\" elementType=\"HTTPArgument\">\r\n"
													+ "                <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\r\n"
													+ "                <stringProp name=\"Argument.name\">"
													+ campoH
													+ "</stringProp>\r\n"
													+ "                <stringProp name=\"Argument.value\">"
													+ "${V"+campoH+"}"
													+ "</stringProp>\r\n"
													+ "                <stringProp name=\"Argument.metadata\">=</stringProp>\r\n"
													+ "                <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\r\n"
													+ "            </elementProp>\r\n");		
								}								
							}
							if((page.getHmpFieldInput().size() > 0) || (page.getHiddenFieldsList().size()> 0)){
								code.append("          </collectionProp>\r\n");
										
							}
							code
														.append("        </elementProp>\r\n"
																+ "            <stringProp name=\"HTTPSampler.domain\"></stringProp>\r\n"
																+ "            <stringProp name=\"HTTPSampler.port\"></stringProp>\r\n"
																+ "            <stringProp name=\"HTTPSampler.protocol\"></stringProp>\r\n"
																+ "            <stringProp name=\"HTTPSampler.contentEncoding\"></stringProp>\r\n"
																+ "            <stringProp name=\"HTTPSampler.path\">"
																+ pathAnterior
																+ "</stringProp>\r\n"
																+ "            <stringProp name=\"HTTPSampler.method\">"
																+ page.getMethod()
																+ "</stringProp>\r\n"
																+ "            <boolProp name=\"HTTPSampler.follow_redirects\">true</boolProp>\r\n"
																+ "            <boolProp name=\"HTTPSampler.auto_redirects\">false</boolProp>\r\n"
																+ "            <boolProp name=\"HTTPSampler.use_keepalive\">true</boolProp>\r\n"
																+ "            <boolProp name=\"HTTPSampler.DO_MULTIPART_POST\">false</boolProp>\r\n"
																+ "            <stringProp name=\"HTTPSampler.FILE_NAME\"></stringProp>\r\n"
																+ "            <stringProp name=\"HTTPSampler.FILE_FIELD\"></stringProp>\r\n"
																+ "            <stringProp name=\"HTTPSampler.mimetype\"></stringProp>\r\n"
																+ "            <stringProp name=\"HTTPSampler.monitor\">false</stringProp>\r\n"
																+ "            <stringProp name=\"HTTPSampler.embedded_url_re\"></stringProp>\r\n"
																+ "            <stringProp name=\"TestPlan.comments\">Gerado pela FERRARE 1.0</stringProp>\r\n"
																+ "          </HTTPSampler>\r\n"
																+ "          <hashTree/>\r\n"
																+ "        </hashTree>\r\n");
																//TODO
																if((page.getCapture() == -1) && (!page.getHiddenFieldsList().isEmpty()) &&(pos < pages.size()-1)){
																    pages.get(pos+1).setHiddenFieldsList(page.getHiddenFieldsList());
																	Iterator<?> camposHidden = pages.get(pos).getHiddenFieldsList().iterator();								
																	while (camposHidden.hasNext()) {
																		String campoH = (String) camposHidden.next();
																		code.append(""//"        <hashTree>\r\n" // adicionado
																		+ "          <RegexExtractor guiclass=\"RegexExtractorGui\" testclass=\"RegexExtractor\" testname=\""+campoH+"\" enabled=\"true\">\r\n"
															            + "		       <stringProp name=\"RegexExtractor.useHeaders\">false</stringProp>\r\n"
															            + "		       <stringProp name=\"RegexExtractor.refname\">V"+campoH+"</stringProp>\r\n"
															            + "		       <stringProp name=\"RegexExtractor.regex\">&lt;input.*name=&quot;"+campoH+"&quot;.*value=\\&quot;(\\w.*)\\&quot;.*&gt;</stringProp>\r\n"
															            + "		       <stringProp name=\"RegexExtractor.template\">$1$</stringProp>\r\n"
															            + "		       <stringProp name=\"RegexExtractor.default\"></stringProp>\r\n"
															            + "		       <stringProp name=\"RegexExtractor.match_number\">1</stringProp>\r\n"
															            + "		       <stringProp name=\"TestPlan.comments\">Gerado pela FERRARE 1.0</stringProp>\r\n"
															            + "		     </RegexExtractor>\r\n"
															            + "		     <hashTree/>\r\n");
																	}													
																	//
																}																			
						} else {  
		
							//
							//Requisição Normal, com url própria
							//
							
							code
									.append("        <HTTPSampler guiclass=\"HttpTestSampleGui\" testclass=\"HTTPSampler\" testname=\""
											+ page.getPath()
											+ "\" enabled=\"true\">\r\n"
											+ "          <elementProp name=\"HTTPsampler.Arguments\" elementType=\"Arguments\" guiclass=\"HTTPArgumentsPanel\" testclass=\"Arguments\" testname=\"User Defined Variables\" enabled=\"true\">\r\n");
		
							if (page.getHmpFieldInput()== null) {
								code
										.append("            <collectionProp name=\"Arguments.arguments\"/>\r\n");
							} else {
								code
										.append("            <collectionProp name=\"Arguments.arguments\">\r\n");
								
								Iterator<?> fields = page.getHmpFieldInput().keySet().iterator();
								
								while (fields.hasNext()) {
									Field field = (Field)fields.next();
									String value = page.getHmpFieldInput().get(field).getValue();
									if(field.getReferenceToCSV().length() > 0){
										value = "${"+field.getReferenceToCSV()+"}";
									}
									code
											.append("              <elementProp name=\"\" elementType=\"HTTPArgument\">\r\n"
													+ "                <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\r\n"
													+ "                <stringProp name=\"Argument.name\">"
													+ field.getName()
													+ "</stringProp>\r\n"
													+ "                <stringProp name=\"Argument.value\">"
													+ value
													+ "</stringProp>\r\n"
													+ "                <stringProp name=\"Argument.metadata\">=</stringProp>\r\n"
													+ "                <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\r\n"
													+ "              </elementProp>\r\n");
		
								}
								//campos hidden
								if(page.getHmpFieldInput().size() > 0){
									if (page.getHiddenFieldsList().size() > 0) {								
										Iterator<?> camposHidden = page.getHiddenFieldsList().iterator();
										while (camposHidden.hasNext()) {
											String campoH = (String) camposHidden.next();
											code
													.append("              <elementProp name=\"\" elementType=\"HTTPArgument\">\r\n"
															+ "                <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\r\n"
															+ "                <stringProp name=\"Argument.name\">"
															+ campoH
															+ "</stringProp>\r\n"
															+ "                <stringProp name=\"Argument.value\">"
															+ "${V"+campoH+"}"
															+ "</stringProp>\r\n"
															+ "                <stringProp name=\"Argument.metadata\">=</stringProp>\r\n"
															+ "                <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\r\n"
															+ "            </elementProp>\r\n");		
										}								
									}
								}
								///
								
								code.append("            </collectionProp>\r\n");
							}
							code
									.append("          </elementProp>\r\n"										
											+ "          <stringProp name=\"HTTPSampler.port\">"
											+ page.getPort()
											+ "</stringProp>\r\n"
											+ "          <stringProp name=\"HTTPSampler.protocol\"></stringProp>\r\n"
											+ "          <stringProp name=\"HTTPSampler.method\">"
											+ page.getMethod()
											+ "</stringProp>\r\n"
											+ "          <stringProp name=\"HTTPSampler.contentEncoding\"></stringProp>\r\n"
											+ "          <stringProp name=\"HTTPSampler.path\">"
											+ page.getPath()
											+ "</stringProp>\r\n"
											+ "          <boolProp name=\"HTTPSampler.follow_redirects\">true</boolProp>\r\n"
											+ "          <boolProp name=\"HTTPSampler.auto_redirects\">false</boolProp>\r\n"
											+ "          <boolProp name=\"HTTPSampler.use_keepalive\">true</boolProp>\r\n"
											+ "          <boolProp name=\"HTTPSampler.DO_MULTIPART_POST\">false</boolProp>\r\n"
											+ "          <stringProp name=\"HTTPSampler.mimetype\"></stringProp>\r\n"
											+ "          <stringProp name=\"HTTPSampler.FILE_NAME\"></stringProp>\r\n"
											+ "          <stringProp name=\"HTTPSampler.FILE_FIELD\"></stringProp>\r\n"
											+ "          <stringProp name=\"HTTPSampler.monitor\">false</stringProp>\r\n"
											+ "          <stringProp name=\"HTTPSampler.embedded_url_re\"></stringProp>\r\n"
											+ "          <stringProp name=\"TestPlan.comments\">Gerado pela FERRARE 1.0</stringProp>"
											+ "        </HTTPSampler>\r\n"
											+ "		  <hashTree>\r\n");
							pathAnterior = page.getPath();
						}
				
			}//fim do if mais externo
			
			code
					.append("          <DurationAssertion guiclass=\"DurationAssertionGui\" testclass=\"DurationAssertion\" testname=\"Duration Assertion\" enabled=\"true\">\r\n"
							+ "            <stringProp name=\"DurationAssertion.duration\">"
							+ page.getLimitTime()
							+ "</stringProp>\r\n"
							+ "            <stringProp name=\"TestPlan.comments\">Gerado pela FERRARE 1.0</stringProp>\r\n"
							+ "          </DurationAssertion>\r\n"
							+ "          <hashTree/>\r\n"
							+ "          <ResponseAssertion guiclass=\"AssertionGui\" testclass=\"ResponseAssertion\" testname=\"Response Assertion\" enabled=\"true\">\r\n"
							+ "            <collectionProp name=\"Asserion.test_strings\">\r\n"
							+ "              <stringProp name=\""
							+ Math.random() * 1000000
							+ "\">"
							+ page.getExpectedResponse()
							+ "</stringProp>\r\n"
							+ "            </collectionProp>\r\n"
							+ "            <stringProp name=\"Assertion.test_field\">Assertion.response_data</stringProp>\r\n"
							+ "            <stringProp name=\"Assertion.assume_success\">false</stringProp>\r\n"
							+ "            <intProp name=\"Assertion.test_type\">2</intProp>\r\n"
							+ "            <stringProp name=\"TestPlan.comments\">Gerado pela FERRARE 1.0</stringProp>\r\n"
							+ "          </ResponseAssertion>\r\n"
							+ "          <hashTree/>\r\n"							
							+ "      </hashTree>\r\n");
			pos++;			
			
		}

		code
				.append("	     <UniformRandomTimer guiclass=\"UniformRandomTimerGui\" testclass=\"UniformRandomTimer\" testname=\"Uniform Random Timer\" enabled=\"true\">\r\n"
						+ "		  <stringProp name=\"ConstantTimer.delay\">"
						+ constantTimer
						+ "</stringProp>\r\n"
						+ "		  <stringProp name=\"RandomTimer.range\">"
						+ randomTimer
						+ "</stringProp>\r\n"
						+ "       <stringProp name=\"TestPlan.comments\">Gerado pela FERRARE 1.0</stringProp>"
						+ "	     </UniformRandomTimer>\r\n"
						+ "	     <hashTree/>\r\n"
						+ "	   </hashTree>\r\n"
						+ "      <DurationAssertion guiclass=\"DurationAssertionGui\" testclass=\"DurationAssertion\" testname=\"Duration Assertion\" enabled=\"true\">\r\n"
						+ "        <stringProp name=\"DurationAssertion.duration\">"
						+ maxTimeout
						+ "</stringProp>\r\n"
						+ "        <stringProp name=\"TestPlan.comments\">Gerado pela FERRARE 1.0</stringProp>\r\n"
						+ "      </DurationAssertion>\r\n"
						+ "      <hashTree/>\r\n"

						+ "      <ConfigTestElement guiclass=\"HttpDefaultsGui\" testclass=\"ConfigTestElement\" testname=\"HTTP Request Defaults\" enabled=\"true\">"
				        + "        <stringProp name=\"TestPlan.comments\">Gerado pela FERRARE 1.0</stringProp>"
				        + "        <stringProp name=\"HTTPSampler.protocol\">http</stringProp>"
				        + "        <stringProp name=\"HTTPSampler.domain\">"+urlBase+"</stringProp>"
				        + "        <stringProp name=\"HTTPSampler.path\"></stringProp>"
				        + "        <elementProp name=\"HTTPsampler.Arguments\" elementType=\"Arguments\" guiclass=\"HTTPArgumentsPanel\" testclass=\"Arguments\" testname=\"User Defined Variables\" enabled=\"true\">"
				        + "          <collectionProp name=\"Arguments.arguments\"/>"
				        + "        </elementProp>"				        
				        + "      </ConfigTestElement>"
				        + "      <hashTree/>"
				        
						+ "      <CookieManager guiclass=\"CookiePanel\" testclass=\"CookieManager\" testname=\"HTTP Cookie Manager\" enabled=\"true\">\r\n"
						+ "        <collectionProp name=\"CookieManager.cookies\"/>\r\n"
						+ "        <boolProp name=\"CookieManager.clearEachIteration\">false</boolProp>\r\n"
						+ "        <stringProp name=\"CookieManager.policy\">rfc2109</stringProp>\r\n"
						+ "        <stringProp name=\"TestPlan.comments\">Gerado pela FERRARE 1.0</stringProp>\r\n"
						+ "      </CookieManager>\r\n"
						+ "      <hashTree/>\r\n"
						+ "      <ResultCollector guiclass=\"ViewResultsFullVisualizer\" testclass=\"ResultCollector\" testname=\"View Results Tree\" enabled=\"true\">\r\n"
						+ "        <boolProp name=\"ResultCollector.error_logging\">false</boolProp>\r\n"
						+ "        <objProp>\r\n"
						+ "          <value class=\"SampleSaveConfiguration\">\r\n"
						+ "            <time>true</time>\r\n"
						+ "            <latency>true</latency>\r\n"
						+ "            <timestamp>true</timestamp>\r\n"
						+ "            <success>true</success>\r\n"
						+ "            <label>true</label>\r\n"
						+ "            <code>true</code>\r\n"
						+ "            <message>true</message>\r\n"
						+ "            <threadName>true</threadName>\r\n"
						+ "            <dataType>true</dataType>\r\n"
						+ "            <encoding>false</encoding>\r\n"
						+ "            <assertions>true</assertions>\r\n"
						+ "            <subresults>true</subresults>\r\n"
						+ "            <responseData>false</responseData>\r\n"
						+ "            <samplerData>false</samplerData>\r\n"
						+ "            <xml>true</xml>\r\n"
						+ "            <fieldNames>false</fieldNames>\r\n"
						+ "            <responseHeaders>false</responseHeaders>\r\n"
						+ "            <requestHeaders>false</requestHeaders>\r\n"
						+ "            <responseDataOnError>false</responseDataOnError>\r\n"
						+ "            <saveAssertionResultsFailureMessage>false</saveAssertionResultsFailureMessage>\r\n"
						+ "            <assertionsResultsToSave>0</assertionsResultsToSave>\r\n"
						+ "            <bytes>true</bytes>\r\n"
						+ "          </value>\r\n"
						+ "          <name>saveConfig</name>\r\n"
						+ "        </objProp>\r\n"
						+ "        <stringProp name=\"filename\"></stringProp>\r\n"
						+ "        <stringProp name=\"TestPlan.comments\">Gerado pela FERRARE 1.0</stringProp>\r\n"
						+ "      </ResultCollector>\r\n"
						+ "      <hashTree/>\r\n"

						+ "      <ResultCollector guiclass=\"TableVisualizer\" testclass=\"ResultCollector\" testname=\"View Results in Table\" enabled=\"true\">\r\n"
						+ "        <boolProp name=\"ResultCollector.error_logging\">false</boolProp>\r\n"
						+ "        <objProp>\r\n"
						+ "          <value class=\"SampleSaveConfiguration\">\r\n"
						+ "            <time>true</time>\r\n"
						+ "            <latency>true</latency>\r\n"
						+ "            <timestamp>true</timestamp>\r\n"
						+ "            <success>true</success>\r\n"
						+ "            <label>true</label>\r\n"
						+ "            <code>true</code>\r\n"
						+ "            <message>true</message>\r\n"
						+ "            <threadName>true</threadName>\r\n"
						+ "            <dataType>true</dataType>\r\n"
						+ "            <encoding>false</encoding>\r\n"
						+ "            <assertions>true</assertions>\r\n"
						+ "            <subresults>true</subresults>\r\n"
						+ "            <responseData>false</responseData>\r\n"
						+ "            <samplerData>false</samplerData>\r\n"
						+ "            <xml>true</xml>\r\n"
						+ "            <fieldNames>false</fieldNames>\r\n"
						+ "            <responseHeaders>false</responseHeaders>\r\n"
						+ "            <requestHeaders>false</requestHeaders>\r\n"
						+ "            <responseDataOnError>false</responseDataOnError>\r\n"
						+ "            <saveAssertionResultsFailureMessage>false</saveAssertionResultsFailureMessage>\r\n"
						+ "            <assertionsResultsToSave>0</assertionsResultsToSave>\r\n"
						+ "            <bytes>true</bytes>\r\n"
						+ "          </value>\r\n"
						+ "          <name>saveConfig</name>\r\n"
						+ "        </objProp>\r\n"
						+ "        <stringProp name=\"filename\"></stringProp>\r\n"
						+ "        <stringProp name=\"TestPlan.comments\">Gerado pela FERRARE 1.0</stringProp>\r\n"
						+ "      </ResultCollector>\r\n"
						+ "      <hashTree/>\r\n"
						+ "		 <CSVDataSet guiclass=\"TestBeanGUI\" testclass=\"CSVDataSet\" testname=\"CSV Data Set Config\" enabled=\"true\">\r\n"
						+ "		   <stringProp name=\"delimiter\">,</stringProp>"				        
				        + "		   <stringProp name=\"fileEncoding\"></stringProp>"
				        + "		   <stringProp name=\"filename\">"+csvFileName+"</stringProp>"
				        + "		   <boolProp name=\"recycle\">true</boolProp>"
				        + "		   <stringProp name=\"variableNames\">"+csvVariablesNames+"</stringProp>"
				        + "		 </CSVDataSet>"
				        + "		 <hashTree/>"
				        + "    </hashTree>\r\n"
						+ "  </hashTree>\r\n" + "</jmeterTestPlan>");

		FileWriter fw = new FileWriter(jmx);
		String s = code.toString();
		// System.out.println(jmx.canWrite());
		fw.append(s);
		fw.close();

		return true;
	}
}
