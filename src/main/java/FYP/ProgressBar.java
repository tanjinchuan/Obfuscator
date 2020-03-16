package FYP;


import javax.swing.*;

class ProgressBar extends JProgressBar{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JProgressBar progressBar = new JProgressBar();
    

    public void runProgress() {
        progressBar.setValue(0);
        setBounds(88, 219, 160, 16);
        progressBar.setStringPainted(true);
        
        
        for(int i = 0; i <= 100; i++){
            int percent = i;
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        progressBar.setValue(percent);
                    }
                });
                java.lang.Thread.sleep(50);
            } catch (InterruptedException e) {

            }
        }
    }

    
}