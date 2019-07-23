package com.github.brun0xp.orateplugin;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class WorldGuardWrapper {

    public List<String> getRegionsName(Location location) {
        RegionManager regionManager = Main.getWorldGuard().getRegionManager(location.getWorld());
        ApplicableRegionSet set = regionManager.getApplicableRegions(location);
        List<String> names = new ArrayList<>();
        for (ProtectedRegion region : set) {
            names.add(region.getId());
        }
        return names;
    }
}
