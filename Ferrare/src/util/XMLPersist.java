package util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import performanceTestRepresentation.TestPlan;
import functionalTestRepresentation.TestBattery;


/**
 * Classe que trata da Serialização e Deserialização dos Objetos em XML
 * @author Ismayle de Sousa Santos
 */
public class XMLPersist {

	
	public static void XMLEncode(Object obj, String fileName) throws IOException {
		File file = new File(fileName);
		XMLEncode(obj, file);
	}

	public static void XMLEncode(Object o, File file) throws IOException {
		FileOutputStream fos;
		XMLEncoder encoder;
		fos = new FileOutputStream(file);

		encoder = new XMLEncoder(new BufferedOutputStream(fos));

		encoder.writeObject(o);
		encoder.close();
		fos.close();
	}
	
	public static Object XMLDecode(String filePath) throws IOException {
		File file = new File(filePath);
		return XMLDecode(file);
	}
	
	public static Object XMLDecode(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);

		XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(fis));

		return (decoder.readObject());
	}

	/**
	 * Salva uma Bateria de Testes
	 * @param testBattery Bateria de Testes a ser salva
	 * @param path Endereço para salvar o arquivo com a Bateria de Testes
	 * @throws IOException
	 */
	public static void saveBattery(TestBattery testBattery, String path)
			throws IOException {
		XMLEncode(testBattery, path);
	}

	/**
	 * Salva uma Bateria de Testes
	 * @param testBattery Bateria de Testes a ser salva
	 * @param file Arquivo a ser criado com a Bateria de Testes
	 * @throws IOException
	 */	
	public static void saveBattery(TestBattery testBattery, File file)
			throws IOException {
		XMLEncode(testBattery, file);		
	}

	/**
	 * Carrega uma Bateria de Testes 
	 * @param path Localização da Bateria de Testes
	 * @return Bateria de Testes ou null
	 * @throws IOException
	 */

	public static TestBattery loadBattery(String path) throws IOException {
		Object o = XMLDecode(path);
		if (o instanceof TestBattery){
			TestBattery battery = new TestBattery();
			battery = (TestBattery) o;
			return (battery);
		}			
		else
			return null;
	}

	/**
	 * Carrega uma Bateria de Testes 
	 * @param file Arquivo que contém a Bateria de Testes
	 * @return Bateria de Testes ou null
	 * @throws IOException
	 */

	public static TestBattery loadBattery(File file) throws IOException {
		Object o = XMLDecode(file);
		if (o instanceof TestBattery){
			TestBattery battery = new TestBattery();
			battery = (TestBattery) o;
			return (battery);
		}
		else
			return null;
	}

	/**
	 * Salva um Plano de Teste
	 * @param testPlan Plano de Teste a ser salvo
	 * @param path Endereço para salvar o arquivo com o Plano de Teste
	 * @throws IOException
	 */
	public static void savePerformanceTestPlan(TestPlan testPlan, String path)
			throws IOException {
		XMLEncode(testPlan, path);		
	}

	/**
	 * Carrega um Plano de Teste
	 * @param path Localização do Plano de Teste
	 * @return Plano de Teste ou null
	 * @throws IOException
	 */
	public static TestPlan loadPerfomanceTestPlan(String path)
			throws IOException {
		Object o = XMLDecode(path);
		if (o instanceof TestPlan)
			return ((TestPlan) o);
		else
			return null;
	}

}
