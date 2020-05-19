package FYP;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;

import java.awt.Desktop;

public class Help {
	
	public void openPDF() {
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("helpmanual.pdf");
			
        try {
			
			File tempFile = File.createTempFile("obfuscator_helpmanual", ".pdf");

			tempFile.deleteOnExit();

			try (FileOutputStream out = new FileOutputStream(tempFile)) {
            //copy stream
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
			}
			Desktop.getDesktop().open(tempFile);
        }
		} catch (IOException e) {
			
		}

	}


}
           

