package br.ufpi.datagenerator.creator;

import java.math.BigDecimal;
import java.util.Random;

import br.ufpi.datagenerator.domain.NumericAttribute;
import br.ufpi.datagenerator.domain.TextAttribute;
import br.ufpi.datagenerator.domain.TimeAttribute;
import br.ufpi.datagenerator.domain.types.DateType;
import br.ufpi.datagenerator.domain.types.IntegerType;
import br.ufpi.datagenerator.domain.types.RealType;
import br.ufpi.datagenerator.domain.types.StringType;
import br.ufpi.datagenerator.util.IrregularDateException;

import com.generator.RegularExpressionDataGenerator;

/**
 * Deve criar os diversos tipos de dados
 * 
 * @author Iure
 * 
 */
public class TypesCreator {

	/**
	 * Deve receber o valor anterior para a mesma tabela, e criará o proximo
	 * valor aceitavel
	 * 
	 * @param integerType
	 * @param unsigned
	 * @param beforeNumber
	 * @return long
	 */
	public long nexInteger(IntegerType integerType, boolean unsigned,
			long beforeNumber) {

		// se é unsigned ao chegar ao ultimo valor retornará ao primeiro
		if (unsigned) {
			if (beforeNumber == integerType.getUnsignedValue()) {
				return 0;
			} else {
				return (++beforeNumber);
			}
		} else {
			if (beforeNumber == integerType.getMaxValue()) {
				return integerType.getMinValue();
			} else {
				return (++beforeNumber);
			}
		}
	}

	/**
	 * Deve receber o valor anterior para a mesma tabela, e criará o proximo
	 * valor aceitavel
	 * 
	 * @param numericAttribute
	 * @param beforeNumber
	 * @return BigDecimal - O valor do proximo valor double a ser adicionado
	 */
	public BigDecimal nextReal(NumericAttribute numericAttribute,
			BigDecimal beforeNumber) {

		double maxvalue;

		double minValue;

		double unsignedValue;

		// verifica qual informação deve ser usada como limite o limite padrão
		// ou o informado pelo information schema
		if (numericAttribute.getNonDefaltmaxValue() != 0) {

			maxvalue = Math.min(numericAttribute.getNonDefaltmaxValue(),
					((RealType) numericAttribute.getType()).getMaxValue());
		} else {
			maxvalue = ((RealType) numericAttribute.getType()).getMaxValue();
		}

		if (numericAttribute.getNonDefaltminValue() != 0) {
			minValue = Math.max(numericAttribute.getNonDefaltminValue(),
					((RealType) numericAttribute.getType()).getMinValue());
		} else {
			minValue = ((RealType) numericAttribute.getType()).getMinValue();
		}

		if (numericAttribute.getNonDefaltunsignedValue() != 0) {
			unsignedValue = Math.min(numericAttribute
					.getNonDefaltunsignedValue(), ((RealType) numericAttribute
					.getType()).getMaxValue());
		} else {
			unsignedValue = ((RealType) numericAttribute.getType())
					.getMaxValue();
		}

		// será o expoente para o calculo do proximo numero levando em conta a
		// falta de precisão de float e double
		final int margin = 14;

		Random random = new Random();

		BigDecimal result;

		int index = beforeNumber.toString().indexOf(".");

		int precision = 0;

		// se tiver pontos a precisão deve ser calculada apos o ponto
		if (index > 0)
			precision = (new BigDecimal(beforeNumber.toString().substring(0,
					index))).precision();
		else
			precision = beforeNumber.precision();

		double nextNumber = 1.0;

		// se a precisão for maior que a margem deve então utilizar um numero
		// elevado a um expoente para não resultar em conflito
		if (precision > margin) {
			nextNumber = Math.pow(10, precision - margin);
		}

		// verifica se é unsigned para decidir a forma de criação
		if (numericAttribute.isUnsigned()) {
			if (beforeNumber.add(new BigDecimal(nextNumber)).compareTo(
					new BigDecimal((unsignedValue))) >= 0) {
				return new BigDecimal(0.0);
			} else {

				result = beforeNumber.add(new BigDecimal(random.nextDouble()));
				result = result.add(new BigDecimal(nextNumber));
				return result;
			}

		} else {
			if (beforeNumber.add(new BigDecimal(nextNumber)).compareTo(
					new BigDecimal((maxvalue))) >= 0) {
				return new BigDecimal(minValue);
			} else {
				result = beforeNumber.add(new BigDecimal(random.nextDouble()));
				result = result.add(new BigDecimal(nextNumber));
				return result;
			}
		}
	}

	/**
	 * Cria valores String aleatorios
	 * 
	 * @param textAttribute
	 * @return uma string com no maximo o tamanho definnodo na classe ou no tipo
	 */
	public String nextString(TextAttribute textAttribute) {

		RegularExpressionDataGenerator regularExpressionDataGenerator = new RegularExpressionDataGenerator();

		long num;

		if (textAttribute.getNonDefaltLenght() != 0) {
			num = textAttribute.getNonDefaltLenght();
		} else {
			num = ((StringType) textAttribute.getType()).getLength();
		}

		if (num > 1048576) {
			num = (1048576);
		}

		// reduzir a probabilidade de nomes repetidos
		int min = 8;
		if (num < 20) {
			min = (int) num / 2;
		}

		String name = regularExpressionDataGenerator
				.generateData("[A-Za-z0-9]{" + min + "," + num + "}");

		return name;
	}

	/**
	 * Uma data dentro do limite especificado no tipo
	 * 
	 * @param timeAttribute
	 * @return - um String com uma data no formato definido no tipo
	 * @throws IrregularDateException
	 */
	public String nextDate(TimeAttribute timeAttribute)
			throws IrregularDateException {

		String formate = ((DateType) timeAttribute.getType()).getFormate();

		String minLimit = ((DateType) timeAttribute.getType()).getMinValue();

		String maxLimit = ((DateType) timeAttribute.getType()).getMaxValue();

		int aMin = 0;
		int aMax = 0;

		int mMin = 0;
		int mMax = 0;

		int dMin = 0;
		int dMax = 0;

		int hMin = 0;
		int hMax = 0;

		int minMin = 0;
		int minMax = 0;

		int sMin = 0;
		int sMax = 0;

		int i = 0;
		while (i < formate.length()) {

			char key = formate.charAt(i);

			switch (key) {
			case 'A':
				aMin = i;
				for (int j = 0; j < 2; j++) {
					if (formate.charAt(aMin + j) != 'A') {
						throw new IrregularDateException("A");
					}
					i++;
				}
				aMax = i;
				if (formate.charAt(i) == 'A')
					for (int j = 0; j < 2; j++) {
						if (formate.charAt(aMax + j) != 'A') {
							throw new IrregularDateException("A");
						}
						i++;
					}

				aMax = i;
				break;
			case 'M':
				mMin = i;
				for (int j = 0; j < 2; j++) {
					if (formate.charAt(mMin + j) != 'M') {
						throw new IrregularDateException("M");
					}
					i++;
				}
				mMax = i;
				break;
			case 'D':
				dMin = i;
				for (int j = 0; j < 2; j++) {
					if (formate.charAt(dMin + j) != 'D') {
						throw new IrregularDateException("D");
					}
					i++;
				}
				dMax = i;
				break;
			case 'H':
				hMin = i;
				for (int j = 0; j < 2; j++) {
					if (formate.charAt(hMin + j) != 'H') {
						throw new IrregularDateException("H");
					}
					i++;
				}
				hMax = i;

				if (formate.charAt(i) == 'H')
					for (int j = 0; j < 1; j++) {
						if (formate.charAt(hMax + j) != 'H') {
							throw new IrregularDateException("H");
						}
						i++;
					}
				hMax = i;

				break;

			case 'm':
				minMin = i;
				for (int j = 0; j < 2; j++) {
					if (formate.charAt(minMin + j) != 'm') {
						throw new IrregularDateException("m");
					}
					i++;
				}
				minMax = i;

				break;

			case 'S':
				sMin = i;
				for (int j = 0; j < 2; j++) {
					if (formate.charAt(sMin + j) != 'S') {
						throw new IrregularDateException("S");
					}
					i++;
				}
				sMax = i;
				break;

			case ' ':
				i++;
				break;
			case '-':
				i++;
				break;
			case ':':
				i++;
				break;

			default:
				throw new IrregularDateException(" caractere valido");

			}
		}

		Random random = new Random();

		String result = formate;

		int randomMonth = 0;

		if (aMax != 0) {
			int minYear = Integer.parseInt((minLimit.substring(aMin, aMax)));
			int maxYear = Integer.parseInt((maxLimit.substring(aMin, aMax)));
			int interval = maxYear - minYear;

			int randomYear = minYear + random.nextInt(interval);

			if (aMax - aMin == 4)
				result = result.replace("AAAA", randomYear + "");
			else
				result = result.replace("AA", randomYear + "");

		}
		if (mMax != 0) {
			int minMonth = Integer.parseInt((minLimit.substring(mMin, mMax)));
			int maxMonth = Integer.parseInt((maxLimit.substring(mMin, mMax)));
			int interval = maxMonth - minMonth;

			randomMonth = minMonth + random.nextInt(interval);

			result = result.replace("MM", randomMonth + "");

		}
		if (dMax != 0) {
			int minDay = Integer.parseInt((minLimit.substring(dMin, dMax)));
			int maxDay = Integer.parseInt((maxLimit.substring(dMin, dMax)));
			int interval = maxDay - minDay;

			if (randomMonth % 2 == 0)
				interval--;
			if (randomMonth == 2)
				interval = 28;

			int randonDay = minDay + random.nextInt(interval);

			result = result.replace("DD", randonDay + "");

		}

		if (hMax != 0) {
			int minHour = Integer.parseInt((minLimit.substring(hMin, hMax)));
			int maxHour = Integer.parseInt((maxLimit.substring(hMin, hMax)));
			int interval = maxHour - minHour;

			int randonHour = minHour + random.nextInt(interval);

			if (hMax - hMin == 2)
				result = result.replace("HH", randonHour + "");
			else
				result = result.replace("HHH", randonHour + "");

		}
		if (minMax != 0) {
			int minMinute = Integer.parseInt((minLimit
					.substring(minMin, minMax)));
			int maxMinute = Integer.parseInt((maxLimit
					.substring(minMin, minMax)));
			int interval = maxMinute - minMinute;

			int randomMinute = minMinute + random.nextInt(interval);

			result = result.replace("mm", randomMinute + "");

		}
		if (sMax != 0) {
			int minSecond = Integer.parseInt((minLimit.substring(sMin, sMax)));
			int maxSecond = Integer.parseInt((maxLimit.substring(sMin, sMax)));
			int interval = maxSecond - minSecond;

			int randomsecond = minSecond + random.nextInt(interval);

			result = result.replace("SS", randomsecond + "");

		}

		return result;
	}

}
