package com.ipay.client.task;

import java.util.HashMap;

/**GenericTask的参数类
 * 此类维护了一个HashMap用来放入参数。
 * 
 * @author tangym
 *
 */
public class TaskParams {
	
	//参数容器
	private HashMap<String, Object> params = null;

    public TaskParams() {
            params = new HashMap<String, Object>();
    }

    public TaskParams(String key, Object value) {
            this();
            put(key, value);
    }

    /**存入参数
     * 
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
            params.put(key, value);
    }

    private Object get(String key) {
            return params.get(key);
    }

    /**
     * Get the boolean value associated with a key.
     * 
     * @param key
     *            A key string.
     * @return The truth.
     * @throws ParamsNotFoundException
     *             if the value is not a Boolean or the String "true" or
     *             "false".
     */
    public boolean getBoolean(String key) throws ParamsNotFoundException {
            Object object = get(key);
            if (object.equals(Boolean.FALSE)
                            || (object instanceof String && ((String) object)
                                            .equalsIgnoreCase("false"))) {
                    return false;
            } else if (object.equals(Boolean.TRUE)
                            || (object instanceof String && ((String) object)
                                            .equalsIgnoreCase("true"))) {
                    return true;
            }
            throw new ParamsNotFoundException(key + " is not a Boolean.");
    }

    /**
     * Get the double value associated with a key.
     * 
     * @param key
     *            A key string.
     * @return The numeric value.
     * @throws ParamsNotFoundException
     *             if the key is not found or if the value is not a Number
     *             object and cannot be converted to a number.
     */
    public double getDouble(String key) throws ParamsNotFoundException {
            Object object = get(key);
            try {
                    return object instanceof Number ? ((Number) object).doubleValue()
                                    : Double.parseDouble((String) object);
            } catch (Exception e) {
                    throw new ParamsNotFoundException(key + " is not a number.");
            }
    }

    /**
     * Get the int value associated with a key.
     * 
     * @param key
     *            A key string.
     * @return The integer value.
     * @throws ParamsNotFoundException
     *             if the key is not found or if the value cannot be converted
     *             to an integer.
     */
    public int getInt(String key) throws ParamsNotFoundException {
            Object object = get(key);
            try {
                    return object instanceof Number ? ((Number) object).intValue()
                                    : Integer.parseInt((String) object);
            } catch (Exception e) {
                    throw new ParamsNotFoundException(key + " is not an int.");
            }
    }

    /**
     * Get the string associated with a key.
     * 
     * @param key
     *            A key string.
     * @return A string which is the value.
     * @throws ParamsNotFoundException
     *             if the key is not found.
     */
    public String getString(String key) throws ParamsNotFoundException {
            Object object = get(key);
            return object == null ? null : object.toString();
    }

    /**
     * 是否含有键值
     * 
     * @param key
     *    
     * @return true if contains the value, else false;
     */
    public boolean has(String key) {
            return this.params.containsKey(key);
    }

}
