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

import net.milkbowl.vault.permission.Permission;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import fr.imagicraft.armorsurvey.listeners.PlayerPickupItemListener;

/**
 * Main plugin file
 * 
 * @author MinerCrafter
 */
public class ArmorSurvey extends JavaPlugin
{
	protected Permission perms;
	protected PlayerPickupItemListener ppuiListener;
	
	/**
	 * 
	 * @return Vault permissions system if enabled
	 */
	public Permission getPerms()
	{
		return perms;
	}
	
	@Override
	public void onEnable()
	{
		try
		{			
			// Initializing Vault permissions
			if ( null == this.getServer().getPluginManager().getPlugin( "Vault" ) )
				throw new Exception( "Vault isn't installed on this server." );

			RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration( Permission.class );
			if ( null == rsp )
				throw new Exception( "Vault isn't installed on this server." );

			this.perms = rsp.getProvider();
			if ( null == this.perms )
				throw new Exception( "Vault isn't installed on this server." );

			// Listeners
			this.ppuiListener = new PlayerPickupItemListener( this );
			this.getServer().getPluginManager().registerEvents( this.ppuiListener, this );
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.getPluginLoader().disablePlugin( this );
		}
	}

	@Override
	public void onDisable()
	{
		this.ppuiListener = null;
		this.perms = null;
	}

}
