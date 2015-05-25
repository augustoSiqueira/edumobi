package br.com.edu_mob.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * Filtro para pesquisas. A chave do filtro representa o par�metro de pesquisa e o valor do filtro
 * representa o valor do par�metro de pesquisa.
 */
@SuppressWarnings("serial")
public class Filter extends HashMap<String, Object> {

	private static final int ESCALA_PADRAO = 2;

	private Map<String, Boolean> order;

	/**
	 * Cria uma nova inst�ncia desta classe.
	 */
	public Filter() {
		this.order = new HashMap<String, Boolean>();
	}

	/**
	 * Cria uma nova inst�ncia desta classe.
	 *
	 * @param filter Um filtro a ter os par�metros copiados.
	 */
	public Filter(Filter filter) {
		this(filter, false);
	}

	/**
	 * Cria uma nova inst�ncia desta classe.
	 *
	 * @param filter Um filtro a ter os par�metros copiados.
	 * @param discardNullValues Indica se � para descartar as chaves com valores nulos ou com
	 *        Strings em branco.
	 */
	public Filter(Filter filter, boolean discardNullValues) {
		this();

		if (discardNullValues) {

			// adicionando ao filtro apenas os parametros com valores n�o nulos
			for (java.util.Map.Entry<String, Object> entry : filter.entrySet()) {
				if (this.isNotNull(entry.getValue())) {
					this.put(entry.getKey(), entry.getValue()); // NOPMD
				}
			}
		} else {
			this.putAll(filter);
		}
		this.order.putAll(filter.getOrder());
	}

	private boolean isNotNull(Object value) {
		boolean result = false;

		if (value instanceof String) {
			result = StringUtils.isNotBlank((String) value);
		} else {
			result = value != null;
		}
		return result;
	}

	/**
	 * Adiciona o atributo a ser usado na ordena��o.
	 *
	 * @param attribute O atributo a ser usado na ordena��o.
	 */
	public void addOrder(String attribute) {
		this.addOrder(attribute, true);
	}

	/**
	 * Adiciona o atributo a ser usado na ordena��o.
	 *
	 * @param attribute O atributo a ser usado na ordena��o.
	 * @param ascending Indica se a ordem � ascendente ou descendente.
	 */
	public void addOrder(String attribute, Boolean ascending) {
		this.order.put(attribute, ascending);
	}

	/**
	 * Retorna a lista de atributos a serem usados na ordena��o.
	 *
	 * @return Um mapa com o campo a ser ordenado e o tipo de ordena��o.
	 */
	public Map<String, Boolean> getOrder() {
		return this.order;
	}

	/**
	 * Atribui a lista de atributos a serem usados na ordena��o.
	 *
	 * @param order Um mapa com o campo a ser ordenado e o tipo de ordena��o.
	 */
	public void setOrder(Map<String, Boolean> order) {
		this.order = order;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object put(String key, Object value) {
		if ((value == null) || ((value instanceof String) && StringUtils.isBlank((String) value))) {
			this.remove(key);
			return null;
		}
		return super.put(key, value);
	}

	/**
	 * Retorna o valor do atributo como um BigDecimal.
	 *
	 * @param attribute O atributo a ter o valor retornado.
	 * @return Uma inst�ncia de BigDecimal.
	 */
	public BigDecimal getAsBigDecimal(String attribute) {
		BigDecimal value = null;
		Object attributeValue = this.get(attribute);

		if (attributeValue == null) {
			value = null;
		} else if (attributeValue instanceof BigDecimal) {
			if (attributeValue.equals(BigDecimal.ZERO)) {
				value = null;
			} else {
				value = (BigDecimal) attributeValue;
			}
		} else if (attributeValue instanceof Double) {
			value = new BigDecimal((Double) attributeValue).setScale(ESCALA_PADRAO, BigDecimal.ROUND_HALF_UP);
		} else if (attributeValue instanceof String) {
			if (!StringUtils.isBlank((String) attributeValue)) {
				value = new BigDecimal((String) attributeValue).setScale(ESCALA_PADRAO, BigDecimal.ROUND_HALF_UP);
			}
		} else {
			throw new IllegalArgumentException("O tipo do atributo a ser convertido � inv�lido: " + attribute
					+ " (tipo do atributo = " + attributeValue.getClass().getName());
		}
		return value;
	}

	/**
	 * Retorna o valor do atributo como uma String.
	 *
	 * @param attribute O atributo a ter o valor retornado.
	 * @return Uma inst�ncia de String.
	 */
	public String getAsString(String attribute) {
		String value = null;

		if (!StringUtils.isBlank((String) this.get(attribute))) {
			value = (String) this.get(attribute);
		}
		return value;
	}
}
