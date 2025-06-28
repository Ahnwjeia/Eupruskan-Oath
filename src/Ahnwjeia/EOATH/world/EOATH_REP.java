package Ahnwjeia.EOATH.world;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;

import java.util.HashMap;
import java.util.Map;

import static Ahnwjeia.EOATH.ModPlugin.eoath;

public class EOATH_REP implements EveryFrameScript{
    private static final String MAIN_FACTION = eoath;

    private static final Map<String, Float> FACTION_RELATION_MAXIMUMS = new HashMap<>();
    static {
        FACTION_RELATION_MAXIMUMS.put(Factions.HEGEMONY, -1.00f);
        FACTION_RELATION_MAXIMUMS.put(Factions.LUDDIC_PATH, -1.00f);
        FACTION_RELATION_MAXIMUMS.put(Factions.KOL, -1.00f);
        FACTION_RELATION_MAXIMUMS.put(Factions.LUDDIC_CHURCH, -1.00f);
        FACTION_RELATION_MAXIMUMS.put(Factions.PIRATES, -1.00f);
        FACTION_RELATION_MAXIMUMS.put(Factions.PERSEAN, -0.60f);
        FACTION_RELATION_MAXIMUMS.put(Factions.TRITACHYON, -0.60f);
        FACTION_RELATION_MAXIMUMS.put(Factions.LIONS_GUARD, -1.00f);
        FACTION_RELATION_MAXIMUMS.put(Factions.DIKTAT, -1.00f);
    }

    @Override
    public boolean isDone() {
        return false;
    }
    @Override
    public boolean runWhilePaused() {
        return false;
    }

    @Override
    public void advance( float amount ) {
        SectorAPI sector = Global.getSector();
        if (sector == null) {
            return;
        }
        if (sector.getFaction(MAIN_FACTION) == null) {
            return;
        }
}}
