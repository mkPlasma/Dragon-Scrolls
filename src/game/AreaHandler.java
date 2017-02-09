package game;

public class AreaHandler {
	
	private static Area[] accessibleAreas;
	private static Area area;
	
	public static void setArea(Area area){
		AreaHandler.area = area;
	}
	
	public static Area getArea(){
		return area;
	}
	
	public static void setWaitEnabled(boolean waitEnabled){
		area.setWaitEnabled(waitEnabled);
		
		for(int i = 0; i < accessibleAreas.length; i++){
			accessibleAreas[i].setWaitEnabled(waitEnabled);
		}
	}
	
	public static void addAccessibleArea(Area area){
		
		Area[] areaTemp = new Area[accessibleAreas.length + 1];
		
		for(int i = 0; i < accessibleAreas.length; i++){
			areaTemp[i] = accessibleAreas[i];
		}
		
		areaTemp[areaTemp.length - 1] = area;
		
		accessibleAreas = areaTemp.clone();
	}
	
	public static boolean isAreaAccessible(Area area){
		for(int i = 0; i < accessibleAreas.length; i++){
			if(accessibleAreas[i].getName().equalsIgnoreCase(area.getName())){
				return true;
			}
		}
		
		return false;
	}
	
	public static void setAccessibleAreas(Area[] accessibleAreas){
		AreaHandler.accessibleAreas = accessibleAreas;
	}
	
	public static Area[] getAccessibleAreas(){
		return accessibleAreas;
	}
}
