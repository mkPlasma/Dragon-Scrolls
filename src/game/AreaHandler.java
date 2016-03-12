package game;

public class AreaHandler {
	
	Area[] accessibleAreas;
	Area area;
	
	public void setArea(Area area){
		this.area = area;
	}
	
	public Area getArea(){
		return area;
	}
	
	public void setWaitEnabled(boolean waitEnabled){
		area.setWaitEnabled(waitEnabled);
		
		for(int i = 0; i < accessibleAreas.length; i++){
			accessibleAreas[i].setWaitEnabled(waitEnabled);
		}
	}
	
	public void addAccessibleArea(Area area){
		
		Area[] areaTemp = new Area[accessibleAreas.length + 1];
		
		for(int i = 0; i < accessibleAreas.length; i++){
			areaTemp[i] = accessibleAreas[i];
		}
		
		areaTemp[areaTemp.length - 1] = area;
		
		accessibleAreas = areaTemp.clone();
	}
	
	public boolean isAreaAccessible(Area area){
		for(int i = 0; i < accessibleAreas.length; i++){
			if(accessibleAreas[i].getName().equalsIgnoreCase(area.getName())){
				return true;
			}
		}
		
		return false;
	}
	
	public void setAccessibleAreas(Area[] accessibleAreas){
		this.accessibleAreas = accessibleAreas;
	}
	
	public Area[] getAccessibleAreas(){
		return accessibleAreas;
	}
}
