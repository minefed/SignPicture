package com.kamesuta.mc.signpic.util;

import javax.annotation.Nonnull;

import org.lwjgl.util.Timer;

import com.kamesuta.mc.signpic.Client;
import com.kamesuta.mc.signpic.Config;
import com.kamesuta.mc.signpic.entry.EntryId;
import com.kamesuta.mc.signpic.entry.EntryIdBuilder;
import com.kamesuta.mc.signpic.gui.GuiPAAS;
import com.kamesuta.mc.signpic.preview.SignEntity;

import io.netty.buffer.Unpooled;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.tileentity.TileEntitySign;

public class Sign {
	public static int maxText = 15*4;

	public static final @Nonnull SignEntity preview = new SignEntity();
	public static @Nonnull EntryIdBuilder builder = new EntryIdBuilder();

	public static void updatePreview(final @Nonnull EntryId entryId) {
		entryId.toEntity(preview.getTileEntity());
	}

	public static void sendSign(final @Nonnull EntryId entryId, final @Nonnull TileEntitySign sourceentity) {
		entryId.toEntity(sourceentity);
		sourceentity.markDirty();
		final NetHandlerPlayClient nethandlerplayclient = Client.mc.getConnection();
		if (nethandlerplayclient!=null)
			nethandlerplayclient.sendPacket(new CPacketUpdateSign(sourceentity.getPos(), sourceentity.signText));
		sourceentity.setEditable(true);
	}

	public static void placeSign(final @Nonnull EntryId entryId, final @Nonnull TileEntitySign sourceentity) {
		if (Config.getConfig().multiplayPAAS.get()&&!Client.mc.isSingleplayer())
			Client.mc.displayGuiScreen(new GuiPAAS(new SendPacketTask(entryId, sourceentity)));
		else
			sendSign(entryId, sourceentity);
	}

	public static void sendRepairName(final @Nonnull String name) {
		Client.mc.thePlayer.connection.sendPacket(new CPacketCustomPayload("MC|ItemName", new PacketBuffer(Unpooled.buffer()).writeString(name)));
	}

	public static void setRepairName(final @Nonnull String name, final @Nonnull GuiTextField textField, final @Nonnull ContainerRepair containerRepair) {
		textField.setText(name);
		containerRepair.updateItemName(name);
		sendRepairName(name);
	}

	public static class SendPacketTask {
		public final long limit;
		public final @Nonnull EntryId id;
		public final @Nonnull String[] lines;
		public final @Nonnull TileEntitySign entity;
		public final @Nonnull Timer timer;

		public SendPacketTask(final @Nonnull EntryId id, final @Nonnull TileEntitySign entity) {
			this.timer = new Timer();
			this.lines = new String[4];
			id.toStrings(this.lines);
			this.limit = getExpectedEditTime(this.lines, false);
			this.id = id;
			this.entity = entity;
		}

		private void sendPacket() {
			Sign.sendSign(this.id, this.entity);
		}

		public boolean tick() {
			if (this.timer.getTime()*1000>this.limit) {
				sendPacket();
				return true;
			}
			return false;
		}

		private static long getExpectedEditTime(final @Nonnull String[] lines, final boolean skipEmpty) {
			long expected = Config.getConfig().multiplayPAASMinEditTime.get();
			final int minchartime = Config.getConfig().multiplayPAASMinCharTime.get();
			int n = 0;
			for (String line : lines)
				if (line!=null) {
					line = line.trim().toLowerCase();
					if (!line.isEmpty()) {
						final int chars = line.length();
						n += 1;
						expected += minchartime*chars;
					}
				}
			if (skipEmpty&&n==0)
				return 0;
			if (n>1)
				expected += Config.getConfig().multiplayPAASMinLineTime.get()*n;
			return expected;
		}
	}
}
