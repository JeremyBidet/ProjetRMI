import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	public static class DB {

		private List<HashMap<String, Object>> values;

		public DB(List<HashMap<String, Object>> values) {
			this.values = values;
		}

		public String[] getHeaders() {
			return (String[]) values.get(0).keySet().toArray();
		}

		public HashMap<String, Object> getLine(int index) {
			return values.get(index);
		}

		public HashMap<String, Object> findValuesByHeader(String header, Object value) {
			return values.stream().filter(hm -> hm.get(header).equals(value)).findAny().get();
		}

		public Object findSiblingByHeaders(String srcHeader, Object value, String trgHeader) {
			return findValuesByHeader(srcHeader, value).get(trgHeader);
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			Set<String> headers = values.get(0).keySet();
			
			for(String s : headers) {
				sb.append(s).append(',');
			}
			sb.append('\n');
			
			for(HashMap<String, Object> hm : values) {
				for(Object o : hm.values()) {
					sb.append(o).append(',');
				}
				sb.append('\n');
			}
			
			return sb.toString();
		}

	}

	private static final String integerRegex = "-?\\d+";
	private static final String doubleRegex = "-?\\d+\\.\\d+";
	private static final String stringRegex = "\".*\"";
	private static final String booleanRegex = "true|false";
	//private static final String mailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	//private static final String sha1Regex = "[0-9a-f]{40}";
	
	private static final String dbFieldRegex = "(" + "(" + integerRegex + ")" + "|" + "(" + doubleRegex + ")" + "|" + "(" + booleanRegex + ")" + "|" + "(" + stringRegex + ")" + ")";
	private static final String dbRegex = dbFieldRegex + "(" + "," + dbFieldRegex + ")*";

	private static Class<?> checkType(String field) {
		if(Pattern.matches(integerRegex, field)) return Integer.class;
		if(Pattern.matches(doubleRegex, field)) return Double.class;
		if(Pattern.matches(booleanRegex, field)) return Boolean.class;
		return String.class;
	}
	
	public static DB parseDB(String db) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(db + ".db")));
			Pattern p1 = Pattern.compile(dbRegex);
			String[] headers = null;
			List<String[]> lines = new ArrayList<String[]>();
			// read first line (headers)
			String line = br.readLine();
			Matcher m1 = p1.matcher(line);
			if (m1.matches()) {
				headers = line.replace("\"", "").split(",");
			}
			// read next lines (data)
			while ((line = br.readLine()) != null) {
				m1 = p1.matcher(line);
				if (m1.matches()) {
					lines.add(line.replace("\"", "").split(","));
				}
			}
			br.close();

			List<HashMap<String, Object>> values = new ArrayList<HashMap<String, Object>>(lines.size() - 1);
			for(String[] ss : lines) {
				HashMap<String, Object> hm = new HashMap<String, Object>();
				for(int i=0; i<ss.length; i++) {
					Class<?> _class = checkType(ss[i]);
					hm.put(headers[i], _class.getConstructor(String.class).newInstance(ss[i]));
				}
				values.add(hm);
			}
			return new DB(values);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void insertDB(String db, Object[] values) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(db + ".db")));
			StringBuilder sb = new StringBuilder();
			String line = "";
			while((line = br.readLine()) != null) {
				if(Arrays.asList(line.replace("\"", "").split(",")).contains(values[0])) {
					br.close();
					throw new Exception("This login already exists!");
				}
				sb.append(line).append('\n');
			}
			sb.append("\"" + values[0] + "\"");
			for(int i=1; i<values.length; i++) {
				sb.append(',').append("\"" + values[i] + "\"");
			}
			sb.append('\n');
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(db + ".db")));
			bw.write(sb.toString());
			br.close();
			bw.close();
		} catch (Exception e) {
		}
	}

}
