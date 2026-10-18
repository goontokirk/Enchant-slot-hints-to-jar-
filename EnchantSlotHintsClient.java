package dev.enchantslothints;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;

/**
 * Client-Mod: zeigt den Enchantment-Hinweis jedes der drei Slots am
 * Enchanting Table direkt im Feld an, statt der unlesbaren "Galactic"-Schrift.
 */
public class EnchantSlotHintsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Wird bei jedem (Neu-)Aufbau eines Screens aufgerufen, auch beim Fenster-Resize.
        // Deshalb registrieren wir den Render-Handler hier jedes Mal neu.
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof EnchantmentScreen enchantScreen) {
                ScreenEvents.afterRender(screen).register((s, graphics, mouseX, mouseY, tickDelta) ->
                        SlotHintRenderer.render(client, enchantScreen, graphics, mouseX, mouseY));
            }
        });
    }
}
