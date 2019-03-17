package vip.wulang.spring.util;

import java.util.*;

/**
 * String utils.
 *
 * @author CoolerWu on 2019/1/5.
 * @version 1.0
 */
public class StringUtils {
    private static final String EMPTY_STRING = "";
    private static final String SPACE_STRING = " ";

    private StringUtils() {
    }

    public static String[] splitString(String var1, String var2) {
        if (Objects.isNull(var1) || Objects.isNull(var2)) {
            return null;
        }

        return var1.split(var2);
    }

    public static String[] splitStringOnlyFirstTime(String var1, String var2) {
        if (Objects.isNull(var1) || Objects.isNull(var2)) {
            return null;
        }

        String[] split = var1.split(var2);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < split.length; i++) {
            if (i != split.length - 1) {
                sb.append(split[i]).append(var2);
            } else {
                sb.append(split[i]);
            }
        }

        String[] new_str = new String[2];
        new_str[0] = split[0];
        new_str[1] = sb.toString();

        return new_str;
    }

    public static String emptySpace(String var1) {
        if (Objects.isNull(var1)) {
            return null;
        }

        return var1.replaceAll(SPACE_STRING, EMPTY_STRING);
    }

    public static String toLowerCase(String var1) {
        if (Objects.isNull(var1)) {
            return null;
        }

        return var1.toLowerCase();
    }

    public static String toUpperCase(String var1) {
        if (Objects.isNull(var1)) {
            return null;
        }

        return var1.toUpperCase();
    }

    public static String replace(String inString, String oldPattern, String newPattern) {
        if (hasLength(inString) && hasLength(oldPattern) && newPattern != null) {
            int index = inString.indexOf(oldPattern);
            if (index == -1) {
                return inString;
            } else {
                int capacity = inString.length();
                if (newPattern.length() > oldPattern.length()) {
                    capacity += 16;
                }

                StringBuilder sb = new StringBuilder(capacity);
                int pos = 0;

                for(int patLen = oldPattern.length(); index >= 0; index = inString.indexOf(oldPattern, pos)) {
                    String str = inString.substring(pos, index);
                    sb.append(str);
                    sb.append(newPattern);
                    pos = index + patLen;
                }

                sb.append(inString.substring(pos));
                return sb.toString();
            }
        } else {
            return inString;
        }
    }

    public static String cleanPath(String path) {
        if (!hasLength(path)) {
            return path;
        } else {
            String pathToUse = replace(path, "\\", "/");
            int prefixIndex = pathToUse.indexOf(58);
            String prefix = "";
            if (prefixIndex != -1) {
                prefix = pathToUse.substring(0, prefixIndex + 1);
                if (prefix.contains("/")) {
                    prefix = "";
                } else {
                    pathToUse = pathToUse.substring(prefixIndex + 1);
                }
            }

            if (pathToUse.startsWith("/")) {
                prefix = prefix + "/";
                pathToUse = pathToUse.substring(1);
            }

            String[] pathArray = delimitedListToStringArray(pathToUse, "/");
            List<String> pathElements = new LinkedList<>();
            int tops = 0;

            int i;
            for(i = pathArray.length - 1; i >= 0; --i) {
                String element = pathArray[i];
                if (!".".equals(element)) {
                    if ("..".equals(element)) {
                        ++tops;
                    } else if (tops > 0) {
                        --tops;
                    } else {
                        pathElements.add(0, element);
                    }
                }
            }

            for(i = 0; i < tops; ++i) {
                pathElements.add(0, "..");
            }

            return prefix + collectionToDelimitedString(pathElements, "/");
        }
    }

    public static String collectionToDelimitedString(Collection<?> coll, String delim, String prefix, String suffix) {
        if (coll == null || coll.isEmpty()) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            Iterator it = coll.iterator();

            while(it.hasNext()) {
                sb.append(prefix).append(it.next()).append(suffix);
                if (it.hasNext()) {
                    sb.append(delim);
                }
            }

            return sb.toString();
        }
    }

    public static String collectionToDelimitedString(Collection<?> coll, String delim) {
        return collectionToDelimitedString(coll, delim, "", "");
    }

    public static String collectionToCommaDelimitedString(Collection<?> coll) {
        return collectionToDelimitedString(coll, ",");
    }

    public static boolean hasLength(String str) {
        return str != null && !str.isEmpty();
    }

    public static String[] delimitedListToStringArray(String str, String delimiter) {
        return delimitedListToStringArray(str, delimiter, (String)null);
    }

    public static String[] delimitedListToStringArray(String str, String delimiter, String charsToDelete) {
        if (str == null) {
            return new String[0];
        } else if (delimiter == null) {
            return new String[]{str};
        } else {
            List<String> result = new ArrayList<>();
            int pos;
            if ("".equals(delimiter)) {
                for(pos = 0; pos < str.length(); ++pos) {
                    result.add(deleteAny(str.substring(pos, pos + 1), charsToDelete));
                }
            } else {
                int delPos;
                for(pos = 0; (delPos = str.indexOf(delimiter, pos)) != -1; pos = delPos + delimiter.length()) {
                    result.add(deleteAny(str.substring(pos, delPos), charsToDelete));
                }

                if (str.length() > 0 && pos <= str.length()) {
                    result.add(deleteAny(str.substring(pos), charsToDelete));
                }
            }

            return toStringArray(result);
        }
    }

    public static String deleteAny(String inString, String charsToDelete) {
        if (hasLength(inString) && hasLength(charsToDelete)) {
            StringBuilder sb = new StringBuilder(inString.length());

            for(int i = 0; i < inString.length(); ++i) {
                char c = inString.charAt(i);
                if (charsToDelete.indexOf(c) == -1) {
                    sb.append(c);
                }
            }

            return sb.toString();
        } else {
            return inString;
        }
    }

    public static String[] toStringArray(Collection<String> collection) {
        return collection.toArray(new String[0]);
    }

    public static String[] toStringArray(Enumeration<String> enumeration) {
        return toStringArray(Collections.list(enumeration));
    }

    public static boolean isEmpty(String var) {
        if (Objects.isNull(var)) {
            return false;
        }

        return "".equals(var);
    }

    public static boolean notEmpty(String var) {
        return !isEmpty(var);
    }
}
