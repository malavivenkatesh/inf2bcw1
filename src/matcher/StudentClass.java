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
		
		//Iterate through every character in the pattern, starting from 1 since q=0 is already defined
		for (int q = 1; q < m; q++) {
		    //Decrease k according to the pi array as long as there isn't a match within the pattern
			while (k > 0 && (pattern.charAt(k) != pattern.charAt(q))) {
				k = pi[k-1];
			}
			//Increment k for a match since we want to check whether the next index in the pattern will also
			//be a match
			if (pattern.charAt(k) == pattern.charAt(q)) { k = k+1; }
			//set pi[] for the current q once k has been calculated from the lines above
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
			
		    //Do nothing if the pattern is longer than the text
			if (patternLen > textLen) { return; }
			
			//Index of the pattern
			int q = 0;
			//Iterate through the text with i as the index for the character within the text we are comparing
			for (int i = 0; i < textLen; i++) {
				while (q > 0 && (pattern.charAt(q) != text.charAt(i))) { 
					q = prefixFunction[q-1]; 
				}
				//Match for a single character in the pattern, so increment q 
				if (pattern.charAt(q) == text.charAt(i)){ q++; }
				//If the end of the pattern is reached, then add the relevant index to the Queue and reset q according
				//to the prefix function
				if (q == patternLen) {
					matchIndices.enqueue(i-patternLen+1); //add 1 since i starts at 0 in the code but 
														  //i starts at 1 in the pseudocode
					q = prefixFunction[q-1]; 
				}	
			}
			
			return;
		}
	}
	
//	Main function for testing
	
//	public static void main(String args[]) {
//	    //Matcher.testKMPMatcher(50, 10);
//		//Matcher.getRuntimes(10, 100, "matcherTimes.txt");
//		//Matcher.getRatios(10, 100,40000,"matcherRatios.txt");
//	    Matcher.plotRuntimes(0.012725, 0.008318, "matcherTimes.txt");
//	}
}