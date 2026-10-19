package com.example.donuttracker;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.regex.Matcher;

public class DonutTrackerClient implements ClientModInitializer {
    public static final ProfitTracker TRACKER = new ProfitTracker();

    @Override
    public void onInitializeClient() {
        // Read-only: we only inspect messages the client already received.
        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
            if (overlay) return;
            String text = message.getString();

            Matcher paid = ChatPatterns.PAID.matcher(text);
            if (paid.find()) {
                String who = paid.group(1);
                String amt = paid.group(2);
                MinecraftClient mc = MinecraftClient.getInstance();
                mc.execute(() -> SystemToast.add(
                    mc.getToastManager(),
                    SystemToast.Type.PERIODIC_NOTIFICATION,
                    Text.literal(who + " paid you"),
                    Text.literal("$" + amt)
                ));
                return;
            }

            Matcher win = ChatPatterns.WIN.matcher(text);
            if (win.find()) {
                TRACKER.recordWin(ChatPatterns.parseAmount(win.group(1)));
                return;
            }

            Matcher loss = ChatPatterns.LOSS.matcher(text);
            if (loss.find()) {
                TRACKER.recordLoss(ChatPatterns.parseAmount(loss.group(1)));
            }
        });

        // Small HUD line, top left (uses the newer HUD element API).
        HudElementRegistry.attachElementBefore(
            VanillaHudElements.CHAT,
            Identifier.of("donuttracker", "profit"),
            (context, tickCounter) -> {
                MinecraftClient mc = MinecraftClient.getInstance();
                if (mc.options.hudHidden || mc.player == null) return;
                // 0xFFFFFFFF = opaque white (colors need the alpha byte in 1.21.6+)
                context.drawTextWithShadow(mc.textRenderer, TRACKER.summary(), 6, 6, 0xFFFFFFFF);
            }
        );
    }
}
