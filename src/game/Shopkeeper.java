package game;


public class Shopkeeper{
	
	private final Item[] items;
	private final int[] itemPrices;
	private final int[] itemCounts;
	
	private final String name, greetMessage, endMessage;
	
	public Shopkeeper(String name, String greetMessage, String endMessage, Item[] items, int[] itemPrices, int[] itemCounts){
		this.name = name;
		this.greetMessage = greetMessage;
		this.endMessage = endMessage;
		
		this.items = items;
		this.itemPrices = itemPrices;
		this.itemCounts = itemCounts;
	}
	
	public String getName(){
		return name;
	}
	
	public String getGreetMessage(){
		return greetMessage;
	}
	
	public String getEndMessage(){
		return endMessage;
	}
	
	public Item[] getItems(){
		return items;
	}
	
	public int[] getItemPrices(){
		return itemPrices;
	}
	
	public int[] getItemCounts(){
		return itemCounts;
	}
	
	public void removeItem(Item item){
		for(int i = 0; i < items.length - 0; i++){
			if(items[i].equals(item) && itemCounts[i] > 0){
				itemCounts[i] -= 1;
			}
		}
	}
}
