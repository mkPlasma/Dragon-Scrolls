package game;

import java.awt.Color;

public final class ObjectList{
	
	public static final Color COLOR_STOCK = Color.BLACK,
	COLOR_COMMON = new Color(32, 32, 160),
	COLOR_SEMIRARE = new Color(0, 128, 0),
	COLOR_RARE = new Color(160, 0, 0),
	COLOR_LEGENDARY = new Color(160, 0, 160),
	COLOR_DRAGON = new Color(96, 96, 0),
	COLOR_GOD = new Color(160, 0, 0);
	
	//Attacks
	private static final Attack[][] playerNormalAttacks = {
		
		//Knight
		
		{
		new Attack(15, 20, 70, "Basic Slash", "weapons/sword01.wav", COLOR_STOCK),		// Basic Slash
		new Attack(25, 35, 50, "Strong Slash", "weapons/sword03.wav", COLOR_STOCK),		// Strong Slash
		},
		
		{
		new Attack(20, 25, 70, "Basic Slash", "weapons/sword01.wav", COLOR_COMMON),		// Basic Slash
		new Attack(30, 40, 50, "Strong Slash", "weapons/sword03.wav", COLOR_COMMON),		// Strong Slash
		},
		
		{
		new Attack(25, 30, 70, "Basic Slash", "weapons/sword01.wav", COLOR_COMMON),		// Basic Slash
		new Attack(35, 45, 50, "Strong Slash", "weapons/sword03.wav", COLOR_COMMON),		// Strong Slash
		},
		
		{
		new Attack(30, 35, 70, "Basic Slash", "weapons/sword01.wav", COLOR_COMMON),		// Basic Slash
		new Attack(40, 50, 50, "Strong Slash", "weapons/sword03.wav", COLOR_COMMON),		// Strong Slash
		new Attack(10, 15, 80, "Quick Slash", new StatusEffect(8, 4, 2, 60, false), "weapons/sword02.wav", COLOR_SEMIRARE),	// Quick Slash
		},
		
		{
		new Attack(35, 40, 70, "Basic Slash", "weapons/sword01.wav", COLOR_COMMON),		// Basic Slash
		new Attack(45, 55, 50, "Strong Slash", "weapons/sword03.wav", COLOR_COMMON),		// Strong Slash
		new Attack(10, 15, 80, "Flame Slash", new StatusEffect(9, 5, 2, 60, false), "weapons/fire03.wav", COLOR_SEMIRARE),	// Flame Slash
		},
		
		{
		new Attack(35, 40, 85, "Magic Cut", "weapons/sword01.wav", COLOR_SEMIRARE),		// Magic Cut
		new Attack(40, 45, 70, "Magic Slice", "weapons/sword02.wav", COLOR_SEMIRARE),		// Magic Slice
		new Attack(10, 15, 80, "Magic Slash", new StatusEffect(8, 4, 3, 60, false), "weapons/sword03.wav", COLOR_SEMIRARE),	// Magic Slash
		},
		
		{
		new Attack(40, 45, 85, "Shadow Cut", "weapons/sword01.wav", COLOR_SEMIRARE),		// Shadow Cut
		new Attack(45, 50, 70, "Shadow Slice", "weapons/sword02.wav", COLOR_SEMIRARE),	// Shadow Slice
		new Attack(10, 15, 80, "Shadow Slash", new StatusEffect(8, 5, 3, 60, false), "weapons/sword03.wav", COLOR_SEMIRARE),	// Shadow Slash
		},
		
		{
		new Attack(45, 50, 85, "Cut", "weapons/sword01.wav", COLOR_SEMIRARE),		// Cut
		new Attack(50, 55, 70, "Slice", "weapons/sword02.wav", COLOR_SEMIRARE),	// Slice
		new Attack(10, 15, 80, "Slash", new StatusEffect(8, 4, 4, 60, false), "weapons/sword03.wav", COLOR_RARE),		// Slash
		},
		
		{
		new Attack(50, 55, 85, "Cut", "weapons/sword01.wav", COLOR_SEMIRARE),		// Cut
		new Attack(55, 60, 70, "Slice", "weapons/sword02.wav", COLOR_SEMIRARE),	// Slice
		new Attack(10, 15, 80, "Slash", new StatusEffect(8, 5, 4, 60, false), "weapons/sword03.wav", COLOR_RARE),		// Slash
		new Attack(5, 10, 80, "Pierce", new StatusEffect(13, 3, 5, 60, false), "weapons/sword02.wav", COLOR_RARE),	// Pierce
		},
		
		{
		new Attack(55, 60, 85, "Cut", "weapons/sword01.wav", COLOR_RARE),			// Cut
		new Attack(60, 65, 70, "Slice", "weapons/sword02.wav", COLOR_RARE),		// Slice
		new Attack(10, 15, 80, "Slash", new StatusEffect(8, 4, 5, 60, false), "weapons/sword03.wav", COLOR_RARE),		// Slash
		new Attack(5, 10, 80, "Pierce", new StatusEffect(13, 5, 5, 60, false), "weapons/sword02.wav", COLOR_RARE),	// Pierce
		},
		
		{
		new Attack(60, 65, 85, "Cut", "weapons/sword01.wav", COLOR_RARE),			// Cut
		new Attack(65, 70, 70, "Slice", "weapons/sword02.wav", COLOR_RARE),		// Slice
		new Attack(10, 15, 80, "Slash", new StatusEffect(8, 5, 5, 60, false), "weapons/sword03.wav", COLOR_RARE),		// Slash
		new Attack(10, 15, 80, "Ironrock Slam", new StatusEffect(13, 3, 8, 60, false), "weapons/", COLOR_RARE),		// Ironrock Slam
		},
		
		{
		new Attack(65, 70, 85, "Cut", "weapons/sword01.wav", COLOR_RARE),			// Cut
		new Attack(70, 75, 70, "Slice", "weapons/sword02.wav", COLOR_RARE),		// Slice
		new Attack(10, 15, 80, "Slash", new StatusEffect(8, 4, 6, 60, false), "weapons/sword03.wav", COLOR_LEGENDARY),		// Slash
		new Attack(5, 10, 80, "Pierce", new StatusEffect(13, 5, 8, 60, false), "weapons/sword02.wav", COLOR_LEGENDARY),		// Pierce
		},
		
		{
		new Attack(70, 75, 85, "Cut", "weapons/sword01.wav", COLOR_RARE),			// Cut
		new Attack(75, 80, 70, "Slice", "weapons/sword02.wav", COLOR_RARE),		// Slice
		new Attack(10, 15, 80, "Slash", new StatusEffect(8, 5, 6, 60, false), "weapons/sword03.wav", COLOR_LEGENDARY),		// Slash
		new Attack(5, 10, 80, "Dragon Rage", new StatusEffect(13, 3, 11, 60, false), "weapons/", COLOR_LEGENDARY),			// Dragon Rage
		},
		
		{
		new Attack(75, 80, 85, "Cut", "weapons/sword01.wav", COLOR_LEGENDARY),		// Cut
		new Attack(80, 85, 70, "Slice", "weapons/sword02.wav", COLOR_LEGENDARY),	// Slice
		new Attack(10, 15, 80, "Slash", new StatusEffect(8, 4, 7, 60, false), "weapons/sword03.wav", COLOR_LEGENDARY),		// Slash
		new Attack(40, 120, 50, "Lance", "weapons/sword01.wav", COLOR_LEGENDARY),		// Lance
		new Attack(5, 10, 80, "Guardslash", new StatusEffect(13, 5, 11, 60, false), "weapons/sword01.wav", COLOR_LEGENDARY),	// Guardslash
		},
		
		{
		new Attack(80, 85, 85, "Cut", "weapons/sword01.wav", COLOR_LEGENDARY),		// Cut
		new Attack(85, 90, 70, "Slash", "weapons/sword03.wav", COLOR_LEGENDARY),	// Slash
		new Attack(10, 15, 80, "Starlash", new StatusEffect(8, 5, 7, 60, false), "weapons/sword02.wav", COLOR_DRAGON),		// Starslash
		new Attack(5, 10, 80, "Light Slice", new StatusEffect(13, 3, 14, 60, false), "weapons/sword02.wav", COLOR_DRAGON),	// Light Slice
		new Attack(40, 160, 40, "Blade Nova", "weapons/", COLOR_LEGENDARY),		// Blade Nova
		},
		
		{
		new Attack(90, 100, 80, "Sword Summon", "weapons/", COLOR_LEGENDARY),		// Sword Summon
		new Attack(20, 25, 80, "Theory Slash", new StatusEffect(8, 5, 10, 50, false), "weapons/", COLOR_LEGENDARY),		// Theory Slash
		new Attack(50, 60, 50, "Hammer Summon", new StatusEffect(13, 5, 15, 50, false), "weapons/", COLOR_LEGENDARY),		// Hammer Summon
		new Attack(5, 10, 80, "Dragon Slash", new StatusEffect(13, 4, 14, 60, false), "weapons/", COLOR_DRAGON),			// Dragon Slash
		new Attack(50, 60, 80, "Dragon Claw", new StatusEffect(3, 5, 10, 50, true), "weapons/", COLOR_DRAGON),			// Dragon Claw
		new Attack(90, 110, 25, "Summon Inorexite Dragon", new StatusEffect[]{new StatusEffect(13, 3, 10, 70, false),
		new StatusEffect(8, 3, 5, 70, false), new StatusEffect(19, 1, 10, 90, true)}, "weapons/", COLOR_DRAGON),			// Summon Inorexite Dragon
		},
		
		
		//Archer
		
		{
		new Attack(8, 12, 80, "Basic Arrow", "weapons/", COLOR_STOCK),		// Basic Arrow
		new Attack(14, 18, 65, "Basic Snipe", "weapons/", COLOR_STOCK),		// Basic Snipe
		},
		
		{
		new Attack(12, 14, 80, "Basic Arrow", "weapons/", COLOR_COMMON),		// Basic Arrow
		new Attack(18, 22, 65, "Basic Snipe", "weapons/", COLOR_COMMON),		// Basic Snipe
		},
		
		{
		new Attack(16, 18, 80, "Basic Arrow", "weapons/", COLOR_COMMON),		// Basic Arrow
		new Attack(22, 26, 65, "Basic Snipe", "weapons/", COLOR_COMMON),		// Basic Snipe
		},
		
		{
		new Attack(18, 22, 80, "Basic Arrow", "weapons/", COLOR_COMMON),		// Basic Arrow
		new Attack(26, 30, 65, "Basic Snipe", "weapons/", COLOR_COMMON),		// Basic Snipe
		new Attack(10, 40, 90, "Rapidfire Arrow", "weapons/", COLOR_SEMIRARE),	// Rapidfire Arrow
		},
		
		{
		new Attack(22, 26, 80, "Basic Arrow", "weapons/", COLOR_COMMON),		// Basic Arrow
		new Attack(30, 34, 65, "Basic Snipe", "weapons/", COLOR_COMMON),		// Basic Snipe
		new Attack(30, 40, 70, "Normal Snipe", new StatusEffect(8, 4, 2, 60, false), "weapons/", COLOR_SEMIRARE),	// Normal Snipe
		},
		
		{
		new Attack(26, 30, 80, "Magic Arrow", "weapons/", COLOR_SEMIRARE),		// Magic Arrow
		new Attack(34, 38, 65, "Magic Snipe", new StatusEffect(8, 5, 2, 60, false), "weapons/", COLOR_SEMIRARE),	// Magic Snipe
		new Attack(10, 48, 90, "Rapidfire Arrow", "weapons/", COLOR_SEMIRARE),	// Rapidfire Arrow
		},
		
		{
		new Attack(30, 34, 80, "Arrow Shot", "weapons/", COLOR_SEMIRARE),		// Arrow Shot
		new Attack(32, 40, 65, "Normal Snipe", new StatusEffect(8, 6, 2, 60, false), "weapons/", COLOR_SEMIRARE),	// Normal Snipe
		new Attack(10, 56, 90, "Rapidfire Arrow", "weapons/", COLOR_SEMIRARE),	// Rapidfire Arrow
		},
		
		{
		new Attack(34, 38, 80, "Arrow Shot", "weapons/", COLOR_SEMIRARE),		// Arrow Shot
		new Attack(32, 42, 65, "Normal Snipe", new StatusEffect(8, 5, 3, 60, false), "weapons/", COLOR_SEMIRARE),	// Normal Snipe
		new Attack(10, 64, 90, "Rapidfire Arrow", "weapons/", COLOR_RARE),		// Rapidfire Arrow
		},
		
		{
		new Attack(42, 46, 80, "Arrow Shot", "weapons/", COLOR_SEMIRARE),		// Arrow Shot
		new Attack(34, 42, 65, "Normal Snipe", new StatusEffect(8, 6, 3, 60, false), "weapons/", COLOR_SEMIRARE),	// Normal Snipe
		new Attack(10, 72, 90, "Rapidfire Arrow", "weapons/", COLOR_RARE),		// Rapidfire Arrow
		new Attack(10, 20, 75, "Ice Arrow", new StatusEffect(18, 4, 2, 50, false), "weapons/", COLOR_RARE),		// Ice Arrow
		},
		
		{
		new Attack(46, 50, 80, "Arrow Shot", "weapons/", COLOR_RARE),			// Arrow Shot
		new Attack(34, 44, 65, "Normal Snipe", new StatusEffect(8, 7, 3, 60, false), "weapons/", COLOR_RARE),	// Normal Snipe
		new Attack(10, 80, 90, "Rapidfire Arrow", "weapons/", COLOR_RARE),	// Rapidfire Arrow
		new Attack(10, 20, 75, "Fire Arrow", new StatusEffect(18, 5, 2, 50, false), "weapons/", COLOR_RARE),	// Fire Arrow
		},
		
		{
		new Attack(50, 54, 80, "Arrow Shot", "weapons/", COLOR_RARE),			// Arrow Shot
		new Attack(36, 44, 70, "Precise Snipe", new StatusEffect(8, 6, 4, 60, false), "weapons/", COLOR_RARE),	// Precise Snipe
		new Attack(10, 88, 90, "Rapidfire Arrow", "weapons/", COLOR_RARE),	// Rapidfire Arrow
		new Attack(10, 88, 60, "Arrow Barrage", new StatusEffect(18, 3, 2, 50, false), "weapons/", COLOR_RARE),	// Arrow Barrage
		},
		
		{
		new Attack(54, 58, 80, "Arrow Shot", "weapons/", COLOR_RARE),				// Arrow Shot
		new Attack(36, 46, 70, "Precise Snipe", new StatusEffect(8, 7, 4, 60, false), "weapons/", COLOR_RARE),	// Precise Snipe
		new Attack(10, 96, 90, "Rapidfire Arrow", "weapons/", COLOR_LEGENDARY),	// Rapidfire Arrow
		new Attack(10, 96, 60, "Arrow Barrage", new StatusEffect(18, 3, 2, 50, false), "weapons/", COLOR_LEGENDARY),	// Arrow Barrage
		},
		
		{
		new Attack(62, 66, 80, "Dragon Arrow", "weapons/", COLOR_LEGENDARY),		// Dragon Arrow
		new Attack(38, 46, 70, "Precise Snipe", new StatusEffect(8, 8, 4, 60, false), "weapons/", COLOR_RARE),	// Precise Snipe
		new Attack(10, 102, 90, "Rapidfire Arrow", "weapons/", COLOR_RARE),		// Rapidfire Arrow
		new Attack(10, 102, 60, "Dragon Barrage", new StatusEffect(18, 4, 3, 50, false), "weapons/", COLOR_LEGENDARY),	// Dragon Barrage
		},
		
		{
		new Attack(66, 70, 80, "Arrow Shot", "weapons/", COLOR_LEGENDARY),			// Arrow Shot
		new Attack(38, 48, 70, "Precise Snipe", new StatusEffect(8, 7, 5, 60, false), "weapons/", COLOR_LEGENDARY),		// Precise Snipe
		new Attack(10, 110, 90, "Rapidfire Arrow", "weapons/", COLOR_LEGENDARY),	// Rapidfire Arrow
		new Attack(10, 110, 60, "Arrow Barrage", new StatusEffect(18, 4, 3, 50, false), "weapons/", COLOR_LEGENDARY),		// Arrow Barrage
		new Attack(10, 20, 75, "Power Arrow", new StatusEffect(18, 5, 3, 50, false), "weapons/", COLOR_LEGENDARY),			// Power Arrow
		},
		
		{
		new Attack(70, 74, 80, "Star Shot", "weapons/", COLOR_LEGENDARY),			// Star Shot
		new Attack(40, 48, 70, "Star Snipe", new StatusEffect(8, 8, 5, 60, false), "weapons/", COLOR_LEGENDARY),			// Star Snipe
		new Attack(10, 118, 90, "Rapidfire Arrow", "weapons/", COLOR_LEGENDARY),	// Rapidfire Arrow
		new Attack(10, 118, 60, "Arrow Barrage", new StatusEffect(18, 4, 4, 50, false), "weapons/", COLOR_DRAGON),		// Arrow Barrage
		new Attack(10, 20, 75, "Cosmic Arrow", new StatusEffect(18, 6, 3, 50, false), "weapons/", COLOR_DRAGON),		// Cosmic Arrow
		},
		
		{
		new Attack(80, 90, 80, "Superior Arrow", "weapons/", COLOR_LEGENDARY),		// Superior Arrow
		new Attack(45, 55, 70, "Superior Snipe", new StatusEffect(8, 10, 5, 60, false), "weapons/", COLOR_LEGENDARY),		// Superior Snipe
		new Attack(10, 150, 60, "Superior Barrage", new StatusEffect(18, 4, 5, 50, false), "weapons/", COLOR_LEGENDARY),	// Superior Barrage
		new Attack(10, 20, 75, "Magic Power Arrow", new StatusEffect(18, 6, 4, 50, false), "weapons/", COLOR_DRAGON),		// Magic Power Arrow
		new Attack(10, 150, 90, "Magic Arrow Storm", "weapons/", COLOR_DRAGON),	// Magic Arrow Storm
		new Attack(100, 120, 35, "Summon Dragon Archer", new StatusEffect[]{new StatusEffect(8, 5, 5, 60, false),
			new StatusEffect(18, 5, 6, 20, false), new StatusEffect(19, 1, 10, 60, true)}, "weapons/", COLOR_DRAGON),				// Summon Dragon Archer
		},
		
		
		//Mage

		{
		new Attack(10, 15, 80, "Magic Bolt", "weapons/", COLOR_STOCK),		// Magic Bolt
		new Attack(15, 20, 60, "Magic Stream", "weapons/", COLOR_STOCK),		// Magic Stream
		},
		
		{
		new Attack(15, 20, 80, "Magic Bolt", "weapons/", COLOR_COMMON),		// Magic Bolt
		new Attack(20, 25, 60, "Magic Stream", "weapons/", COLOR_COMMON),		// Magic Stream
		},
		
		{
		new Attack(20, 25, 80, "Magic Bolt", "weapons/", COLOR_COMMON),		// Magic Bolt
		new Attack(25, 30, 60, "Magic Stream", "weapons/", COLOR_COMMON),		// Magic Stream
		},
		
		{
		new Attack(25, 30, 80, "Condensed Magic", "weapons/", COLOR_COMMON),	// Condensed Magic
		new Attack(30, 35, 60, "Energy Bolt", "weapons/", COLOR_COMMON),		// Energy Bolt
		},
		
		{
		new Attack(30, 35, 80, "Condensed Magic", "weapons/", COLOR_COMMON),	// Condensed Magic
		new Attack(35, 40, 60, "Energy Bolt", "weapons/", COLOR_COMMON),		// Energy Bolt
		},
		
		{
		new Attack(40, 45, 80, "Condensed Magic", "weapons/", COLOR_SEMIRARE),	// Condensed Magic
		new Attack(45, 50, 60, "Energy Bolt", "weapons/", COLOR_SEMIRARE),		// Energy Bolt
		new Attack(10, 30, 90, "Energy Stream", "weapons/", COLOR_SEMIRARE),		// Energy Stream
		},
		
		{
		new Attack(45, 50, 80, "Condensed Magic", "weapons/", COLOR_SEMIRARE),	// Condensed Magic
		new Attack(50, 55, 60, "Energy Bolt", "weapons/", COLOR_SEMIRARE),		// Energy Bolt
		new Attack(10, 35, 90, "Energy Stream", "weapons/", COLOR_SEMIRARE),		// Energy Stream
		},
		
		{
		new Attack(50, 55, 80, "Condensed Magic", "weapons/", COLOR_SEMIRARE),	// Condensed Magic
		new Attack(55, 60, 60, "Energy Bolt", "weapons/", COLOR_SEMIRARE),		// Energy Bolt
		new Attack(10, 40, 90, "Energy Stream", "weapons/", COLOR_RARE),			// Energy Stream
		},
		
		{
		new Attack(50, 55, 85, "Condensed Magic", "weapons/", COLOR_SEMIRARE),	//Condensed Magic
		new Attack(10, 45, 90, "Energy Stream", new StatusEffect(18, 3, 4, 15, false), "weapons/", COLOR_SEMIRARE),	// Energy Stream
		new Attack(55, 60, 60, "Earth Bolt", "weapons/", COLOR_RARE),			// Earth Bolt
		new Attack(10, 50, 80, "Stone Missile", new StatusEffect(13, 3, 5, 50, false), "weapons/", COLOR_RARE),	// Stone Missile
		},
		
		{
		new Attack(55, 60, 85, "Condensed Magic", "weapons/", COLOR_RARE),		// Condensed Magic
		new Attack(10, 50, 90, "Energy Stream", new StatusEffect(18, 3, 6, 15, false), "weapons/", COLOR_RARE),	// Energy Stream
		new Attack(60, 65, 60, "Fireball", "weapons/", COLOR_RARE),			// Fireball
		new Attack(10, 50, 80, "Fire Missile", new StatusEffect(9, 3, 5, 50, false), "weapons/", COLOR_RARE),	// Fire Missile
		},
		
		{
		new Attack(55, 60, 90, "Condensed Magic", "weapons/", COLOR_RARE),		// Condensed Magic
		new Attack(10, 55, 90, "Energy Stream", new StatusEffect(18, 3, 8, 15, false), "weapons/", COLOR_RARE),	// Energy Stream
		new Attack(65, 70, 75, "Dark Bolt", "weapons/", COLOR_RARE),			// Dark Bolt
		new Attack(10, 50, 80, "Light Banish", new StatusEffect(11, 3, 5, 50, false), "weapons/", COLOR_RARE),	// Light Banish
		},
		
		{
		new Attack(60, 65, 90, "Condensed Magic", "weapons/", COLOR_RARE),		// Condensed Magic
		new Attack(10, 60, 90, "Energy Stream", new StatusEffect(18, 3, 10, 15, false), "weapons/", COLOR_RARE),	// Energy Stream
		new Attack(70, 80, 75, "Pure Magic Bolt", "weapons/", COLOR_LEGENDARY),		// Pure Magic Bolt
		new Attack(80, 100, 30, "Pure Magic Missile", "weapons/", COLOR_LEGENDARY),	// Pure Magic Missile
		},
		
		{
		new Attack(60, 65, 95, "Condensed Magic", "weapons/", COLOR_RARE),			// Condensed Magic
		new Attack(10, 65, 90, "Energy Stream", new StatusEffect(18, 3, 12, 15, false), "weapons/", COLOR_RARE),		// Energy Stream
		new Attack(10, 60, 80, "Dragon Fire", new StatusEffect(9, 5, 5, 50, false), "weapons/", COLOR_LEGENDARY),		// Dragon Fire
		new Attack(75, 85, 75, "Dragon Bolt", "weapons/", COLOR_LEGENDARY),		// Dragon Bolt
		},
		
		{
		new Attack(65, 70, 95, "Condensed Magic", "weapons/", COLOR_LEGENDARY),	// Condensed Magic
		new Attack(80, 90, 75, "Energy Bolt", "weapons/", COLOR_LEGENDARY),		// Energy Bolt
		new Attack(10, 50, 90, "Energy Stream", new StatusEffect(18, 3, 4, 40, false), "weapons/", COLOR_LEGENDARY),	// Energy Stream
		new Attack(10, 70, 90, "Magic Cloud", new StatusEffect(18, 3, 14, 15, false), "weapons/", COLOR_LEGENDARY),	// Magic Cloud
		new Attack(80, 120, 30, "Magic Spiral", "weapons/", COLOR_LEGENDARY),		// Magic Spiral
		},
		
		{
		new Attack(65, 70, 100, "Condensed Star", "weapons/", COLOR_LEGENDARY),	// Condensed Star
		new Attack(95, 105, 60, "Star Bolt", "weapons/", COLOR_LEGENDARY),			// Star Bolt
		new Attack(10, 75, 90, "Arcane Nebula", new StatusEffect(18, 3, 16, 15, false), "weapons/", COLOR_LEGENDARY),	// Arcane Nebula
		new Attack(10, 50, 90, "Stardust Cloud", new StatusEffect(18, 3, 6, 60, false), "weapons/", COLOR_DRAGON),		// Stardust Cloud
		new Attack(80, 140, 30, "Mini Nova", "weapons/", COLOR_DRAGON),			// Mini Nova
		},
		
		{
		new Attack(60, 80, 100, "Arcane Nova", "weapons/", COLOR_LEGENDARY),				// Arcane Nova
		new Attack(80, 200, 33, "Ancient Missile", "weapons/", COLOR_LEGENDARY),			// Ancient Missile
		new Attack(30, 60, 90, "Ancient Earth Bolt", new StatusEffect(13, 3, 10, 50, false), "weapons/", COLOR_DRAGON),	// Ancient Earth Bolt
		new Attack(30, 60, 90, "Ancient Fireball", new StatusEffect(9, 3, 10, 50, false), "weapons/", COLOR_DRAGON),	// Ancient Fireball
		new Attack(30, 60, 90, "Ancient Banish", new StatusEffect(11, 3, 10, 50, false), "weapons/", COLOR_DRAGON),		// Ancient Banish
		new Attack(200, 400, 20, "Summon Energy Dragon", "weapons/", COLOR_DRAGON),		// Summon Energy Dragon
		},
	};
	
	private static final Attack[][] playerSpecialAttacks = {
		
		//Knight
		
		{
		new Attack(0, 0, 100, "Block", 40, new StatusEffect(3, 2, 2, 100, true), "weapons/", COLOR_STOCK),	// Block
		},
		
		{
		new Attack(0, 0, 100, "Block", 50, new StatusEffect(3, 2, 4, 100, true), "weapons/", COLOR_COMMON),	// Block
		},
		
		{
		new Attack(0, 0, 100, "Block", 60, new StatusEffect(3, 2, 6, 100, true), "weapons/", COLOR_COMMON),	// Block
		},
		
		{
		new Attack(0, 0, 100, "Block", 60, new StatusEffect(3, 2, 8, 100, true), "weapons/", COLOR_COMMON),	// Block
		},
		
		{
		new Attack(0, 0, 100, "Block", 70, new StatusEffect(3, 2, 10, 100, true), "weapons/", COLOR_COMMON),	// Block
		new Attack(0, 0, 100, "Guard", 60, new StatusEffect(3, 5, 4, 100, true), "weapons/", COLOR_COMMON),	// Guard
		},
		
		{
		new Attack(0, 0, 100, "Block", 80, new StatusEffect(3, 2, 12, 100, true), "weapons/", COLOR_SEMIRARE),		// Block
		new Attack(0, 0, 100, "Guard", 65, new StatusEffect(3, 6, 4, 100, true), "weapons/", COLOR_SEMIRARE),			// Guard
		},
		
		{
		new Attack(0, 0, 100, "Block", 80, new StatusEffect(3, 2, 14, 100, true), "weapons/", COLOR_SEMIRARE),		// Block
		new Attack(0, 0, 100, "Guard", 70, new StatusEffect(3, 7, 4, 100, true), "weapons/", COLOR_SEMIRARE),			// Guard
		new Attack(0, 0, 100, "Shield", 100, new StatusEffect(3, 10, 4, 100, true), "weapons/", COLOR_SEMIRARE),		// Shield
		},
		
		{
		new Attack(0, 0, 100, "Block", 90, new StatusEffect(3, 2, 16, 100, true), "weapons/", COLOR_SEMIRARE),		// Block
		new Attack(0, 0, 100, "Guard", 70, new StatusEffect(3, 8, 4, 100, true), "weapons/", COLOR_SEMIRARE),			// Guard
		new Attack(0, 0, 100, "Shield", 110, new StatusEffect(3, 10, 4, 100, true), "weapons/", COLOR_RARE),			// Shield
		},
		
		{
		new Attack(0, 0, 100, "Block", 100, new StatusEffect(3, 2, 18, 100, true), "weapons/", COLOR_SEMIRARE),		// Block
		new Attack(0, 0, 100, "Guard", 70, new StatusEffect(3, 9, 4, 100, true), "weapons/", COLOR_SEMIRARE),			// Guard
		new Attack(0, 0, 100, "Frost Shield", 120, new StatusEffect(3, 10, 6, 100, true), "weapons/", COLOR_RARE),	// Frost Shield
		new Attack(0, 0, 100, "Frost Barrier", 120, new StatusEffect(19, 5, 2, 100, true), "weapons/", COLOR_RARE),	// Frost Barrier
		},
		
		{
		new Attack(0, 0, 100, "Block", 110, new StatusEffect(3, 2, 20, 100, true), "weapons/", COLOR_RARE),			// Block
		new Attack(0, 0, 100, "Guard", 75, new StatusEffect(3, 10, 4, 100, true), "weapons/", COLOR_RARE),			// Guard
		new Attack(0, 0, 100, "Fire Shield", 130, new StatusEffect(3, 10, 8, 100, true), "weapons/", COLOR_RARE),		// Fire Shield
		new Attack(0, 0, 100, "Fire Barrier", 130, new StatusEffect(19, 5, 3, 100, true), "weapons/", COLOR_RARE),	// Fire Barrier
		},
		
		{
		new Attack(0, 0, 100, "Block", 120, new StatusEffect(3, 2, 22, 100, true), "weapons/", COLOR_RARE),			// Block
		new Attack(0, 0, 100, "Guard", 80, new StatusEffect(3, 12, 6, 100, true), "weapons/", COLOR_RARE),			// Guard
		new Attack(0, 0, 100, "Shield", 140, new StatusEffect(3, 10, 10, 100, true), "weapons/", COLOR_RARE),			// Shield
		new Attack(0, 0, 100, "Barrier", 140, new StatusEffect(19, 5, 4, 100, true), "weapons/", COLOR_RARE),			// Barrier
		},
		
		{
		new Attack(0, 0, 100, "Block", 130, new StatusEffect(3, 2, 24, 100, true), "weapons/", COLOR_RARE),			// Block
		new Attack(0, 0, 100, "Guard", 85, new StatusEffect(3, 14, 6, 100, true), "weapons/", COLOR_RARE),			// Guard
		new Attack(0, 0, 100, "Shield", 150, new StatusEffect(3, 10, 12, 100, true), "weapons/", COLOR_RARE),			// Shield
		new Attack(0, 0, 100, "Barrier", 150, new StatusEffect(19, 5, 5, 100, true), "weapons/", COLOR_RARE),			// Barrier
		},
		
		{
		new Attack(0, 0, 100, "Block", 140, new StatusEffect(3, 2, 26, 100, true), "weapons/", COLOR_RARE),			// Block
		new Attack(0, 0, 100, "Guard", 90, new StatusEffect(3, 16, 6, 100, true), "weapons/", COLOR_RARE),			// Guard
		new Attack(0, 0, 100, "Shield", 160, new StatusEffect(3, 10, 14, 100, true), "weapons/", COLOR_LEGENDARY),	// Shield
		new Attack(0, 0, 100, "Dragon Bulwark", 200, new StatusEffect[]{new StatusEffect(3, 4, 10, 90, true),
			new StatusEffect(1, 4, 40, 90, true), new StatusEffect(4, 4, 10, 90, true)}, "weapons/", COLOR_DRAGON),	// Dragon Bulwark
		},
		
		{
		new Attack(0, 0, 100, "Block", 150, new StatusEffect(3, 2, 30, 100, true), "weapons/", COLOR_LEGENDARY),		// Block
		new Attack(0, 0, 100, "Guard", 95, new StatusEffect(3, 20, 6, 100, true), "weapons/", COLOR_LEGENDARY),		// Guard
		new Attack(0, 0, 100, "Shield", 170, new StatusEffect(3, 10, 18, 100, true), "weapons/", COLOR_LEGENDARY),	// Shield
		new Attack(0, 0, 100, "Barrier", 170, new StatusEffect(19, 5, 8, 100, true), "weapons/", COLOR_LEGENDARY),	// Barrier
		new Attack(0, 0, 100, "Bulwark", 200, new StatusEffect[]{new StatusEffect(3, 4, 10, 90, true),
			new StatusEffect(4, 4, 10, 90, true)}, "weapons/", COLOR_LEGENDARY),										// Bulwark
		},
		
		{
		new Attack(0, 0, 100, "Block", 160, new StatusEffect(3, 2, 35, 100, true), "weapons/", COLOR_LEGENDARY),		// Block
		new Attack(0, 0, 100, "Guard", 100, new StatusEffect(3, 22, 8, 100, true), "weapons/", COLOR_LEGENDARY),		// Guard
		new Attack(0, 0, 100, "Shield", 180, new StatusEffect(3, 10, 20, 100, true), "weapons/", COLOR_LEGENDARY),	// Shield
		new Attack(0, 0, 100, "Barrier", 180, new StatusEffect(19, 5, 9, 100, true), "weapons/", COLOR_LEGENDARY),	// Void Barrier
		new Attack(0, 0, 100, "Bulwark", 200, new StatusEffect[]{new StatusEffect(3, 4, 10, 90, true),
			new StatusEffect(4, 4, 10, 90, true), new StatusEffect(13, 4, 15, 50, false)}, "weapons/", COLOR_LEGENDARY),	// Oblivion Bulwark
		},
		
		{
		new Attack(0, 0, 100, "Block", 170, new StatusEffect(3, 2, 40, 100, true), "weapons/", COLOR_LEGENDARY),				// Block
		new Attack(0, 0, 100, "Guard", 100, new StatusEffect(3, 24, 8, 100, true), "weapons/", COLOR_LEGENDARY),				// Guard
		new Attack(0, 0, 100, "Shield Summon", 190, new StatusEffect(3, 10, 24, 100, true), "weapons/", COLOR_LEGENDARY),		// Shield Summon
		new Attack(0, 0, 100, "Colossal Barrier", 200, new StatusEffect(19, 5, 10, 100, true), "weapons/", COLOR_DRAGON),		// Colossal Barrier
		new Attack(0, 0, 100, "Colossal Buffer", 200, new StatusEffect(19, 2, 20, 100, true), "weapons/", COLOR_DRAGON),		// Colossal Buffer
		new Attack(0, 0, 100, "Summon Inorexite Dragon", 300, new StatusEffect[]{new StatusEffect(3, 4, 10, 60, true),
			new StatusEffect(1, 4, 40, 60, true), new StatusEffect(4, 4, 10, 60, true),
			new StatusEffect(13, 4, 40, 40, false), new StatusEffect(19, 4, 5, 60, true)}, "weapons/", COLOR_DRAGON),			// Summon Inorexite Dragon
		},
		
		
		//Archer
		
		{
		new Attack(20, 30, 100, "Flint Arrow Snipe", 50, "weapons/", COLOR_STOCK),		// Flint Arrow Snipe
		},
		
		{
		new Attack(30, 40, 100, "Iron Arrow Snipe", 50, "weapons/", COLOR_COMMON),		// Iron Arrow Snipe
		},
		
		{
		new Attack(40, 50, 100, "Steel Arrow Snipe", 55, "weapons/", COLOR_COMMON),		// Steel Arrow Snipe
		},
		
		{
		new Attack(50, 60, 100, "Silver Arrow Snipe", 60, "weapons/", COLOR_COMMON),		// Silver Arrow Snipe
		},
		
		{
		new Attack(60, 70, 100, "Golden Arrow Snipe", 65, "weapons/", COLOR_COMMON),		// Golden Arrow Snipe
		new Attack(20, 60, 80, "Golden Doubleshot", 40, "weapons/", COLOR_COMMON),		// Golden Doubleshot
		},
		
		{
		new Attack(70, 80, 100, "Gem Arrow Snipe", 70, "weapons/", COLOR_SEMIRARE),		// Gem Arrow Snipe
		new Attack(30, 70, 80, "Gem Doubleshot", 50, "weapons/", COLOR_SEMIRARE),			// Gem Doubleshot
		},
		
		{
		new Attack(80, 90, 100, "Shadow Snipe", 75, "weapons/", COLOR_SEMIRARE),											// Shadow Arrow Snipe
		new Attack(40, 80, 80, "Shadow Doubleshot", 60, "weapons/", COLOR_SEMIRARE),										// Shadow Doubleshot
		new Attack(10, 30, 80, "Shadow Storm", 80, new StatusEffect(18, 4, 5, 80, false), "weapons/", COLOR_SEMIRARE),	// Shadow Storm
		},
		
		{
		new Attack(90, 100, 100, "Bluesteel Arrow Snipe", 80, "weapons/", COLOR_SEMIRARE),								// Bluesteel Arrow Snipe
		new Attack(50, 90, 80, "Bluesteel Doubleshot", 70, "weapons/", COLOR_SEMIRARE),									// Bluesteel Doubleshot
		new Attack(20, 80, 80, "Arrow Storm", 90, new StatusEffect(18, 4, 5, 80, false), "weapons/", COLOR_RARE),			// Arrow Storm
		},
		
		{
		new Attack(100, 110, 100, "Ice Arrow Snipe", 85, "weapons/", COLOR_SEMIRARE),										// Ice Arrow Snipe
		new Attack(60, 100, 80, "Ice Doubleshot", 80, "weapons/", COLOR_SEMIRARE),										// Ice Doubleshot
		new Attack(30, 90, 80, "Ice Storm", 100, new StatusEffect(18, 4, 5, 80, false), "weapons/", COLOR_RARE),			// Ice Storm
		new Attack(120, 160, 100, "Power Snipe", 120, new StatusEffect(13, 4, 5, 75, false), "weapons/", COLOR_RARE),		// Power Snipe
		},
		
		{
		new Attack(110, 120, 100, "Fire Arrow Snipe", 90, "weapons/", COLOR_RARE),										// Fire Arrow Snipe
		new Attack(70, 110, 80, "Fire Doubleshot", 90, "weapons/", COLOR_RARE),											// Fire Doubleshot
		new Attack(40, 100, 80, "Fire Storm", 110, new StatusEffect(18, 5, 5, 80, false), "weapons/", COLOR_RARE),		// Fire Storm
		new Attack(130, 170, 100, "Power Snipe", 130, new StatusEffect(13, 4, 5, 75, false), "weapons/", COLOR_RARE),		// Power Snipe
		},
		
		{
		new Attack(120, 130, 100, "Ironrock Arrow Snipe", 95, "weapons/", COLOR_RARE),									// Ironrock Arrow Snipe
		new Attack(80, 120, 80, "Ironrock Doubleshot", 100, "weapons/", COLOR_RARE),										// Ironrock Doubleshot
		new Attack(40, 100, 80, "Arrow Storm", 120, new StatusEffect(18, 6, 5, 80, false), "weapons/", COLOR_RARE),		// Arrow Storm
		new Attack(140, 180, 100, "Power Snipe", 140, new StatusEffect(13, 4, 5, 75, false), "weapons/", COLOR_RARE),		// Power Snipe
		},
		
		{
		new Attack(130, 140, 100, "Magic Arrow Snipe", 100, "weapons/", COLOR_RARE),										// Magic Arrow Snipe
		new Attack(90, 130, 80, "Magic Doubleshot", 120, "weapons/", COLOR_RARE),											// Magic Doubleshot
		new Attack(50, 110, 80, "Arrow Storm", 130, new StatusEffect(18, 6, 5, 80, false), "weapons/", COLOR_RARE),		// Arrow Storm
		new Attack(150, 190, 100, "Power Snipe", 150, new StatusEffect(13, 4, 5, 75, false), "weapons/", COLOR_RARE),		// Power Snipe
		},
		
		{
		new Attack(140, 150, 100, "Dragon Arrow Snipe", 105, "weapons/", COLOR_RARE),											// Dragon Arrow Snipe
		new Attack(90, 150, 80, "Dragon Tripleshot", 130, "weapons/", COLOR_RARE),											// Dragon Tripleshot
		new Attack(50, 110, 80, "Dragon Storm", 140, new StatusEffect(18, 7, 5, 80, false), "weapons/", COLOR_LEGENDARY),		// Dragon Storm
		new Attack(160, 200, 100, "Power Snipe", 160, new StatusEffect(13, 5, 5, 75, false), "weapons/", COLOR_LEGENDARY),	// Power Snipe
		},
		
		{
		new Attack(150, 160, 100, "Inorexite Arrow Snipe", 110, "weapons/", COLOR_LEGENDARY),									// Inorexite Arrow Snipe
		new Attack(100, 150, 80, "Inorexite Tripleshot", 140, "weapons/", COLOR_LEGENDARY),									// Inorexite Tripleshot
		new Attack(60, 120, 80, "Arrow Storm", 150, new StatusEffect(18, 7, 5, 80, false), "weapons/", COLOR_LEGENDARY),		// Arrow Storm
		new Attack(170, 210, 100, "Power Snipe", 170, new StatusEffect(13, 5, 5, 75, false), "weapons/", COLOR_LEGENDARY),	// Power Snipe
		new Attack(50, 100, 60, "Power Disable", 120, new StatusEffect[]{new StatusEffect(8, 4, 10, 100, false),
		new StatusEffect(11, 4, 4, 100, false), new StatusEffect(13, 4, 15, 100, false)}, "weapons/", COLOR_LEGENDARY),		// Power Disable
		},
		
		{
		new Attack(160, 180, 100, "Oblivion Arrow Snipe", 120, "weapons/", COLOR_LEGENDARY),									// Oblivion Arrow Snipe
		new Attack(100, 200, 80, "Oblivion Multishot", 150, "weapons/", COLOR_LEGENDARY),										// Oblivion Multishot
		new Attack(60, 120, 80, "Void Storm", 160, new StatusEffect(18, 8, 5, 80, false), "weapons/", COLOR_LEGENDARY),		// Void Storm
		new Attack(180, 220, 100, "Power Snipe", 180, new StatusEffect(13, 5, 5, 75, false), "weapons/", COLOR_LEGENDARY),	// Power Snipe
		new Attack(50, 100, 60, "Power Nullify", 130, new StatusEffect[]{new StatusEffect(8, 4, 15, 100, false),
		new StatusEffect(11, 4, 6, 100, false), new StatusEffect(13, 4, 20, 100, false)}, "weapons/", COLOR_LEGENDARY),		// Power Nullify
		},
		
		{
		new Attack(180, 200, 100, "Photon Snipe", 130, "weapons/", COLOR_LEGENDARY),											// Photon Snipe
		new Attack(125, 200, 80, "Galaxian Multishot", 160, "weapons/", COLOR_LEGENDARY),										// Galaxian Multishot
		new Attack(80, 120, 80, "Stardust Storm", 170, new StatusEffect(18, 8, 5, 80, false), "weapons/", COLOR_LEGENDARY),	// Stardust Storm
		new Attack(200, 250, 100, "Dragon Snipe", 200, new StatusEffect(13, 6, 5, 75, false), "weapons/", COLOR_DRAGON),		// Dragon Snipe
		new Attack(75, 125, 60, "Power Crunch", 150, new StatusEffect[]{new StatusEffect(8, 4, 20, 100, false),
		new StatusEffect(11, 4, 8, 100, false), new StatusEffect(13, 4, 25, 100, false)}, "weapons/", COLOR_DRAGON),			// Power Crunch
		new Attack(250, 350, 90, "Summon Dragon", 250, new StatusEffect[]{new StatusEffect(15, 5, 100, 15, true),
		new StatusEffect(8, 10, 5, 80, false), new StatusEffect(19, 4, 5, 80, true)}, "weapons/", COLOR_DRAGON),				// Nova Dragon Summon
		},
		
		
		//Mage
		
		{
		new Attack(15, 25, 80, "Magic Turret", 40, "weapons/", COLOR_STOCK),		// Magic Turret
		},
		
		{
		new Attack(20, 30, 80, "Magic Missile", 50, "weapons/", COLOR_COMMON),	// Magic Missile
		},
		
		{	
		new Attack(25, 35, 80, "Light Stone Summon", 60, new StatusEffect(13, 5, 3, 75, false), "weapons/", COLOR_COMMON),		// Light Stone Summon
		},
		
		{
		new Attack(30, 40, 80, "Light Fireball", 70, new StatusEffect(9, 5, 5, 75, false), "weapons/fire02.wav", COLOR_COMMON),	// Light Fireball
		},
		
		{
		new Attack(35, 45, 80, "Light Banish", 160, new StatusEffect[]{new StatusEffect(11, 3, 5, 75, false),
		new StatusEffect(5, 3, 1, 100, true)}, "weapons/", COLOR_COMMON),															// Light Banish
		},
		
		{
		new Attack(50, 60, 80, "Magic Star", 90, "weapons/", COLOR_SEMIRARE),														// Magic Star
		new Attack(20, 100, 80, "Magic Nebula", 60, "weapons/", COLOR_SEMIRARE),													// Magic Nebula
		},
		
		{
		new Attack(55, 65, 80, "Stone Summon", 100, new StatusEffect(13, 5, 8, 75, false), "weapons/", COLOR_SEMIRARE),			// Stone Summon
		new Attack(20, 30, 90, "Landslide Cast", 120, new StatusEffect(13, 5, 15, 75, false), "weapons/", COLOR_SEMIRARE),		// Landslide Cast
		},
		
		{
		new Attack(60, 70, 80, "Fireball", 110, new StatusEffect(9, 5, 10, 75, false), "weapons/fire02.wav", COLOR_SEMIRARE),			// Fireball
		new Attack(25, 35, 90, "Eruption Cast", 130, new StatusEffect(9, 5, 18, 75, false), "weapons/fire02.wav", COLOR_SEMIRARE),	// Eruption Cast
		new Attack(50, 100, 75, "Flame Spire", 150, new StatusEffect(9, 5, 15, 80, false), "weapons/fire02.wav", COLOR_RARE),			// Flame Spire
		},
		
		{
		new Attack(65, 75, 80, "Banish", 240, new StatusEffect[]{new StatusEffect(11, 3, 10, 75, false),
		new StatusEffect(5, 3, 1, 100, true)}, "weapons/", COLOR_SEMIRARE),															// Banish
		new Attack(30, 40, 90, "Energy Siphon", 140, new StatusEffect(11, 5, 4, 75, false), "weapons/", COLOR_SEMIRARE),				// Energy Siphon
		new Attack(60, 110, 75, "Dark Depletion", 160, new StatusEffect(11, 5, 3, 80, false), "weapons/", COLOR_RARE),				// Dark Depletion
		},
		
		{
		new Attack(80, 100, 80, "Magic Star", 130, "weapons/", COLOR_RARE),															// Magic Star
		new Attack(60, 140, 80, "Magic Nebula", 100, "weapons/", COLOR_RARE),															// Magic Nebula
		new Attack(100, 180, 80, "Galaxy Cast", 200, "weapons/", COLOR_RARE),															// Galaxy Cast
		new Attack(0, 0, 95, "Arcane Refreshment", 0, new StatusEffect[]{new StatusEffect(2, 4, 20, 100, true),
		new StatusEffect(14, 4, 20, 100, true), new StatusEffect(5, 6, 1, 100, true)}, "weapons/", COLOR_RARE),						// Arcane Refreshment
		},
		
		{
		new Attack(75, 85, 80, "Boulder Summon", 140, new StatusEffect(13, 5, 20, 75, false), "weapons/", COLOR_RARE),				// Boulder Summon
		new Attack(40, 50, 90, "Landslide Cast", 160, new StatusEffect(13, 5, 25, 75, false), "weapons/", COLOR_RARE),				// Landslide Cast
		new Attack(65, 115, 75, "Earthquake Cast", 180, new StatusEffect(13, 5, 15, 80, false), "weapons/", COLOR_RARE),				// Earthquake Cast
		new Attack(0, 0, 75, "Reformation", 180, new StatusEffect[]{new StatusEffect(3, 3, 15, 100, true),
		new StatusEffect(19, 3, 4, 100, true)}, "weapons/", COLOR_RARE),																// Reformation
		},
		
		{
		new Attack(80, 90, 80, "Solar Flare", 150, new StatusEffect(9, 5, 15, 75, false), "weapons/fire02.wav", COLOR_RARE),			// Solar Flare
		new Attack(45, 55, 90, "Eruption Cast", 170, new StatusEffect(9, 5, 25, 75, false), "weapons/fire02.wav", COLOR_RARE),		// Eruption Cast
		new Attack(70, 120, 75, "Flame Tower", 190, new StatusEffect(9, 5, 10, 75, false), "weapons/fire02.wav", COLOR_RARE),			// Flame Tower
		new Attack(0, 0, 75, "Re-ignition", 190, new StatusEffect[]{new StatusEffect(1, 3, 25, 100, true),
		new StatusEffect(4, 3, 10, 100, true)}, "weapons/fire02.wav", COLOR_RARE),													// Re-ignition
		},
		
		{
		new Attack(85, 95, 80, "Heavy Banish", 320, new StatusEffect[]{new StatusEffect(11, 3, 15, 75, false),
		new StatusEffect(5, 3, 1, 100, true)}, "weapons/", COLOR_LEGENDARY),															// Heavy Banish
		new Attack(50, 55, 90, "Energy Siphon", 180, new StatusEffect(11, 5, 6, 75, false), "weapons/", COLOR_RARE),					// Energy Siphon
		new Attack(75, 125, 75, "Dark Depletion", 200, new StatusEffect(11, 5, 4, 80, false), "weapons/", COLOR_RARE),				// Dark Depletion
		new Attack(0, 0, 75, "Necrotic Transfer", 200, new StatusEffect[]{new StatusEffect(8, 4, 25, 100, false),
		new StatusEffect(1, 4, 25, 100, false), new StatusEffect(5, 5, 1, 100, true)}, "weapons/", COLOR_LEGENDARY),					// Necrotic Transfer
		},
		
		{
		new Attack(90, 110, 80, "Magic Nebula", 170, "weapons/", COLOR_LEGENDARY),													// Magic Nebula
		new Attack(70, 150, 80, "Galaxy Cast", 140, "weapons/", COLOR_LEGENDARY),														// Galaxy Cast
		new Attack(120, 200, 80, "Cluster Cast", 240, "weapons/", COLOR_LEGENDARY),													// Cluster Cast
		new Attack(120, 260, 80, "Nova Cast", 270, "weapons/", COLOR_LEGENDARY),														// Nova Cast
		new Attack(0, 0, 95, "Arcane Refreshment", 0, new StatusEffect[]{new StatusEffect(2, 4, 25, 100, true),
		new StatusEffect(14, 4, 25, 100, true), new StatusEffect(5, 6, 1, 100, true)}, "weapons/", COLOR_LEGENDARY),					// Arcane Refreshment
		},
		
		{
		new Attack(120, 150, 80, "Magic Nebula", 200, "weapons/", COLOR_LEGENDARY),													// Magic Nebula
		new Attack(100, 200, 80, "Galaxy Cast", 180, "weapons/", COLOR_LEGENDARY),													// Galaxy Cast
		new Attack(180, 250, 80, "Supercluster Cast", 280, "weapons/", COLOR_LEGENDARY),												// Supercluster Cast
		new Attack(180, 300, 80, "Supernova Cast", 310, "weapons/", COLOR_LEGENDARY),													// Supernova Cast
		new Attack(0, 0, 95, "Arcane Refreshment", 0, new StatusEffect[]{new StatusEffect(2, 5, 30, 100, true),
		new StatusEffect(14, 5, 30, 100, true), new StatusEffect(5, 7, 1, 100, true)}, "weapons/", COLOR_LEGENDARY),					// Arcane Refreshment
		},
		
		{
		new Attack(120, 140, 80, "Asteroid Summon", 200, new StatusEffect(13, 5, 25, 75, false), "weapons/", COLOR_LEGENDARY),			// Asteroid Summon
		new Attack(80, 100, 90, "Core Blast", 160, new StatusEffect(13, 5, 40, 75, false), "weapons/", COLOR_LEGENDARY),					// Core Blast
		new Attack(100, 160, 75, "Geological Destruction", 180, new StatusEffect(13, 5, 20, 80, false), "weapons/", COLOR_DRAGON),		// Geologica Destruction
		new Attack(0, 0, 75, "Carbon Defense", 200, new StatusEffect[]{new StatusEffect(3, 4, 30, 100, true),
		new StatusEffect(19, 4, 8, 100, true)}, "weapons/", COLOR_DRAGON),																// Carbon Defense
		new Attack(200, 300, 75, "Summon Earth Core Dragon", 300, new StatusEffect[]{new StatusEffect(3, 4, 30, 80, true),
		new StatusEffect(13, 4, 30, 80, false), new StatusEffect(19, 4, 5, 60, true)}, "weapons/", COLOR_DRAGON),							// Summon Earth Core Dragon
		},
		
		{
		new Attack(140, 160, 80, "Solar Flame", 210, new StatusEffect(9, 5, 20, 75, false), "weapons/fire02.wav", COLOR_LEGENDARY),		// Solar Flame
		new Attack(90, 120, 90, "Inferno Collapse", 170, new StatusEffect(9, 5, 30, 75, false), "weapons/fire02.wav", COLOR_LEGENDARY),	// Inferno Collapse
		new Attack(120, 180, 75, "Life Meltdown", 190, new StatusEffect(9, 5, 15, 75, false), "weapons/fire02.wav", COLOR_DRAGON),		// Life Meltdown
		new Attack(0, 0, 75, "Phoenix Infinity", 250, new StatusEffect[]{new StatusEffect(1, 3, 100, 100, true),
		new StatusEffect(4, 3, 20, 100, true)}, "weapons/fire02.wav", COLOR_DRAGON),														// Phoenix Infinity
		new Attack(210, 350, 75, "Summon Fire Hydra", 350, new StatusEffect[]{new StatusEffect(9, 10, 8, 80, false),
		new StatusEffect(4, 4, 10, 100, true), new StatusEffect(19, 4, 5, 60, true)}, "weapons/fire02.wav", COLOR_DRAGON),				// Summon Fire Hydra
		},
		
		{
		new Attack(160, 180, 80, "Infinite Banish", 450, new StatusEffect[]{new StatusEffect(11, 3, 20, 75, false),
		new StatusEffect(5, 4, 1, 100, true)}, "weapons/", COLOR_DRAGON),																	// Infinite Banish
		new Attack(100, 130, 90, "Energy Siphon", 220, new StatusEffect(11, 5, 8, 75, false), "weapons/", COLOR_LEGENDARY),				// Energy Siphon
		new Attack(120, 200, 75, "Dark Depletion", 240, new StatusEffect(11, 5, 4, 80, false), "weapons/", COLOR_LEGENDARY),				// Dark Depletion
		new Attack(0, 0, 75, "Necrotic Transfer", 240, new StatusEffect[]{new StatusEffect(8, 4, 40, 100, false),
		new StatusEffect(1, 4, 40, 100, false), new StatusEffect(5, 6, 1, 100, true)}, "weapons/", COLOR_DRAGON),							// Necrotic Transfer
		new Attack(250, 400, 75, "Summon Void Dragon", 500, new StatusEffect[]{new StatusEffect(11, 5, 15, 90, false),
		new StatusEffect(11, 5, 10, 100, true), new StatusEffect(5, 7, 1, 100, true),
		new StatusEffect(19, 4, 5, 60, true)}, "weapons/", COLOR_DRAGON),																	// Summon Void Dragon
		},
		
		{
		new Attack(200, 250, 80, "Andromeda Magic", 350, "weapons/", COLOR_LEGENDARY),													// Andromeda Magic
		new Attack(150, 350, 70, "Virgo Supercast", 325, "weapons/", COLOR_LEGENDARY),													// Virgo Supercast
		new Attack(180, 300, 80, "Hypernova Cast", 280, "weapons/", COLOR_LEGENDARY),														// Hypernova Cast
		new Attack(200, 400, 80, "Magic Singularity", 400, "weapons/", COLOR_DRAGON),														// Magic Singularity
		new Attack(0, 0, 95, "Nebula Absorption", 0, new StatusEffect[]{new StatusEffect(2, 6, 40, 100, true),
		new StatusEffect(14, 6, 100, 100, true), new StatusEffect(5, 8, 1, 100, true)}, "weapons/", COLOR_DRAGON),						// Nebula Absorption
		new Attack(300, 500, 60, "Summon Nova Dragon", 500, new StatusEffect[]{new StatusEffect(18, 5, 15, 45, false),
		new StatusEffect(5, 8, 1, 100, true)}, "weapons/", COLOR_DRAGON),																	// Summon Nova Dragon
		}
	};
	
	private static final Attack[][] enemyAttacks = {
		
		//Tutorial
		//Eagle
		{
			new Attack(8, 12, 80, "Peck", 60, "enemy/attacks/", COLOR_STOCK),			// Peck
			new Attack(12, 14, 60, "Swoop", 40, "enemy/attacks/", COLOR_STOCK),		// Swoop
		},
		
		//Oakorn Forest
		
		//Jade Snake
		{
			new Attack(10, 15, 80, "Bite", 70, "enemy/attacks/", COLOR_COMMON),												// Bite
			new Attack(10, 15, 60, "Venom Bite", 30, new StatusEffect(10, 9, 1, 80, true), "enemy/attacks/", COLOR_COMMON),	// Venom Bite
		},
		
		//Giant Spider
		{
			new Attack(10, 15, 80, "Bite", 70, "enemy/attacks/", COLOR_COMMON),												// Bite
			new Attack(5, 20, 90, "Web Spin", 30, new StatusEffect(18, 4, 3, 60, true), "enemy/attacks/", COLOR_COMMON),		// Web Spin
		},
		
		//Bear
		{
			new Attack(10, 15, 80, "Claw", 49, "enemy/attacks/", COLOR_COMMON),				// Claw
			new Attack(15, 20, 70, "Strong Bite", 51, "enemy/attacks/", COLOR_COMMON),		// Strong Bite
		},
		
		//Goblin Minion
		{
			new Attack(14, 18, 70, "Punch", 50, "enemy/attacks/", COLOR_COMMON),				// Punch
			new Attack(16, 20, 70, "Kick", 30, "enemy/attacks/", COLOR_COMMON),				// Kick
		},
		
		//Goblin Scout
		{
			new Attack(14, 18, 70, "Punch", 50, "enemy/attacks/", COLOR_COMMON),				// Punch
			new Attack(16, 20, 70, "Kick", 30, "enemy/attacks/", COLOR_COMMON),				// Kick
			new Attack(10, 12, 90, "Goblin Arrow", 50, "enemy/attacks/", COLOR_COMMON),		// Goblin Arrow
		},
		
		//Goblin Warrior
		{
			new Attack(14, 16, 70, "Cut", 60, "enemy/attacks/sword02.wav", COLOR_COMMON),		// Cut
			new Attack(18, 22, 60, "Slash", 40, "enemy/attacks/sword03.wav", COLOR_COMMON),	// Slash
		},
		
		//Goblin Leader
		{
			new Attack(14, 20, 90, "Goblin Arrow", 30, "enemy/attacks/", COLOR_COMMON),		// Goblin Arrow
			new Attack(18, 22, 70, "Cut", 40, "enemy/attacks/sword02.wav", COLOR_COMMON),		// Cut
			new Attack(22, 26, 60, "Slash", 30, new StatusEffect(8, 4, 5, 80, true), "enemy/attacks/sword03.wav", COLOR_COMMON),	// Slash
		},
		
		
		//Spectral Forest
		
		//Ghost
		{
			new Attack(8, 14, 70, "Ghost Punch", 50, "enemy/attacks/", COLOR_COMMON),			// Ghost Punch
			new Attack(8, 20, 60, "Duplicate", 30, "enemy/attacks/", COLOR_COMMON),			// Duplicate
			new Attack(0, 0, 60, "Magic Steal", 20, new StatusEffect(12, 3, 10, 100, true), "enemy/attacks/", COLOR_COMMON),	//Magic Steal
		},
		
		//Mini Ghost
		{
			new Attack(4, 16, 70, "Rapid Ghost Punch", 60, "enemy/attacks/", COLOR_COMMON),	// Rapid Ghost Punch
			new Attack(6, 12, 60, "Spectre", 40, new StatusEffect(11, 3, 4, 50, true), "enemy/attacks/", COLOR_COMMON),	// Spectre
		},
		
		//Wolf
		{
			new Attack(6, 20, 60, "Bite", 49, "enemy/attacks/", COLOR_COMMON),		// Bite
			new Attack(8, 12, 80, "Scratch", 51, "enemy/attacks/", COLOR_COMMON),		// Scratch
		},
		
		//Dark Wisp
		{
			new Attack(5, 15, 75, "Dark Magic", 65, "enemy/attacks/", COLOR_COMMON),	// Dark Magic
			new Attack(0, 0, 100, "Confusion", 35, new StatusEffect(18, 5, 3, 80, true), "enemy/attacks/", COLOR_COMMON), // Confusion
		},
		
		//Haunted Ent
		{
			new Attack(15, 20, 75, "Dark Magic", 40, "enemy/attacks/", COLOR_COMMON),		// Dark Magic
			new Attack(20, 30, 75, "Wither", 30, "enemy/attacks/", COLOR_COMMON),			// Wither
			new Attack(0, 0, 100, "Magic Sap", 15, new StatusEffect(12, 3, 20, 90, true), "enemy/attacks/", COLOR_COMMON),		// Magic Sap
			new Attack(0, 0, 100, "Dark Synthesis", 15, new StatusEffect(1, 4, 5, 90, false), "enemy/attacks/", COLOR_COMMON),	// Dark Synthesis
		},
		
		//Arcane Spirit
		{
			new Attack(10, 25, 80, "Spirit Shock", 30, "enemy/attacks/", COLOR_COMMON),		// Spirit Shock
			new Attack(20, 50, 60, "Spirit Magic", 30, "enemy/attacks/", COLOR_COMMON),		// Spirit Magic
			new Attack(5, 15, 80, "Phase", 10, new StatusEffect(18, 2, 8, 75, true), "enemy/attacks/", COLOR_COMMON),			// Phase
			new Attack(0, 0, 100, "Absorption", 10, new StatusEffect(20, 1, 5, 80, false), "enemy/attacks/", COLOR_COMMON),		// Absorption
		},
		
		//Spirit Guardian
		{
			new Attack(10, 25, 80, "Spirit Shock", 35, "enemy/attacks/", COLOR_COMMON),		// Spirit Shock
			new Attack(20, 50, 60, "Spirit Magic", 35, "enemy/attacks/", COLOR_COMMON),		// Spirit Magic
			new Attack(5, 75, 90, "Dark Cloud", 14, "enemy/attacks/", COLOR_SEMIRARE),		// Dark Cloud
			new Attack(10, 30, 80, "Phase", 4, new StatusEffect(18, 2, 8, 75, true), "enemy/attacks/", COLOR_COMMON),			// Phase
			new Attack(0, 0, 100, "Absorption", 4, new StatusEffect(20, 1, 10, 80, false), "enemy/attacks/", COLOR_COMMON),		// Absorption
			new Attack(0, 0, 100, "Spectral Defense", 4, new StatusEffect(3, 5, 5, 70, false), "enemy/attacks/", COLOR_SEMIRARE),		// Spectral Defense
			new Attack(0, 0, 100, "Regeneration Magic", 4, new StatusEffect(1, 5, 5, 70, false), "enemy/attacks/", COLOR_SEMIRARE),		// Regeneration Magic
		},
		
		//Nightmare Castle
		
		//Ghost Knight
		{
			new Attack(25, 30, 70, "Cut", 40, "enemy/attacks/sword02.wav", COLOR_COMMON),		// Cut
			new Attack(30, 35, 60, "Slash", 40, "enemy/attacks/sword03.wav", COLOR_COMMON),	// Slash
			new Attack(0, 0, 100, "Block", 20, new StatusEffect(3, 2, 10, 100, false), "enemy/attacks/", COLOR_COMMON),	// Block
		},
		
		//Ghost Archer
		{
			new Attack(20, 25, 70, "Standard Arrow", 40, "enemy/attacks/", COLOR_COMMON),		// Standard Arrow
			new Attack(20, 30, 65, "Snipe", 40, "enemy/attacks/", COLOR_COMMON),				// Snipe
			new Attack(30, 35, 80, "Superior Snipe", 20, new StatusEffect(8, 4, 5, 80, true), "enemy/attacks/sword02.wav", COLOR_COMMON),		// Superior Snipe
		},
		
		//Ghost Priest
		{
			new Attack(15, 35, 75, "Ghost Missile", 45, "enemy/attacks/", COLOR_COMMON),		// Ghost Missile
			new Attack(5, 15, 60, "Purify", 35, new StatusEffect(1, 3, 10, 90, false), "enemy/attacks/", COLOR_COMMON),			// Purify
			new Attack(30, 35, 70, "Exorcism", 20, new StatusEffect(12, 5, 10, 90, true), "enemy/attacks/", COLOR_COMMON),		// Exorcism
		},
		
		//Ghost Jester
		{
			new Attack(5, 40, 80, "Strange Magic", 70, "enemy/attacks/", COLOR_COMMON),		// Strange Magic
			new Attack(1, 3, 80, "Delusion", 15, new StatusEffect(18, 2, 5, 80, true), "enemy/attacks/", COLOR_COMMON),			// Delusion
			new Attack(0, 0, 100, "Ghost Dance", 15, new StatusEffect[]{new StatusEffect(1, 5, 10, 75, false),
				new StatusEffect(4, 5, 5, 75, false)}, "enemy/attacks/", COLOR_COMMON),		// Ghost Dance
		},
		
		//Nightmare Spirit
		{
			new Attack(20, 40, 70, "Nightmare Magic", 40, "enemy/attacks/", COLOR_COMMON),	// Nightmare Magic
			new Attack(20, 60, 50, "Night Terror", 40, "enemy/attacks/", COLOR_COMMON),		// Night Terror
			new Attack(5, 50, 80, "Shadow Wave", 30, new StatusEffect(18, 2, 10, 80, true), "enemy/attacks/", COLOR_COMMON),		// Shadow Wave
			new Attack(0, 0, 100, "Shadow Shield", 30, new StatusEffect(3, 3, 10, 100, true), "enemy/attacks/", COLOR_COMMON),	// Shadow Shield
			new Attack(0, 0, 70, "Dark Regeneration", 30, new StatusEffect[]{new StatusEffect(1, 5, 20, 100, false),
				new StatusEffect(8, 5, 20, 100, true)}, "enemy/attacks/", COLOR_SEMIRARE),		// Dark Regeneration
		},
		
		//Ghost King
		{
			new Attack(70, 80, 50, "Dark Slash", 25, "enemy/attacks/", COLOR_COMMON),													// Dark Slash
			new Attack(10, 80, 80, "Night Arrow", 25, "enemy/attacks/", COLOR_COMMON),													// Night Arrow
			new Attack(40, 60, 80, "Eclipse Magic", 25, "enemy/attacks/", COLOR_COMMON),												// Eclipse Magic
			new Attack(0, 0, 100, "Spirit Shield", 10, new StatusEffect[]{new StatusEffect(3, 5, 10, 100, false),
				new StatusEffect(19, 5, 3, 100, false)}, "enemy/attacks/", COLOR_COMMON),												// Spirit Shield
			new Attack(0, 0, 100, "Ancestral Regeneration", 10, new StatusEffect(1, 5, 20, 100, false), "enemy/attacks/", COLOR_COMMON),// Ancestral Regeneration
			new Attack(60, 80, 75, "Twilight Execution", 5, new StatusEffect[]{new StatusEffect(9, 3, 15, 100, true),
				new StatusEffect(10, 3, 15, 100, true)}, "enemy/attacks/", COLOR_SEMIRARE),												// Twilight Execution
		}
	};
	
	
	//Items
	private static final Item[] normalWeaponItems = {
			
			//Knight
			new Item(0, 0, "Iron Sword",		 "an Iron Sword",								1, playerNormalAttacks[0], "items/metal02.wav", COLOR_STOCK),			//0 - Iron Sword			(T1)
			new Item(0, 0, "Silver Sword", 		"a Silver Sword",								1, playerNormalAttacks[1], "items/metal02.wav", COLOR_COMMON),		//1 - Silver Sword			(T2)
			new Item(0, 0, "Golden Sword", 		"a Golden Sword",								1, playerNormalAttacks[2], "items/metal02.wav", COLOR_COMMON),		//2 - Golden Sword			(T3)
			new Item(0, 0, "Cutlass", 			"a Cutlass",									1, playerNormalAttacks[3], "items/metal02.wav", COLOR_COMMON),		//3 - Cutlass				(T4)
			new Item(0, 0, "Flame Sword", 		"a Flame Sword", 								1, playerNormalAttacks[4], "items/flame01.wav", COLOR_COMMON),		//4 - Flame Sword			(T5)
			new Item(0, 0, "Magic Sword", 		"a Magic Sword",								1, playerNormalAttacks[5], "items/metal02.wav", COLOR_SEMIRARE),		//5 - Magic Sword			(T6)
			new Item(0, 0, "Shadow Sword", 		"a Shadow Sword",								1, playerNormalAttacks[6], "items/metal02.wav", COLOR_SEMIRARE),		//6 - Shadow Steel Sword	(T7)
			new Item(0, 0, "Bluesteel Sword", 	"a Bluesteel Sword",							1, playerNormalAttacks[7], "items/metal02.wav", COLOR_SEMIRARE),		//7 - Bluesteel Sword		(T8)
			new Item(0, 0, "Ruby Sword",		 "a Ruby Sword",								1, playerNormalAttacks[8], "items/gem01.wav", COLOR_SEMIRARE),		//8 - Ruby Sword			(T9)
			new Item(0, 0, "Emerald Sword", 	"an Emerald Sword",								1, playerNormalAttacks[9], "items/gem01.wav", COLOR_RARE),			//9 - Emerald Sword			(T10)
			new Item(0, 0, "Ironrock Sword", 	"an Ironrock Sword",							1, playerNormalAttacks[10], "items/stone01.wav", COLOR_RARE),			//10 - Ironrock Sword		(T11)
			new Item(0, 0, "Diamond Sword", 	"a Diamond Sword",								1, playerNormalAttacks[11], "items/gem01.wav", COLOR_RARE),			//11 - Diamond Sword		(T12)
			new Item(0, 0, "Dragonstone Sword", "a Dragonstone Sword",							1, playerNormalAttacks[12], "items/stone01.wav", COLOR_RARE),			//12 - Dragonstone Sword	(T13)
			new Item(0, 0, "Inorexite Sword", 	"an Inorexite Sword",							1, playerNormalAttacks[13], "items/metal02.wav", COLOR_LEGENDARY),	//13 - Inorexite Sword		(T14)
			new Item(0, 0, "Starsword", 		"a Starsword",									1, playerNormalAttacks[14], "items/", COLOR_LEGENDARY),				//14 - Starsword			(T15)
			new Item(0, 0, "Dragon Scroll III",	"Dragon Scroll III: Arcane Summoning Sword",	1, playerNormalAttacks[15], "items/cloth01.wav", COLOR_DRAGON),		//15 - Dragon Scroll III: Arcane Summoning Sword
			
			
			//Archer
			new Item(0, 1, "Wooden Bow", 		"a Wooden Bow",							1, playerNormalAttacks[16], "items/wood01.wav", COLOR_STOCK),				//16 - Wooden Bow			(T1)
			new Item(0, 1, "Silver Bow", 		"a Silver Bow",							1, playerNormalAttacks[17], "items/metal01.wav", COLOR_COMMON),			//17 - Silver Bow			(T2)
			new Item(0, 1, "Golden Bow", 		"a Golden Bow",							1, playerNormalAttacks[18], "items/metal01.wav", COLOR_COMMON),			//18 - Golden Bow			(T3)
			new Item(0, 1, "Longbow", 			"a Longbow",							1, playerNormalAttacks[19], "items/wood01.wav", COLOR_COMMON),			//19 - Longbow				(T4)
			new Item(0, 1, "Sniping Bow", 		"a Sniping Bow",						1, playerNormalAttacks[20], "items/wood01.wav", COLOR_COMMON),			//20 - Sniping Bow			(T5)
			new Item(0, 1, "Magic Bow", 		"a Magic Bow",							1, playerNormalAttacks[21], "items/wood01.wav", COLOR_SEMIRARE),			//21 - Magic Bow			(T6)
			new Item(0, 1, "Shadow Steel Bow", 	"a Shadow Steel Bow",					1, playerNormalAttacks[22], "items/metal01.wav", COLOR_SEMIRARE),			//22 - Shadow Steel Bow		(T7)
			new Item(0, 1, "Bluesteel Bow", 	"a Bluesteel Bow",						1, playerNormalAttacks[23], "items/metal01.wav", COLOR_SEMIRARE),			//23 - Bluesteel Bow		(T8)
			new Item(0, 1, "Ice Bow", 			"an Ice Bow",							1, playerNormalAttacks[24], "items/", COLOR_SEMIRARE),					//24 - Ice Bow				(T9)
			new Item(0, 1, "Flame Bow", 		"a Flame Bow",							1, playerNormalAttacks[25], "items/flame01.wav", COLOR_RARE),				//25 - Flame Bow 			(T10)
			new Item(0, 1, "Ironrock Bow", 		"an Ironrock Bow",						1, playerNormalAttacks[26], "items/stone01.wav", COLOR_RARE),				//26 - Ironrock Bow			(T11)
			new Item(0, 1, "Magic Iron Bow", 	"a Magic Iron Bow",						1, playerNormalAttacks[27], "items/metal01.wav", COLOR_RARE),				//27 - Magic Iron Bow 		(T12)
			new Item(0, 1, "Dragon Bow", 		"a Dragon Bow",							1, playerNormalAttacks[28], "items/", COLOR_RARE),						//28 - Dragon Bow 			(T13)
			new Item(0, 1, "Inorexite Bow", 	"an Inorexite Bow",						1, playerNormalAttacks[29], "items/metal01.wav", COLOR_LEGENDARY),		//29 - Inorexite Bow 		(T14)
			new Item(0, 1, "Cosmic Arrow Bow", 	"a Cosmic Arrow Bow",					1, playerNormalAttacks[30], "items/", COLOR_LEGENDARY),					//30 - Cosmic Arrow Bow 	(T15)
			new Item(0, 1, "Dragon Scroll I",	"Dragon Scroll I: Magic Arrow Storm",	1, playerNormalAttacks[31], "items/cloth01.wav", COLOR_DRAGON),			//31 - Dragon Scroll I: Magic Arrow Storm
			
			
			//Mage
			new Item(0, 2, "Magic Wand",		"a Magic Wand",									1, playerNormalAttacks[32], "items/wood01.wav", COLOR_STOCK),			//32 - Magic Wand			(T1)
			new Item(0, 2, "Silver Swirl Wand",	"a Silver Swirl Wand",							1, playerNormalAttacks[33], "items/metal01.wav", COLOR_COMMON),		//33 - Silver Swirl Wand	(T2)
			new Item(0, 2, "Golden Swirl Wand",	"a Golden Swirl Wand",							1, playerNormalAttacks[34], "items/metal01.wav", COLOR_COMMON),		//34 - Golden Swirl Wand	(T3)
			new Item(0, 2, "Magic Swirl Wand",	"a Magic Swirl Wand",							1, playerNormalAttacks[35], "items/metal01.wav", COLOR_COMMON),		//35 - Magic Swirl Wand		(T4)
			new Item(0, 2, "Silver Plate Wand",	"a Silver Plate Wand",							1, playerNormalAttacks[36], "items/metal01.wav", COLOR_COMMON),		//36 - Silver Plate Wand	(T5)
			new Item(0, 2, "Golden Plate Wand",	"a Golden Plate Wand",							1, playerNormalAttacks[37], "items/metal01.wav", COLOR_SEMIRARE),		//37 - Golden Plate Wand	(T6)
			new Item(0, 2, "Magic Plate Wand",	"a Magic Plate Wand",							1, playerNormalAttacks[38], "items/metal01.wav", COLOR_SEMIRARE),		//38 - Magic Plate Wand		(T7)
			new Item(0, 2, "Bluesteel Wand",	"a Bluesteel Wand",								1, playerNormalAttacks[39], "items/metal01.wav", COLOR_SEMIRARE),		//39 - Bluesteel Wand		(T8)
			new Item(0, 2, "Earth Wand",		"an Earth Wand",								1, playerNormalAttacks[40], "items/wood01.wav", COLOR_SEMIRARE),		//40 - Earth Wand			(T9)
			new Item(0, 2, "Fire Wand",			"a Fire Wand",									1, playerNormalAttacks[41], "items/flame01.wav", COLOR_RARE),			//41 - Fire Wand			(T10)
			new Item(0, 2, "Banishment Wand",	"a Banishment Wand",							1, playerNormalAttacks[42], "items/metal01.wav", COLOR_RARE),			//42 - Banishment Wand		(T11)
			new Item(0, 2, "Pure Magic Wand",	"a Pure Magic Wand",							1, playerNormalAttacks[43], "items/gem01.wav", COLOR_RARE),			//43 - Pure Magic Wand		(T12)
			new Item(0, 2, "Dragonstone Wand",	"a Dragonstone Wand",							1, playerNormalAttacks[44], "items/stone01.wav", COLOR_RARE),			//44 - Dragonstone Wand		(T13)
			new Item(0, 2, "Inorexite Wand",	"an Inorexite Wand",							1, playerNormalAttacks[45], "items/metal01.wav", COLOR_LEGENDARY),	//45 - Inorexite Wand		(T14)
			new Item(0, 2, "Supernova Wand",	"a Supernova Wand",								1, playerNormalAttacks[46], "items/metal01.wav", COLOR_LEGENDARY),	//46 - Supernova Wand		(T15)
			new Item(0, 2, "Dragon Scroll V",	"Dragon Scroll V: Ancient Spells of Wizardry",	1, playerNormalAttacks[47], "items/cloth01.wav", COLOR_DRAGON),		//47 - Dragon Scroll V: Ancient Spells of Wizardry
	};
	
	private static final Item[] specialWeaponItems = {
			
			//Knight
			new Item(1, 0, "Wooden Shield",			"a Wooden Shield",								1, playerSpecialAttacks[0], "items/wood01.wav", COLOR_STOCK),				//0 - Wooden Shield			(T1)
			new Item(1, 0, "Iron Shield",			"an Iron Shield",								1, playerSpecialAttacks[1], "items/metal01.wav", COLOR_COMMON),			//1 - Iron Shield			(T2)
			new Item(1, 0, "Steel Shield",			"a Steel Shield",								1, playerSpecialAttacks[2], "items/metal01.wav", COLOR_COMMON),			//2 - Steel Shield			(T3)
			new Item(1, 0, "Golden Shield",			"a Golden Shield",								1, playerSpecialAttacks[3], "items/metal01.wav", COLOR_COMMON),			//3 - Golden Shield			(T4)
			new Item(1, 0, "Kite Shield",			"an Kite Shield",								1, playerSpecialAttacks[4], "items/metal01.wav", COLOR_COMMON),			//4 - Kite Shield			(T5)
			new Item(1, 0, "Guardian Shield",		"a Guardian Shield",							1, playerSpecialAttacks[5], "items/metal01.wav", COLOR_SEMIRARE),			//5 - Guardian Shield		(T6)
			new Item(1, 0, "Shadow Steel Shield",	"a Shadow Steel Shield",						1, playerSpecialAttacks[6], "items/metal01.wav", COLOR_SEMIRARE),			//6 - Shadow Steel Shield	(T7)
			new Item(1, 0, "Bluesteel Shield",		"a Bluesteel Shield",							1, playerSpecialAttacks[7], "items/metal01.wav", COLOR_SEMIRARE),		//7 - Bluesteel Shield		(T8)
			new Item(1, 0, "Ice Shield",			"an Ice Shield",								1, playerSpecialAttacks[8], "items/metal01.wav", COLOR_SEMIRARE),		//8 - Ice Shield			(T9)
			new Item(1, 0, "Volcanic Rock Shield",	"a Volcanic Rock Shield",						1, playerSpecialAttacks[9], "items/stone01.wav", COLOR_RARE),			//9 - Volcanic Rock Shield	(T10)
			new Item(1, 0, "Ironrock Shield",		"a Ironrock Shield",							1, playerSpecialAttacks[10], "items/stone01.wav", COLOR_RARE),			//10 - Ironrock Shield		(T11)
			new Item(1, 0, "Dragonscale Shield",	"a Dragonscale Shield",							1, playerSpecialAttacks[11], "items/", COLOR_RARE),						//11 - Dragonescale Shield	(T12)
			new Item(1, 0, "Dragonstone Shield",	"a Dragonstone Shield",							1, playerSpecialAttacks[12], "items/stone01.wav", COLOR_LEGENDARY),		//12 - Dragonstone Shield	(T13)
			new Item(1, 0, "Inorexite Shield",		"an Inorexite Shield",							1, playerSpecialAttacks[13], "items/metal01.wav", COLOR_LEGENDARY),		//13 - Inorexite Shield		(T14)
			new Item(1, 0, "Voiding Shield",		"a Voiding Shield",								1, playerSpecialAttacks[14], "items/metal01.wav", COLOR_LEGENDARY),		//14 - Voiding Shield		(T15)
			new Item(1, 0, "Dragon Scroll IV",		"Dragon Scroll IV: Colossal Buffer Shield",		1, playerSpecialAttacks[15], "items/cloth01.wav", COLOR_DRAGON),			//15 - Dragon Scroll IV: Colossal Buffer Shield
			
			
			//Archer
			new Item(1, 1, "Flint Quiver",			"an Flint Quiver",								1, playerSpecialAttacks[16], "items/metal01.wav", COLOR_STOCK),		//16 - Flint Quiver			(T1)
			new Item(1, 1, "Iron Quiver",			"an Iron Quiver",								1, playerSpecialAttacks[17], "items/metal01.wav", COLOR_COMMON),		//17 - Iron Quiver			(T2)
			new Item(1, 1, "Steel Quiver",			"a Steel Quiver",								1, playerSpecialAttacks[18], "items/metal01.wav", COLOR_COMMON),		//18 - Steel Quiver			(T3)
			new Item(1, 1, "Silver Quiver",			"a Silver Quiver",								1, playerSpecialAttacks[19], "items/metal01.wav", COLOR_COMMON),		//19 - Silver Quiver		(T4)
			new Item(1, 1, "Golden Quiver",			"a Golden Quiver",								1, playerSpecialAttacks[20], "items/metal01.wav", COLOR_COMMON),		//20 - Golden Quiver		(T5)
			new Item(1, 1, "Gem Quiver",			"a Gem Quiver",									1, playerSpecialAttacks[21], "items/metal01.wav", COLOR_SEMIRARE),	//21 - Gem Quiver			(T6)
			new Item(1, 1, "Shadow Steel Quiver",	"a Shadow Steel Quiver",						1, playerSpecialAttacks[22], "items/metal01.wav", COLOR_SEMIRARE),	//22 - Shadow Steel Quiver	(T7)
			new Item(1, 1, "Bluesteel Quiver",		"a Bluesteel Quiver",							1, playerSpecialAttacks[23], "items/metal01.wav", COLOR_SEMIRARE),	//23 - Bluesteel Quiver		(T8)
			new Item(1, 1, "Ice Quiver",			"an Ice Quiver",								1, playerSpecialAttacks[24], "items/metal01.wav", COLOR_SEMIRARE),	//24 - Ice Quiver			(T9)
			new Item(1, 1, "Flame Quiver",			"a Flame Quiver",								1, playerSpecialAttacks[25], "items/metal01.wav", COLOR_RARE),		//25 - Flame Quiver			(T10)
			new Item(1, 1, "Ironrock Quiver",		"an Ironrock Quiver",							1, playerSpecialAttacks[26], "items/metal01.wav", COLOR_RARE),		//26 - Ironrock Quiver		(T11)
			new Item(1, 1, "Magic Iron Quiver",		"a Magic Iron Quiver",							1, playerSpecialAttacks[27], "items/metal01.wav", COLOR_RARE),		//27 - Magic Iron Quiver	(T12)
			new Item(1, 1, "Dragonstone Quiver",	"a Dragonstone Quiver",							1, playerSpecialAttacks[28], "items/stone01.wav", COLOR_RARE),		//28 - Dragonstone Quiver	(T13)
			new Item(1, 1, "Inorexite Quiver",		"an Inorexite Quiver",							1, playerSpecialAttacks[29], "items/metal01.wav", COLOR_LEGENDARY),	//29 - Inorexite Quiver		(T14)
			new Item(1, 1, "Oblivion Quiver",		"an Oblivion Quiver",							1, playerSpecialAttacks[30], "items/metal01.wav", COLOR_LEGENDARY),	//30 - Oblivion Quiver		(T15)
			new Item(1, 1, "Dragon Scroll II",		"Dragon Scroll II: Cosmic Piercing Quiver",		1, playerSpecialAttacks[31], "items/cloth01.wav", COLOR_DRAGON),		//31 - Dragon Scroll II: Cosmic Piercing Quiver
			
			
			//Mage
			new Item(1, 2, "Magic Turret Spell",		"a Magic Turret Spell",								1, playerSpecialAttacks[32], "items/cloth01.wav", COLOR_STOCK),		//32 - Magic Turret Spell		(T1)
			new Item(1, 2, "Magic Missile Spell",		"a Magic Missile Spell",							1, playerSpecialAttacks[33], "items/cloth01.wav", COLOR_COMMON),		//33 - Magic Missile Spell		(T2)
			new Item(1, 2, "Light Earth Spell",			"a Light Earth Spell",								1, playerSpecialAttacks[34], "items/cloth01.wav", COLOR_COMMON),		//34 - Light Earth Spell		(T3)
			new Item(1, 2, "Light Fire Spell",			"a Light Fire Spell",								1, playerSpecialAttacks[35], "items/cloth01.wav", COLOR_COMMON),		//35 - Light Fire Spell			(T4)
			new Item(1, 2, "Light Banishment Spell",	"a Light Banishment Spell",							1, playerSpecialAttacks[36], "items/cloth01.wav", COLOR_COMMON),		//36 - Light Banishment Spell	(T5)
			new Item(1, 2, "Magic Star Spell",			"a Magic Star Spell",								1, playerSpecialAttacks[37], "items/cloth01.wav", COLOR_SEMIRARE),	//37 - Magic Star Spell			(T6)
			new Item(1, 2, "Earth Spell",				"an Earth Spell",									1, playerSpecialAttacks[38], "items/cloth01.wav", COLOR_SEMIRARE),	//38 - Earth Spell				(T7)
			new Item(1, 2, "Fire Spell",				"a Fire Spell",										1, playerSpecialAttacks[39], "items/cloth01.wav", COLOR_SEMIRARE),	//39 - Fire Spell				(T8)
			new Item(1, 2, "Banishment Spell",			"a Banishment Spell",								1, playerSpecialAttacks[40], "items/cloth01.wav", COLOR_SEMIRARE),	//40 - Banishment Spell			(T9)
			new Item(1, 2, "Magic Nebula Spell",		"a Magic Nebula Spell",								1, playerSpecialAttacks[41], "items/cloth01.wav", COLOR_RARE),		//41 - Magic Nebula Spell		(T10)
			new Item(1, 2, "Heavy Earth Spell",			"a Heavy Earth Spell",								1, playerSpecialAttacks[42], "items/cloth01.wav", COLOR_RARE),		//42 - Heavy Earth Spell		(T11)
			new Item(1, 2, "Heavy Fire Spell",			"a Heavy Fire Spell",								1, playerSpecialAttacks[43], "items/cloth01.wav", COLOR_RARE),		//43 - Heavy Fire Spell			(T12)
			new Item(1, 2, "Heavy Banishment Spell",	"a Heavy Banishment Spell",							1, playerSpecialAttacks[44], "items/cloth01.wav", COLOR_RARE),		//44 - Heavy Banishment Spell	(T13)
			new Item(1, 2, "Magic Galaxy Spell",		"a Magic Galaxy Spell",								1, playerSpecialAttacks[45], "items/cloth01.wav", COLOR_LEGENDARY),	//45 - Magic Galaxy Spell		(T14)
			new Item(1, 2, "Magic Supernova Spell",		"a Magic Supernova Spell",							1, playerSpecialAttacks[46], "items/cloth01.wav", COLOR_LEGENDARY),	//46 - Magic Supernova Spell	(T15)
			new Item(1, 2, "Dragon Scroll VI",			"Dragon Scroll VI: Geological Destruction Spell",	1, playerSpecialAttacks[47], "items/cloth01.wav", COLOR_DRAGON),		//47 - Dragon Scroll VI: Geological Destruction Spell
			new Item(1, 2, "Dragon Scroll VII",			"Dragon Scroll VII: Solar Flame Spell",				1, playerSpecialAttacks[48], "items/cloth01.wav", COLOR_DRAGON),		//48 - Dragon Scroll VII: Solar Flame Spell
			new Item(1, 2, "Dragon Scroll VIII",		"Dragon Scroll VIII: Infinite Banishment Spell",	1, playerSpecialAttacks[49], "items/cloth01.wav", COLOR_DRAGON),		//49 - Dragon Scroll VIII: Infinite Banishment Spell
			new Item(1, 2, "Dragon Scroll IX",			"Dragon Scroll IX: Magic Singularity Spell",		1, playerSpecialAttacks[50], "items/cloth01.wav", COLOR_DRAGON),		//50 - Dragon Scroll IX: Magic Singularity Spell
	};
	
	private static final Item[] armorItems = {
			
			//Knight
			new Item(0, "Iron Armor",			"Iron Armor",			1, 10, 0, 0, new int[]{}, "items/metal01.wav", COLOR_STOCK),					//0 - Iron Armor			(T1)
			new Item(0, "Chainmail Armor",		"Chainmail Armor",		1, 15, 0, 0, new int[]{}, "items/metal01.wav", COLOR_COMMON),					//1 - Chainmail Armor		(T2)
			new Item(0, "Steel Armor",			"Steel Armor",			1, 20, 0, 0, new int[]{}, "items/metal01.wav", COLOR_COMMON),					//2 - Steel Armor			(T3)
			new Item(0, "Silver Armor",			"Silver Armor",			1, 25, 0, 0, new int[]{}, "items/metal01.wav", COLOR_COMMON),					//3 - Silver Armor			(T4)
			new Item(0, "Golden Armor",			"Golden Armor",			1, 30, 0, 0, new int[]{}, "items/metal01.wav", COLOR_COMMON),					//4 - Golden Armor			(T5)
			new Item(0, "Shadow Steel Armor",	"Shadow Steel Armor",	1, 30, 10, 0, new int[]{}, "items/metal01.wav", COLOR_SEMIRARE),				//5 - Shadow Steel Armor	(T6)
			new Item(0, "Knight Armor",			"Knight Armor",			1, 45, 10, 0, new int[]{}, "items/metal01.wav", COLOR_SEMIRARE),				//6 - Knight Armor			(T7)
			new Item(0, "Bluesteel Armor",		"Bluesteel Armor",		1, 45, 20, 0, new int[]{}, "items/metal01.wav", COLOR_SEMIRARE),				//7 - Bluesteel Armor		(T8)
			new Item(0, "Frost Armor",			"Frost Armor",			1, 60, 20, 0, new int[]{}, "items/metal01.wav", COLOR_SEMIRARE),				//8 - Frost Armor			(T9)
			new Item(0, "Volcanic Armor",		"Volcanic Armor",		1, 60, 40, 0, new int[]{8, 9}, "items/flame01.wav", COLOR_RARE),				//9 - Volcanic Armor		(T10)
			new Item(0, "Ironrock Armor",		"Ironrock Armor",		1, 75, 40, 10, new int[]{8, 9}, "items/stone01.wav", COLOR_RARE),				//10 - Ironrock Armor		(T11)
			new Item(0, "Magic Iron Armor",		"Magic Iron Armor",		1, 75, 60, 20, new int[]{8, 9}, "items/metal01.wav", COLOR_RARE),				//11 - Magic Iron Armor		(T12)
			new Item(0, "Dragonstone Armor",	"Dragonstone Armor",	1, 90, 60, 30, new int[]{8, 9}, "items/stone01.wav", COLOR_RARE),				//12 - Dragonstone Armor	(T13)
			new Item(0, "Inorexite Armor",		"Inorexite Armor",		1, 90, 80, 40, new int[]{8, 9}, "items/metal01.wav", COLOR_LEGENDARY),		//13 - Inorexite Armor		(T14)
			new Item(0, "Cosmic Armor",			"Cosmic Armor",			1, 100, 100, 50, new int[]{8, 9, 13}, "items/metal01.wav", COLOR_LEGENDARY),	//14 - Cosmic Armor			(T15)
			
			
			//Archer
			new Item(1, "Leather Shirt",			"a Leather Shirt",			1, 5, 0, 0, new int[]{}, "items/cloth01.wav", COLOR_STOCK),					//15 - Leather Shirt			(T1)
			new Item(1, "Leather Armor",			"Leather Armor",			1, 6, 0, 0, new int[]{}, "items/cloth01.wav", COLOR_COMMON),					//16 - Leather Armor			(T2)
			new Item(1, "Hardened Leather Armor",	"Hardened Leather Armor",	1, 8, 0, 0, new int[]{}, "items/cloth01.wav", COLOR_COMMON),					//17 - Hardened Leather Armor	(T3)
			new Item(1, "Reinforced Leather Armor",	"Reinforced Leather Armor",	1, 10, 0, 0, new int[]{}, "items/cloth01.wav", COLOR_COMMON),					//18 - Reinforced Leather Armor	(T4)
			new Item(1, "Bear Hide Armor",			"Bear Hide Armor",			1, 14, 0, 0, new int[]{}, "items/cloth01.wav", COLOR_COMMON),					//19 - Bear Hide Armor			(T5)
			new Item(1, "Lizard Skin Armor",		"Lizard Skin Armor",		1, 12, 5, 5, new int[]{}, "items/cloth01.wav", COLOR_SEMIRARE),				//20 - Lizard Skin Armor		(T6)
			new Item(1, "Komodo Hide Armor",		"Komodo Hide Armor",		1, 14, 5, 5, new int[]{}, "items/cloth01.wav", COLOR_SEMIRARE),				//21 - Komodo Hide Armor		(T7)
			new Item(1, "Wolf Armor",				"Wolf Armor",				1, 16, 10, 10, new int[]{}, "items/cloth01.wav", COLOR_SEMIRARE),				//22 - Wolf Armor				(T8)
			new Item(1, "Serpent Armor",			"Serpent Armor",			1, 18, 10, 10, new int[]{}, "items/cloth01.wav", COLOR_SEMIRARE),				//23 - Serpent Armor			(T9)
			new Item(1, "Griffon Armor",			"Griffon Armor",			1, 20, 15, 15, new int[]{7, 11}, "items/cloth01.wav", COLOR_RARE),			//24 - Griffon Armor			(T10)
			new Item(1, "Dragon Armor",				"Dragon Armor",				1, 22, 15, 15, new int[]{7, 11}, "items/cloth01.wav", COLOR_RARE),			//25 - Dragon Armor				(T11)
			new Item(1, "Hydra Armor",				"Hydra Armor",				1, 24, 20, 20, new int[]{7, 11}, "items/cloth01.wav", COLOR_RARE),			//26 - Hydra Armor				(T12)
			new Item(1, "Inorexite Leather Armor",	"Inorexite Leather Armor",	1, 30, 20, 20, new int[]{7, 11}, "items/cloth01.wav", COLOR_RARE),			//27 - Inorexite Leather Armor	(T13)
			new Item(1, "Dark Armor",				"Dark Armor",				1, 34, 25, 25, new int[]{7, 11}, "items/metal01.wav", COLOR_LEGENDARY),		//28 - Dark Armor				(T14)
			new Item(1, "Galaxian Armor",			"Galaxian Armor",			1, 38, 30, 30, new int[]{7, 11, 6}, "items/metal01.wav", COLOR_LEGENDARY),	//29 - Galaxian Armor			(T15)
			
			
			//Mage
			new Item(2, "Leather Robe",			"a Leather Robe",		1, 3, 0, 0, new int[]{}, "items/cloth01.wav", COLOR_STOCK),					//30 - Leather Robe			(T1)
			new Item(2, "Silk Robe",			"a Silk Robe",			1, 4, 0, 0, new int[]{}, "items/cloth01.wav", COLOR_COMMON),					//31 - Silk Robe			(T2)
			new Item(2, "Apprentice's Robe",	"an Apprentice's Robe",	1, 5, 0, 0, new int[]{}, "items/cloth01.wav", COLOR_COMMON),					//32 - Apprentice Robe		(T3)
			new Item(2, "Mage Robe",			"a Mage Robe",			1, 6, 0, 0, new int[]{}, "items/cloth01.wav", COLOR_COMMON),					//33 - Mage Robe			(T4)
			new Item(2, "Wizard Robe",			"a Wizard Robe",		1, 7, 0, 0, new int[]{}, "items/cloth01.wav", COLOR_COMMON),					//34 - Wizard Robe			(T5)
			new Item(2, "Necromancer Robe",		"a Necromancer Robe",	1, 9, 0, 10, new int[]{}, "items/cloth01.wav", COLOR_SEMIRARE),				//35 - Necromancer Robe		(T6)
			new Item(2, "Warlock Robe",			"a Warlock Robe",		1, 11, 0, 10, new int[]{}, "items/cloth01.wav", COLOR_SEMIRARE),				//36 - Warlock Robe			(T7)
			new Item(2, "Sorcerer Robe",		"Sorcerer Robe",		1, 13, 0, 20, new int[]{}, "items/cloth01.wav", COLOR_SEMIRARE),				//37 - Sorcerer Robe		(T8)
			new Item(2, "Metallic Robe",		"Metallic Robe",		1, 15, 0, 20, new int[]{}, "items/metal01.wav", COLOR_SEMIRARE),				//38 - Metallic Robe		(T9)
			new Item(2, "Earth Robe",			"Earth Robe",			1, 17, 5, 30, new int[]{10, 12}, "items/cloth01.wav", COLOR_RARE),			//39 - Earth Robe			(T10)
			new Item(2, "Flame Robe",			"Flame Robe",			1, 19, 5, 30, new int[]{10, 12}, "items/flame01.wav", COLOR_RARE),			//40 - Flame Robe			(T11)
			new Item(2, "Isolation Robe",		"Isolation Robe",		1, 24, 10, 40, new int[]{10, 12}, "items/cloth01.wav", COLOR_RARE),			//41 - Isolation Robe		(T12)
			new Item(2, "Starsilk Robe",		"Starsilk Robe",		1, 30, 10, 40, new int[]{10, 12}, "items/cloth01.wav", COLOR_RARE),			//42 - Starsilk Robe		(T13)
			new Item(2, "Stardust Robe",		"Stardust Robe",		1, 32, 20, 50, new int[]{10, 12}, "items/cloth01.wav", COLOR_LEGENDARY),		//43 - Stardust Robe		(T14)
			new Item(2, "Nebula Robe",			"Nebula Robe",			1, 34, 20, 50, new int[]{10, 12, 5}, "items/cloth01.wav", COLOR_LEGENDARY),	//44 - Nebula Robe			(T15)
	};
	
	private static final Item[] consumableItems = {
			new Item(3, "Basic Health Potion", "a Basic Health Potion", 10, 30, 0, "items/glass01.wav", "items/drink01.wav", COLOR_STOCK),			//0 - Basic Health Potion
			new Item(3, "Health Potion", "a Health Potion", 10, 60, 0, "items/glass01.wav", "items/drink01.wav", COLOR_COMMON),						//1 - Health Potion
			new Item(3, "Strong Health Potion", "a Strong Health Potion", 10, 120, 0, "items/glass01.wav", "items/drink01.wav", COLOR_COMMON),		//2 - Strong Health Potion
			
			new Item(3, "Basic Magic Potion", "a Basic Magic Potion", 10, 0, 30, "items/glass01.wav", "items/drink01.wav", COLOR_STOCK),				//3 - Basic Magic Potion
			new Item(3, "Magic Potion", "a Magic Potion", 10, 0, 60, "items/glass01.wav", "items/drink01.wav", COLOR_COMMON),							//4 - Magic Potion
			new Item(3, "Strong Magic Potion", "a Strong Magic Potion", 10, 0, 120, "items/glass01.wav", "items/drink01.wav", COLOR_COMMON),			//5 - Strong Magic Potion

			new Item(3, "Basic Regen Potion", "a Basic Regen Potion", 10, 0, 0, new StatusEffect(1, 5, 5),
				"items/glass01.wav", "items/drink01.wav", COLOR_STOCK),															//6 - Basic Regen Potion
			new Item(3, "Regen Potion", "a Regen Potion", 10, 0, 0, new StatusEffect(1, 5, 10),
				"items/glass01.wav", "items/drink01.wav", COLOR_STOCK),															//7 - Regen Potion
			new Item(3, "Strong Regen Potion", "a Strong Regen Potion", 10, 0, 0, new StatusEffect(1, 5, 15),
				"items/glass01.wav", "items/drink01.wav", COLOR_STOCK),															//8 - Strong Regen Potion

			new Item(3, "Basic Magic Regen Potion", "a Basic Magic Regen Potion", 10, 0, 0, new StatusEffect(2, 5, 5),
				"items/glass01.wav", "items/drink01.wav", COLOR_STOCK),															//9 - Basic Magic Regen Potion
			new Item(3, "Basic Magic Regen Potion", "a Magic Regen Potion", 10, 0, 0, new StatusEffect(2, 5, 10),
				"items/glass01.wav", "items/drink01.wav", COLOR_STOCK),															//10 - Magic Regen Potion
			new Item(3, "Strong Magic Regen Potion", "a Strong Magic Regen Potion", 10, 0, 0, new StatusEffect(2, 5, 15),
				"items/glass01.wav", "items/drink01.wav", COLOR_STOCK),															//11 - Strong Magic Regen Potion

			new Item(3, "Basic Restoration Potion", "a Basic Restoration Potion", 5, 30, 30, "items/glass01.wav", "items/drink01.wav", COLOR_STOCK),		//12 - Basic Restoration Potion
			new Item(3, "Restoration Potion", "a Restoration Potion", 5, 60, 60, "items/glass01.wav", "items/drink01.wav", COLOR_STOCK),					//13 - Restoration Potion
			new Item(3, "Strong Restoration Potion", "a Strong Restoration Potion", 5, 120, 120, "items/glass01.wav", "items/drink01.wav", COLOR_STOCK),	//14 - Strong Restoration Potion

			new Item(3, "Life Potion", "a Life Potion", 10, 250, 0, "items/glass01.wav", "items/drink01.wav", COLOR_COMMON),		//15 - Life Potion
			new Item(3, "Mana Potion", "a Mana Potion", 10, 0, 250, "items/glass01.wav", "items/drink01.wav", COLOR_COMMON),		//16 - Mana Potion

			new Item(3, "Strong Magic Regen Potion", "a Strong Magic Regen Potion", 10, 0, 0, new StatusEffect(2, 5, 15),
				"items/glass01.wav", "items/drink01.wav", COLOR_STOCK),															//11 - Strong Magic Regen Potion
	};
	
	private static final Item[] miscItems = {
		new Item(3, "Nightmare Castle Key", "a Nightmare Castle Key", 5, true, "", "", COLOR_COMMON, 10),	
	};
	
	private static final Shopkeeper[] shopkeepers = {
			
			//Forestry Town
			new Shopkeeper("Blacksmith", "Good day, what would you like? Here's what I've got:", "Have a fine day.",		//0 - Blacksmith
				new Item[]{normalWeaponItems[2], normalWeaponItems[3], specialWeaponItems[1], armorItems[1], armorItems[2]},
				new int[]{50, 80, 60, 40, 60}, new int[]{1, 1, 1, 1, 1}),
			
			new Shopkeeper("Hunter", "Hello, interested in bows and arrows?", "Good luck on your journey.",					//1 - Hunter
				new Item[]{normalWeaponItems[17], normalWeaponItems[18], specialWeaponItems[17], armorItems[16], armorItems[17]},
				new int[]{50, 80, 60, 40, 60}, new int[]{1, 1, 1, 1, 1}),
			
			new Shopkeeper("Sorcerer", "What kind of magical equipment would you like?", "Come again.",						//2 - Sorcerer
				new Item[]{normalWeaponItems[33], normalWeaponItems[34], specialWeaponItems[33], armorItems[31], armorItems[32]},
				new int[]{50, 80, 60, 40, 60}, new int[]{1, 1, 1, 1, 1}),
			
			new Shopkeeper("Potion Brewer", "Hello, are you interested in anything?", "Have a good day.",
				new Item[]{consumableItems[0], consumableItems[1], consumableItems[6], consumableItems[3], consumableItems[4], consumableItems[9]},
				new int[]{15, 25, 40, 15, 25, 40}, new int[]{5, 3, 2, 5, 3, 2}),																//3 - Potion Brewer
			
			new Shopkeeper("Tradesman", "Hello, what can I buy?", "Thanks for your offers.", new Item[]{},new int[]{}, new int[]{}),			//4 - Tradesman
			
			//Nightmare Castle
			new Shopkeeper("Potion Merchant", "Hello, adventurer. Are you in need of potions?", "Good luck.",
				new Item[]{consumableItems[1], consumableItems[2], consumableItems[7], consumableItems[4], consumableItems[5], consumableItems[10]},
				new int[]{25, 40, 60, 25, 40, 60}, new int[]{5, 4, 3, 5, 4, 3}),																//5 - Potion Merchant
			
			//Vibrancy Town
			new Shopkeeper("Blacksmith", "Hello, what would you like?", "Good day.",					//6 - Blacksmith
				new Item[]{normalWeaponItems[4], normalWeaponItems[5], normalWeaponItems[6],
					specialWeaponItems[4], specialWeaponItems[5], specialWeaponItems[6],
					armorItems[4], armorItems[5], armorItems[6]},
				new int[]{100, 200, 300, 150, 250, 350, 80, 160, 240}, new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1}),
			
			new Shopkeeper("Hunter", "I've got plenty of goods for archery.", "Goodbye!",				//7 - Hunter 20-22
				new Item[]{normalWeaponItems[20], normalWeaponItems[21], normalWeaponItems[22],
					specialWeaponItems[20], specialWeaponItems[21], specialWeaponItems[22],
					armorItems[19], armorItems[20], armorItems[22]},
				new int[]{100, 200, 300, 150, 250, 350, 80, 160, 240}, new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1}),
			
			new Shopkeeper("Sorcerer", "I have plenty of good spells.", "Good luck, adventurer.",		//8 - Sorcerer 36-38
				new Item[]{normalWeaponItems[36], normalWeaponItems[37], normalWeaponItems[38],
					specialWeaponItems[36], specialWeaponItems[37], specialWeaponItems[38],
					armorItems[34], armorItems[35], armorItems[36]},
				new int[]{100, 200, 300, 150, 250, 350, 80, 160, 240}, new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1}),
			
			new Shopkeeper("Potion Merchant", "Hello, are you interested in anything?", "Have a good day.",
				new Item[]{consumableItems[7], consumableItems[10], consumableItems[12], consumableItems[13], consumableItems[15], consumableItems[16]},
				new int[]{15, 25, 40, 15, 25, 40}, new int[]{10, 5, 3, 10, 5, 3}),						//9 - Potion Brewer
			
			new Shopkeeper("Tradesman", "What have you to sell?", "Thank you!", new Item[]{},new int[]{}, new int[]{}),		//10 - Tradesman
			
			//Bluesteel Mineshaft
			new Shopkeeper("Dwarven Merchant", "Hello, what do you need?", "See ya later, traveller.",
				new Item[]{consumableItems[1], consumableItems[2], consumableItems[6], consumableItems[4], consumableItems[5], consumableItems[7]},
				new int[]{15, 25, 40, 15, 25, 40}, new int[]{10, 5, 3, 10, 5, 3}),						//9 - Dwarven Merchant
			
			//Marble City
			new Shopkeeper("Blacksmith", "Hello, what would you like?", "Good day.",					//6 - Blacksmith
				new Item[]{normalWeaponItems[4], normalWeaponItems[5], normalWeaponItems[6],
					specialWeaponItems[4], specialWeaponItems[5], specialWeaponItems[6],
					armorItems[4], armorItems[5], armorItems[6]},
				new int[]{100, 200, 300, 150, 250, 350, 80, 160, 240}, new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1}),
			
			new Shopkeeper("Hunter", "I've got plenty of goods for archery.", "Goodbye!",				//7 - Hunter 20-22
				new Item[]{normalWeaponItems[20], normalWeaponItems[21], normalWeaponItems[22],
					specialWeaponItems[20], specialWeaponItems[21], specialWeaponItems[22],
					armorItems[19], armorItems[20], armorItems[22]},
				new int[]{100, 200, 300, 150, 250, 350, 80, 160, 240}, new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1}),
			
			new Shopkeeper("Sorcerer", "I have plenty of good spells.", "Good luck, adventurer.",		//8 - Sorcerer 36-38
				new Item[]{normalWeaponItems[36], normalWeaponItems[37], normalWeaponItems[38],
					specialWeaponItems[36], specialWeaponItems[37], specialWeaponItems[38],
					armorItems[34], armorItems[35], armorItems[36]},
				new int[]{100, 200, 300, 150, 250, 350, 80, 160, 240}, new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1}),
			
			new Shopkeeper("Potion Merchant", "Hello, are you interested in anything?", "Have a good day.",
				new Item[]{consumableItems[14], consumableItems[15], consumableItems[16], consumableItems[4], consumableItems[5], consumableItems[7]},
				new int[]{15, 25, 40, 15, 25, 40}, new int[]{10, 5, 3, 10, 5, 3}),						//9 - Potion Brewer
			
			new Shopkeeper("Tradesman", "What have you to sell?", "Thank you!", new Item[]{},new int[]{}, new int[]{}),		//10 - Tradesman
			
	};
	
	//Drops
	
	//Define standard drop rates
	private static final int[] tierRates = {8, 8, 8, 4, 4, 4, 8, 8, 8, 10, 10},
	twoTierRates = {4, 4, 4, 4, 4, 4, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5};
	
	//Return weapons and armor of tier "tier" and comsumables of tier "cTier"
	private static Item[] getTierDrops(int tier, int cTier){
		
		tier -= 1;
		
		// Basic Potions
		Item c1 = consumableItems[0], c2 = consumableItems[3];
		
		switch(cTier){
			case 2:							// Standard Potions
				c1 = consumableItems[1];
				c2 = consumableItems[4];
				break;
			case 3:							// Strong Potions
				c1 = consumableItems[2];
				c2 = consumableItems[5];
				break;
			case 5:							// Basic Regen Potions
				c1 = consumableItems[6];
				c2 = consumableItems[9];
				break;
			case 6:							// Standard Regen Potions
				c1 = consumableItems[7];
				c2 = consumableItems[10];
				break;
			case 7:							// Strong Regen Potions
				c1 = consumableItems[8];
				c2 = consumableItems[11];
				break;
			case 8:							// Basic Restoration Potions
				c1 = consumableItems[12];
				c2 = consumableItems[12];
				break;
			case 9:							// Standard Restoration Potions
				c1 = consumableItems[13];
				c2 = consumableItems[13];
				break;
			case 10:						// Strong Restoration Potions
				c1 = consumableItems[14];
				c2 = consumableItems[14];
				break;
			case 11:						// Life/Mana Potions
				c1 = consumableItems[15];
				c2 = consumableItems[16];
				break;
		}
		
		return new Item[]{normalWeaponItems[tier], normalWeaponItems[16 + tier], normalWeaponItems[32 + tier],
		specialWeaponItems[tier], specialWeaponItems[16 + tier], specialWeaponItems[32 + tier],
		armorItems[tier], armorItems[15 + tier], armorItems[30 + tier],
		c1, c2};
	}
	
	//Return weapons and armor of tier "tier" and comsumables of tier "cTier" and one tier above
	private static Item[] getTwoTierDrops(int tier, int cTier){
		
		tier -= 1;
		cTier -= 1;
		
		return new Item[]{normalWeaponItems[tier], normalWeaponItems[1 + tier], normalWeaponItems[16 + tier], normalWeaponItems[17 + tier], normalWeaponItems[32 + tier], normalWeaponItems[33 + tier],
		specialWeaponItems[tier], specialWeaponItems[1 + tier], specialWeaponItems[16 + tier], specialWeaponItems[17 + tier], specialWeaponItems[32 + tier], specialWeaponItems[33 + tier],
		armorItems[tier], armorItems[1 + tier], armorItems[15 + tier], armorItems[16 + tier], armorItems[30 + tier], armorItems[31 + tier],
		consumableItems[cTier], consumableItems[1 + cTier], consumableItems[3 + cTier], consumableItems[4 + cTier]};
	}
	
	//Appends item to end of item array
	private static Item[] appendItemArray(Item[] items, Item newItem){
		Item[] temp = new Item[items.length + 1];
		
		for(int i = 0; i < items.length; i++){
			temp[i] = items[i];
		}
		
		temp[items.length] = newItem;
		
		return temp;
	}
	
	//Appends int to end of int array
	private static int[] appendIntArray(int[] rates, int newRate){
		int[] temp = new int[rates.length + 1];
		
		for(int i = 0; i < rates.length; i++){
			temp[i] = rates[i];
		}
		
		temp[rates.length] = newRate;
		
		return temp;
	}
	
	
	private static final Enemy[] enemies = {
		
		//Tutorial
		new Enemy(0, "Eagle", 50, 50, 2, 0, false, enemyAttacks[0], new Item[]{},
			new int[]{}, 0, 5, 10, 5, 10, new int[]{}, "enemy/", "enemy/", COLOR_STOCK),			//0 - Eagle
		
		//Oakorn Forest
		new Enemy(30, "Jade Snake", 30, 30, 3, 70, false, enemyAttacks[1], getTierDrops(2, 1),
			tierRates, 25, 5, 20, 5, 10, new int[]{}, "enemy/", "enemy/", COLOR_COMMON),					//1 - Jade Snake
		
		new Enemy(30, "Giant Spider", 25, 25, 5, 60, false, enemyAttacks[2], getTierDrops(2, 1),
			tierRates, 25, 2, 8, 8, 12, new int[]{}, "enemy/", "enemy/", COLOR_COMMON),						//2 - Giant Spider
		
		new Enemy(20, "Bear", 50, 50, 5, 60, false, enemyAttacks[3], 	getTierDrops(2, 1),
			tierRates, 25, 5, 10, 10, 15, new int[]{}, "enemy/", "enemy/", COLOR_COMMON),					//3 - Bear
		
		new Enemy(10, "Goblin Minion", 30, 30, 8, 80, false, enemyAttacks[4], getTierDrops(3, 1),
			tierRates, 25, 15, 20, 14, 18, new int[]{}, "enemy/", "enemy/", COLOR_COMMON),					//4 - Goblin Minion
		
		new Enemy(5, "Goblin Scout", 35, 35, 7, 60, false, enemyAttacks[5], getTierDrops(3, 1),
			tierRates, 25, 20, 25, 16, 22, new int[]{}, "enemy/", "enemy/", COLOR_COMMON),					//5 - Goblin Scout
		
		new Enemy(5, "Goblin Warrior", 45, 45, 10, 50, false, enemyAttacks[6], getTierDrops(3, 2),
			tierRates, 25, 20, 30, 14, 26, new int[]{}, "enemy/", "enemy/", COLOR_COMMON),					//6 - Goblin Warrior

		new Enemy(0, "Goblin Leader", 75, 75, 10, 50, false, enemyAttacks[7], getTwoTierDrops(2, 1),
			twoTierRates, 40, 40, 60, 60, 80, new int[]{}, "enemy/", "enemy/", COLOR_COMMON),				//7 - Goblin Leader
		
		
		//Spectral Forest
		new Enemy(20, "Ghost", 35, 35, 6, 60, false, enemyAttacks[8], appendItemArray(getTierDrops(3, 2), miscItems[0]),
			appendIntArray(tierRates, 25), 25, 10, 15, 10, 15, new int[]{}, "enemy/", "enemy/", COLOR_COMMON),					//8 - Ghost
		
		new Enemy(20, "Mini Ghost", 15, 15, 3, 70, false, enemyAttacks[9], appendItemArray(getTierDrops(3, 1),
			miscItems[0]), appendIntArray(tierRates, 10), 25, 6, 8, 6, 8, new int[]{}, "enemy/", "enemy/", COLOR_COMMON),		//9 - Mini Ghost
		
		new Enemy(20, "Wolf", 50, 50, 5, 60, false, enemyAttacks[10], appendItemArray(getTierDrops(4, 1),
			miscItems[0]), appendIntArray(tierRates, 10), 25, 10, 20, 10, 25, new int[]{}, "enemy/", "enemy/", COLOR_COMMON),	//10 - Wolf
		
		new Enemy(20, "Dark Wisp", 40, 40, 1, 90, false, enemyAttacks[11], appendItemArray(getTierDrops(4, 1),
			miscItems[0]), appendIntArray(tierRates, 15), 25, 8, 15, 10, 30, new int[]{}, "enemy/", "enemy/", COLOR_COMMON),	//11 - Dark Wisp
		
		new Enemy(15, "Haunted Ent", 70, 70, 10, 60, false, enemyAttacks[12], appendItemArray(getTierDrops(5, 1),
			miscItems[0]), appendIntArray(tierRates, 15), 25, 30, 60, 40, 60, new int[]{}, "enemy/", "enemy/", COLOR_COMMON),	//12 - Haunted Ent

		new Enemy(5, "Arcane Spirit", 50, 50, 15, 90, false, enemyAttacks[13], appendItemArray(getTierDrops(5, 2),
			miscItems[0]), appendIntArray(tierRates, 30), 35, 15, 25, 50, 100, new int[]{}, "enemy/", "enemy/", COLOR_SEMIRARE),//13 - Arcane Spirit
		
		new Enemy(0, "Spirit Guardian", 100, 150, 20, 50, false, enemyAttacks[14], getTwoTierDrops(4, 1),
			twoTierRates, 40, 30, 60, 150, 200, new int[]{}, "enemy/", "enemy/", COLOR_SEMIRARE),								//14 - Spirit Guardian
		
		//Nightmare Castle
		new Enemy(20, "Ghost Knight", 70, 70, 15, 60, false, enemyAttacks[15], getTierDrops(4, 1), tierRates, 25,
			40, 50, 40, 50, new int[]{}, "enemy/", "enemy/", COLOR_COMMON),														//15 - Ghost Knight
		
		new Enemy(20, "Ghost Archer", 60, 60, 8, 75, false, enemyAttacks[16], getTierDrops(4, 1), tierRates, 25,
			40, 50, 40, 50, new int[]{}, "enemy/", "enemy/", COLOR_COMMON),														//16 - Ghost Archer
		
		new Enemy(20, "Ghost Priest", 60, 60, 10, 75, false, enemyAttacks[17], getTierDrops(4, 1), tierRates, 25,
			40, 50, 40, 50, new int[]{}, "enemy/", "enemy/", COLOR_COMMON),														//17 - Ghost Priest
		
		new Enemy(20, "Ghost Jester", 70, 70, 10, 50, false, enemyAttacks[18], getTierDrops(5, 1), tierRates, 25,
			40, 50, 60, 80, new int[]{}, "enemy/", "enemy/", COLOR_COMMON),														//18 - Ghost Jester
		
		new Enemy(20, "Nightmare Spirit", 80, 80, 10, 50, false, enemyAttacks[19], getTierDrops(5, 1), tierRates, 35,
			20, 30, 100, 150, new int[]{}, "enemy/", "enemy/", COLOR_SEMIRARE),													//19 - Nightmare Spirit
		
		new Enemy(20, "Ghost King", 150, 150, 15, 50, false, enemyAttacks[20], getTwoTierDrops(4, 1), twoTierRates, 40,
			50, 80, 175, 225, new int[]{}, "enemy/", "enemy/", COLOR_SEMIRARE),													//20 - Ghost King
		
		//Diamond River
	};
	
	
	private static final Area[] areas = {
		new Area("default area", new Shopkeeper[]{}, 0, new Enemy[]{},
			"If you see this, there was probably an error while loading the save. Report this as a bug if you did not modify your save data.",
			new Interaction[]{}, COLOR_STOCK),
		
		// 1 - Forestry Town - T2-T3 Items - Stock
		// Unlocks Oakorn Forest
		new Area("Forestry Town", new Shopkeeper[]{shopkeepers[0], shopkeepers[1], shopkeepers[2], shopkeepers[3], shopkeepers[4]},
			0, new Enemy[]{},
			"There are various houses buildings, people, decorations, and other things. There is also a healer.",
			
			new Interaction[]{new Interaction(1, -1, new String[]{"healer", "heal"},
			"Welcome! I'm glad to be of service.", "Good luck on your journey!", 2, 1000000000, 0, "")}, COLOR_STOCK),
		
		// 2 - Oakorn Forest - T2-T3 Items - Common
		// Unlocks Spectral Forest
		new Area("Oakorn Forest", new Shopkeeper[]{},
			10, new Enemy[]{enemies[1], enemies[2], enemies[3], enemies[4], enemies[5], enemies[6]},
			"You see many oak trees, and there is not much light. However, you can see what appears to be a campfire in the distance.",
			
			new Interaction[]{new Interaction(-1, new String[]{"campfire", "fire", "goblin camp", "goblin's camp", "goblins camp", "goblin camp"},
			"After approaching, you find a goblin camp!", "", 2, enemies[7], "")}, COLOR_COMMON),
		
		// 3 - Spectral Forest - T3-T5 Items - Common
		// Unlocks Nightmare Castle
		new Area("Spectral Forest", new Shopkeeper[]{},
			10, new Enemy[]{enemies[8], enemies[9], enemies[10], enemies[11], enemies[12], enemies[13]},
			"There are many dead trees with purple-tinted leaves. There is a lot of fog and a path you are following. Near the path is a circle of runes. You can also see the door to Nightmare Castle.",
			
			new Interaction[]{new Interaction(-1, new String[]{"circle of runes", "circle of rune", "circle runes", "circle rune", "runes circle", "rune circle", "circle", "runes", "rune"},
			"You examine the runes and a creature appears!", "", 1, enemies[14], "")}, COLOR_COMMON),
		
		// 4 - Nightmare Castle - T4-T5 Items - Common
		// Unlocks Vibrancy Town
		new Area("Nightmare Castle", new Shopkeeper[]{shopkeepers[5]},
			5, new Enemy[]{enemies[15], enemies[16], enemies[17], enemies[18], enemies[19]},
			"There are dark halls, several rooms, stairs, and other various objects. At the end of the main hall, you can make out a throne with a shadowed figure in it.",
			
			new Interaction[]{new Interaction(-1, new String[]{"shadowed figure in throne", "shadow figure in throne", "shadowed figure throne", "shadow figure throne", "shadowed figure",
			"shadow figure", "shadow", "figure", "throne"},
			"You walk up to the throne. The shadow stands and approaches you!", "", 1, enemies[20], "")}, COLOR_COMMON),
		
		// 5 - Vibrancy Town - T5-T7 Items
		// Unlocks Diamond River and Bluesteel Mineshaft - Semirare
		new Area("Vibrancy Town", new Shopkeeper[]{shopkeepers[6], shopkeepers[7], shopkeepers[8], shopkeepers[9], shopkeepers[10]},
			0, new Enemy[]{},
			"The town is bright and lively with several large buildings. There are various shops and a healer.",
			
			new Interaction[]{new Interaction(1, -1, new String[]{"healer", "heal"},
			"Good day, traveler. This will help you on your way.", "Good luck on your adventure.", 2, 1000000000, 1000000000, "")}, COLOR_SEMIRARE),
		/*
		// 6 - Diamond River - T5-T7 Items - Semirare
		// Unlocks Waterfall Valley
		new Area("Diamond River", new Shopkeeper[]{},
			10, new Enemy[]{enemies[21]},
			"There isn't a cloud in the sky and there is a gentle breeze. The river is crystal clear and filled with life.",
			new Interaction[]{}, SEMIRARE),
		
		// 7 - Bluesteel Mineshaft - T6-T8 Items - Semirare
		// Unlocks Waterfall Valley
		new Area("Bluesteel Mineshaft", new Shopkeeper[]{},
			10, new Enemy[]{enemies[22]},
			"The mineshaft is a large cave with blue-tinted walls. There are various abandoned minecart tracks and outposts.",
			new Interaction[]{}, SEMIRARE),
		
		// 8 - Waterfall Valley - T7-T9 Items - Rare
		// Unlocks Marble City
		new Area("Marble City", new Shopkeeper[]{shopkeepers[6], shopkeepers[7], shopkeepers[8], shopkeepers[9], shopkeepers[10]},
			0, new Enemy[]{},
			"There are tall marble buildings built into the mountains. There are several shops and a healer. Ironrock Range and its peaks loom in the distance.",
			
			new Interaction[]{new Interaction(1, -1, new String[]{"healer", "heal"},
			"Good day, traveler. This will help you on your way.", "Good luck on your adventure.", 2, 1000000000, 1000000000, "")}, SEMIRARE),
		*/
		// 9 - Marble City - T9-T11 Items - Rare
		// Unlocks Ironrock Pass
		
		// 10 - Ironrock Pass - T9-T11 Items - Rare
		// Unlocks Ironrock Range
		
		// 11 - Ironrock Range - T10-T12 Items - Legendary
		// Unlocks Dragonstone Mountain
		
		// 12 - Dragonstone Mountain - T11-T13 Items - Dragon
		// Dragon - Dragon Scrolls I and II (Archer)
		// Unlocks Monolith Mountain
		
		// 13 - Monolith Mountain - T12-T14 Items - Dragon
		// Inexorite Dragon - Dragon Scrolls III and IV (Knight)
		// Unlocks Earthkey Mountain
		
		// 14 - Earthkey Mountain - T12-T14 Items - Dragon
		// Earth Core Dragon - Dragon Scrolls V and VI (Mage)
		// Unlocks Scarlet Desert
		
		// 15 - Scarlet Desert - T11-T13 Items - Legendary
		// Unlocks Hydra Hell Castle and Celestial Range
		
		// 16 - Hydra Hell Castle - T13-T15 Items - Dragon
		// Fire Hydra - Dragon Scrolls V and VII (Mage)
		
		// 17 - Celestial Range - T12-T14 Items - Legendary
		// Unlocks Void Mountain
		
		// 18 - Void Mountain - T14-T15 Items - Dragon
		// Black Void Dragon - Dragon Scrolls V and VIII (Mage)
		// Unlocks Azure Star Mountain
		
		//19 - Azure Star Mountain - T15 Items - Dragon
		// Nova Dragon - Dragon Scrolls V and IX (Mage)
		// Unlocks Heaven
		
		// 20 - Heaven - God
		// Dragon God - Endgame
		// Unlocks Hell (Secret)
		
		// 21 - Hell - God
		// Dragon God (Hell) - Endgame
	};
	
	
	public static Attack[][] getPlayerNormalAttackList(){
		return playerNormalAttacks;
	}
	
	
	public static Attack[][] getPlayerSpecialAttackList(){
		return playerSpecialAttacks;
	}
	
	
	public static Attack[][] getPlayerEnemyAttackList(){
		return enemyAttacks;
	}
	
	
	public static Item[] getNormalWeaponItemList(){
		return normalWeaponItems;
	}
	
	
	public static Item[] getSpecialWeaponItemList(){
		return specialWeaponItems;
	}
	
	
	public static Item[] getArmorItemList(){
		return armorItems;
	}
	
	
	public static Item[] getConsumableItemList(){
			return consumableItems;
	}
	
	
	public static Item[] getMiscItemList(){
		return miscItems;
	}
	
	
	public static Enemy[] getEnemyList(){
		return enemies;
	}
	
	
	public static Area[] getAreaList(){
		return areas;
	}
	
	
	public static int[] getItemInfo(Item item){
		
		int listNum = -1;
		int itemNum = -1;
		
		for(int i = 0; i < normalWeaponItems.length; i++){
			if(normalWeaponItems[i].getName().equals(item.getName())){
				listNum = 0;
				itemNum = i;
			}
		}
		
		for(int i = 0; i < specialWeaponItems.length; i++){
			if(specialWeaponItems[i].getName().equals(item.getName())){
				listNum = 1;
				itemNum = i;
			}
		}
		
		for(int i = 0; i < armorItems.length; i++){
			if(armorItems[i].getName().equals(item.getName())){
				listNum = 2;
				itemNum = i;
			}
		}
		
		for(int i = 0; i < consumableItems.length; i++){
			if(consumableItems[i].getName().equals(item.getName())){
				listNum = 3;
				itemNum = i;
			}
		}
		
		for(int i = 0; i < miscItems.length; i++){
			if(miscItems[i].getName().equals(item.getName())){
				listNum = 4;
				itemNum = i;
			}
		}
		
		int[] itemInfo = {listNum, itemNum};
		
		return itemInfo;
	}
	
	
	public static int getAreaInfo(Area area){
		
		int areaInfo = -1;
		
		for(int i = 0; i < areas.length; i++){
			if(areas[i].getName().equals(area.getName())){
				areaInfo = i;
			}
		}
		
		return areaInfo;
	}
}
