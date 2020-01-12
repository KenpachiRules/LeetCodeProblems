package com.hari.coding.leet.closest.palindrome;

/***
 * Closest Palindrome , a stack pop-mechanism to find the easiest value and
 * then backtrack to find if there is any better solution.
 * 
 *  Example: 
 *  28 - The simple approach is stack unwinding ( split the input on the half way mark and put it into a stack
 *  and deliberately pop-out elements from the stack by the pushing the same number as top of the stack each time
 *  , once the stack is empty remember the number and find the absolute difference )
 *  
 *  |   |     | 2  |
 *  | 2 | --> | 2  | -(now pop since the top of the stack and the one before is same)-> value is 22
 *  ----      ------
 *  
 *  But 22 is not the closest palindrome since 33 is. So how do you find it?
 *  The difference between 22 (first found closest palindrome via stack approach) is further away from
 *  the input by -6 which means we try to find if there is any palindrome closer than 6 units in the positive 
 *  direction which is within +6 , so try adding +1 until < 6 and see if we can arrive at a palindrome.
 *  And yes we do , which is 33.
 *  
 * @author harim
 *
 */

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.BiPredicate;
import java.util.Optional;
import static java.util.Arrays.asList;

public class Solution {

	private static Predicate<String> evenSize = inp -> inp.length() % 2 == 0;
	private static Predicate<String> oddSize = inp -> inp.length() % 2 == 1;

	private static Function<String, Supplier<BiPredicate<Integer, Integer>>> statementsWithBiPreds = input -> asList(
			StatementWithBiPredicate.<String, Integer, Integer>newInstance(evenSize, () -> (i, k) -> k - i != -1),
			StatementWithBiPredicate.<String, Integer, Integer>newInstance(oddSize, () -> (i, k) -> k - i != 0))
					.stream().filter(pred -> pred.condition.test(input)).findFirst()
					.map(stmtWithPred -> stmtWithPred.biPred).get();

	private static Function<String, Supplier<BiPredicate<String, String>>> stringCheckBiPreds = input -> asList(
			StatementWithBiPredicate.<String, String, String>newInstance(evenSize, () -> (input1, input2) -> {
				// both input1 and input2 are same.
				String first = input1.substring(0, input1.length() / 2);
				String second = new StringBuilder(input1.substring(input1.length() / 2)).reverse().toString();
				return first.equals(second);
			}), StatementWithBiPredicate.<String, String, String>newInstance(oddSize, () -> (input1, input2) -> {
				// both input1 and input2 are same.
				String first = input1.substring(0, input1.length() / 2);
				String second = new StringBuilder(input1.substring(input1.length() / 2 + 1)).reverse().toString();
				return first.equals(second);
			})).stream().filter(pred -> pred.condition.test(input)).findFirst()
					.map(strCheckBiPredS -> strCheckBiPredS.biPred).get();

	private static BiFunction<Long, Long, Optional<String>> additivePalindromeValue = (input, inc) -> LongStream
			.range(0, inc + 1).map(i -> input + i).filter(i -> i != input).filter(palind -> {
				String palinAsStr = "" + palind;
				return stringCheckBiPreds.apply(palinAsStr).get().test(palinAsStr, palinAsStr);
			}).mapToObj(palin -> "" + palin).findFirst();

	private static BiFunction<Long, Long, Optional<String>> negatedPalindromeValue = (input, inc) -> LongStream
			.range(0, inc + 1).map(i -> input - i).filter(i -> i >= 0).filter(i -> i != input).filter(palind -> {
				String palinAsStr = "" + palind;
				return stringCheckBiPreds.apply(palinAsStr).get().test(palinAsStr, palinAsStr);
			}).mapToObj(palin -> "" + palin).findFirst();

	private static Function<String, Function<String, String>> palindromeInputsWithSwapFunction = inp1 -> asList(
			StatementWithFunction.<String, String, String>newInstance(evenSize, () -> input -> {
				char[] out = input.toCharArray();
				int index = out.length / 2;
				Integer n = Integer.parseInt("" + out[index]);
				char swapVal = n == 0 ? '1' : Character.forDigit(n - 1, 10);
				out[index] = swapVal;
				out[index - 1] = swapVal;
				return new String(out);
			}), StatementWithFunction.<String, String, String>newInstance(oddSize, () -> input -> {
				char[] out = input.toCharArray();
				int index = out.length / 2;
				int n = Integer.parseInt("" + out[index]);
				char swapVal = n == 0 ? '1' : Character.forDigit(n - 1, 10);
				out[index] = swapVal;
				return new String(out);
			})).stream().filter(stmtWithF -> stmtWithF.condition.test(inp1)).findFirst()
					.map(stmtWF -> stmtWF.func.get()).get();

	public String nearestPalindromic(String n) {
		String trimmedInput = n.trim();
		long inpValue = Long.parseLong(trimmedInput);
		// Addressing absurd special case which feels invalid as in a single digit can
		// be considered a palindrome.
		if (trimmedInput.length() == 1 || inpValue == 10) {
			return inpValue - 1 < 0 ? "0" : "" + (inpValue - 1);
		} else if (inpValue == 11)
			return "9";
		// apply the stack approach.
		char[] chars = trimmedInput.toCharArray();
		char[] stackChars = new char[trimmedInput.length()];
		int i = 0;
		int k = trimmedInput.length() - 1;
		// if the input is already a palindrome then
		// for even number of elements look for decrementing the middle two elements and
		// if it is odd number of elements then decrement just the middle one element.

		if (stringCheckBiPreds.apply(trimmedInput).get().test(trimmedInput, trimmedInput)) {
			stackChars = palindromeInputsWithSwapFunction.apply(trimmedInput).apply(trimmedInput).toCharArray();
		} else {
			BiPredicate<Integer, Integer> termCondition = statementsWithBiPreds.apply(trimmedInput).get();
			while (termCondition.test(i, k)) {
				stackChars[i] = chars[i];
				stackChars[k] = chars[i];
				i++;
				k--;
			}
			// then fill out the middle element on the event of it being an odd length
			// string.
			if (chars.length % 2 == 1) {
				stackChars[i] = chars[i];
			}
		}
		// the next step is to evaluate if there is any other value which can have
		// absolute difference which is lesser than the value found via
		// previous approach.
		long newValue = Long.parseLong(new String(stackChars));
		long diff = newValue - inpValue;
		Optional<String> output = Optional.empty();
		output = diff < 0 ? additivePalindromeValue.apply(inpValue, Math.abs(diff))
				: negatedPalindromeValue.apply(inpValue, Math.abs(diff));
		long optValue = output.map(out -> Long.parseLong(out)).orElse(newValue);
		return Math.abs(optValue - inpValue) == Math.abs(newValue - inpValue)
				? (newValue - inpValue > 0 ? "" + optValue : "" + newValue)
				: "" + optValue;
	}

	private static class StatementWithBiPredicate<T, U, V> {
		final Predicate<T> condition;
		final Supplier<BiPredicate<U, V>> biPred;

		private StatementWithBiPredicate(Predicate<T> pred, Supplier<BiPredicate<U, V>> suppWithBiPred) {
			this.condition = pred;
			this.biPred = suppWithBiPred;
		}

		static <T, U, V> StatementWithBiPredicate<T, U, V> newInstance(Predicate<T> pred,
				Supplier<BiPredicate<U, V>> suppWithBiPred) {
			return new StatementWithBiPredicate<T, U, V>(pred, suppWithBiPred);
		}
	}

	private static class StatementWithFunction<T, U, V> {
		final Predicate<T> condition;
		final Supplier<Function<U, V>> func;

		private StatementWithFunction(Predicate<T> cond, Supplier<Function<U, V>> func) {
			this.condition = cond;
			this.func = func;
		}

		static <T, U, V> StatementWithFunction<T, U, V> newInstance(Predicate<T> pred, Supplier<Function<U, V>> func) {
			return new StatementWithFunction<T, U, V>(pred, func);
		}
	}

}
