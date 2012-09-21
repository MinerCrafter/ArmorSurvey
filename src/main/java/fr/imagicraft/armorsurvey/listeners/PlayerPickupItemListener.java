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

package fr.imagicraft.armorsurvey.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.Material;
import org.bukkit.Sound;

import fr.imagicraft.armorsurvey.ArmorItems;
import fr.imagicraft.armorsurvey.ArmorSurvey;

/**
 * Listen all picked up items
 * 
 * @author MinerCrafter
 */
public class PlayerPickupItemListener implements Listener {
	
	/**
	 * Root plugin
	 */
	protected ArmorSurvey base;
	
	/**
	 * Constructor
	 * 
	 * @param Root plugin instance
	 */
	public PlayerPickupItemListener( ArmorSurvey base ) {
		this.base = base;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onItemPickup( PlayerPickupItemEvent event )
	{
		if ( event.isCancelled() ) {
			return;
		}
		
		// Getting item type
		Material itemType = event.getItem().getItemStack().getType();
		if ( null == itemType ) {
			return;
		}

		// The item is a part of an armor ?
		ArmorItems armorItem = ArmorItems.getByMaterial( itemType );
		if ( null == armorItem ) {
			return;
		}
		
		Player player = event.getPlayer();
		
		// Allowed to do this ?
		if ( ! this.base.getPerms().has( player, "armorsurvey.use" ) ) {
			return;
		}
		
		// Getting player's inventory
		PlayerInventory inv = player.getInventory();
		if ( null == inv ) {
			return;
		}
		
		// What is the part of armor ?
		if ( armorItem.isHelmet() ) {
			if ( inv.getHelmet() == null ) {
				inv.setHelmet( event.getItem().getItemStack() );
			}
			else if ( armorItem.checkPriority( inv.getHelmet().getType() ) ) {
				inv.addItem( inv.getHelmet() );
				inv.setHelmet( event.getItem().getItemStack() );
			}
			else {
				return;
			}
		}
		else if ( armorItem.isChestplate() ) {
			if ( inv.getChestplate() == null ) {
				inv.setChestplate( event.getItem().getItemStack() );
			}
			else if ( armorItem.checkPriority( inv.getChestplate().getType() ) ) {
				inv.addItem( inv.getChestplate() );
				inv.setChestplate( event.getItem().getItemStack() );
			}
			else {
				return;
			}
		}
		else if ( armorItem.isLeggings() ) {
			if ( inv.getLeggings() == null ) {
				inv.setLeggings( event.getItem().getItemStack() );
			}
			else if ( armorItem.checkPriority( inv.getLeggings().getType() ) ) {
				inv.addItem( inv.getLeggings() );
				inv.setLeggings( event.getItem().getItemStack() );
			}
			else {
				return;
			}
		}
		else if ( armorItem.isBoots() && ( inv.getBoots() == null || armorItem.checkPriority( inv.getBoots().getType() ) ) ) {
			if ( inv.getBoots() == null ) {
				inv.setBoots( event.getItem().getItemStack() );
			}
			else if ( armorItem.checkPriority( inv.getBoots().getType() ) ) {
				inv.addItem( inv.getBoots() );
				inv.setBoots( event.getItem().getItemStack() );
			}
			else {
				return;
			}
		}
		else {
			return;
		}
		
		// The item is correctly put in an armor slot of the player inventory
		event.getItem().remove();

		// Play pickup sound
		player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1, 1);
		
		// Cancel the event
		event.setCancelled( true );
	}
}
