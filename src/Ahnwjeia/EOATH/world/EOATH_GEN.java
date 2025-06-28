package Ahnwjeia.EOATH.world;

import Ahnwjeia.EOATH.ModPlugin;
import Ahnwjeia.EOATH.world.systems.drotinsystem;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.impl.campaign.ids.Factions;

import static Ahnwjeia.EOATH.ModPlugin.eoath;


public class EOATH_GEN implements SectorGeneratorPlugin {
    @Override
    public void generate(SectorAPI sector) {
        new drotinsystem().generate(sector);
    }
}