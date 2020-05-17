package FYP;

import java.io.File;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.*;

import javax.swing.border.LineBorder;



public class Frame {

	private JFrame frame;

	protected String inputFilePath;
	protected String outputFilePath;
	Obfuscator obfuscator = new Obfuscator();
	LayeredPane layeredPane = new LayeredPane();

	BrowsePanel browsePanel = new BrowsePanel(frame, this, obfuscator, layeredPane);

	
	InitialPanel initialPanel = new InitialPanel(this, layeredPane);

	
	//for advanced settings
	AdvSettingsPanel advSettingsPanel = new AdvSettingsPanel(this, obfuscator, layeredPane);

	//add slider panel
	BasicSettingsPanel basicSettingsPanel = new BasicSettingsPanel(this, obfuscator, layeredPane);


	
	//add progress bar panel
	ProgressBarPanel progressBarPanel = new ProgressBarPanel();

	//add final panel
	FinalPanel finalPanel = new FinalPanel(this, frame, obfuscator, layeredPane);
	

	protected int highscore;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				try {
					//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					UIManager.put("ToolTip.background", Color.WHITE);
					UIManager.put("ToolTip.border",new LineBorder(Color.BLACK,1));
					UIManager.put("Button.font", new Font("Helvetica", Font.BOLD, 16));
					UIManager.put("Label.font", new Font("Helvetica", Font.BOLD, 16));
					UIManager.put("TextField.font", new Font("Helvetica", Font.BOLD, 16));
					UIManager.put("CheckBox.font", new Font("Helvetica", Font.BOLD, 16));
					UIManager.put("RadioButton.font", new Font("Helvetica", Font.BOLD, 16));
					
					UIManager.put("TextArea.font", new Font("Calibri", Font.BOLD, 22));
					
					Frame window = new Frame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	/**
	 * Create the application.
	 */
	public Frame(){
		makedir();
		initialize();
	}
	
	//create settings dir and files
	private void makedir() {
		  try {
			  File folder = new File ("./settings");
					  
			  folder.mkdir();
			  Process p =  Runtime.getRuntime().exec("attrib +H " + folder.getPath());
			  p.waitFor();
			  
		  }
		  catch (IOException e)
		  {
			  e.printStackTrace();
		  }
		  catch (InterruptedException e)
		  {
			  e.printStackTrace();
		  }
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame("Obsfuscator");
		frame.setBounds(100, 100, 1024, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(layeredPane);
		
		//testing
		layeredPane.switchPanel(initialPanel);

		//initialize advsettings.txt;
		advSettingsPanel.createSettingsFile();

		
		
		
	}

	

	

	
}