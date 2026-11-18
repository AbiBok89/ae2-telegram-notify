package com.abi.ae2telegramnotify;

import com.abi.ae2telegramnotify.command.NotifyCommands;
import com.abi.ae2telegramnotify.storage.PlayerTelegramStore;
import com.abi.ae2telegramnotify.telegram.TelegramClient;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(Ae2TelegramNotifyMod.MOD_ID)
public class Ae2TelegramNotifyMod {
    public static final String MOD_ID = "ae2telegramnotify";
    private static final Logger LOGGER = LoggerFactory.getLogger(Ae2TelegramNotifyMod.class);

    public Ae2TelegramNotifyMod(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        NeoForge.EVENT_BUS.addListener(this::onRegisterCommands);
        NeoForge.EVENT_BUS.addListener(this::onServerStarting);
        NeoForge.EVENT_BUS.addListener(this::onServerStopping);
        NeoForge.EVENT_BUS.addListener(this::onPlayerLoggedIn);

        LOGGER.info("AE2 Telegram Notify initialized");
    }

    private void onRegisterCommands(RegisterCommandsEvent event) {
        NotifyCommands.register(event.getDispatcher());
    }

    private void onServerStarting(ServerStartingEvent event) {
        PlayerTelegramStore.getInstance().load();
    }

    private void onServerStopping(ServerStoppingEvent event) {
        PlayerTelegramStore.getInstance().saveIfDirty();
        TelegramClient.shutdown();
    }

    private void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            PlayerTelegramStore.getInstance().updateKnownName(
                    player.getUUID(),
                    player.getGameProfile().getName()
            );
        }
    }
}
