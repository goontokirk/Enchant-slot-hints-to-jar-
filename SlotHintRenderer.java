package dev.enchantslothints;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * Zeichnet in jedes der drei Enchanting-Table-Felder den echten Namen des
 * Hinweis-Enchantments (das, was man sonst erst beim Hovern sieht).
 */
public final class SlotHintRenderer {

    // Standardgroesse der Enchanting-Table-GUI (Vanilla)
    private static final int GUI_WIDTH = 176;
    private static final int GUI_HEIGHT = 166;

    // Lage der drei Felder relativ zur GUI (Vanilla)
    private static final int SLOT_X = 60;
    private static final int SLOT_Y = 14;
    private static final int SLOT_WIDTH = 108;
    private static final int SLOT_HEIGHT = 19;
    private static final int SLOT_STEP = 19;

    // Ab hier (relativ zur GUI) beginnt die Galactic-Schrift, rechts endet das Feld bei 166
    private static final int TEXT_START_X = 80;
    private static final int TEXT_END_X = 166;

    private static final int BACKDROP = 0xFF1F1A12;
    private static final int BACKDROP_HOVER = 0xFF3A3020;
    private static final int TEXT_COLOR = 0xFFFFFFFF;
    private static final int TEXT_COLOR_HOVER = 0xFFFFFF80;

    private SlotHintRenderer() {
    }

    public static void render(Minecraft client, EnchantmentScreen screen, GuiGraphics graphics,
                              int mouseX, int mouseY) {
        if (client.level == null) {
            return;
        }

        EnchantmentMenu menu = screen.getMenu();
        Font font = client.font;
        Registry<Enchantment> registry = client.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);

        int left = (screen.width - GUI_WIDTH) / 2;
        int top = (screen.height - GUI_HEIGHT) / 2;

        for (int i = 0; i < 3; i++) {
            int cost = menu.costs[i];
            int id = menu.enchantClue[i];
            int level = menu.levelClue[i];

            // Feld leer / noch keine Daten vom Server
            if (cost <= 0 || id < 0 || level < 0) {
                continue;
            }

            Enchantment enchantment = registry.byId(id);
            if (enchantment == null) {
                continue;
            }

            int slotX = left + SLOT_X;
            int slotY = top + SLOT_Y + SLOT_STEP * i;
            boolean hovered = mouseX >= slotX && mouseX < slotX + SLOT_WIDTH
                    && mouseY >= slotY && mouseY < slotY + SLOT_HEIGHT;

            // Die Level-Kosten stehen rechts im Feld und bleiben sichtbar
            int costWidth = font.width(String.valueOf(cost));
            int textX = left + TEXT_START_X;
            int backdropEnd = left + TEXT_END_X - costWidth - 1;
            int maxTextWidth = backdropEnd - textX - 1;

            // Balken ueber die Galactic-Schrift legen, damit sie nicht durchscheint
            graphics.fill(textX - 1, slotY + 1, backdropEnd, slotY + SLOT_HEIGHT - 1,
                    hovered ? BACKDROP_HOVER : BACKDROP);

            List<FormattedCharSequence> lines = font.split(buildName(enchantment, level), maxTextWidth);
            int color = hovered ? TEXT_COLOR_HOVER : TEXT_COLOR;

            if (lines.size() <= 1) {
                if (!lines.isEmpty()) {
                    graphics.drawString(font, lines.get(0), textX, slotY + 6, color);
                }
            } else {
                graphics.drawString(font, lines.get(0), textX, slotY + 2, color);
                graphics.drawString(font, lines.get(1), textX, slotY + 10, color);
            }
        }
    }

    private static Component buildName(Enchantment enchantment, int level) {
        MutableComponent name = enchantment.description().copy();
        // Wie in Vanilla: Level nur anzeigen, wenn es mehrere Stufen gibt oder Level != 1
        if (level != 1 || enchantment.getMaxLevel() != 1) {
            name.append(" ");
            if (level >= 1 && level <= 10) {
                name.append(Component.translatable("enchantment.level." + level));
            } else {
                name.append(String.valueOf(level));
            }
        }
        return name;
    }
}
