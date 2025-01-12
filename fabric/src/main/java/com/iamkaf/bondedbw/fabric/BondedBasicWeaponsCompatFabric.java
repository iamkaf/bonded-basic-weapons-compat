package com.iamkaf.bondedbw.fabric;

import com.iamkaf.bonded.api.API;
import com.iamkaf.bondedbw.BondedBW;
import com.khazoda.basicweapons.registry.WeaponRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;

import java.util.Optional;

public final class BondedBasicWeaponsCompatFabric implements ModInitializer {
    private static void loadItemExperienceAndUpgrades(MinecraftServer minecraftServer) {
        WeaponRegistry.getItemsByMaterial(Tiers.WOOD).forEach(item -> {
            API.addExperienceCap(item, 30);
            Optional<Item> upgrade = WeaponRegistry.getItemsByMaterial(Tiers.STONE)
                    .stream()
                    .filter(item1 -> item1.getClass().equals(item.getClass()))
                    .findFirst();
            if (upgrade.isPresent()) {
                Item to = upgrade.get();
                API.addUpgrade(item, to, ((TieredItem) to).getTier().getRepairIngredient());
            }
        });
        WeaponRegistry.getItemsByMaterial(Tiers.STONE).forEach(item -> {
            API.addExperienceCap(item, 30);
            Optional<Item> upgrade = WeaponRegistry.getItemsByMaterial(Tiers.IRON)
                    .stream()
                    .filter(item1 -> item1.getClass().equals(item.getClass()))
                    .findFirst();
            if (upgrade.isPresent()) {
                Item to = upgrade.get();
                API.addUpgrade(item, to, ((TieredItem) to).getTier().getRepairIngredient());
            }
        });
        WeaponRegistry.getItemsByMaterial(Tiers.IRON).forEach(item -> {
            API.addExperienceCap(item, 100);
            Optional<Item> upgrade = WeaponRegistry.getItemsByMaterial(Tiers.DIAMOND)
                    .stream()
                    .filter(item1 -> item1.getClass().equals(item.getClass()))
                    .findFirst();
            if (upgrade.isPresent()) {
                Item to = upgrade.get();
                API.addUpgrade(item, to, ((TieredItem) to).getTier().getRepairIngredient());
            }
        });
        WeaponRegistry.getItemsByMaterial(Tiers.GOLD).forEach(item -> {
            // gold doesn't upgrade
            API.addExperienceCap(item, 300);
        });
        WeaponRegistry.getItemsByMaterial(Tiers.DIAMOND).forEach(item -> {
            // diamond to netherite upgrade happens on the smithing table
            API.addExperienceCap(item, 500);
        });
        WeaponRegistry.getItemsByMaterial(Tiers.NETHERITE).forEach(item -> {
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
