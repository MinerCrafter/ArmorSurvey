/*
	This file is part of ArmorSurvey.

    ArmorSurvey is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ArmorSurvey is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with ArmorSurvey.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.imagicraft.armorsurvey;

import java.util.Map;

import org.bukkit.Material;

import com.google.common.collect.Maps;

/**
 * List of all armor items supported by MineCraft.
 * 
 * @author MinerCrafter
 */
public enum ArmorItems {
	LEATHER_HELMET(			Material.LEATHER_HELMET,		ArmorItemsTypes.HELMET,			5 ),
	LEATHER_CHESTPLATE(		Material.LEATHER_CHESTPLATE,	ArmorItemsTypes.CHESTPLATE,		5 ),
	LEATHER_LEGGINGS(		Material.LEATHER_LEGGINGS,		ArmorItemsTypes.LEGGINGS,		5 ),
	LEATHER_BOOTS(			Material.LEATHER_BOOTS,			ArmorItemsTypes.BOOTS,			5 ),
	CHAINMAIL_HELMET(		Material.CHAINMAIL_HELMET,		ArmorItemsTypes.HELMET,			3 ),
	CHAINMAIL_CHESTPLATE(	Material.CHAINMAIL_CHESTPLATE,	ArmorItemsTypes.CHESTPLATE,		3 ),
	CHAINMAIL_LEGGINGS(		Material.CHAINMAIL_LEGGINGS,	ArmorItemsTypes.LEGGINGS,		3 ),
	CHAINMAIL_BOOTS(		Material.CHAINMAIL_BOOTS,		ArmorItemsTypes.BOOTS,			3 ),
	IRON_HELMET(			Material.IRON_HELMET,			ArmorItemsTypes.HELMET,			2 ),
	IRON_CHESTPLATE(		Material.IRON_CHESTPLATE,		ArmorItemsTypes.CHESTPLATE,		2 ),
	IRON_LEGGINGS(			Material.IRON_LEGGINGS,			ArmorItemsTypes.LEGGINGS,		2 ),
	IRON_BOOTS(				Material.IRON_BOOTS,			ArmorItemsTypes.BOOTS,			2 ),
	GOLD_HELMET(			Material.GOLD_HELMET,			ArmorItemsTypes.HELMET,			4 ),
	GOLD_CHESTPLATE(		Material.GOLD_CHESTPLATE,		ArmorItemsTypes.CHESTPLATE,		4 ),
	GOLD_LEGGINGS(			Material.GOLD_LEGGINGS,			ArmorItemsTypes.LEGGINGS,		4 ),
	GOLD_BOOTS(				Material.GOLD_BOOTS,			ArmorItemsTypes.BOOTS,			4 ),
	DIAMOND_HELMET(			Material.DIAMOND_HELMET,		ArmorItemsTypes.HELMET, 		1 ),
	DIAMOND_CHESTPLATE(		Material.DIAMOND_CHESTPLATE,	ArmorItemsTypes.CHESTPLATE,		1 ),
	DIAMOND_LEGGINGS(		Material.DIAMOND_LEGGINGS,		ArmorItemsTypes.LEGGINGS,		1 ),
	DIAMOND_BOOTS(			Material.DIAMOND_BOOTS,			ArmorItemsTypes.BOOTS,			1 );
	
	private final Material material;
	private final ArmorItemsTypes type;
	private final int priority;
	private final static Map<Material, ArmorItems> BY_MATERIAL = Maps.newHashMap();
	
	private ArmorItems( final Material item, final ArmorItemsTypes type, int priority ) {
		this.material = item;
		this.type = type;
		this.priority = priority;
	}
	
	static {
		for( ArmorItems armorItem : ArmorItems.values() ) {
			BY_MATERIAL.put(armorItem.getMaterial(), armorItem);
		}
	}
	
	/**
	 * Returns the material of the item
	 * 
	 * @return Material of the item
	 */
	public Material getMaterial() {
		return this.material;
	}
	
	/**
	 * Returns the type of the item
	 * 
	 * @return Item type
	 */
	public ArmorItemsTypes getType() {
		return this.type;
	}
	
	/**
	 * Returns the priority of the armor item
	 * 
	 * 1 - Diamond
	 * 2 - Iron
	 * 3 - Chain
	 * 4 - Gold
	 * 5 - Leather
	 * 
	 * @return Priority of the armor item
	 */
	public int getPriority() {
		return this.priority;
	}
	
	/**
	 * Returns the maximum durability of the item
	 * 
	 * @return Maximum durability of the item
	 */
	public int getDurability()
	{
		return this.material.getMaxDurability();
	}
	
	/**
	 * Check if it's a helmet item
	 * 
	 * @return Result of check
	 */
	public boolean isHelmet() {
		return this.type == ArmorItemsTypes.HELMET;
	}
	
	/**
	 * Check if it's a chestplate item
	 * 
	 * @return Result of check
	 */
	public boolean isChestplate() {
		return this.type == ArmorItemsTypes.CHESTPLATE;
	}
	
	/**
	 * Check if it's a leggings item
	 * 
	 * @return Result of check
	 */
	public boolean isLeggings() {
		return this.type == ArmorItemsTypes.LEGGINGS;
	}
	
	/**
	 * Check if it's a boots item
	 * 
	 * @return Result of check
	 */
	public boolean isBoots() {
		return this.type == ArmorItemsTypes.BOOTS;
	}
	
	/**
	 * Check if the picked up item must replace the inventory item
	 * 
	 * @param invItem The actually inventory item
	 * @return True if the picked up item is priority than the inventory item
	 */
	public boolean checkPriority( ArmorItems invItem ) {
		return ( this.priority < invItem.getPriority() );
	}
	
	/**
	 * Check if the picked up item must replace the inventory item
	 * 
	 * @param invItem The actually inventory item
	 * @return True if the picked up item is priority than the inventory item
	 */
	public boolean checkPriority( Material invItem ) {
		return ( this.priority < ArmorItems.getByMaterial( invItem ).getPriority() );
	}
	
	/**
	 * Check if the item is an armor item
	 * 
	 * @param item Item to check
	 * @return Result of check
	 */
	public static boolean isArmorItem( Material item ) {
		return BY_MATERIAL.containsKey( item );
	}
	
	/**
	 * Returns the ArmorItems by specifying a Material
	 * 
	 * @param item Item Material
	 * @return ArmorItems item
	 */
	public static ArmorItems getByMaterial( Material item ) {
		return BY_MATERIAL.get( item );
	}
}
