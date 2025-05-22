package com.helmetcheck;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;


//HelmetCheckConfig is where we have user settings
public interface HelmetCheckConfig extends Config
{
	@ConfigItem(
		keyName = "warnNoHelmet",
		name = "Warn if no helmet",
		description = "Show a warning when not wearing a helmet"
	)
	default boolean warnNoHelmet() {return true;}

	@ConfigItem(
			keyName = "onlyCombatHelmets",
			name="Only combat helmets",
			description = "Only warn for proper combat helmets"
	)	default boolean onlyCombatHelmets() {return false;}
}
