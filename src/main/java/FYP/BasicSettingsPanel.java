package FYP;

import java.util.Hashtable;
import java.awt.Color;
import java.awt.Font;

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

    public BasicSettingsPanel(AdvSettingsPanel advSettingsPanel) {

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