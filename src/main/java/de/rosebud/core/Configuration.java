package de.rosebud.core;

// holds global project configuration for rosebud
public class Configuration {
	
	public enum DebugLevel {
		
		SUPER(0), DEBUG(10), LIVE(20);
		
		private int level = 0;
		DebugLevel(int _level) {
			level = _level;
		}
		public int getLevel() {
			return level;
		}
	}
	
	
	
	DebugLevel debugLevel = DebugLevel.LIVE;



	public DebugLevel getDebugLevel() {
		return debugLevel;
	}



	public void setDebugLevel(DebugLevel debugLevel) {
		this.debugLevel = debugLevel;
	}
	
	
	
	
	
}
