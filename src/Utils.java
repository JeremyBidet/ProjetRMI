import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

		public Object findSiblingByHeaderValue(String srcHeader, Object srcValue, String trgHeader) {
			return findValuesByHeader(srcHeader, srcValue).get(trgHeader);
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

	public static final String integerRegex = "-?\\d+";
	public static final String doubleRegex = "-?\\d+\\.\\d+";
	public static final String stringRegex = "\".*\"";
	public static final String booleanRegex = "true|false";
	public static final String mailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static final String sha1Regex = "[0-9a-f]{40}";
	
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
			BufferedReader br = new BufferedReader(new FileReader(new File(Utils.class.getResource(db + ".db").getFile())));
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
	
	public static boolean insertDB(String db, Object[] values) throws RemoteException {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(Utils.class.getResource(db + ".db").getFile())));
			StringBuilder sb = new StringBuilder();
			String line = "";
			while((line = br.readLine()) != null) {
				if(Arrays.asList(line.replace("\"", "").split(",")).contains(values[0])) {
					br.close();
					return false;
				}
				sb.append(line).append('\n');
			}
			sb.append("\"" + values[0] + "\"");
			for(int i=1; i<values.length; i++) {
				sb.append(',').append("\"" + values[i] + "\"");
			}
			sb.append('\n');
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(Utils.class.getResource(db + ".db").getFile())));
			bw.write(sb.toString());
			br.close();
			bw.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public static void writeDB(String db, DB data) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(Utils.class.getResource(db + ".db").getFile())));
			bw.write(data.toString());
			bw.close();
		} catch(IOException e) {
		}
	}
	
	public static String sha1(String password) {
		String sha1 = "";
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(password.getBytes("UTF-8"));
			sha1 = bytesToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sha1;
	}
	
	private static String bytesToHex(byte[] bytes) {
		char[] hexArray = "0123456789abcdef".toCharArray();
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}

}
