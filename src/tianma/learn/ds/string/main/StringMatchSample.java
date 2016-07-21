package tianma.learn.ds.string.main;

/**
 * 字符串匹配Sample
 * <p>
 * 匹配算法:<br>
 * 1. 暴力匹配<br>
 * 2. <a href="http://blog.csdn.net/v_july_v/article/details/7041827">KMP匹配 </a>
 * </br>
 * 3. 改进KMP匹配<br>
 * 
 * @author Tianma
 *
 */
public class StringMatchSample {

	private interface StringMatcher {
		/**
		 * 从原字符串中查找模式字符串的位置,如果模式字符串存在,则返回模式字符串第一次出现的位置,否则返回-1
		 * 
		 * @param source
		 *            原字符串
		 * @param pattern
		 *            模式字符串
		 * @return if substring exists, return the first occurrence of pattern
		 *         substring, return -1 if not.
		 */
		int indexOf(String source, String pattern);
	}

	/**
	 * 暴力匹配
	 * <p>
	 * 时间复杂度: O(m*n), m = pattern.length, n = source.length
	 */
	static class ViolentStringMatcher implements StringMatcher {

		@Override
		public int indexOf(String source, String pattern) {
			int i = 0, j = 0;
			int sLen = source.length(), pLen = pattern.length();
			char[] src = source.toCharArray();
			char[] ptn = pattern.toCharArray();
			while (i < sLen && j < pLen) {
				if (src[i] == ptn[j]) {
					// 如果当前字符匹配成功,则将两者各自增1,继续比较后面的字符
					i++;
					j++;
				} else {
					// 如果当前字符匹配不成功,则i回溯到此次匹配最开始的位置+1处,也就是i = i - j + 1
					// (因为i,j是同步增长的), j = 0;
					i = i - j + 1;
					j = 0;
				}
			}
			// 匹配成功,则返回模式字符串在原字符串中首次出现的位置;否则返回-1
			if (j == pLen)
				return i - j;
			else
				return -1;
		}
	}

	/**
	 * KMP模式匹配
	 * 
	 * @author Tianma
	 *
	 */
	static class KMPStringMatcher implements StringMatcher {

		/**
		 * 获取KMP算法中pattern字符串对应的next数组
		 * 
		 * @param p
		 *            模式字符串对应的字符数组
		 * @return
		 */
		protected int[] getNext(char[] p) {
			// 已知next[j] = k,利用递归的思想求出next[j+1]的值
			// 如果已知next[j] = k,如何求出next[j+1]呢?具体算法如下:
			// 1. 如果p[j] = p[k], 则next[j+1] = next[k] + 1;
			// 2. 如果p[j] != p[k], 则令k=next[k],如果此时p[j]==p[k],则next[j+1]=k+1,
			// 如果不相等,则继续递归前缀索引,令 k=next[k],继续判断,直至k=-1(即k=next[0])或者p[j]=p[k]为止
			int pLen = p.length;
			int[] next = new int[pLen];
			int k = -1;
			int j = 0;
			next[0] = -1; // next数组中next[0]为-1
			while (j < pLen - 1) {
				if (k == -1 || p[j] == p[k]) {
					k++;
					j++;
					next[j] = k;
				} else {
					k = next[k];
				}
			}
			return next;
		}

		@Override
		public int indexOf(String source, String pattern) {
			int i = 0, j = 0;
			char[] src = source.toCharArray();
			char[] ptn = pattern.toCharArray();
			int sLen = src.length;
			int pLen = ptn.length;
			int[] next = getNext(ptn);
			while (i < sLen && j < pLen) {
				// 如果j = -1,或者当前字符匹配成功(src[i] = ptn[j]),都让i++,j++
				if (j == -1 || src[i] == ptn[j]) {
					i++;
					j++;
				} else {
					// 如果j!=-1且当前字符匹配失败,则令i不变,j=next[j],即让pattern模式串右移j-next[j]个单位
					j = next[j];
				}
			}
			if (j == pLen)
				return i - j;
			return -1;
		}
	}

	/**
	 * 优化的KMP算法(对next数组的获取进行优化)
	 * 
	 * @author Tianma
	 *
	 */
	static class OptimizedKMPStringMatcher extends KMPStringMatcher {

		@Override
		protected int[] getNext(char[] p) {
			// 已知next[j] = k,利用递归的思想求出next[j+1]的值
			// 如果已知next[j] = k,如何求出next[j+1]呢?具体算法如下:
			// 1. 如果p[j] = p[k], 则next[j+1] = next[k] + 1;
			// 2. 如果p[j] != p[k], 则令k=next[k],如果此时p[j]==p[k],则next[j+1]=k+1,
			// 如果不相等,则继续递归前缀索引,令 k=next[k],继续判断,直至k=-1(即k=next[0])或者p[j]=p[k]为止
			int pLen = p.length;
			int[] next = new int[pLen];
			int k = -1;
			int j = 0;
			next[0] = -1; // next数组中next[0]为-1
			while (j < pLen - 1) {
				if (k == -1 || p[j] == p[k]) {
					k++;
					j++;
					// 修改next数组求法
					if (p[j] != p[k]) {
						next[j] = k;// KMPStringMatcher中只有这一行
					} else {
						// 不能出现p[j] = p[next[j]],所以如果出现这种情况则继续递归,如 k = next[k],
						// k = next[[next[k]]
						next[j] = next[k];
					}
				} else {
					k = next[k];
				}
			}
			return next;
		}

	}

	public static void main(String[] args) {
		StringMatcher matcher = new ViolentStringMatcher();
		System.out.println(matcher.indexOf("helloworld", "ow"));
		matcher = new KMPStringMatcher();
		System.out.println(matcher.indexOf("helloworld", "ow"));
		matcher = new OptimizedKMPStringMatcher();
		System.out.println(matcher.indexOf("helloworld", "ow"));
	}

}
