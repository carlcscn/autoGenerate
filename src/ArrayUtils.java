/**
 * 数组工具
 * 
 * @author yangle
 */
public class ArrayUtils {

	/**
	 * 合并数组
	 * 
	 * @param firstArray
	 *            第一个数组
	 * @param secondArray
	 *            第二个数组
	 * @return 合并后的数组
	 */
	public static byte[] concat(byte[] firstArray, byte[] secondArray) {
		if (firstArray == null || secondArray == null) {
			return null;
		}
		byte[] bytes = new byte[firstArray.length + secondArray.length];
		System.arraycopy(firstArray, 0, bytes, 0, firstArray.length);
		System.arraycopy(secondArray, 0, bytes, firstArray.length, secondArray.length);
		return bytes;
	}



	public static String hexToAscii(String hexStr) {
		StringBuilder output = new StringBuilder("");
		for (int i = 0; i < hexStr.length(); i += 2) {
			String str = hexStr.substring(i, i + 2);
			output.append((char) Integer.parseInt(str, 16));
		}
		return output.toString();
	}



	public static String zhuanbiaozhun(String num){
		StringBuffer a = new StringBuffer();
		a.append(num.substring(0,1));
		a.append(".");
		a.append(num.substring(1,3));
		a.append("E-");
		int b = Integer.valueOf(num.substring(4,6));
		a.append(String.valueOf(b-2));


		return a.toString();
	}


}
