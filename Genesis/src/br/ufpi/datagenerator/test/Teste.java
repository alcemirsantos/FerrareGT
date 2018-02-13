package br.ufpi.datagenerator.test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import br.ufpi.datagenerator.creator.PersistenceGenerator;
import br.ufpi.datagenerator.creator.RequiredTables;
import br.ufpi.datagenerator.domain.connectors.ConnectionFactory;
import br.ufpi.datagenerator.initialconfiguration.InitialValues;
import br.ufpi.datagenerator.util.SqlScript;

public class Teste {

	/**
	 * @param args
	 * 
	 *            <class name="br.ufpi.test.PersistenceReaderTest"/> <class
	 *            name="br.ufpi.test.PersistenceDiscovererTest"/> <class
	 *            name="br.ufpi.test.PersistenceGeneratorNullValuesTest"/>
	 *            <class name="br.ufpi.test.PersistenceGeneratorTest"/> <class
	 *            name="br.ufpi.test.TypesCreatorMysqlTest"/>
	 */

	public void dbcreator() {

		try {

			File dir1 = new File(".");
			SqlScript sqlScript = new SqlScript(dir1
					+ "\\test\\mysql\\PersistencegeneratorTest.sql");
			sqlScript.loadScript();
			sqlScript.execute();

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public static void main(String args[]) {

		Teste t = new Teste();

		// t.dbcreator();

		try {

			// InitialValues.initValues();
			// Connection connection = ConnectionFactory.getConnection(
			// "com.mysql.jdbc.Driver", "localhost", "INFORMATION_SCHEMA",
			// "root", "eruife");
			// PersistenceDiscoverer persistenceDiscoverer = new
			// PersistenceDiscoverer(
			// "anyquestion", connection);
			// //
			// HashMap<String, Table> tables1 = persistenceDiscoverer
			// .dependenceDiscoverer();
			//
			// for (Map.Entry<String, Table> tables : tables1.entrySet()) {
			// Table table = tables.getValue();
			//
			// System.out.print("new Table(" + table.getName());
			// System.out.print(", new MyArrayList(new Object[]{");
			// for (PrimitiveAttributes primitiveAttributes : table
			// .getPrimitiveAtributes()) {
			//
			// if (primitiveAttributes instanceof NumericAttribute) {
			// System.out.print("new NumericAttribute(\""
			// + primitiveAttributes.getName() + "\",");
			// } else if (primitiveAttributes instanceof TextAttribute) {
			// System.out.print("new TextAttribute(\""
			// + primitiveAttributes.getName() + "\",");
			// } else if (primitiveAttributes instanceof TimeAttribute) {
			// System.out.print("new TimeAttribute(\""
			// + primitiveAttributes.getName() + "\",");
			// }
			//
			// if (primitiveAttributes.getType() instanceof IntegerType)
			// System.out.print("new IntegerType(\""
			// + primitiveAttributes.getType().getName()
			// + "\",Group."
			// + primitiveAttributes.getType().getGroup()
			// + ","
			// + ((IntegerType) primitiveAttributes.getType())
			// .getMaxValue()
			// + "l,"
			// + ((IntegerType) primitiveAttributes.getType())
			// .getMinValue()
			// + "l,"
			// + ((IntegerType) primitiveAttributes.getType())
			// .getUnsignedValue() + "l),");
			// else if (primitiveAttributes.getType() instanceof RealType)
			// System.out.print("new RealType(\""
			// + primitiveAttributes.getType().getName()
			// + "\",Group."
			// + primitiveAttributes.getType().getGroup()
			// + ","
			// + ((RealType) primitiveAttributes.getType())
			// .getMaxValue()
			// + "d,"
			// + ((RealType) primitiveAttributes.getType())
			// .getMinValue() + "d),");
			// else if (primitiveAttributes.getType() instanceof StringType)
			// System.out.print("new StringType(\""
			// + primitiveAttributes.getType().getName()
			// + "\",Group."
			// + primitiveAttributes.getType().getGroup()
			// + ","
			// + ((StringType) primitiveAttributes.getType())
			// .getLength() + "l),");
			// else if (primitiveAttributes.getType() instanceof DateType)
			// System.out.print("new DateType(\""
			// + primitiveAttributes.getType().getName()
			// + "\",Group."
			// + primitiveAttributes.getType().getGroup()
			// + ",\""
			// + ((DateType) primitiveAttributes.getType())
			// .getFormate()
			// + "\",\""
			// + ((DateType) primitiveAttributes.getType())
			// .getMinValue()
			// + "\",\""
			// + ((DateType) primitiveAttributes.getType())
			// .getMaxValue() + "\"),");
			// else
			// System.out.print("new Type(\""
			// + primitiveAttributes.getType().getName()
			// + "\",Group."
			// + primitiveAttributes.getType().getGroup()
			// + "),");
			//
			// System.out.print(primitiveAttributes.isUnique() + ",");
			// System.out.print(primitiveAttributes.isPrimaryKey() + ",");
			// System.out.print(primitiveAttributes.isNullable() + ",");
			//
			// if (primitiveAttributes instanceof NumericAttribute) {
			// System.out
			// .print(((NumericAttribute) primitiveAttributes)
			// .isUnsigned()
			// + ",");
			// System.out
			// .print(((NumericAttribute) primitiveAttributes)
			// .isAutoIncrement()
			// + "");
			// } else if (primitiveAttributes instanceof TextAttribute) {
			// System.out.print(((TextAttribute) primitiveAttributes)
			// .getNonDefaltLenght()
			// + "l");
			// }
			//
			// System.out.print("),");
			// }
			//
			// System.out.print("}),");
			// System.out.print(" new MyArrayList(new Object[]{");
			// for (Relationship relationship : table.getRelationship()) {
			//
			// System.out.print("new Relationship(\""
			// + relationship.getName() + "\","
			// + relationship.getCardinality() + ","
			// + relationship.isNullable() + ",new Table("
			// + relationship.getForeignKey().getName()
			// + ",null,null))");
			//
			// }
			// System.out.println("})),");
			//
			// }

			// // monta os resultados de o retorno de um relacionamento
			// ArrayList<Relationship> emprestimos = (ArrayList<Relationship>)
			// persistenceDiscoverer
			// .relationshipFinder("funcionarios");
			//			
			// for (Relationship relationship : emprestimos) {
			//			
			// System.out.println("new Relationship(\""
			// + relationship.getName() + "\","
			// + relationship.getCardinality() + ","
			// + relationship.isNullable() + ",new Table("
			// + relationship.getForeignKey().getName()
			// + ",null,null));");
			//			
			// }

			// ArrayList<PrimitiveAttributes> primitives =
			// (ArrayList<PrimitiveAttributes>) persistenceDiscoverer
			// .atributesFinder("emprestimo");
			// // escreve o teste de atributos primitivos
			// for (PrimitiveAttributes primitiveAttributes : primitives) {
			//
			// if (primitiveAttributes instanceof NumericAttribute) {
			// System.out.print("new NumericAttribute(\""
			// + primitiveAttributes.getName() + "\",");
			// } else if (primitiveAttributes instanceof TextAttribute) {
			// System.out.print("new TextAttribute(\""
			// + primitiveAttributes.getName() + "\",");
			// } else if (primitiveAttributes instanceof TimeAttribute) {
			// System.out.print("new TimeAttribute(\""
			// + primitiveAttributes.getName() + "\",");
			// }
			//
			// if (primitiveAttributes.getType() instanceof IntegerType)
			// System.out.print("new IntegerType(\""
			// + primitiveAttributes.getType().getName()
			// + "\",Group."
			// + primitiveAttributes.getType().getGroup()
			// + ","
			// + ((IntegerType) primitiveAttributes.getType())
			// .getMaxValue()
			// + "l,"
			// + ((IntegerType) primitiveAttributes.getType())
			// .getMinValue()
			// + "l,"
			// + ((IntegerType) primitiveAttributes.getType())
			// .getUnsignedValue() + "l),");
			// else if (primitiveAttributes.getType() instanceof RealType)
			// System.out.print("new RealType(\""
			// + primitiveAttributes.getType().getName()
			// + "\",Group."
			// + primitiveAttributes.getType().getGroup()
			// + ","
			// + ((RealType) primitiveAttributes.getType())
			// .getMaxValue()
			// + "d,"
			// + ((RealType) primitiveAttributes.getType())
			// .getMinValue() + "d),");
			// else if (primitiveAttributes.getType() instanceof StringType)
			// System.out.print("new StringType(\""
			// + primitiveAttributes.getType().getName()
			// + "\",Group."
			// + primitiveAttributes.getType().getGroup()
			// + ","
			// + ((StringType) primitiveAttributes.getType())
			// .getLength() + "l),");
			// else if (primitiveAttributes.getType() instanceof DateType)
			// System.out.print("new DateType(\""
			// + primitiveAttributes.getType().getName()
			// + "\",Group."
			// + primitiveAttributes.getType().getGroup()
			// + ",\""
			// + ((DateType) primitiveAttributes.getType())
			// .getFormate()
			// + "\",\""
			// + ((DateType) primitiveAttributes.getType())
			// .getMinValue()
			// + "\",\""
			// + ((DateType) primitiveAttributes.getType())
			// .getMaxValue() + "\"),");
			// else
			// System.out.print("new Type(\""
			// + primitiveAttributes.getType().getName()
			// + "\",Group."
			// + primitiveAttributes.getType().getGroup() + "),");
			//
			// System.out.print(primitiveAttributes.isUnique() + ",");
			// System.out.print(primitiveAttributes.isPrimaryKey() + ",");
			// System.out.print(primitiveAttributes.isNullable() + ",");
			//
			// if (primitiveAttributes instanceof NumericAttribute) {
			// System.out.print(((NumericAttribute) primitiveAttributes)
			// .isUnsigned()
			// + ",");
			// System.out.print(((NumericAttribute) primitiveAttributes)
			// .isAutoIncrement()
			// + "");
			// } else if (primitiveAttributes instanceof TextAttribute) {
			// System.out.print(((TextAttribute) primitiveAttributes)
			// .getNonDefaltLenght()
			// + "l");
			// }
			//
			// System.out.println(");");
			// }

			// for (Map.Entry<String, Table> tables : tb.entrySet()) {
			// Table table = tables.getValue();
			// System.out.print("\"" + table.getName() + "\",");
			// System.out.println();
			// for (Relationship prim : table.getRelationship()) {
			// if (prim instanceof OneOrManyToOneRelationship)
			// System.out.print("\""
			// + ((OneOrManyToOneRelationship) prim).getName()
			// + "\",");
			// System.out.print("\"" + prim.getCardinality() + "\",");
			// System.out.print("\"" + prim.isNullable() + "\",");
			// System.out.println();
			// }
			// for (PrimitiveAttributes prim : table.getPrimitiveAtributes()) {
			// System.out.print("\"" + prim.getName() + "\",");
			// System.out.print("\"" + prim.getType() + "\",");
			// System.out.print("\"" + prim.isPrimaryKey() + "\",");
			// System.out.println();
			//
			// }
			// System.out.println();
			// System.out.println();
			//
			// }

			// InitialValues.initValues();
			// Connection connection = ConnectionFactory.getConnection(
			// "com.mysql.jdbc.Driver", "localhost", "INFORMATION_SCHEMA",
			// "root", "eruife");
			// PersistenceDiscoverer persistenceDiscoverer = new
			// PersistenceDiscoverer(
			// "testatributos", connection);
			// //
			//
			// HashMap<String, Table> tables1 = persistenceDiscoverer
			// .dependenceDiscoverer();
			//
			// connection.close();
			//
			// connection = ConnectionFactory.getConnection(
			// "com.mysql.jdbc.Driver", "localhost", "testatributos",
			// "root", "eruife");
			//
			// PersistenceReader persistenceReader = new PersistenceReader();
			//
			// HashMap<String, ArrayList<HashMap<String, String>>> actualState =
			// persistenceReader
			// .actualState(tables1, connection);
			//
			// PersistenceGenerator persistenceGenerator = new
			// PersistenceGenerator(
			// connection, tables1);
			//
			// HashMap<String, String> keys = new HashMap<String, String>();
			// String[] att = persistenceGenerator.unreferencedColoumn(tables1
			// .get("table1").getPrimitiveAtributes(), actualState.get(
			// "table1").get(1), "table1", keys);
			//
			// for (int i = 0; i < att.length; i++) {
			// String string = att[i];
			// System.out.println(string);
			//
			// }

			// Set<String> tableNames = actualState.keySet();

			// System.out
			// .println("HashMap<String, ArrayList<HashMap<String, String>>> actualState2 = new HashMap<String, ArrayList<HashMap<String, String>>>();");
			//
			// int i = 5, j = 5;
			//
			// for (String tableName : tableNames) {
			//
			// ArrayList<HashMap<String, String>> lines =
			// (ArrayList<HashMap<String, String>>) actualState
			// .get(tableName);
			//
			// System.out.println("ArrayList<HashMap<String, String>> lines"
			// + i + " = new ArrayList<HashMap<String, String>>();");
			//
			// for (HashMap<String, String> hashMap : lines) {
			//
			// System.out.println("HashMap<String, String> hashMap" + j
			// + " = new HashMap<String, String>();");
			//
			// Set<String> columNames = hashMap.keySet();
			//
			// for (String columnName : columNames) {
			// String a = (String) hashMap.get(columnName);
			// System.out.println("hashMap" + j + ".put(\""
			// + columnName + "\",\"" + a + "\");");
			//
			// }
			// System.out.println("lines" + i + ".add(hashMap" + j + ");");
			// j++;
			// }
			//
			// System.out.println("actualState2.put(\"" + tableName
			// + "\",lines" + i + ");");
			// i++;
			// }
			//
			// InitialValues.initValues();
			// Connection connection = ConnectionFactory.getConnection(
			// "com.mysql.jdbc.Driver", "localhost", "INFORMATION_SCHEMA",
			// "root", "eruife");
			// PersistenceDiscoverer persistenceDiscoverer = new
			// PersistenceDiscoverer(
			// "loja", connection);
			// //
			//
			// HashMap<String, Table> tables1 = persistenceDiscoverer
			// .dependenceDiscoverer();
			//
			// connection.close();
			//
			// connection = ConnectionFactory.getConnection(
			// "com.mysql.jdbc.Driver", "localhost", "loja", "root",
			// "eruife");
			//
			// connection.setAutoCommit(false);
			// PersistenceReader persistenceReader = new PersistenceReader();
			//
			// HashMap<String, ArrayList<HashMap<String, String>>> actualState =
			// persistenceReader
			// .actualState(tables1, connection);
			//
			// PersistenceGenerator persistenceGenerator = new
			// PersistenceGenerator(
			// connection, tables1);
			//
			// int i = 0;
			//
			// Savepoint savepoint = null;
			//
			// while (i < 2) {
			//
			// try {
			//
			// connection.setAutoCommit(false);
			//
			// savepoint = connection.setSavepoint("save1");
			//
			// ActualState actualState2 = new ActualState();
			//
			// persistenceGenerator.actualState = actualState2;
			//
			// Collection<HashMap<String, String>> dependences =
			// persistenceGenerator
			// .dependenceGenerator(tables1.get("funcionarios"));
			//
			// // dependences.addAll(persistenceGenerator
			// // .dependenceGenerator(tables1.get("a")));
			//
			// persistenceGenerator.criateUpdate();
			// connection.commit();
			// System.out.println(dependences);
			// i++;
			//
			// // depois verificar casos em que a repetição de elementos
			// // não é completa, algumas entradas ja existem pois estavão
			// // no meio da recursão outras não
			//
			// // teste em atributo nulo de uma tabela q ja existe
			//
			// } catch (SQLException e) {
			//
			// e.printStackTrace();
			// System.out
			// .println("####################################save point##################################");
			// System.out
			// .println("####################################save point##################################");
			// System.out
			// .println("####################################save point##################################");
			// System.out
			// .println("####################################save point##################################");
			// System.out
			// .println("####################################save point##################################");
			//
			// connection.rollback(savepoint);
			//
			// }
			//
			// }
			// connection.close();
			//
			// } catch (SQLException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (Exception e) {
			// // TODO: handle exception
			// e.printStackTrace();
			// }
			//		

			Connection connection = ConnectionFactory.getConnection(
					"com.mysql.jdbc.Driver", "localhost", "anyquestion",
					"root", "eruife", "mysql");

			PersistenceGenerator persistenceGenerator = new PersistenceGenerator(
					connection);

			HashMap<String, Collection<String>> a = new HashMap<String, Collection<String>>();
			RequiredTables requiredTables = new RequiredTables();

			ArrayList<String> columNames = new ArrayList<String>();

			columNames.add("login");
			columNames.add("senha");

			a.put("usuario", columNames);

			columNames = new ArrayList<String>();

			columNames.add("id");

			a.put("pesquisa", columNames);

			requiredTables.setTables(a);

			requiredTables.setNumberOfReplications(100);

			requiredTables.setSchema("anyquestion");

			InitialValues.initValues("mysql");

			persistenceGenerator.generateData(requiredTables);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}
