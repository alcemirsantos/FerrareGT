package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 * Classe que trata dos arquivos 
 * @author Ismayle de Sousa Santos
 */
public class FileHandler {
	private BufferedReader in = null;

	private LinkedList<String> outputs;

	/**
	 * Construtor da Classe
	 * @param fileName Localização do arquivo
	 * @throws FileNotFoundException Arquivo não encontrado
	 */
	public FileHandler(String fileName) throws FileNotFoundException {
		setFile(fileName);
	}

	/**
	 * Construtor da Classe
	 * @param file arquivo Nome do arquivo
	 * @throws FileNotFoundException Arquivo não encontrado
	 */
	public FileHandler(File file) throws FileNotFoundException {
		setFile(file);
	}

	/**
	 * Altera o arquivo
	 * @param fileName Novo caminho do arquivo
	 * @throws FileNotFoundException Arquivo não encontrado
	 */
	public void setFile(String fileName) throws FileNotFoundException {
		FileInputStream fileInputStream = new FileInputStream(fileName);
		InputStreamReader inputStreamReader = new InputStreamReader(
				fileInputStream);
		in = new BufferedReader(inputStreamReader);
		outputs = new LinkedList<String>();
	}

	/**
	 * Altera o arquivo
	 * @param file Novo arquivo
	 * @throws FileNotFoundException Arquivo não encontrado
	 */
	public void setFile(File file) throws FileNotFoundException {
		FileInputStream fileInputStream = new FileInputStream(file);
		InputStreamReader inputStreamReader = new InputStreamReader(
				fileInputStream);
		in = new BufferedReader(inputStreamReader);
		outputs = new LinkedList<String>();
	}

	/**
	 * Lê uma linha do arquivo
	 * @return Linha do arquivo
	 */
	public String readLine() {
		if (in != null) {
			if (!outputs.isEmpty()) {
				String result = outputs.getFirst();
				outputs.removeFirst();
				return result;
			} else {
				try {
					return in.readLine();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		return null;
	}

	/**
	 * Fecha o ponteiro do arquivo
	 */
	public void close() {
		try {
			in.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		in = null;
	}

	/**
	 * Indica o final do arquivo
	 * @return True se é o final do arquivo e False caso contrário
	 */
	public boolean endOfFile() {
		if (!outputs.isEmpty()) {
			return false;
		} else {
			String result = null;
			try {
				result = in.readLine();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			if (result != null) {
				outputs.addLast(result);
				return false;
			} else {
				return true;
			}
		}
	}
}
