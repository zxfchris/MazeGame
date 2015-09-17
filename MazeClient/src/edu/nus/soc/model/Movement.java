package edu.nus.soc.model;

public enum Movement {
	S, E, N, W, NOMOVE,;
	
	public static Movement getMovementByString(String str) {
		if (null != str) {
			try {
				return Enum.valueOf(Movement.class, str);
			}
			catch (IllegalArgumentException e) {
				
			}
		}
			
		return null;
	}
}
