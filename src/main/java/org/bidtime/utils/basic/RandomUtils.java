/**
 * 
 */
package org.bidtime.utils.basic;

import java.util.Random;

/**
 * @author jss
 *
 */
public class RandomUtils {

	// private static String S_DICTION = "abcdefghijklmnopqrstuvwxyz0123456789";
	private static char[] DICTION = "abcdefghijklmnopqrstuvwxyz0123456789"
			.toCharArray();

	// private static String S_DICTION_REMOVE_ZERO =
	// "abcdefghijklmnpqrstuvwxyz123456789";
	private static char[] DICTION_REMOVE_ZERO = "abcdefghijklmnpqrstuvwxyz123456789"
			.toCharArray();

	/**
	 * 产生随机字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String getCode(int length) {
		Random r = new Random();
		StringBuilder sbRand = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int rnd = Math.abs(r.nextInt()) % DICTION.length;
			sbRand.append(DICTION[rnd]);
		}
		return sbRand.toString();
	}

	/**
	 * @param length
	 * @return
	 */
	public static String getCodeNoneZero(int length) {
		Random r = new Random();
		StringBuilder sbRand = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int rnd = Math.abs(r.nextInt()) % DICTION_REMOVE_ZERO.length;
			sbRand.append(DICTION_REMOVE_ZERO[rnd]);
		}
		return sbRand.toString();
	}

	/**
	 * @param length
	 * @return
	 */
	public static String getNumberCode(int length) {
		Random r = new Random();
		StringBuilder sbRand = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sbRand.append(String.valueOf(r.nextInt(10)));
			// 将认证码显示到图象中
			// g.setColor(new
			// Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			// g.drawString(rand,13*i+6,16);
		}
		return sbRand.toString();
	}

	/**
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandom(int min, int max) {
		return new Random().nextInt(max) % (max - min + 1) + min;
	}

}
