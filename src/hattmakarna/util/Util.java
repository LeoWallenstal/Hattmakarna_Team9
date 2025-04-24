package hattmakarna.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

@Deprecated
public class Util {

	/**
	 * Mapper fälten av det angivna object från hashmapen. Notera att fältens namn
	 * måste matcha exakt resultatens attribute namn. Attribut som ej matchar ett
	 * fält ignoreras.
	 * 
	 * @param ref      Objektet som ska mappas.
	 * @param response Hashmapen med nycklar och värden som matchar objektet.
	 */
	public static void DatabaseObjectMapper(Object ref, HashMap<String, String> response) {

		// Maps columns with the matching field by name
		for (Field f : ref.getClass().getDeclaredFields()) {

			try {
				boolean access = f.canAccess(ref);
				// Set to accessible temporary
				f.setAccessible(true);

				// If the field name doesn't exist in the class, continue;
				if (response.get(f.getName()) == null)
					continue;

				// Set fields value to responses value
				f.set(ref, stringToFieldType(response.get(f.getName()), f));

				// Set back to previous accessibility
				f.setAccessible(access);

			} catch (IllegalArgumentException ex) {
				Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IllegalAccessException ex) {
				Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

	}

	/**
	 * Konverterar en string i mysql format av ett värde till ett fälts datatype.
	 * Vid misslyckande ges felmeddelande och returerar NULL.
	 * 
	 * @param value Mysql string värdet.
	 * @param f     fält objektet vars type ska konveteras till.
	 * @return value konverterat till Field f's datatyp. Vid fel null.
	 */
	private static Object stringToFieldType(String value, Field f) {

		Class<?> type = f.getType();

		if (type == String.class) {
			return value;
		} else if (type == int.class || type == Integer.class) {
			return Integer.parseInt(value);
		} else if (type == long.class || type == Long.class) {
			return Long.parseLong(value);
		} else if (type == double.class || type == Double.class) {
			return Double.parseDouble(value);
		} else if (type == float.class || type == Float.class) {
			return Float.parseFloat(value);
		} else if (type == boolean.class || type == Boolean.class) {
			return Boolean.parseBoolean(value);
		} else if (type == short.class || type == Short.class) {
			return Short.parseShort(value);
		} else if (type == byte.class || type == Byte.class) {
			return Byte.parseByte(value);
		} else if (type == char.class || type == Character.class) {
			return value.charAt(0); // Assumes it's a single character string
		} else if (type == ArrayList.class) {
			// Get the generic type of the list
			Type genericType = f.getGenericType();

			if (genericType instanceof ParameterizedType) {
				ParameterizedType pt = (ParameterizedType) genericType;
				Type elementType = pt.getActualTypeArguments()[0];

				if (elementType instanceof Class) {
					Class<?> elementClass = (Class<?>) elementType;
					return parseStringToList(value, elementClass);
				}
			}

		}

		throw new IllegalArgumentException("Datatypen av fältet stöds ej än. Datatyp: " + type.getName()
				+ ". Du kan ingorera detta fel om fältet inte behövs mappas :)");
	}

	/**
	 * Konverterar en String som är en Mysql array till en ArrayList vars element
	 * typ blir elemenType.
	 * 
	 * @param value       stringen som ska konverteras. Måste vara i mysl format.
	 * @param elementType typen vars elementen är av
	 * @return
	 */
	private static ArrayList<Object> parseStringToList(String value, Class<?> elementType) {
		String[] parts = value.split(",");
		// Mysql array har '[' i början och ']' i slut, dem ska bort!
		if (parts.length == 0)
			return new ArrayList<Object>();
		parts[0].substring(1);
		parts[parts.length - 1].substring(0, parts[parts.length - 1].length() - 1);

		ArrayList<Object> result = new ArrayList<>();

		for (String part : parts) {
			part = part.trim();
			if (elementType == String.class)
				result.add(part);
			else if (elementType == Integer.class || elementType == int.class)
				result.add(Integer.parseInt(part));
			else if (elementType == Double.class || elementType == double.class)
				result.add(Double.parseDouble(part));
			else if (elementType == Boolean.class || elementType == boolean.class)
				result.add(Boolean.parseBoolean(part));
			else if (elementType == Long.class || elementType == long.class)
				result.add(Long.parseLong(part));
			else if (elementType == Float.class || elementType == float.class)
				result.add(Float.parseFloat(part));
			else if (elementType == Character.class || elementType == char.class)
				result.add(part.charAt(0));
			else
				throw new IllegalArgumentException("Unsupported list element type: " + elementType.getName());
		}

		return result;
	}
        
        //Jämför två listor och ser om de är likadana, sett till innehållet. 
        //Tar inte ordning i beaktning!
        public static <T> boolean contentEquals(List<T> list1, List<T> list2) {
            //Är de inte lika stora kan de inte vara likadana.
            if (list1.size() != list2.size()) {
                return false;
            }

            Map<T, Integer> freq1 = new HashMap<>();
            Map<T, Integer> freq2 = new HashMap<>();

            for (T item : list1) {
                freq1.put(item, freq1.getOrDefault(item, 0) + 1);
            }
            for (T item : list2) {
                freq2.put(item, freq2.getOrDefault(item, 0) + 1);
            }
            //Om elementen har samma frekvens, har listorna samma innehåll.
            return freq1.equals(freq2);
        }

        
        /**
     * Sort *any* list of T by extracting a Comparable key of type U.
     *
     * @param source       the input list (never modified)
     * @param keyExtractor function T → U, where U is Comparable
     * @param ascending    true for ascending, false for descending
     * @param <T>          the element type
     * @param <U>          the key type (must implement Comparable)
     * @return a new ArrayList<T> sorted as requested
     */
    public static <T, U extends Comparable<? super U>> ArrayList<T> sortBy(List<T> source,
        Function<? super T, ? extends U> keyExtractor,boolean ascending) 
        {
            ArrayList<T> copy = new ArrayList<>(source);
            Comparator<T> cmp = Comparator.comparing(keyExtractor);
            
            if (!ascending) cmp = cmp.reversed();
            
            copy.sort(cmp);
            return copy;
        }

        

}
