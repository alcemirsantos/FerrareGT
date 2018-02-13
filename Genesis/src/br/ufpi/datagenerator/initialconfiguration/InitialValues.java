package br.ufpi.datagenerator.initialconfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.ufpi.datagenerator.domain.types.DateType;
import br.ufpi.datagenerator.domain.types.Group;
import br.ufpi.datagenerator.domain.types.IntegerType;
import br.ufpi.datagenerator.domain.types.RealType;
import br.ufpi.datagenerator.domain.types.StringType;
import br.ufpi.datagenerator.domain.types.Type;

import com.thoughtworks.xstream.XStream;

public class InitialValues {

	/**
	 * Dado um sgbd ser� a lista que contem todos os tipos e informa�oes dos
	 * mesmos com a chave sendo o nome do tipo
	 */
	public static HashMap<String, Type> types = new HashMap<String, Type>();

	/**
	 * Contem as SQL do banco, juntamente com o seu tipo
	 */
	public static DataBaseParameters dataBaseParameters;

	/**
	 * 
	 * @return Collection<type> - retorna os tipos do mysql
	 */
	private static Collection<Type> mysqlTypes() {
		IntegerType integerType = new IntegerType();

		integerType.setName("TINYINT");
		integerType.setMaxValue(127);
		integerType.setMinValue(-127);
		integerType.setUnsignedValue(256);
		integerType.setGroup(Group.NUMERIC);

		IntegerType smallint = new IntegerType();

		smallint.setName("SMALLINT");
		smallint.setMaxValue(32767);
		smallint.setMinValue(-32768);
		smallint.setUnsignedValue((2 * 32768));
		smallint.setGroup(Group.NUMERIC);

		IntegerType mediumint = new IntegerType();

		mediumint.setName("MEDIUMINT");
		mediumint.setMaxValue(8388607);
		mediumint.setMinValue(-8388608);
		mediumint.setUnsignedValue((2 * 8388608));
		mediumint.setGroup(Group.NUMERIC);

		IntegerType bigint = new IntegerType();

		bigint.setName("BIGINT");
		bigint.setMaxValue(9223372036854775801l);
		bigint.setMinValue(-9223372036854775801l);
		bigint.setUnsignedValue((9223372036854775801l));
		bigint.setGroup(Group.NUMERIC);

		IntegerType ints = new IntegerType();

		ints.setName("INT");
		ints.setMaxValue(2147483647);
		ints.setMinValue(-2147483648);
		ints.setUnsignedValue((2 * 2147483648l));
		ints.setGroup(Group.NUMERIC);

		StringType chars = new StringType();
		chars.setName("CHAR");
		chars.setLength(255);
		chars.setGroup(Group.STRING);

		StringType TEXT = new StringType();
		TEXT.setName("TEXT");
		TEXT.setLength(65535);
		TEXT.setGroup(Group.STRING);

		StringType MEDIUMTEXT = new StringType();
		MEDIUMTEXT.setName("MEDIUMTEXT");
		MEDIUMTEXT.setLength(4294967295l);
		MEDIUMTEXT.setGroup(Group.STRING);

		StringType LONGTEXT = new StringType();
		LONGTEXT.setName("LONGTEXT");
		LONGTEXT.setLength(16777215);
		LONGTEXT.setGroup(Group.STRING);

		StringType TINYBLOB = new StringType();
		TINYBLOB.setName("TINYBLOB");
		TINYBLOB.setLength(255);
		TINYBLOB.setGroup(Group.STRING);

		StringType enumm = new StringType();
		enumm.setName("ENUM");
		enumm.setLength(255);
		enumm.setGroup(Group.STRING);

		StringType tinytext = new StringType();
		tinytext.setName("TINYTEXT");
		tinytext.setLength(255);
		tinytext.setGroup(Group.STRING);

		StringType varchar = new StringType();
		varchar.setName("VARCHAR");
		varchar.setLength(255);
		varchar.setGroup(Group.STRING);

		RealType floats = new RealType();
		floats.setName("FLOAT");
		floats.setMaxValue(9999999);
		floats.setMinValue(-9999999);
		floats.setGroup(Group.NUMERIC);

		RealType doubles = new RealType();
		doubles.setName("DOUBLE");
		doubles.setMinValue(-2.2250738596294014E80);
		doubles.setMaxValue(2.2250738596294014E80);
		doubles.setGroup(Group.NUMERIC);

		RealType decimal = new RealType();
		decimal.setName("DECIMAL");
		decimal.setMinValue(-1000000000d);
		decimal.setMaxValue(1000000000d);
		decimal.setGroup(Group.NUMERIC);

		DateType DATE = new DateType();
		DATE.setName("DATE");
		DATE.setFormate("AAAA-MM-DD");
		DATE.setMinValue("1000-01-01");
		DATE.setMaxValue("9999-12-31");
		DATE.setGroup(Group.DATE_TIME);

		DateType DATETIME = new DateType();
		DATETIME.setName("DATETIME");
		DATETIME.setFormate("AAAA-MM-DD HH:mm:SS");
		DATETIME.setMaxValue("9999-12-31 23:59:59");
		DATETIME.setMinValue("1000-01-01 00:00:00");
		DATETIME.setGroup(Group.DATE_TIME);

		DateType TIMESTAMP = new DateType();
		TIMESTAMP.setName("TIMESTAMP");
		TIMESTAMP.setFormate("AAAA-MM-DD HH:mm:SS");
		TIMESTAMP.setMinValue("1970-01-01 00:00:00");
		TIMESTAMP.setMaxValue("2036-12-31 23:59:59");
		TIMESTAMP.setGroup(Group.DATE_TIME);

		DateType TIME = new DateType();
		TIME.setName("TIME");
		TIME.setFormate("HHH:mm:SS");
		TIME.setMaxValue("838:59:59");
		TIME.setMinValue("000:00:00");
		TIME.setGroup(Group.DATE_TIME);

		DateType YEAR = new DateType();
		YEAR.setName("YEAR");
		YEAR.setFormate("AAAA");
		YEAR.setMinValue("1901");
		YEAR.setMaxValue("2155");
		YEAR.setGroup(Group.DATE_TIME);

		IntegerType bit = new IntegerType();
		bit.setName("BIT");
		bit.setGroup(Group.NUMERIC);
		bit.setMaxValue(1);
		bit.setMinValue(0);
		bit.setUnsignedValue(1);
		Collection<Type> typesMYSQL = new ArrayList<Type>();

		typesMYSQL.add(YEAR);
		typesMYSQL.add(TIME);
		typesMYSQL.add(TIMESTAMP);
		typesMYSQL.add(DATETIME);
		typesMYSQL.add(DATE);

		typesMYSQL.add(floats);
		typesMYSQL.add(doubles);
		typesMYSQL.add(decimal);

		typesMYSQL.add(integerType);
		typesMYSQL.add(smallint);
		typesMYSQL.add(mediumint);
		typesMYSQL.add(ints);
		typesMYSQL.add(bigint);

		typesMYSQL.add(chars);
		typesMYSQL.add(varchar);
		typesMYSQL.add(tinytext);
		typesMYSQL.add(MEDIUMTEXT);
		typesMYSQL.add(TEXT);
		typesMYSQL.add(LONGTEXT);
		typesMYSQL.add(TINYBLOB);
		typesMYSQL.add(enumm);

		typesMYSQL.add(bit);

		return typesMYSQL;
	}

	/**
	 * 
	 * @return Collection<type> - os tipos do postgres
	 */
	private static Collection<Type> postgresTypes() {

		Collection<Type> typesPOSTGRES = new ArrayList<Type>();

		StringType text = new StringType();
		text.setName("text".toUpperCase());
		text.setLength(65535);
		text.setGroup(Group.STRING);

		StringType character = new StringType();
		character.setName("character".toUpperCase());
		character.setLength(255);
		character.setGroup(Group.STRING);

		StringType charactervarying = new StringType();
		charactervarying.setName("character varying".toUpperCase());
		charactervarying.setLength(255);
		charactervarying.setGroup(Group.STRING);

		StringType booleantype = new StringType();
		booleantype.setName("boolean".toUpperCase());
		booleantype.setLength(1);
		booleantype.setGroup(Group.STRING);

		typesPOSTGRES.add(text);
		typesPOSTGRES.add(character);
		typesPOSTGRES.add(charactervarying);
		typesPOSTGRES.add(booleantype);

		RealType numeric = new RealType();
		numeric.setName("NUMERIC");
		numeric.setMinValue(-1000000000d);
		numeric.setMaxValue(1000000000d);
		numeric.setGroup(Group.NUMERIC);

		RealType decimal = new RealType();
		decimal.setName("decimal".toUpperCase());
		decimal.setMinValue(-1000000000d);
		decimal.setMaxValue(1000000000d);
		decimal.setGroup(Group.NUMERIC);

		RealType real = new RealType();
		real.setName("real".toUpperCase());
		real.setMaxValue(999999);
		real.setMinValue(-999999);
		real.setGroup(Group.NUMERIC);

		RealType doubleprecision = new RealType();
		doubleprecision.setName("double precision".toUpperCase());
		doubleprecision.setMinValue(99999999999999l);
		doubleprecision.setMaxValue(-99999999999999l);
		doubleprecision.setGroup(Group.NUMERIC);

		typesPOSTGRES.add(numeric);
		typesPOSTGRES.add(decimal);
		typesPOSTGRES.add(real);
		typesPOSTGRES.add(doubleprecision);

		IntegerType bigserial = new IntegerType();
		bigserial.setName("bigserial".toUpperCase());
		bigserial.setMaxValue(9223372036854775807l);
		bigserial.setMinValue(1);
		bigserial.setUnsignedValue(9223372036854775807l);
		bigserial.setGroup(Group.NUMERIC);

		IntegerType serial = new IntegerType();
		serial.setName("serial".toUpperCase());
		serial.setMaxValue(2147483647);
		serial.setMinValue(1);
		serial.setUnsignedValue(2147483647);
		serial.setGroup(Group.NUMERIC);

		IntegerType bigint = new IntegerType();
		bigint.setName("bigint".toUpperCase());
		bigint.setMaxValue(9223372036854775807l);
		bigint.setMinValue(-9223372036854775808l);
		bigint.setUnsignedValue(9223372036854775807l);
		bigint.setGroup(Group.NUMERIC);

		IntegerType integer = new IntegerType();
		integer.setName("integer".toUpperCase());
		integer.setMaxValue(2147483647);
		integer.setMinValue(-2147483648);
		integer.setUnsignedValue(2147483647);
		integer.setGroup(Group.NUMERIC);

		IntegerType smallint = new IntegerType();
		smallint.setName("smallint".toUpperCase());
		smallint.setMaxValue(32767);
		smallint.setMinValue(-32768);
		smallint.setUnsignedValue(32768);
		smallint.setGroup(Group.NUMERIC);

		typesPOSTGRES.add(bigserial);
		typesPOSTGRES.add(serial);
		typesPOSTGRES.add(bigint);
		typesPOSTGRES.add(integer);
		typesPOSTGRES.add(smallint);

		DateType DATE = new DateType();
		DATE.setName("date".toUpperCase());
		DATE.setFormate("MM/DD/AAAA");
		DATE.setMaxValue("12/30/9999");
		DATE.setMinValue("1/1/0000");
		DATE.setGroup(Group.DATE_TIME);

		return typesPOSTGRES;

	}

	/**
	 * Gera o banco com configura�oes pad�o do mysql e postgres caso n�o exista
	 * nenhuma configura��o anterior
	 * 
	 * @return String - com o xml do banco
	 * @throws IOException
	 */
	private static String generateXML() throws IOException {

		XStream xstream = new XStream();

		DataBaseParameters mysql = new DataBaseParameters();

		mysql
				.setCollumnsSQL("SELECT DISTINCT a.COLUMN_NAME,DATA_TYPE,if(COLUMN_KEY='PRI','YES','NO') as IS_PRIMARY_KEY,if(CONSTRAINT_TYPE='UNIQUE','YES','NO') as IS_UNIQUE,IS_NULLABLE,if(EXTRA='auto_increment','YES','NO') as IS_AUTO_INCREMENT,CHARACTER_MAXIMUM_LENGTH as TEXT_LENGHT,NUMERIC_PRECISION,NUMERIC_SCALE, if((column_type like('%unsigned%')),'YES','NO') as IS_UNSIGNED FROM (select a.table_name,a.table_schema,a.column_name,constraint_type from information_schema.KEY_COLUMN_USAGE a inner join information_schema.TABLE_CONSTRAINTS b on a.constraint_name=b.constraint_name and constraint_type='UNIQUE') c right join (SELECT * FROM INFORMATION_SCHEMA.COLUMNS) a on c.table_name=a.table_name and c.table_schema=a.table_schema and a.column_name=c.column_name LEFT JOIN (SELECT column_name as column_name2, table_name as table_name2, table_schema as table_schema2 FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE d where CONSTRAINT_NAME like('%FK%')) b ON column_name2 = a.column_name and a.table_name=table_name2 and a.table_schema=table_schema2 WHERE column_name2 IS NULL and a.table_schema=? AND a.table_name=?");
		mysql.setDataBaseName("mysql");
		mysql
				.setRelationshipSQL("SELECT DISTINCT IS_NULLABLE, INFORMATION_SCHEMA.COLUMNS.COLUMN_NAME,REFERENCED_TABLE_NAME,REFERENCED_COLUMN_NAME,if(COLUMN_KEY='PRI','YES','NO') as IS_PRIMARY_KEY FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE JOIN INFORMATION_SCHEMA.COLUMNS ON INFORMATION_SCHEMA.KEY_COLUMN_USAGE.column_name= INFORMATION_SCHEMA.COLUMNS.column_name AND INFORMATION_SCHEMA.KEY_COLUMN_USAGE.table_name= INFORMATION_SCHEMA.COLUMNS.table_name WHERE CONSTRAINT_NAME LIKE('%FK%') AND INFORMATION_SCHEMA.COLUMNS.table_schema=? and INFORMATION_SCHEMA.COLUMNS.table_name=?");

		mysql
				.setTableNamesSQL("SELECT table_name FROM INFORMATION_SCHEMA.TABLES where table_schema=?");

		mysql.setTypes(mysqlTypes());

		mysql.setDriver("com.mysql.jdbc.Driver");

		mysql.setJdbcName("mysql");

		Collection<DataBaseParameters> dataBases = new ArrayList<DataBaseParameters>();

		dataBases.add(mysql);

		// postgres

		DataBaseParameters postgres = new DataBaseParameters();

		postgres
				.setCollumnsSQL("select COLUMN_NAME,DATA_TYPE, (CASE primary_key WHEN 'PRIMARY KEY' THEN 'YES' ELSE 'NO' END ) as IS_PRIMARY_KEY,(CASE primary_key WHEN 'UNIQUE' THEN 'YES' ELSE 'NO' END ) as IS_UNIQUE, IS_NULLABLE,(CASE is_generated WHEN 'YES' THEN 'NO' ELSE 'NO' END ) as IS_AUTO_INCREMENT,	CHARACTER_MAXIMUM_LENGTH as TEXT_LENGHT,NUMERIC_PRECISION,NUMERIC_SCALE,(CASE is_generated WHEN 'YES' THEN 'NO' ELSE 'NO' END ) as IS_UNSIGNED from information_schema.columns a left JOIN (select distinct on(column_name2) column_name2,constraint_type2 as primary_key,constraint_type as foreing_key,table_name2 as table_name  from (select column_name as column_name2, a.table_name as table_name2, constraint_type as constraint_type2 from information_schema.table_constraints a inner join information_schema.key_column_usage b on a.constraint_name=b.constraint_name ) c left join 	(select * from information_schema.table_constraints a inner join information_schema.key_column_usage b on a.constraint_name=b.constraint_name where constraint_type='FOREIGN KEY') d on c.column_name2=d.column_name ) b ON column_name2=column_name where   foreing_key isnull and (?='a' or true) and a.table_name=?");

		postgres.setDataBaseName("postgreSQL8.2");

		postgres
				.setRelationshipSQL("select distinct on (c.column_name) IS_NULLABLE,c.column_name as COLUMN_NAME,b.table_name as REFERENCED_TABLE_NAME,b.column_name as REFERENCED_COLUMN_NAME ,	(CASE e.constraint_type2 WHEN 'PRIMARY KEY' THEN 'YES' ELSE 'NO' END ) as IS_PRIMARY_KEY from information_schema.table_constraints a  inner join 	information_schema.constraint_column_usage b on a.constraint_name=b.constraint_name  inner join information_schema.key_column_usage c on c.constraint_name=a.constraint_name and constraint_type='FOREIGN KEY' inner join information_schema.key_column_usage d on d.table_name=b.table_name  and d.column_name=b.column_name  and d.ordinal_position=c.ordinal_position 	left join (select column_name as column_name2, a.table_name as table_name2, constraint_type as constraint_type2,a.constraint_name from information_schema.table_constraints a inner join information_schema.key_column_usage b on a.constraint_name=b.constraint_name where constraint_type='PRIMARY KEY') e on c.column_name=e.column_name2 and a.table_name=e.table_name2 left join information_schema.columns k on k.table_name=	b.table_name and k.column_name=d.column_name where (?='a' or true) and a.table_name=?");

		postgres
				.setTableNamesSQL("SELECT table_name FROM information_schema.tables  WHERE table_type = 'BASE TABLE' AND (?='a' or true) AND table_schema NOT IN  ('pg_catalog', 'information_schema')");

		postgres.setTypes(postgresTypes());

		postgres.setDriver("org.postgresql.Driver");

		postgres.setJdbcName("postgresql");

		dataBases.add(postgres);

		String typesXML = xstream.toXML(dataBases);

		System.out.println(typesXML);

		File file2 = new File("..");
		String slash="\\";
		if (file2.getCanonicalFile().toString().indexOf("/") >= 0) {
			slash = "/";
		}

		File file = new File(file2.getCanonicalFile() + slash+"dataBases");

		if (!file.canRead()) {
			file.mkdir();
		}

		FileWriter fw = new FileWriter(file.getCanonicalPath()
				+ slash +"dataBaseParameters.xml", false);

		fw.write(typesXML);
		fw.close();

		return typesXML;

	}

	@SuppressWarnings("unchecked")
	public static Collection<DataBaseParameters> getDataBaseParameters()
			throws IOException {

		XStream xstream = new XStream();

		File file2 = new File("..");
		String slash="\\";
		if (file2.getCanonicalFile().toString().indexOf("/") >= 0) {
			slash = "/";
		}
		File file = new File(file2.getCanonicalFile()
				+ slash + "dataBases" + slash +"dataBaseParameters.xml");

		if (!file.canRead()) {
			generateXML();
		}

		FileReader fr = new FileReader(file.getCanonicalPath());

		BufferedReader in = new BufferedReader(fr);

		String str = "";
		while (in.ready()) {

			str = str + in.readLine() + "\n";
		}
		in.close();

		fr.close();

		return (Collection<DataBaseParameters>) xstream.fromXML(str);

	}

	/**
	 * Carrega as configura�oes encontrados no arquivo dataBaseParameters.XML
	 * 
	 * @param dbname
	 *            - nome da configura��o do banco de dados
	 * @throws IOException
	 */
	public static void initValues(String dbname) throws IOException {

		Collection<DataBaseParameters> dataBases = getDataBaseParameters();

		// procura o nome do banco de dados nos modelos existentes
		for (DataBaseParameters dataBaseParameters2 : dataBases) {
			if (dataBaseParameters2.getDataBaseName().equals(dbname)) {

				dataBaseParameters = dataBaseParameters2;

				break;
			}

		}

		List<Type> typesUml = (List<Type>) dataBaseParameters.getTypes();

		for (Type type : typesUml) {
			types.put(type.getName(), type);
		}

	}
}
