package FYP;

import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

@SuppressWarnings("serial")

public class SliderOptionPanel extends JPanel implements ChangeListener, PropertyChangeListener{
    
    private int level = 0;
    
    public SliderOptionPanel(AdvOptionsPanel advOptionsPanel) {

        this.setLayout(null);

        JLabel sliderOptionDescriptionLabel = new JLabel();
		sliderOptionDescriptionLabel.setBounds(92, 243, 562, 40);
		this.add(sliderOptionDescriptionLabel);
		
		//JSlider + description label
		JSlider slider = new JSlider(0, 3, 0);
		slider.addChangeListener((ChangeListener)this);
		slider.addChangeListener(new ChangeListener() {
			
		@Override
		public void stateChanged(ChangeEvent e) {
			int level = ((JSlider)e.getSource()).getValue();
			if(level == 0) {
				sliderOptionDescriptionLabel.setText("Comments Removal | Method Name Change");
			}
			else if (level == 1) {
				sliderOptionDescriptionLabel.setText("Comments Removal | Method & Variable Name Change");
			}
			else if (level == 2) {
				sliderOptionDescriptionLabel.setText("Comments Removal | ");
			}
			else if (level == 3) {
				sliderOptionDescriptionLabel.setText("Flow Obfuscation");
			}
			
			}
        });
     
        
        
        //Create the label table 
		Hashtable labelTable = new Hashtable();
		labelTable.put(new Integer(0), new JLabel("Weak"));
		labelTable.put(new Integer(1), new JLabel("Medium"));
		labelTable.put(new Integer(2), new JLabel("Strong"));
		labelTable.put(new Integer(3), new JLabel("Extreme"));
		slider.setLabelTable(labelTable); 
		slider.setPaintLabels(true);
		slider.setBounds(92, 164, 562, 52);
        this.add(slider);
        
        

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