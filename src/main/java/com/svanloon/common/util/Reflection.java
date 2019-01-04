package com.svanloon.common.util;

import java.lang.reflect.Method;
import java.util.logging.Logger;

/**
 * Utility methods for performing Java reflection.
 */
public class Reflection {
	private static Logger _logger = Logger.getLogger(Reflection.class.getName());

	/**
	 * Pass in the String name of a class, and return its class.
	 * @param className 
	 * @return Object
	 */
	public static Object getObject(String className) {
		Object object = null;
		try {
			Class<?> classDefinition = Class.forName(className);
			object = classDefinition.newInstance();
		} catch (InstantiationException e) {
			_logger.warning(e.toString() + " - " + e.getMessage());
		} catch (IllegalAccessException e) {
			_logger.warning(e.toString() + " - " + e.getMessage());
		} catch (ClassNotFoundException e) {
			_logger.warning(e.toString() + " - " + e.getMessage());
		}

		return object;
	}

	/**
	 * 
	 *  Document the invokeMethod method 
	 *
	 * @param object
	 * @param methodName
	 * @return Object
	 */
	public static Object invokeMethod(Object object, String methodName) {
		return invokeMethod(object, methodName, null, false);
	}

	/**
	 * 
	 *  Document the invokeMethod method 
	 *
	 * @param object
	 * @param methodName
	 * @param silent
	 * @return Object
	 */
	public static Object invokeMethod(Object object, String methodName,
			boolean silent) {
		return invokeMethod(object, methodName, null, silent);
	}

	/**
	 * 
	 *  Document the invokeMethod method 
	 *
	 * @param object
	 * @param methodName
	 * @param parameters
	 * @return Object
	 */
	public static Object invokeMethod(Object object, String methodName,
			Object[] parameters) {
		return invokeMethod(object, methodName, parameters, false);
	}

	/**
	 * 
	 *  Document the invokeMethod method 
	 *
	 * @param object
	 * @param methodName
	 * @param parameters
	 * @param silent
	 * @return Object
	 */
	public static Object invokeMethod(Object object, String methodName,
			Object[] parameters, boolean silent) {
		Class[] parameterTypes = null;

		if (parameters != null) {
			parameterTypes = new Class[parameters.length];

			for (int i = 0; i < parameters.length; i++) {
				parameterTypes[i] = parameters[i] != null ? parameters[i]
						.getClass() : null;
			}
		}

		return invokeMethod(object, methodName, parameters, parameterTypes,
				silent);
	}

	/**
	 * 
	 *  Document the invokeMethod method 
	 *
	 * @param object
	 * @param methodName
	 * @param parameters
	 * @param parameterTypes
	 * @return Object
	 */
	public static Object invokeMethod(Object object, String methodName,
			Object[] parameters, Class[] parameterTypes) {
		return invokeMethod(object, methodName, parameters, parameterTypes,
				false);
	}

	/**
	 * 
	 *  Document the invokeMethod method 
	 *
	 * @param object
	 * @param methodName
	 * @param parameters
	 * @param parameterTypes
	 * @param silent
	 * @return Object
	 */
	public static Object invokeMethod(Object object, String methodName,
			Object[] parameters, Class[] parameterTypes, boolean silent) {
		try {
			Method method = object.getClass().getMethod(methodName,
					parameterTypes);

			return method.invoke(object, parameters);
		} catch (Exception e) {
			if (!silent) {
				_logger.warning("Could not invoke " + object + " . "
						+ methodName + " (" + getParameterTypes(parameters)
						+ "):  " + e);
			}

			return null;
		}
	}

	/**
	 * 
	 *  Document the getParameterTypes method 
	 *
	 * @param parameters
	 * @return Object
	 */
	public static String getParameterTypes(Object[] parameters) {
		if (parameters == null || parameters.length == 0) {
			return "null";
		}

		StringBuffer paramTypes = new StringBuffer();

		for (int i = 0, j = parameters.length - 1; i <= j; i++) {
			paramTypes.append(parameters[i].getClass().getName());

			if (i < j) {
				paramTypes.append(", ");
			}
		}

		return paramTypes.toString();
	}

}
