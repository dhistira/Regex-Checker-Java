import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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

public class main {
	
	//SWT Declaration
	protected Shell shlRegexChecker;
	private Text inputRegex, textInput, inputSaveRegex, resultInput;
	private Browser displayHistory, Tab1, Tab2, Tab3;
	private Label separator, lblDictionary, labelSaved, lblSource;
	private TabFolder dictionary;
	private Button btnSave;
	private TabItem CharacterTab, PredefinedTab, BoundaryTab;
	private MessageBox dialog, dialogGagal, dialogSukses;
	
	//ECLIPSE 
	final String dir = System.getProperty("user.dir");
	private String regexUser, trueString, delimiter = " ", tempSave;
	private String[] tempArray;
	
	//UTIL
	private Timer mytimer;
	private long delay, period;
	private TimerTask autoUpdate;
	private static FileOutputStream output;
	private DateTimeFormatter dtf;
	private LocalDateTime now;

	
	//CONSTRUCTOR
	public main() {
		
		//CORE
		shlRegexChecker = new Shell();
		displayHistory = new Browser(shlRegexChecker, SWT.NONE);
		inputRegex = new Text(shlRegexChecker, SWT.BORDER);
		textInput = new Text(shlRegexChecker, SWT.BORDER);
		separator = new Label(shlRegexChecker, SWT.SEPARATOR | SWT.HORIZONTAL);
		resultInput = new Text(shlRegexChecker, SWT.BORDER);
		mytimer =  new Timer("Timer");
		
		//SAVE REGEX
		inputSaveRegex = new Text(shlRegexChecker, SWT.BORDER);
		labelSaved = new Label(shlRegexChecker, SWT.NONE);
		btnSave = new Button(shlRegexChecker, SWT.NONE);
		dialogGagal = new MessageBox(shlRegexChecker, SWT.ICON_ERROR | SWT.OK);
		dialogSukses = new MessageBox(shlRegexChecker, SWT.ICON_INFORMATION | SWT.OK);
		
		//DICTIONARY
		dictionary = new TabFolder(shlRegexChecker, SWT.NONE);
		new Label(dictionary, SWT.NONE);
		lblDictionary = new Label(shlRegexChecker, SWT.NONE);
		lblSource = new Label(shlRegexChecker, SWT.NONE);
		
		//TABBER
		Tab1 = new Browser(dictionary, SWT.NONE);
		CharacterTab = new TabItem(dictionary, SWT.H_SCROLL);
		PredefinedTab = new TabItem(dictionary, SWT.H_SCROLL);
		Tab2 = new Browser(dictionary, SWT.NONE);
		BoundaryTab = new TabItem(dictionary, SWT.NONE);
		Tab3 = new Browser(dictionary, SWT.NONE);
		
	}
	
	public static void main(String[] args) {
		try {
			main window = new main();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	protected void createContents() {
		shlRegexChecker.setSize(766, 610);
		shlRegexChecker.setText("Regex Checker");
		
		//PENGECEKAN REGEX
		inputRegex.setBounds(10, 10, 438, 70);
		inputRegex.setMessage("Put your regex here");
		textInput.setBounds(10, 102, 438, 87);
		textInput.setMessage("Put your text here");
		separator.setText("Your Result");
		separator.setBounds(10, 86, 438, 10);
		resultInput.setBounds(10, 207, 438, 93);
		resultInput.setMessage("Your result will be displayed here");
		
		//DICTIONARY START
		dictionary.setBounds(10, 338, 438, 208);
		lblDictionary.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD | SWT.ITALIC));
		lblDictionary.setBounds(10, 306, 270, 48);
		lblDictionary.setText("REGEX DICTIONARY");
		
		getDictionary getDictionary = new getDictionary();
		CharacterTab.setText("Character");
		CharacterTab.setControl(Tab1);
		Tab1.setText(getDictionary.getDictionary(1));
		PredefinedTab.setText("Predefined");
		Tab2.setText(getDictionary.getDictionary(2));
		PredefinedTab.setControl(Tab2);
		BoundaryTab.setText("Boundary");
		Tab3.setText(getDictionary.getDictionary(3));
		BoundaryTab.setControl(Tab3);
		
		lblSource.setBounds(10, 550, 625, 21);
		lblSource.setText("Dictionary Source: https://zeroturnaround.com/rebellabs/java-regular-expressions-cheat-sheet/");
		
		//SAVE REGEX START
		inputSaveRegex.setBounds(462, 10, 278, 31);
		inputSaveRegex.setMessage("Put your regex name");
		labelSaved.setBounds(462, 52, 99, 21);
		labelSaved.setText("SAVED REGEX");
		displayHistory.setBounds(462, 79, 278, 467);
		String history = dir+"/saved.txt";
		displayHistory.setUrl(history);

		//BUTON SAVE REGEX
		btnSave.setBounds(655, 47, 85, 26);
		btnSave.setText("SAVE");
		btnSave.addListener(SWT.Selection, new Listener()
		{
		    @Override
		    public void handleEvent(Event event)
		    {
		    	if(inputSaveRegex.getText().trim().length() == 0 || inputRegex.getText().trim().length() == 0) {
		    		dialogGagal.setMessage("Nama Regex tidak Boleh Kosong");
		    		dialogGagal.open();
		    		displayHistory.setUrl(history);
		    	}else {
		    	tempSave = getText(inputSaveRegex.getText(),inputRegex.getText());
		    		try {
						saveRegex(tempSave);
					} catch (IOException e) {
			    		dialogGagal.setMessage("Regex gagal disimpan!");
			    		dialogGagal.open();
			    		displayHistory.setUrl(history);
					} catch (NullPointerException es) {
						dialogGagal.setMessage("Nama  Regex tidak Boleh Kosong");
			    		dialogGagal.open();
			    		displayHistory.setUrl(history);
					}
		    		dialogSukses.setMessage("Regex berhasil disimpan");
		    		dialogSukses.open();
		    		displayHistory.setUrl(history);
		    	}
		    }
		});
		
		//AUTO CHECK REGEX
		autoUpdate = new TimerTask() {
			public void run() {
				Display.getDefault().syncExec(new Runnable() {
					@Override
					public void run() {
						if(inputRegex.getText().trim().length() > 0) {
							try {
								tempArray = textInput.getText().split(delimiter);
								trueString = "";
									try {
										for (int i = 0; i < tempArray.length; i++) {
											regexUser = inputRegex.getText();
											if(tempArray[i].matches(regexUser)) {
												trueString = trueString + " " + tempArray[i];
											}else {
											}
										}
										resultInput.setText(trueString);
									}catch(PatternSyntaxException e) {
										resultInput.setText("Error pattern");
									}
							}catch(SWTException e) {
								resultInput.setText("Error");
							}
						}
					}
				});
			}
		};
		delay = 50L;
		period = 50L;
		mytimer.schedule(autoUpdate, delay, period);
	}
	
	
	//DAPAT STRING SAVE REGEX
	public String getText(String namaregex, String inputregex) {
		dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
		now = LocalDateTime.now();  
		return "["+dtf.format(now)+"] \n Nama Regex: "+namaregex+"\n Regex: "+inputregex+"\n"+"====================";
	}
	
	
	//INPUT OUTPUT KE TXT
	public static void saveRegex(String tempSave) throws IOException {	
		BufferedReader br = new BufferedReader(new FileReader("saved.txt"));
		try {
			
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			output = new FileOutputStream("saved.txt");
			
			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			
			String newSave = tempSave+"\n"+sb;
			byte[] finalSave = newSave.getBytes();
			output.write(finalSave);
		} finally {
			br.close();
		}
		
	}
}
