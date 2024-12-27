package com.iamkaf.bondedbw.fabric;

import com.iamkaf.bonded.api.API;
import com.iamkaf.bondedbw.BondedBW;
import com.seacroak.basicweapons.registry.MainRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

import java.util.Optional;

public final class BondedBasicWeaponsCompatFabric implements ModInitializer {
    private static void loadItemExperienceAndUpgrades(MinecraftServer minecraftServer) {
        MainRegistry.woodenWeapons.forEach(item -> {
            API.addExperienceCap(item, 30);
            Optional<Item> upgrade = MainRegistry.stoneWeapons.stream()
                    .filter(item1 -> item1.getClass().equals(item.getClass()))
                    .findFirst();
            if (upgrade.isPresent()) {
                Item to = upgrade.get();
                API.addUpgrade(item, to, ItemTags.STONE_TOOL_MATERIALS);
            }
        });
        MainRegistry.stoneWeapons.forEach(item -> {
            API.addExperienceCap(item, 30);
            Optional<Item> upgrade = MainRegistry.ironWeapons.stream()
                    .filter(item1 -> item1.getClass().equals(item.getClass()))
                    .findFirst();
            if (upgrade.isPresent()) {
                Item to = upgrade.get();
                API.addUpgrade(item, to, ItemTags.IRON_TOOL_MATERIALS);
            }
        });
        MainRegistry.ironWeapons.forEach(item -> {
            API.addExperienceCap(item, 100);
            Optional<Item> upgrade = MainRegistry.diamondWeapons.stream()
                    .filter(item1 -> item1.getClass().equals(item.getClass()))
                    .findFirst();
            if (upgrade.isPresent()) {
                Item to = upgrade.get();
                API.addUpgrade(item, to, ItemTags.DIAMOND_TOOL_MATERIALS);
            }
        });
        MainRegistry.goldenWeapons.forEach(item -> {
            // gold doesn't upgrade
            API.addExperienceCap(item, 300);
        });
        MainRegistry.diamondWeapons.forEach(item -> {
            // diamond to netherite upgrade happens on the smithing table
            API.addExperienceCap(item, 500);
        });
        MainRegistry.netheriteWeapons.forEach(item -> {
            // netherite doesn't upgrade
            API.addExperienceCap(item, 1000);
        });
    }

    @Override
    public void onInitialize() {
        BondedBW.init();
        ServerLifecycleEvents.SERVER_STARTED.register(BondedBasicWeaponsCompatFabric::loadItemExperienceAndUpgrades);
    }
}
