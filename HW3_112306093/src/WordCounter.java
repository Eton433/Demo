import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.Identity;

import javax.swing.text.AbstractDocument.BranchElement;

public class WordCounter {
	private String urlStr;
	private String content;

	public WordCounter(String urlStr) {
		this.urlStr = urlStr;
	}

	private String fetchContent() throws IOException {
		URL url = new URL(this.urlStr);
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();
		BufferedReader bf = new BufferedReader(new InputStreamReader(in));
		String retVal = "";
		String line = null;
		while ((line = bf.readLine()) != null) {
			retVal += line + "\n";
		}
		return retVal;
	}

	public int BoyerMoore(String T, String P) {
		int n = T.length();
		int m = P.length();
		int i = m - 1;
		int j = m - 1;
		while (i < n) {
			if (T.charAt(i) == P.charAt(j)) {
				if (j == 0)
					return i;
				else {
					i--;
					j--;
				}

			} else {
				int l = last(T.charAt(i), T);
				i = i + m - min(1 + l, j);
				j = m - 1;
			}
		}
		return -1;
	}

	public int last(char c, String P) {
		// look for c from behind
		int lastIndex = -1;
		for (int i = P.length() - 1; i >= 0; i--) {
			if (P.charAt(i) == c) {
				lastIndex = i;
				break;
			}
		}
		return lastIndex;
	}

	public int min(int a, int b){
        if (a < b)
            return a;
        else if (b < a)
            return b;
        else 
            return a;
    }

	public int countKeyword(String keyword) throws IOException {
		if (content == null) {
			content = fetchContent();
		}
		content = content.toUpperCase();
		keyword = keyword.toUpperCase();
		int i = 0;
		int retVal = 0;
		while (i < content.length()) {
			int position = BoyerMoore(content.substring(i), keyword);
			if (position != -1) {
				retVal++;
				i += position + keyword.length();
			} else {
				break;
			}

		}

		return retVal;
	}

}
