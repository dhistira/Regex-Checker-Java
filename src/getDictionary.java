public class getDictionary {
	
	//DAPAT TABEL DICTIONARY
		public static String getDictionary(int opt) {
			String dictionaries = null;
			if(opt == 1) {
				dictionaries = "<table>"
						+ "<tr><td style='width:100px;' valign='top'><b>[abc]</b></td><td>matches <b>a</b> or <b>b</b>, or <b>c</b></td></tr>"
						+ "<tr><td valign='top'><b>^[abc]</b></td><td>Negation, matches everything except <b>a</b> or <b>b</b>, or <b>c</b></td></tr>"
						+ "<tr><td valign='top'><b>^[a-c]</b></td><td>Range, matches <b>a</b> or <b>b</b>, or <b>c</b></td></tr>"
						+ "<tr><td valign='top'><b>[a-b[c-f]]</b></td><td>Union, matches <b>a</b>, <b>b</b>, <b>c</b>, <b>d</b>, <b>e</b>, <b>f</b></td></tr>"
						+ "<tr><td valign='top'><b>[a-c&&[b-c]]</b></td><td>intersection, matches <b>b</b> or <b>c</b></td></tr>"
						+ "<tr><td valign='top'><b>[a-c&&[^b-c]]</b></td><td>subtraction, matches <b>a</b></td></tr>"
						+ "</table>";
			}else if(opt == 2) {
				dictionaries = "<table>"
						+ "<tr><td style ='width:100px;' valign='top'><b>.</b></td><td>Any character.</td></tr>"
						+ "<tr><td valign='top'><b>\\d</b></td><td>A digit [0-9]</td></tr>"
						+ "<tr><td valign='top'><b>\\D</b></td><td>A non digit A non-digit: [^0-9]</td></tr>"
						+ "<tr><td valign='top'><b>\\s</b></td><td>A whitespace character: [ \\t\\n\\x0B\\f\\r]</td></tr>"
						+ "<tr><td valign='top'><b>\\S</b></td><td>A non-whitespace character: [^\\s]</td></tr>"
						+ "<tr><td valign='top'><b>\\w</b></td><td>A word character: [a-zA-Z_0-9]</td></tr>"
						+ "<tr><td valign='top'><b>\\W</b></td><td>A non-word character: [^\\w]</td></tr>"
						+ "</table>";
			}else if(opt == 3) {
				dictionaries = "<table><tr><td style ='width:100px;' valign='top'><b>^</b></td><td>A beginning of a line.</td></tr>"
						+ "<tr><td valign='top'><b>$</b></td><td>The end of life</td></tr>"
						+ "<tr><td valign='top'><b>\\b</b></td><td>A word boundary</td></tr>"
						+ "<tr><td valign='top'><b>\\B</b></td><td>A non-word boundary</td></tr>"
						+ "<tr><td valign='top'><b>\\A</b></td><td>The beginning of input</td></tr>"
						+ "<tr><td valign='top'><b>\\G</b></td><td>The end of previous match</td></tr>"
						+ "<tr><td valign='top'><b>\\Z</b></td><td>The end of the input but for the final terminator, if any</td></tr>"
						+ "<tr><td valign='top'><b>\\z</b></td><td>The end of the input.</td></tr>"
						+ "</table>";
			}
			
			return dictionaries;
		}

}
