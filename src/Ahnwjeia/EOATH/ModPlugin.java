package Ahnwjeia.EOATH;

import Ahnwjeia.EOATH.world.EOATH_GEN;
import Ahnwjeia.EOATH.world.EOATH_REP;
import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;

public class ModPlugin extends BaseModPlugin {
    public static final String eoath="eupruskan_oath";
    @Override
    public void onNewGame() {
        initeoath();
        new EOATH_GEN().generate(Global.getSector());
        generate(Global.getSector());
    }
    public void generate(SectorAPI sector){
        initFactionRelationships(sector);
    }

    private static void initeoath(){
        Global.getSector().addScript((new EOATH_REP()));
    }


    public static void initFactionRelationships(SectorAPI sector) {
        FactionAPI eoath = sector.getFaction(ModPlugin.eoath);

        eoath.setRelationship(Factions.PLAYER, -0.4f);
        eoath.setRelationship(Factions.HEGEMONY, -1f);
        eoath.setRelationship(Factions.TRITACHYON, -1f);
        eoath.setRelationship(Factions.PIRATES, -1f);
        eoath.setRelationship(Factions.INDEPENDENT, -0.4f);
        eoath.setRelationship(Factions.LUDDIC_CHURCH, -1f);
        eoath.setRelationship(Factions.KOL, -1f);
        eoath.setRelationship(Factions.LUDDIC_PATH, -1f);
        eoath.setRelationship(Factions.PERSEAN, -0.4f);
        eoath.setRelationship(Factions.DIKTAT, -1f);
        eoath.setRelationship(Factions.LIONS_GUARD, -1f);
    }
}