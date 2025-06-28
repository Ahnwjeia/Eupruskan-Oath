package Ahnwjeia.EOATH.world.systems;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.impl.campaign.terrain.BaseRingTerrain;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.impl.campaign.terrain.MagneticFieldTerrainPlugin;
import com.fs.starfarer.api.util.Misc;
import Ahnwjeia.EOATH.world.EOATH_GEN;
import org.lazywizard.lazylib.MathUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static Ahnwjeia.EOATH.ModPlugin.eoath;

public class drotinsystem implements SectorGeneratorPlugin { //A SectorGeneratorPlugin is a class from the game, that identifies this as a script that will have a 'generate' method
    @Override
    public void generate(SectorAPI sector) { //the parameter sector is passed. This is the instance of the campaign map that this script will add a star system to
        //initialise system
        StarSystemAPI system = sector.createStarSystem("Drotin"); //create a new variable called system. this is assigned an instance of the new star system added to the Sector at the same time
        system.getLocation().set(12000, -41000); //sets location of system in hyperspace. map size is in the order of 100000x100000, and 0, 0 is the center of the map, this will set the location to the east and slightly south of the center
        system.setBackgroundTextureFilename("graphics/backgrounds/background4.jpg"); //sets the background image for when in the system. this is a filepath to an image in the core game files

        //set up star
        PlanetAPI star = system.initStar( //stars and planets are technically the same category of object, so stars use PlanetAPI
                "drotinstar", //set star id, this should be unique
                "star_yellow", //set star type, the type IDs come from starsector-core/data/campaign/procgen/star_gen_data.csv
                1200, //set radius, 900 is a typical radius size
                12000, //sets the location of the star's one-way jump point in hyperspace, since it is the center of the star system, we want it to be in the center of the star system jump points in hyperspace
                -41000,
                1000 //radius of corona terrain around star
        );

        //generate up to three entities in the centre of the system and returns the orbit radius of the furthest entity
        float innerOrbitDistance = StarSystemGenerator.addOrbitingEntities(
                system, //star system variable, used to add entities
                star, //focus object for entities to orbit
                StarAge.AVERAGE, //used by generator to decide which kind of planets to add
                0, //minimum number of entities
                0, //maximum number of entities
                4000, //the radius between the first generated entity and the focus object, in this case the star
                1, //used to assign roman numerals to the generated entities if not given special names
                true //generator will give unique names like "Ordog" instead of "Example Star System III"
        );

        //add first planet
        PlanetAPI planetOne = system.addPlanet( //assigns instance of newly created planet to variable planetOne
                "zemstvoplanet", //unique id string
                star, //orbit focus for planet
                "Zemstvo", //display name of planet
                "barren3", //planet type id, comes from starsector-core/data/campaign/procgen/planet_gen_data.csv
                35f, //starting angle in orbit
                320f, //planet size
                innerOrbitDistance + 1200, //1500 radius gap from the outer randomly generated entity created above
                365 //number of in-game days for it to orbit once
        );
        planetOne.setCustomDescriptionId("zemstvodesc");
        planetOne.setInteractionImage("illustrations", "mine");

        //use helper method from other script to easily configure the market. feel free to copy it into your own project
        MarketAPI zemstvomarket = drotinmarketadd.addMarketplace( //A Market is separate to a Planet, and contains data about population, industries and conditions. This is a method from the other script in this mod, that will assign all marketplace conditions to the planet in one go, making it simple and easy
                eoath, //Factions.INDEPENDENT references the id String of the Independent faction, so it is the same as writing "independent", but neater. This determines the Faction associated with this market
                planetOne, //the PlanetAPI variable that this market will be assigned to
                null, //some mods and vanilla will have additional floating space stations or other entities, that when accessed, will open this marketplace. We don't have any associated entities for this method to add, so we leave null
                "Zemstvo", //Display name of market
                6, //population size
                new ArrayList<>(Arrays.asList( //List of conditions for this method to iterate through and add to the market
                        Conditions.MILD_CLIMATE,
                        Conditions.POPULATION_6,
                        Conditions.ORE_RICH,
                        Conditions.RARE_ORE_ABUNDANT,
                        Conditions.HABITABLE,
                        Conditions.RUINS_WIDESPREAD,
                        Conditions.SOLAR_ARRAY
                )),
                new ArrayList<>(Arrays.asList( //list of submarkets for this method to iterate through and add to the market. if a military base industry was added to this market, it would be consistent to add a military submarket too
                        Submarkets.SUBMARKET_OPEN, //add a default open market
                        Submarkets.SUBMARKET_STORAGE, //add a player storage market
                        Submarkets.SUBMARKET_BLACK //add a black market
                )),
                new ArrayList<>(Arrays.asList( //list of industries for this method to iterate through and add to the market
                        Industries.POPULATION, //population industry is required for weirdness to not happen
                        Industries.MEGAPORT, //same with spaceport
                        Industries.MINING,
                        Industries.FARMING,
                        Industries.ORBITALWORKS,
                        Industries.HEAVYBATTERIES,
                        Industries.LIGHTINDUSTRY,
                        Industries.STARFORTRESS_MID,
                        Industries.HIGHCOMMAND,
                        Industries.WAYSTATION
                )),
                true, //if true, the planet will have visual junk orbiting and will play an ambient chatter audio track when the player is nearby
                false //used by the method to make a market hidden like a pirate base, not recommended for generating markets in a core world
        );

        PlanetAPI planetTwo = system.addPlanet( //assigns instance of newly created planet to variable planetOne
                "rodiniaplanet", //unique id string
                star, //orbit focus for planet
                "Rodinia", //display name of planet
                "rocky_metallic", //planet type id, comes from starsector-core/data/campaign/procgen/planet_gen_data.csv
                80f, //starting angle in orbit
                100f, //planet size
                innerOrbitDistance + 3600, //1500 radius gap from the outer randomly generated entity created above
                584 //number of in-game days for it to orbit once,
        );
        planetTwo.setCustomDescriptionId("rodiniadesc");
        planetTwo.setInteractionImage("illustrations", "ilm");

        //use helper method from other script to easily configure the market. feel free to copy it into your own project
        MarketAPI rodiniamarket = drotinmarketadd.addMarketplace( //A Market is separate to a Planet, and contains data about population, industries and conditions. This is a method from the other script in this mod, that will assign all marketplace conditions to the planet in one go, making it simple and easy
                eoath, //Factions.INDEPENDENT references the id String of the Independent faction, so it is the same as writing "independent", but neater. This determines the Faction associated with this market
                planetTwo, //the PlanetAPI variable that this market will be assigned to
                null, //some mods and vanilla will have additional floating space stations or other entities, that when accessed, will open this marketplace. We don't have any associated entities for this method to add, so we leave null
                "Rodinia", //Display name of market
                6, //population size
                new ArrayList<>(Arrays.asList( //List of conditions for this method to iterate through and add to the market
                        Conditions.COLD,
                        Conditions.POPULATION_6,
                        Conditions.ORE_MODERATE,
                        Conditions.RARE_ORE_ULTRARICH,
                        Conditions.VOLATILES_PLENTIFUL,
                        Conditions.HABITABLE,
                        Conditions.LOW_GRAVITY,
                        Conditions.METEOR_IMPACTS
                )),
                new ArrayList<>(Arrays.asList( //list of submarkets for this method to iterate through and add to the market. if a military base industry was added to this market, it would be consistent to add a military submarket too
                        Submarkets.SUBMARKET_OPEN, //add a default open market
                        Submarkets.SUBMARKET_STORAGE, //add a player storage market
                        Submarkets.SUBMARKET_BLACK //add a black market
                )),
                new ArrayList<>(Arrays.asList( //list of industries for this method to iterate through and add to the market
                        Industries.POPULATION, //population industry is required for weirdness to not happen
                        Industries.MEGAPORT, //same with spaceport
                        Industries.MINING,
                        Industries.FUELPROD,
                        Industries.HEAVYBATTERIES,
                        Industries.STARFORTRESS_MID
                )),
                true, //if true, the planet will have visual junk orbiting and will play an ambient chatter audio track when the player is nearby
                false //used by the method to make a market hidden like a pirate base, not recommended for generating markets in a core world
        );

        float stationOneAngle = 15f;
        SectorEntityToken stationOne = system.addCustomEntity("burlakstation", "Burlak", "station_sporeship_derelict", eoath);
        stationOne.setCircularOrbitPointingDown(star,stationOneAngle,2900, -85);
        stationOne.setInteractionImage("illustrations", "abandoned_station");
        stationOne.setCustomDescriptionId("burlakdesc");

        //use helper method from other script to easily configure the market. feel free to copy it into your own project
        MarketAPI stationOnemarket = drotinmarketadd.addMarketplace( //A Market is separate to a Planet, and contains data about population, industries and conditions. This is a method from the other script in this mod, that will assign all marketplace conditions to the planet in one go, making it simple and easy
                eoath, //Factions.INDEPENDENT references the id String of the Independent faction, so it is the same as writing "independent", but neater. This determines the Faction associated with this market
                stationOne, //the PlanetAPI variable that this market will be assigned to
                null, //some mods and vanilla will have additional floating space stations or other entities, that when accessed, will open this marketplace. We don't have any associated entities for this method to add, so we leave null
                "Burlak", //Display name of market
                6, //population size
                new ArrayList<>(Arrays.asList( //List of conditions for this method to iterate through and add to the market
                        Conditions.POPULATION_6
                )),
                new ArrayList<>(Arrays.asList( //list of submarkets for this method to iterate through and add to the market. if a military base industry was added to this market, it would be consistent to add a military submarket too
                        Submarkets.SUBMARKET_OPEN, //add a default open market
                        Submarkets.SUBMARKET_STORAGE, //add a player storage market
                        Submarkets.SUBMARKET_BLACK //add a black market
                )),
                new ArrayList<>(Arrays.asList( //list of industries for this method to iterate through and add to the market
                        Industries.POPULATION, //population industry is required for weirdness to not happen
                        Industries.MEGAPORT, //same with spaceport
                        Industries.HEAVYBATTERIES,
                        Industries.ORBITALWORKS,
                        Industries.REFINING,
                        Industries.STARFORTRESS_MID
                )),
                true, //if true, the planet will have visual junk orbiting and will play an ambient chatter audio track when the player is nearby
                false //used by the method to make a market hidden like a pirate base, not recommended for generating markets in a core world
        );

        //add an asteroid belt. asteroids are separate entities inside these, it will randomly distribute a defined number of them around the ring
        system.addAsteroidBelt(
                star, //orbit focus
                210, //number of asteroid entities
                innerOrbitDistance + 3600, //orbit radius is 500 gap for outer randomly generated entity above
                520, //width of band
                190, //minimum and maximum visual orbit speeds of asteroids
                220,
                Terrain.ASTEROID_BELT, //ID of the terrain type that appears in the section above the abilities bar
                "Knout Belt" //display name
        );

        //add a ring texture. it will go under the asteroid entities generated above
        system.addRingBand(star,
                "misc", //used to access band texture, this is the name of a category in settings.json
                "rings_dust0", //specific texture id in category misc in settings.json
                256f, //texture width, can be used for scaling shenanigans
                1,
                Color.white, //colour tint
                256f, //band width in game
                innerOrbitDistance + 3550, //same as above
                200f,
                null,
                null
        );
        //add a ring texture. it will go under the asteroid entities generated above
        system.addRingBand(star,
                "misc", //used to access band texture, this is the name of a category in settings.json
                "rings_dust0", //specific texture id in category misc in settings.json
                256f, //texture width, can be used for scaling shenanigans
                0,
                Color.white, //colour tint
                256f, //band width in game
                innerOrbitDistance + 3400, //same as above
                200f,
                null,
                null
        );
        system.addRingBand(star,
                "misc", //used to access band texture, this is the name of a category in settings.json
                "rings_asteroids0", //specific texture id in category misc in settings.json
                256f, //texture width, can be used for scaling shenanigans
                0,
                Color.white, //colour tint
                256f, //band width in game
                innerOrbitDistance + 3700, //same as above
                200f,
                null,
                null
        );

        system.addRingBand(star, "misc", "rings_dust0", 256f, 0, Color.white, 256f, 10200, 80f);
        system.addRingBand(star, "misc", "rings_dust0", 256f, 1, Color.white, 256f, 10400, 100f);
        system.addRingBand(star, "misc", "rings_dust0", 256f, 2, Color.white, 256f, 10600, 130f);
        system.addRingBand(star, "misc", "rings_dust0", 256f, 1, Color.white, 256f, 10800, 80f);


        // add one ring that covers all of the above
        SectorEntityToken ring = system.addTerrain(Terrain.RING, new BaseRingTerrain.RingParams(600 + 256, 10500, null, "Mirovia Disk"));
        ring.setCircularOrbit(star, 0, 0, 100);

        SectorEntityToken zemstvoField = system.addTerrain(Terrain.MAGNETIC_FIELD,
                new MagneticFieldTerrainPlugin.MagneticFieldParams(planetOne.getRadius() + 500f,
                        (planetOne.getRadius() + 500f) / 2f,
                planetOne,
                planetOne.getRadius() + 150f,
                planetOne.getRadius() + 150f + 550,
                new Color(80, 20, 60, 100), // base color
                0.25f, // probability to spawn aurora sequence
                new Color(230, 70, 200),
                new Color(180, 80, 210),
                new Color(150, 100, 200),
                new Color(110, 120, 250),
                new Color(70, 100, 240),
                new Color(240, 70, 130),
                new Color(190, 110, 150)));
        zemstvoField.setCircularOrbit(planetOne, 0, 0, 100);

        //add makeshift comm relay entity to system
        SectorEntityToken makeshiftRelay = system.addCustomEntity(
                "drotin_domain_relay",
                "Drotin System Relay",
                Entities.COMM_RELAY,
                eoath
        );
        //assign an orbit
        makeshiftRelay.setCircularOrbit(star, 270f, innerOrbitDistance + 3900f, 950f); //assign an orbit

        //add domain sensor array
        SectorEntityToken sensorArray = system.addCustomEntity(
                "drotin_domain_sensor",
                "Drotin Sensor Array",
                Entities.SENSOR_ARRAY,
                eoath
        );
        //assign an orbit, point down ensures it rotates to point towards center while orbiting
        sensorArray.setCircularOrbitPointingDown(star, 90f, innerOrbitDistance + 2500f, 200f);

        //domain nav buoy
        SectorEntityToken navBuoy = system.addCustomEntity(
                "drotin_nav_buoy",
                "Drotin Navigation Beacon",
                Entities.NAV_BUOY,
                eoath
        );
        //assign orbit, this time it is orbiting planetOne
        navBuoy.setCircularOrbitPointingDown(star, 0f, innerOrbitDistance + 8200f, 200f);

        //autogenerate jump points that will appear in hyperspace and in system
        system.autogenerateHyperspaceJumpPoints(true, true);

        //the following is hyperspace cleanup code that will remove hyperstorm clouds around this system's location in hyperspace
        //don't need to worry about this, it's more or less copied from vanilla

        //set up hyperspace editor plugin
        HyperspaceTerrainPlugin hyperspaceTerrainPlugin = (HyperspaceTerrainPlugin) Misc.getHyperspaceTerrain().getPlugin(); //get instance of hyperspace terrain
        NebulaEditor nebulaEditor = new NebulaEditor(hyperspaceTerrainPlugin); //object used to make changes to hyperspace nebula

        //set up radiuses in hyperspace of system
        float minHyperspaceRadius = hyperspaceTerrainPlugin.getTileSize() * 2f; //minimum radius is two 'tiles'
        float maxHyperspaceRadius = system.getMaxRadiusInHyperspace();

        //hyperstorm-b-gone (around system in hyperspace)
        nebulaEditor.clearArc(system.getLocation().x, system.getLocation().y, 0, minHyperspaceRadius + maxHyperspaceRadius, 0f, 360f, 0.25f);
    }
}

