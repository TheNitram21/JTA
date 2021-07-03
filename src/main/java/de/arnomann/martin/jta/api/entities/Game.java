package de.arnomann.martin.jta.api.entities;

/**
 * Represents a stream game.
 */
public enum Game {
    MINECRAFT("Minecraft"),
    JUST_CHATTING("Just Chatting"),
    PLANET_COASTER("Planet Coaster"),
    POKEMON_FIRERED("Pokémon FireRed/LeafGreen"),
    IT_TAKES_TWO("It Takes Two"),
    VALORANT("VALORANT"),
    DEAD_BY_DAYLIGHT("Dead by Daylight"),
    WII_SPORTS("Wii Sports"),
    FORTNITE("Fortnite"),
    PHASMOPHOBIA("Phasmophobia"),
    APEX_LEGENDS("Apex Legends"),
    THE_SIMS_FOUR("The Sims 4"),
    KEEP_TALKING_AND_NOBODY_EXPLODES("Keep Talking and Nobody Explodes"),
    SPORTS("Sports"),
    ROCKET_LEAGUE("Rocket League"),
    SEA_OF_THIEVES("Sea of Thieves"),
    GRAND_THEFT_AUTO_FIVE("Grand Theft Auto 5"),
    CLASH_ROYALE("Clash Royale"),
    AMONG_US("Among Us"),
    ART("Art"),
    GEOGUESSER("GeoGuesser"),
    LEAGUE_OF_LEGENDS("League of Legends"),
    FALL_GUYS("Fall Guys: Ultimate Knockout"),
    ROBLOX("Roblox"),
    MUSIC("Music"),
    CALL_OF_DUTY_WARZONE("Call of Duty: Warzone"),
    BEAT_SABER("Beat Saber"),
    GARTIC_PHONE("Gartic Phone"),
    TOM_CLANCYS_RAINBOW_SIX_SIEGE("Tom Clancy's Rainbow Six Siege"),
    GENSHIN_IMPACT("Genshin Impact"),
    TALKSHOWS_AND_PODCASTS("Talk Shows & Podcasts"),
    ARK_SURVIVAL_EVOLVED("Ark: Survival Evolved"),
    ESCAPE_FROM_TARKOV("Escape from Tarkov"),
    RAFT("Raft"),
    PRISON_ARCHITECT("Prison Architect"),
    OVERCOOCED_TWO("Overcooked! 2"),
    ASMR("ASMR"),
    SNOWRUNNER("SnowRunner"),
    OVERWATCH("Overwatch"),
    COUNTER_STRIKE_GLOBAL_OFFENSIVE("Counter-Strike: Global Offensive"),
    STARDEW_VALLEY("Stardew Valley"),
    POKEMON_SWORD_SHIELD("Pokémon Sowrd/Shield"),
    FRIDAY_NIGHT_FUNKIN("FRIDAY NIGHT FUNKIN'"),
    MARBLES_ON_STREAM("Marbles On Stream"),
    RUST("Rust"),
    DETROIT_BECOME_HUMAN("Detroit: Become Human"),
    FIREWORKS_MANIA("Fireworks Mania"),
    HOUSE_FLIPPER("House Flipper"),
    VRCHAT("VRChat"),
    THE_FOREST("The Forest"),
    PUMMEL_PARTY("Pummel Party"),
    RESIDENT_EVIL_VILLAGE("Resident Evil Village"),
    DOKI_DOKI_LITERATURE_CLUB_PLUS("Doki Doki Literature Club Plus!"),
    SUBNAUTICA_BELOW_ZERO("Subnautica: Below Zero"),
    RESIDENT_EVIL_SEVEN_BIOHAZARD("Resident Evil 7 biohazard"),
    POLICE_SIMULATOR_PATROL_OFFICERS("Police Simulator: Patrol Officers"),
    BRAWL_STARS("Brawl Stars"),
    OCTODAD_DADLIEST_CATCH("Octodad: Dadliest Catch"),
    MARIO_GOLF_SUPER_RUSH("Mario Golf: Super Rush"),
    THE_BINDING_OF_ISAAC_REPENTANCE("The Binding of Isaac: Repentance"),
    THE_LEGEND_OF_ZELDA_BREATH_OF_THE_WILD("The Legend of Zelda: Breath of the Wild");

    private final java.lang.String name;


    Game(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Game getByName(String name) {
        for(Game game : values()) {
            if(game.getName().equals(name))
                return game;
        }
        return null;
    }
}
