package com.helmetcheck;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.gameval.ItemID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import java.util.Set;

@Slf4j
@PluginDescriptor(
	name = "Helmet reminder",
	description = "warns if you forget a helmet",
	tags = {"hint", "gear", "head"}
)

//HelmetCheckPlugin is where the main logic is
public class HelmetCheckPlugin extends Plugin {
	@Inject private Client client;
	@Inject private HelmetCheckConfig config;
	@Inject private OverlayManager overlayManager;
	@Inject private HelmetCheckOverlay overlay;

	//This is where you add your items/helmets to show warning
	private static final Set<Integer> COMBAT_HELMETS = Set.of(
			ItemID.BLACK_FULL_HELM,
			ItemID.BRONZE_FULL_HELM,
			ItemID.IRON_FULL_HELM,
			ItemID.MITHRIL_FULL_HELM,
			ItemID.ADAMANT_FULL_HELM,
			ItemID.RUNE_FULL_HELM_BANDOS
			//This is where you can add more items

	);

	@Provides HelmetCheckConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(HelmetCheckConfig.class);
	}


	@Override
	protected void startUp(){
		overlay = new HelmetCheckOverlay(client,this);
		overlayManager.add(overlay);
	}
	@Override
	protected void shutDown(){
		overlayManager.remove(overlay);
	}

	//This is where our in game logic begins.
	//
	@Subscribe
	public void onGameTick(GameTick event){

		if(!config.warnNoHelmet()) return;
		ItemContainer equipment = client.getItemContainer((InventoryID.EQUIPMENT));
		if(equipment == null) return;

		Item helmet = equipment.getItem(EquipmentInventorySlot.HEAD.getSlotIdx());
		if(helmet == null){
			client.addChatMessage(ChatMessageType.GAMEMESSAGE,"","WARNING: You're not wearing a helmet",null);
		} else if (config.onlyCombatHelmets()&&!COMBAT_HELMETS.contains(helmet.getId())) {
			client.addChatMessage(ChatMessageType.GAMEMESSAGE,"","WARNING: Wear a proper combat helmet",null);


		}
	}

	public boolean isWearingHelmet() {
		ItemContainer equipment = client.getItemContainer(InventoryID.EQUIPMENT);
		if(equipment ==null) return false;
		Item helmet = equipment.getItem(EquipmentInventorySlot.HEAD.getSlotIdx());
		if(helmet == null){return false;}
		if(config.onlyCombatHelmets()){
			return COMBAT_HELMETS.contains(helmet.getId());
		}
		return true;
	}
}

