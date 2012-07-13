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

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.Material;

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
		
		// Allowed to do this ?
		if ( ! this.base.getPerms().has( event.getPlayer(), "armorsurvey.use" ) ) {
			return;
		}
		
		// Getting player's inventory
		PlayerInventory inv = event.getPlayer().getInventory();
		if ( null == inv ) {
			return;
		}
		
		// What is the part of armor ?
		if ( armorItem.isHelmet() && inv.getHelmet() == null ) {
			inv.setHelmet( event.getItem().getItemStack() );
		}
		else if ( armorItem.isChestplate() && inv.getChestplate() == null ) {
			inv.setChestplate( event.getItem().getItemStack() );
		}
		else if ( armorItem.isLeggings() && inv.getLeggings() == null ) {
			inv.setLeggings( event.getItem().getItemStack() );
		}
		else if ( armorItem.isBoots() && inv.getBoots() == null ) {
			inv.setBoots( event.getItem().getItemStack() );
		}
		else {
			return;
		}
		
		// The item is correctly put in an armor slot of the player inventory ?

		event.getItem().remove();

		// Todo : Play pickup sound
		//event.getPlayer().getWorld().playEffect(event.getPlayer().getLocation(), Effect.CLICK2, 0);
		event.setCancelled( true );
	}
}
