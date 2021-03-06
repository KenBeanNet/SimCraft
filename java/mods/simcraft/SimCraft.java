package mods.simcraft;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import mods.simcraft.common.Content;
import mods.simcraft.common.Repository;
import mods.simcraft.creative.CreativeTab;
import mods.simcraft.data.HomeManager;
import mods.simcraft.data.MarketManager;
import mods.simcraft.data.WorldManager;
import mods.simcraft.network.CommonProxy;
import mods.simcraft.network.PacketPipeline;
import mods.simcraft.player.PlayerEventListener;
import mods.simcraft.player.PlayerHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = SimCraft.MODID, version = SimCraft.VERSION)
public class SimCraft
{
    public static final String MODID = "simcraft";
    public static final String VERSION = "0.1";
    public static final String CLIENT_PROXY = "mods.simcraft.network.ClientProxy";
    public static final String SERVER_PROXY = "mods.simcraft.network.CommonProxy";
    
    @Mod.Instance("simcraft")
    public static SimCraft instance;
    
    @SidedProxy(clientSide =  SimCraft.CLIENT_PROXY, serverSide = SimCraft.SERVER_PROXY)
    public static CommonProxy proxy;
    
    public static final PacketPipeline packetPipeline = new PacketPipeline();

    private PlayerHandler playerHandler = new PlayerHandler();
    private WorldManager worldManager;
    private PlayerEventListener events;
    public static CreativeTab tabBlocks;
    public static CreativeTab tabBricks;
    public static CreativeTab tabFences;
    public static CreativeTab tabCarpets;
    public static CreativeTab tabFlowers;
    public static CreativeTab tabRoofs;
    
    public static Content content;
    
    public SimCraft()
    {
    	MinecraftForge.EVENT_BUS.register(events = new PlayerEventListener());
    	MinecraftForge.EVENT_BUS.register(worldManager = new WorldManager());
    }
    @EventHandler
    public void preInit (FMLPreInitializationEvent event)
    {
    	tabBlocks = new CreativeTab("SimBlocks");
    	tabBricks = new CreativeTab("SimBricks");
    	tabFences = new CreativeTab("SimFences");
    	tabCarpets = new CreativeTab("SimCarpets");
    	tabFlowers = new CreativeTab("SimFlowers");
    	tabRoofs = new CreativeTab("SimRoofs");
    	
    	content = new Content();
    	
    	
        FMLCommonHandler.instance().bus().register(playerHandler);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	packetPipeline.initalise();
    	packetPipeline.registerPackets();
    	
    	
    	tabBlocks.init(new ItemStack(Repository.homeBlock, 1));
    	tabBricks.init(new ItemStack(Blocks.bedrock, 1));
    	tabFences.init(new ItemStack(Blocks.fence, 1));
    	tabCarpets.init(new ItemStack(Blocks.carpet, 1));
    	tabFlowers.init(new ItemStack(Blocks.waterlily, 1));
    	tabRoofs.init(new ItemStack(Repository.blockRoofSlopes[0], 1));
    }
    
    @EventHandler
    public void postInitialise(FMLPostInitializationEvent evt) 
    {
        packetPipeline.postInitialise();
    }
    
    @EventHandler
    public void load(FMLInitializationEvent evt)
    {
    	proxy.registerHandlers();
    	NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
    	MinecraftForge.EVENT_BUS.register(this);
    }
    
    @EventHandler
    public void serverStart(FMLServerStartingEvent event)
    {
    	HomeManager.loadAllHomes();
    	MarketManager.loadMarketPlace();
    }
    
    @EventHandler
    public void serverStop(FMLServerStoppingEvent event)
    {
    	MarketManager.saveMarketPlace();
    }
}
