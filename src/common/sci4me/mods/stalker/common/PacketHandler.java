package sci4me.mods.stalker.common;

import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{
	public void onPacketData(NetworkManager manager, Packet250CustomPayload packet, Player player) 
	{
		ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
		System.out.println("Custom payload packet received, first int: " + dat.readInt());
	}
}
