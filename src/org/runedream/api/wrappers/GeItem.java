package org.runedream.api.wrappers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Grand Exchange lookup convenience wrapper.
 * <br>
 * Use {@link GeItem#lookup(int)} or {@link GeItem#lookup(String)} to define a GeItem.
 * 
 * @author Vulcan
 */
public class GeItem {

	public static final String HOST = "http://services.runescape.com";
	public static final String GET_ID = "/m=itemdb_rs/api/catalogue/detail.json?item=";
	public static final String GET_NAME = "/m=itemdb_rs/results.ws?query=";

	private int id;
	private int price;
	private int changeToday;
	private double[] changes;
	private boolean members;
	private String name;
	private String description;
	private String iconUrl;
	private String largeIconUrl;
	private String type;
	private String typeIconUrl;

	private GeItem(int id, String name, int price, boolean members, int changeToday, double[] changes,
			String description, String iconUrl, String largeIconUrl, String type, String typeIconUrl) {
		this.id = id;
		this.price = price;
		this.changeToday = changeToday;
		this.changes = changes;
		this.members = members;
		this.name = name;
		this.description = description;
		this.iconUrl = iconUrl;
		this.largeIconUrl = largeIconUrl;
		this.type = type;
		this.typeIconUrl = typeIconUrl;
	}

	public double getChange30Days() {
		return changes[0];
	}

	public double getChange90Days() {
		return changes[1];
	}

	public double getChange180Days() {
		return changes[2];
	}

	public int getChangeToday() {
		return changeToday;
	}

	public String getDescription() {
		return description;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public int getId() {
		return id;
	}

	public String getLargeIconUrl() {
		return largeIconUrl;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public String getType() {
		return type;
	}

	public String getTypeIconUrl() {
		return typeIconUrl;
	}

	public boolean isMembers() {
		return members;
	}

	public static GeItem lookup(int itemId) {
		try {
			URL url = new URL(HOST + GET_ID + itemId);
			URLConnection con = url.openConnection();
			con.setReadTimeout(10000);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuilder jsonsb = new StringBuilder();
			String line = null;
			while ((line = in.readLine()) != null) {
				jsonsb.append(line);
			}
			String json = jsonsb.toString();
			String name = searchJSON(json, "item", "name");
			int price = parseMultiplier(searchJSON(json, "item", "current", "price"));
			boolean members = Boolean.parseBoolean(searchJSON(json, "item", "members"));
			int changeToday = parseMultiplier(searchJSON(json, "item", "today", "price"));
			double change30 = Double.parseDouble(searchJSON(json, "item", "day30", "change").replace("%", ""));
			double change90 = Double.parseDouble(searchJSON(json, "item", "day90", "change").replace("%", ""));
			double change180 = Double.parseDouble(searchJSON(json, "item", "day180", "change").replace("%", ""));
			double[] changes = { change30, change90, change180 };
			String description = searchJSON(json, "item", "description");
			String iconUrl = searchJSON(json, "item", "icon");
			String largeIconUrl = searchJSON(json, "item", "icon_large");
			String type = searchJSON(json, "item", "type");
			String typeIconUrl = searchJSON(json, "item", "typeIcon");
			return new GeItem(itemId, name, price, members, changeToday, changes,
					description, iconUrl, largeIconUrl, type, typeIconUrl);
		} catch (Exception e) {
		}
		return null;
	}
	
	public static GeItem lookup(String itemName) {
		try {
			itemName = itemName.toLowerCase();
			URL url = new URL(HOST + GET_NAME + itemName.replaceAll(" ", "+"));
			URLConnection con = url.openConnection();
			con.setReadTimeout(10000);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuilder srcsb = new StringBuilder();
			String line = null;
			while ((line = in.readLine()) != null) {
				srcsb.append(line);
			}
			String src = srcsb.substring(srcsb.indexOf("<table class=\"results\">"), srcsb.indexOf("<p id=\"res-tips\">"));
			Pattern p = Pattern.compile(".*?/" + itemName.replaceAll(" ", "_") + "/viewitem\\.ws\\?obj=([\\d]+?)\\\">" + itemName + "</a>.*");
			Matcher m = p.matcher(src.toLowerCase());
			if (m.find()) {
				int id = Integer.parseInt(m.group(1));
				return lookup(id);
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	private static int parseMultiplier(String str) {
		if (str.matches("-?\\d+(\\.\\d+)?[kmb]")) {
			return (int) (Double.parseDouble(str.substring(0, str.length() - 1))
					* (str.endsWith("b") ? 1000000000D : str.endsWith("m") ? 1000000
					: str.endsWith("k") ? 1000 : 1));
		} else {
			return Integer.parseInt(str);
		}
	}

	private static String searchJSON(String json, String...keys) {
		String search = "\"" + keys[0] + "\":";
		int idx = json.indexOf(search) + search.length();
		if (keys.length > 1 && json.charAt(idx) == '{') {
			String[] subKeys = new String[keys.length - 1];
			System.arraycopy(keys, 1, subKeys, 0, subKeys.length);
			return searchJSON(json.substring(idx), subKeys);
		}
		Pattern p = Pattern.compile(".*?[,\\{]\\\"" + keys[0] + "\\\":(-?[\\d]|[\\\"\\d].*?[kmb]?[^\\\\][\\\"\\d])[,\\}].*");
		Matcher m = p.matcher(json);
		if (m.find()) {
			String value = m.group(1);
			if (value.matches("\\\".*?\\\"")) {
				value = value.substring(1, value.length() - 1);
			}
			return value;
		}
		return "";
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getName()).append("[");
		Method[] methods = GeItem.class.getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			Package pack = method.getDeclaringClass().getPackage();
			if (pack == null || pack.equals(GeItem.class.getPackage())) {
				if ((method.getParameterTypes().length | method.getAnnotations().length) != 0) {
					continue;
				}
				String methodName = method.getName();
				if (methodName.equals("getName") || methodName.equals("toString")) {
					continue;
				}
				sb.append(methodName).append("=");
				try {
					sb.append(method.invoke(this, new Object[0]));
				} catch (Exception ignored) {
				}
				sb.append(",");
			}
		}
		String string = sb.toString();
		return string.substring(0, string.lastIndexOf(",")) + "]";
	}

}
