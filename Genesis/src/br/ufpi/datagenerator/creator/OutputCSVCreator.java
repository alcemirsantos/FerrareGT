/**
 * 
 */
package br.ufpi.datagenerator.creator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import br.ufpi.datagenerator.util.UtilMethods;
import br.ufpi.genesis.util.Util;

/**
 * Responsavel por criar o arquivo de saida de uma replica��o
 * 
 * @author iure
 * 
 */
public class OutputCSVCreator {

	private Collection<String> header = null;

	private ICsvMapWriter writer;

	private RequiredTables requiredTables;

	/**
	 * Cria o diretorio e um arquivo CVS com o nome do schema
	 * 
	 * @param fileName
	 * @param inputNames
	 * @throws IOException
	 */
	public OutputCSVCreator(RequiredTables requiredTables,
			HashMap<String, ArrayList<HashMap<String, String>>> model)
			throws IOException {

		this.requiredTables = requiredTables;

		Set<String> requirerTAbleNames = requiredTables.getTables().keySet();

		ArrayList<String> requiredFields = new ArrayList<String>();  // itens do XXXXHeader.txt

		// cria o numero de elementos que ter� o csv
		requiredFields = UtilMethods.getRequiredFields(requiredTables, model);

		header = requiredFields;

		FileWriter dir = null;

		File file = new File("..");
		
				
		file = new File(file.getCanonicalFile() + Util.slash +"output");

		if (!file.canRead()) {
			file.mkdir();
		}

		String inputString = requiredFields.get(0);

		for (int i = 1; i < requiredFields.size(); i++) {
			inputString = inputString + "," + requiredFields.get(i);
		}

		File fileHeader = new File(file.getCanonicalFile() + Util.slash
				+ requiredTables.getSchema() + "Header.txt");

		if (fileHeader.exists()) {
			fileHeader.delete();
		}
		FileWriter fw = new FileWriter(fileHeader.getCanonicalPath(), false);

		fw.write(inputString);
		fw.close();

		file = new File(file.getCanonicalFile() + Util.slash
				+ requiredTables.getSchema() + ".csv");

		if (file.exists()) {
			file.delete();
		}

		dir = new FileWriter(file.getCanonicalFile(), true);

		System.out.println(file.getCanonicalPath());

		writer = new CsvMapWriter(dir, CsvPreference.EXCEL_PREFERENCE);

	}

	/**
	 * Adiciona uma nova linha ao arquivo CSV
	 * 
	 * @param newLine
	 * @throws IOException
	 */
	private void createFile(Collection<String> newLine) throws IOException {

		final HashMap<String, ? super Object> data1 = new HashMap<String, Object>();

		// inssere uma nova linha no csv
		for (int i = 0; i < header.size(); i++) {

			data1.put(((ArrayList<String>) header).get(i),
					((ArrayList<String>) newLine).get(i));

		}

		String[] elements = new String[header.size()];

		((ArrayList<String>) header).toArray(elements);

		writer.write(data1, elements);

	}

	/**
	 * recebe o estado atual e a adiciona no arquivo csv
	 * 
	 * @param actualState
	 * @throws IOException
	 */
	public void addTeplicate(ActualState actualState) throws IOException {

		Set<String> requirerTAbleNames = requiredTables.getTables().keySet();

		ArrayList<String> columnValues = new ArrayList<String>();
		for (String requiredTableName : requirerTAbleNames) {

			Set<Integer> columNunmbers = actualState.getTupleCreated().get(
					requiredTableName).keySet();

			ArrayList<String> fields = (ArrayList<String>) requiredTables
					.getTables().get(requiredTableName);

			for (String field : fields) {
				for (Integer columnNumber : columNunmbers) {

					columnValues.add(actualState.getTupleCreated().get(
							requiredTableName).get(columnNumber).get(field)
							.replace("'", ""));

				}

			}

		}

		createFile(columnValues);
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		writer.close();
	}

}
