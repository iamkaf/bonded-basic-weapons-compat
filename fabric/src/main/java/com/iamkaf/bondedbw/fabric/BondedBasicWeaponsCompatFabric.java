package com.iamkaf.bondedbw.fabric;

import com.iamkaf.bonded.api.API;
import com.iamkaf.bondedbw.BondedBW;
import com.seacroak.basicweapons.registry.MainRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;

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
                API.addUpgrade(item, to, ((TieredItem) to).getTier().getRepairIngredient());
            }
        });
        MainRegistry.stoneWeapons.forEach(item -> {
            API.addExperienceCap(item, 30);
            Optional<Item> upgrade = MainRegistry.ironWeapons.stream()
                    .filter(item1 -> item1.getClass().equals(item.getClass()))
                    .findFirst();
            if (upgrade.isPresent()) {
                Item to = upgrade.get();
                API.addUpgrade(item, to, ((TieredItem) to).getTier().getRepairIngredient());
            }
        });
        MainRegistry.ironWeapons.forEach(item -> {
            API.addExperienceCap(item, 100);
            Optional<Item> upgrade = MainRegistry.diamondWeapons.stream()
                    .filter(item1 -> item1.getClass().equals(item.getClass()))
                    .findFirst();
            if (upgrade.isPresent()) {
                Item to = upgrade.get();
                API.addUpgrade(item, to, ((TieredItem) to).getTier().getRepairIngredient());
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
