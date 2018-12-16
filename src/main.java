import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.PatternSyntaxException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main {

	protected Shell shell;
	private Text regexInput;
	private Text textAreaInput;
	private Label lblYourResult;
	private Text labelSaveRegex;
	private Text result;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			main window = new main();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */

	protected void createContents() {
		final String dir = System.getProperty("user.dir");
		String delimiter = " ";
		
		shell = new Shell();
		shell.setSize(840, 600);
		shell.setText("SWT Application");
		
		Browser browser = new Browser(shell, SWT.NONE);
		browser.setBounds(10, 42, 230, 504);
		String history = dir+"/history.html";
		browser.setUrl(history);
		
		Browser browser_1 = new Browser(shell, SWT.NONE);
		browser_1.setBounds(584, 79, 230, 467);
		String log = dir+"/log.html";
		browser_1.setUrl(log);
		
		regexInput = new Text(shell, SWT.BORDER);
		regexInput.setBounds(257, 10, 308, 70);
		regexInput.setMessage("Put your regex here");
		
		textAreaInput = new Text(shell, SWT.BORDER);
		textAreaInput.setBounds(257, 102, 308, 87);
		textAreaInput.setMessage("Put your text here");
		
		lblYourResult = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblYourResult.setText("Your Result");
		lblYourResult.setBounds(257, 86, 308, 10);
		
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setBounds(257, 338, 308, 208);
		
		Label label = new Label(tabFolder, SWT.NONE);
		label.setText("New Label");
		
		TabItem CharacterTab = new TabItem(tabFolder, SWT.H_SCROLL);
		CharacterTab.setText("Character");
		
		Browser Tab1 = new Browser(tabFolder, SWT.NONE);
		CharacterTab.setControl(Tab1);
		Tab1.setText(""
				+ "<table>"
				+ "<tr><td style='width:100px;' valign='top'><b>[abc]</b></td><td>matches <b>a</b> or <b>b</b>, or <b>c</b></td></tr>"
				+ "<tr><td valign='top'><b>^[abc]</b></td><td>Negation, matches everything except <b>a</b> or <b>b</b>, or <b>c</b></td></tr>"
				+ "<tr><td valign='top'><b>^[a-c]</b></td><td>Range, matches <b>a</b> or <b>b</b>, or <b>c</b></td></tr>"
				+ "<tr><td valign='top'><b>[a-b[c-f]]</b></td><td>Union, matches <b>a</b>, <b>b</b>, <b>c</b>, <b>d</b>, <b>e</b>, <b>f</b></td></tr>"
				+ "<tr><td valign='top'><b>[a-c&&[b-c]]</b></td><td>intersection, matches <b>b</b> or <b>c</b></td></tr>"
				+ "<tr><td valign='top'><b>[a-c&&[^b-c]]</b></td><td>subtraction, matches <b>a</b></td></tr>"
				+ "</table>");
		
		TabItem PredefinedTab = new TabItem(tabFolder, SWT.H_SCROLL);
		PredefinedTab.setText("Predefined");
		
		Browser Tab2 = new Browser(tabFolder, SWT.NONE);
		Tab2.setText("<table>"
				+ "<tr><td style ='width:100px;' valign='top'><b>.</b></td><td>Any character.</td></tr>"
				+ "<tr><td valign='top'><b>\\d</b></td><td>A digit [0-9]</td></tr>"
				+ "<tr><td valign='top'><b>\\D</b></td><td>A non digit A non-digit: [^0-9]</td></tr>"
				+ "<tr><td valign='top'><b>\\s</b></td><td>A whitespace character: [ \\t\\n\\x0B\\f\\r]</td></tr>"
				+ "<tr><td valign='top'><b>\\S</b></td><td>A non-whitespace character: [^\\s]</td></tr>"
				+ "<tr><td valign='top'><b>\\w</b></td><td>A word character: [a-zA-Z_0-9]</td></tr>"
				+ "<tr><td valign='top'><b>\\W</b></td><td>A non-word character: [^\\w]</td></tr>"
				+ "</table>");
		PredefinedTab.setControl(Tab2);
		
		TabItem BoundaryTab = new TabItem(tabFolder, SWT.NONE);
		BoundaryTab.setText("Boundary");
		
		Browser Tab3 = new Browser(tabFolder, SWT.NONE);
		Tab3.setText("<table><tr><td style ='width:100px;' valign='top'><b>^</b></td><td>A beginning of a line.</td></tr>"
				+ "<tr><td valign='top'><b>$</b></td><td>The end of life</td></tr>"
				+ "<tr><td valign='top'><b>\\b</b></td><td>A word boundary</td></tr>"
				+ "<tr><td valign='top'><b>\\B</b></td><td>A non-word boundary</td></tr>"
				+ "<tr><td valign='top'><b>\\A</b></td><td>The beginning of input</td></tr>"
				+ "<tr><td valign='top'><b>\\G</b></td><td>The end of previous match</td></tr>"
				+ "<tr><td valign='top'><b>\\Z</b></td><td>The end of the input but for the final terminator, if any</td></tr>"
				+ "<tr><td valign='top'><b>\\z</b></td><td>The end of the input.</td></tr>"
				+ "</table>");
		BoundaryTab.setControl(Tab3);
		
		Label lblDictionary = new Label(shell, SWT.NONE);
		lblDictionary.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD | SWT.ITALIC));
		lblDictionary.setBounds(257, 306, 270, 48);
		lblDictionary.setText("REGEX DICTIONARY");
		
		labelSaveRegex = new Text(shell, SWT.BORDER);
		labelSaveRegex.setBounds(584, 10, 230, 31);
		labelSaveRegex.setMessage("Put your regex name");
		
		Label labelLog = new Label(shell, SWT.NONE);
		labelLog.setBounds(10, 15, 138, 21);
		labelLog.setText("YOUR REGEX LOG");
		
		Label labelSaved = new Label(shell, SWT.NONE);
		labelSaved.setBounds(584, 48, 99, 21);
		labelSaved.setText("SAVED REGEX");
		
		Button btnSave = new Button(shell, SWT.NONE);
		btnSave.setBounds(729, 45, 85, 26);
		btnSave.setText("SAVE");
		
		result = new Text(shell, SWT.BORDER);
		result.setBounds(257, 206, 308, 93);
		result.setMessage("Your result will be displayed here");
		
		TimerTask autoUpdate = new TimerTask() {
			public void run() {
				Display.getDefault().syncExec(new Runnable() {
					@Override
					public void run() {
						try {
							String[] tempArray = textAreaInput.getText().split(delimiter);
							String trueString = "";
								try {
									for (int i = 0; i < tempArray.length; i++) {
										// TODO Auto-generated method stub
										String regexUser = regexInput.getText();
										if(tempArray[i].matches(regexUser)) {
											trueString = trueString + " " + tempArray[i];
										}else {
										}
									}
									result.setText(trueString);
								}catch(PatternSyntaxException e) {
									result.setText("Error pattern");
								}
						}catch(SWTException e) {
							result.setText("Error");
						}
					}
				});
			}
		};
		Timer mytimer =  new Timer("Timer");
		long delay = 50L;
		long period = 50L;
		mytimer.schedule(autoUpdate, delay, period);

	}
}
