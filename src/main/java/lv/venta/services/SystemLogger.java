package lv.venta.services;

import org.springframework.stereotype.Service;

import lv.venta.services.impl.ISystemLogger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Service
public class SystemLogger implements ISystemLogger{
	
	private static final String LOG_FILE_PATH = "application.log";
	
	public void logInfo(String message) {
		log("INFO", message);
    }

    public void logWarning(String message) {
    	log("WARNING", message);
    }

    public void logError(String message) {
    	log("ERROR", message);
    }

    private void log(String level, String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(new Date());

        try (FileWriter fileWriter = new FileWriter(LOG_FILE_PATH, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println(formattedDate + " [" + level + "] " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
