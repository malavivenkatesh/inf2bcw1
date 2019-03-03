package matcher;

public class StudentClass {

	public KMPMatcher kmpMatcher;

	public StudentClass(String text, String pattern) {
		kmpMatcher = new KMPMatcher(text, pattern);
	}

	public void buildPrefixFunction() {
		kmpMatcher.setPrefixFunction(computePrefixFunction(kmpMatcher.getPattern()));
	}
	
	public static int[] computePrefixFunction(String pattern) {
		int m = pattern.length();
		int[] pi = new int[m];
		pi[0] = 0; //using 0 indexing, so pi[1] is equivalent to pi[0] in the code
		int k = 0;
		
		for (int q = 1; q < m; q++) {
			while (k > 0 && (pattern.charAt(k) != pattern.charAt(q))) {
				k = pi[k-1];
			}
			if (pattern.charAt(k) == pattern.charAt(q)) { k = k+1; }
			pi[q] = k;
		}
		
		return pi;
	}

	public static class KMPMatcher {

		private String text;
		private String pattern;
		private int textLen;
		private int patternLen;
		private int[] prefixFunction;
		private Queue matchIndices;

		public KMPMatcher(String text, String pattern) {
			this.text = text;
			this.pattern = pattern;
			this.textLen = text.length();
			this.patternLen = pattern.length();
			this.prefixFunction = new int[patternLen + 1];
			this.matchIndices = new Queue();
		}

		public void setPrefixFunction(int[] prefixFunction) {
			this.prefixFunction = prefixFunction;
		}

		public int[] getPrefixFunction() {
			return prefixFunction;
		}

		public String getPattern() {
			return pattern;
		}

		public Queue getMatchIndices() {
			return matchIndices;
		}

		public void search() {
			
			if (patternLen > textLen) { return; }
			
//			this.prefixFunction = computePrefixFunction(pattern);
			int q = 0;
			
			for (int i = 0; i < textLen; i++) {
				while (q > 0 && (pattern.charAt(q) != text.charAt(i))) { 
					q = prefixFunction[q-1]; 
				}
				if (pattern.charAt(q) == text.charAt(i)){ q++; }
				if (q == patternLen) {
					matchIndices.enqueue(i-patternLen+1); //add 1 since i starts at 0 in the code but 
														  //i starts at 1 in the pseudocode
					q = prefixFunction[q-1]; 
				}	
			}
			
			return;
		}
	}
	
	public static void main(String args[]) {
		Matcher.getRuntimes(10, 100, "matcherTimes.txt");
		//crossover point is t = 12 line 44 in matcherTimes file
		//Matcher.getRatios(10, 100, ,"fRatios");
	}
}