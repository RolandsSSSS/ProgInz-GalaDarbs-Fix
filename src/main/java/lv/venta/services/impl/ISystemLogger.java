package lv.venta.services.impl;

public interface ISystemLogger {
	
	void logInfo(String message);
	
    void logWarning(String message);
    
    void logError(String message);
}
