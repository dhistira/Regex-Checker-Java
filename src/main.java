import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main {

	protected Shell shlRegexChecker;
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
		shlRegexChecker.open();
		shlRegexChecker.layout();
		while (!shlRegexChecker.isDisposed()) {
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
		
		shlRegexChecker = new Shell();
		shlRegexChecker.setSize(766, 600);
		shlRegexChecker.setText("Regex Checker");
		
		Browser displayHistory = new Browser(shlRegexChecker, SWT.NONE);
		displayHistory.setBounds(462, 79, 278, 467);
		String history = dir+"/saved.txt";
		displayHistory.setUrl(history);
		
		regexInput = new Text(shlRegexChecker, SWT.BORDER);
		regexInput.setBounds(10, 10, 438, 70);
		regexInput.setMessage("Put your regex here");
		
		textAreaInput = new Text(shlRegexChecker, SWT.BORDER);
		textAreaInput.setBounds(10, 102, 438, 87);
		textAreaInput.setMessage("Put your text here");
		
		lblYourResult = new Label(shlRegexChecker, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblYourResult.setText("Your Result");
		lblYourResult.setBounds(10, 86, 438, 10);
		
		//DICTIONARY START
		TabFolder dictionary = new TabFolder(shlRegexChecker, SWT.NONE);
		dictionary.setBounds(10, 338, 438, 208);
		
		Label label = new Label(dictionary, SWT.NONE);
		label.setText("New Label");
		
		TabItem CharacterTab = new TabItem(dictionary, SWT.H_SCROLL);
		CharacterTab.setText("Character");
		
		Browser Tab1 = new Browser(dictionary, SWT.NONE);
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
		
		TabItem PredefinedTab = new TabItem(dictionary, SWT.H_SCROLL);
		PredefinedTab.setText("Predefined");
		
		Browser Tab2 = new Browser(dictionary, SWT.NONE);
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
		
		TabItem BoundaryTab = new TabItem(dictionary, SWT.NONE);
		BoundaryTab.setText("Boundary");
		
		Browser Tab3 = new Browser(dictionary, SWT.NONE);
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
		
		Label lblDictionary = new Label(shlRegexChecker, SWT.NONE);
		lblDictionary.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD | SWT.ITALIC));
		lblDictionary.setBounds(10, 306, 270, 48);
		lblDictionary.setText("REGEX DICTIONARY");
		
		labelSaveRegex = new Text(shlRegexChecker, SWT.BORDER);
		labelSaveRegex.setBounds(462, 10, 278, 31);
		labelSaveRegex.setMessage("Put your regex name");
		
		Label labelSaved = new Label(shlRegexChecker, SWT.NONE);
		labelSaved.setBounds(462, 52, 99, 21);
		labelSaved.setText("SAVED REGEX");
		
		Button btnSave = new Button(shlRegexChecker, SWT.NONE);
		btnSave.setBounds(655, 47, 85, 26);
		btnSave.setText("SAVE");
		btnSave.addListener(SWT.Selection, new Listener()
		{
		    @Override
		    public void handleEvent(Event event)
		    {
		    	if(labelSaveRegex.getText() != null && !labelSaveRegex.getText().isEmpty() && regexInput.getText() != null && !regexInput.getText().isEmpty()) {
		    		String tempSave = "["+Calendar.DATE+"/"+Calendar.MONTH+"/"+Calendar.YEAR+"]"
		    	+"\n Nama Regex: "+labelSaveRegex.getText()+"\n Regex: "+regexInput.getText()+"\n"
		    		+"====================";
		    		try {
						saveRegex(tempSave);
					} catch (IOException e) {
						MessageBox dialog = new MessageBox(shlRegexChecker, SWT.ICON_ERROR | SWT.OK);
			    		dialog.setMessage("Regex gagal disimpan!");
			    		dialog.open();
			    		displayHistory.setUrl(history);
					}
		    		MessageBox dialog = new MessageBox(shlRegexChecker, SWT.ICON_INFORMATION | SWT.OK);
		    		dialog.setMessage("Regex berhasil disimpan");
		    		dialog.open();
		    		displayHistory.setUrl(history);
		    	} else {
		    		MessageBox dialog = new MessageBox(shlRegexChecker, SWT.ICON_ERROR | SWT.OK);
		    		dialog.setMessage("Nama regex dan regex tidak boleh kosong");
		    		dialog.open();
		    		displayHistory.setUrl(history);
		    	}
		    }
		});
		
		result = new Text(shlRegexChecker, SWT.BORDER);
		result.setBounds(10, 207, 438, 93);
		result.setMessage("Your result will be displayed here");
		
		//REGEX CHECKER START
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
	
	public static void saveRegex(String tempSave) throws IOException {
		
		/*FileInputStream in = null;
		FileOutputStream out = null;
		
		 StringBuilder sb = new StringBuilder(512);
		    try {
		    	out = new FileOutputStream("saved.txt");
				FileInputStream r = new FileInputStream("saved.txt");
		        int c = 0;
		        while ((c = r.read()) != -1) {
		            sb.append((char) c);
		        }
		        String tempSavedBefore = sb.toString();
		        String a = tempSavedBefore + tempSave;
		        byte[] b = a.getBytes();
		        out.write(b);
		        System.out.println(sb.toString());
		    } catch (IOException e) {
		        throw new RuntimeException(e);
		    }*/
		
		FileInputStream in = null;
		BufferedWriter out = null;
		
		 BufferedReader br = new BufferedReader(new FileReader("saved.txt"));
		    try {
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();
		        FileOutputStream w = new FileOutputStream("saved.txt");
		        while (line != null) {
		            sb.append(line);
		            sb.append("\n");
		            line = br.readLine();
		        }
		        String newSave = tempSave+"\n"+sb;
		        byte[] finalSave = newSave.getBytes();
		        w.write(finalSave);
		    } finally {
		        br.close();
		    }
		
	}
}
