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
    
    private PlayerEventListener playerEventListener = new PlayerEventListener();
    private PlayerHandler playerHandler = new PlayerHandler();
    private PlayerEventListener events;
    public static CreativeTab tabBlocks;
    
    public static Content content;
    
    public SimCraft()
    {
    	MinecraftForge.EVENT_BUS.register(events = new PlayerEventListener());
    }
    @EventHandler
    public void preInit (FMLPreInitializationEvent event)
    {
    	tabBlocks = new CreativeTab("SimBlocks");
    	
    	content = new Content();
    	
    	NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
    	
        FMLCommonHandler.instance().bus().register(playerHandler);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	packetPipeline.initalise();
    	packetPipeline.registerPackets();
    	
    	proxy.registerHandlers();
    	
    	tabBlocks.init(new ItemStack(Blocks.bedrock, 1, 0));
    }
    
    @EventHandler
    public void postInitialise(FMLPostInitializationEvent evt) 
    {
        packetPipeline.postInitialise();
    }
    
    @EventHandler
    public void serverStart(FMLServerStartingEvent event)
    {
    	HomeManager.loadAllHomes();
    	MarketManager.loadMarketDefaultPrices();
    	MarketManager.loadMarketPlace();
    }
}
