package FYP;

import java.util.Hashtable;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

@SuppressWarnings("serial")

public class BasicSettingsPanel extends JPanel implements ChangeListener, PropertyChangeListener{
    
    protected int level = 0;
	JComboBox<String> comboBox = new JComboBox<>();

    public BasicSettingsPanel(Frame frameClass, Obfuscator obfuscator, LayeredPane layeredPane) {

        this.setLayout(null);

		Font lblFont = new Font("Helvetica", Font.BOLD, 16);

		//default green and yellow too bright, make my own
		Color green = new Color(0, 210, 0);
		Color yellow = new Color(230, 230, 0);

		JLabel lblLevel = new JLabel("Weak");
		lblLevel.setBounds(720, 120, 150, 25);
		lblLevel.setFont(lblFont);
		lblLevel.setForeground(Color.GREEN);
		this.add(lblLevel);

		JLabel description = new JLabel("Level: ");
		description.setBounds(670, 120, 150, 25);
		description.setFont(lblFont);
		this.add(description);

        JLabel sliderOptionDescriptionLabel = new JLabel();
		sliderOptionDescriptionLabel.setBounds(670, 150, 400, 100);
		this.add(sliderOptionDescriptionLabel);
		
		//JSlider + description label
		JSlider slider = new JSlider(0, 3, 0);

		//default value is weak
		sliderOptionDescriptionLabel.setText("<html>Comments & White Space Removal<br>Class & Method Name Obfuscation");
		
		

		slider.addChangeListener((ChangeListener)this);

		slider.addChangeListener(new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			level = ((JSlider)e.getSource()).getValue();
			if(level == 0) {
				sliderOptionDescriptionLabel.setText("<html>Comments & White Space Removal<br>Class & Method Name Obfuscation");
				lblLevel.setText("Weak");
				lblLevel.setForeground(green);
			}
			else if (level == 1) {
				sliderOptionDescriptionLabel.setText("<html>Comments & White Space Removal<br>Class & Method Name Obfuscation<br>Variable & Parameter Name Obfuscation");
				lblLevel.setText("Medium");
				lblLevel.setForeground(yellow);

			}
			else if (level == 2) {
				sliderOptionDescriptionLabel.setText("<html>Comments & White Space Removal<br>Class & Method Name Obfuscation<br>Variable & Parameter Name Obfuscation<br>String Encoding");
				lblLevel.setText("Strong");
				lblLevel.setForeground(Color.ORANGE);

			}
			else if (level == 3) {
				sliderOptionDescriptionLabel.setText("<html>Comments & White Space Removal<br>Class & Method Name Obfuscation<br>Variable & Parameter Name Obfuscation<br>String Encoding<br>Dummy Code Insertion");
				lblLevel.setText("Extreme");
				lblLevel.setForeground(Color.RED);

			}
			
		}
        });
     
        
        
        //Create the label table 
		Hashtable labelTable = new Hashtable();
		JLabel weak = new JLabel("Weak");
		JLabel medium = new JLabel("Medium");
		JLabel strong = new JLabel("Strong");
		JLabel extreme = new JLabel("Extreme");
		weak.setFont(lblFont);
		medium.setFont(lblFont);
		strong.setFont(lblFont);
		extreme.setFont(lblFont);

		labelTable.put(new Integer(0), weak);
		labelTable.put(new Integer(1), medium);
		labelTable.put(new Integer(2), strong);
		labelTable.put(new Integer(3), extreme);
		
		slider.setLabelTable(labelTable); 
		slider.setPaintLabels(true);
		slider.setBounds(80, 160, 550, 52);
        this.add(slider);
        
		//JLabel for combobox
		JLabel comboBoxLabel = new JLabel("Choose main class");
		comboBoxLabel.setBounds(290, 235, 200, 20);
		comboBoxLabel.setFont(lblFont);
		this.add(comboBoxLabel);

		//JComboBox on basicSettingsPanel

		
		comboBox.setBounds(290, 260, 200, 20);
		this.add(comboBox);


		////////////////////////////////////////////////////////////////////////////////////////////
		//Button to go to advanced settings on basicSettings panel 
		///////////////////////////////////////////////////////////////////////////////////////////
		//read settings file here, get a hash map to check value after i click into advanced settings panel
		

		JButton btnAdvancedSettings = new JButton("Advanced settings");
		btnAdvancedSettings.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

		btnAdvancedSettings.setBounds(800, 310, 180, 25);
		this.add(btnAdvancedSettings);

		btnAdvancedSettings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				layeredPane.switchPanel(frameClass.advSettingsPanel);
				
				frameClass.advSettingsPanel.readSettingsFile();	
				frameClass.advSettingsPanel.setOptions();

			}
		});
		
		
		JButton btnBackSliderPanel = new JButton("Back");
		btnBackSliderPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

		btnBackSliderPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				layeredPane.switchPanel(frameClass.browsePanel);
			}
		});
		btnBackSliderPanel.setBounds(48, 360, 100, 60);
		this.add(btnBackSliderPanel);
		
		

		/////////////////////////////////////////////////////////////////////////////
		//Button "next" to go to progressbarpanel on basicSettings panel
		////////////////////////////////////////////////////////////////////////////
		JButton btnNextSliderPanel = new JButton("Start Obfuscating");
		btnNextSliderPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

		btnNextSliderPanel.setBounds(800, 350, 180, 80);
		this.add(btnNextSliderPanel);

		btnNextSliderPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//set the file paths
					frameClass.inputFilePath = frameClass.browsePanel.getInput();
					frameClass.outputFilePath = frameClass.browsePanel.getOutput();

					//get selected main class
					String selectedClass = String.valueOf(comboBox.getSelectedItem());

					
					obfuscator.obfuscate(frameClass.inputFilePath, frameClass.outputFilePath, getLevel(), selectedClass);
					//start obsfuscation
			
					
					layeredPane.switchPanel(frameClass.progressBarPanel);
					
					//do delay to switch panel
					frameClass.progressBarPanel.update(layeredPane, frameClass.browsePanel, frameClass.finalPanel, obfuscator);
			

			

				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		

    }

    public int getLevel() {
        return level;
	}
	


    @Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}


    
}