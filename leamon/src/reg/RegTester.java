package reg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegTester {

	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("(/w-/w-/w)");
		Matcher matcher = pattern.matcher("s-s-s");
		String str = matcher.group();
		System.out.println(str);
	}
}
