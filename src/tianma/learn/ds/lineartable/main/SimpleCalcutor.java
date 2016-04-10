package tianma.learn.ds.lineartable.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tianma.learn.ds.lineartable.MyArrayList;
import tianma.learn.ds.lineartable.MyArrayStack;
import tianma.learn.ds.lineartable.MyLinkedStack;
import tianma.learn.ds.lineartable.MyList;
import tianma.learn.ds.lineartable.MyStack;

/**
 * 利用中缀表达式->后缀表达式 实现简单四则运算
 * 
 * @author Tianma
 *
 */
public class SimpleCalcutor {

	private class Item {

		private String value;
		private Integer number;

		public Item(String value) {
			this.value = value;
			try {
				number = Integer.parseInt(value);
			} catch (Exception ignore) {
			}
		}

		public boolean isNumber() {
			return number != null;
		}

		public int getNumber() {
			if (isNumber())
				return number;
			throw new NumberFormatException();
		}

		public boolean isAdd() {
			return "+".equals(value);
		}

		public boolean isSub() {
			return "-".equals(value);
		}

		public boolean isMul() {
			return "*".equals(value);
		}

		public boolean isDiv() {
			return "/".equals(value);
		}

		public boolean isLeftBracket() {
			return "(".equals(value);
		}

		public boolean isRightBracket() {
			return ")".equals(value);
		}

		public int getPriority() {
			if (isAdd() || isSub())
				return 0;
			if (isMul() || isDiv())
				return 1;
			throw new RuntimeException("This is not +, -, *, /");
		}

		@Override
		public String toString() {
			return value != null ? value.toString() : null;
		}

	}

	/**
	 * 计算结果
	 * 
	 * @param calStr
	 * @return
	 */
	public int calculate(String calStr) {
		MyList<Item> infixes = parse(calStr);
		MyList<Item> postfixes = infix2postfix(infixes);
		return calculateByPostfix(postfixes);
	}

	/**
	 * 利用正则表达式将待计算的字符串转化为List<Item>形式 ,如 10/2 -> [10, /, 2]
	 * 
	 * @param calStr
	 * @return
	 */
	private MyList<Item> parse(String calStr) {
		Pattern pattern = Pattern.compile("\\D|\\d+");
		Matcher m = pattern.matcher(calStr);
		MyList<Item> items = new MyArrayList<Item>();
		while (m.find()) {
			items.add(new Item(m.group(0)));
		}
		return items;
	}

	/**
	 * 中缀表达式转换为后缀表达式
	 * <p>
	 * 1.当读到一个操作数时，立即将它放到输出中。读到的是操作符则需要接着判断是否该入栈。读到的是左圆括号则入栈。<br>
	 * 2.如果遇到一个右括号，那么就将栈中元素弹出并输出直至遇到左括号为止。但是这个左括号只被弹出，并不输出。<br>
	 * 3.在读到操作符时，如果此操作符优先级小于或等于此时栈顶操作符，则将栈中元素弹出直至(1)遇到左括号或者(2)栈顶元素为更低优先级或者(3)
	 * 栈为空为止。操作符中，'+''-'优先级最低，'('')'优先级最高。 <br>
	 * 4.如果读到输入的末尾，将栈元素弹出直到该栈变成空栈，将符号写到输出中。
	 * 
	 * @return
	 */
	private MyList<Item> infix2postfix(MyList<Item> infixes) {
		MyList<Item> postfixes = new MyArrayList<Item>();
		MyStack<Item> stack = new MyLinkedStack<Item>();
		for (Item item : infixes) {
			if (item.isNumber()) {
				postfixes.add(item);
			} else if (item.isRightBracket()) {
				// ) 右括号,将栈中元素弹出直至左括号,且左括号和右括号不加入到后缀表达式中
				while (true) {
					Item tmp = stack.pop();
					if (tmp.isLeftBracket())
						break;
					postfixes.add(tmp);
				}
			} else if (item.isLeftBracket()) {
				// ( 左括号,将左括号入栈
				stack.push(item);
			} else {
				// 当前操作符为 +, -, *, /,
				if (stack.isEmpty()) {
					// 操作符栈为空,则将当前操作符压入栈
					stack.push(item);
					continue;
				}
				Item top = stack.peek();
				if (top.isLeftBracket()) {
					// 操作符栈顶为左括号(,则将当前操作符压入栈
					stack.push(item);
					continue;
				}
				if (item.getPriority() <= top.getPriority()) {
					// 如果此操作符(+,-,*,/)优先级小于或等于此时栈顶操作符
					// 则将栈中元素弹出直至(1)遇到左括号或者(2)栈顶元素为更低优先级或者(3)栈为空为止
					// 并将弹出的元素加入后缀表达式中,将当前操作符压入栈中
					while (true) {
						Item tmp = stack.peek();
						if (tmp.isLeftBracket() || tmp.getPriority() < item.getPriority()) {
							break;
						}
						postfixes.add(tmp);
						stack.pop();
						if (stack.isEmpty())
							break;
					}
					stack.push(item);
				} else {
					// 如果当前操作符(+,-,*,/)优先级大于此时栈顶操作符,则将当前操作符压入栈
					stack.push(item);
				}
			}
		}
		// 如果栈中元素不为空,则将栈中元素全部弹出,加入后缀表达式中
		while (!stack.isEmpty()) {
			postfixes.add(stack.pop());
		}
		return postfixes;
	}

	/**
	 * 通过后缀表达式计算数值
	 * <p>
	 * 1. 从左到右遍历表达式的每个数字和符号,遇到是数字则进栈,遇到是运算符则将栈顶两个元素出栈,进行运算并将运算结果进栈<br>
	 * 2. 遍历完后缀表达式,此时栈中剩余的数字就是运算结果
	 * 
	 * @param postfixes
	 * @return
	 */
	private int calculateByPostfix(MyList<Item> postfixes) {
		MyStack<Integer> stack = new MyArrayStack<Integer>();
		for (Item item : postfixes) {
			if (item.isNumber()) {
				stack.push(item.getNumber());
			} else {
				// 运算符
				int num1 = stack.pop();
				int num2 = stack.pop();
				int result;
				if (item.isAdd()) {
					result = num2 + num1;
				} else if (item.isSub()) {
					result = num2 - num1;
				} else if (item.isMul()) {
					result = num2 * num1;
				} else if (item.isDiv()) {
					result = num2 / num1;
				} else {
					throw new IllegalArgumentException("Operator invalid : " + item.value);
				}
				stack.push(result);
			}
		}
		return stack.pop();
	}

	public static void main(String[] args) {
		SimpleCalcutor calcutor = new SimpleCalcutor();
		String calStr = "9+(3-1)*3+10/2";
		int result = calcutor.calculate(calStr);
		System.out.println(result);
	}

}
