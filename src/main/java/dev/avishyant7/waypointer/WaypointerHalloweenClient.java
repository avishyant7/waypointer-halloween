package dev.avishyant7.waypointer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public final class WaypointerHalloweenClient implements ClientModInitializer {
    private static final List<BlockPos> WAYPOINTS = new ArrayList<>(List.of(
        new BlockPos(33,74,-51),new BlockPos(10,72,-9),new BlockPos(5,73,-23),new BlockPos(9,73,-70),new BlockPos(-7,74,-80),
        new BlockPos(-21,72,-59),new BlockPos(-33,73,-29),new BlockPos(-37,70,12),new BlockPos(-61,71,-14),new BlockPos(-66,71,16),
        new BlockPos(-58,73,41),new BlockPos(-32,71,36),new BlockPos(-10,73,50),new BlockPos(27,73,58),new BlockPos(48,78,67),
        new BlockPos(80,81,47),new BlockPos(82,86,35),new BlockPos(67,75,17),new BlockPos(57,88,-34),new BlockPos(45,54,-23),
        new BlockPos(64,50,-28),new BlockPos(70,46,-38),new BlockPos(92,41,-61),new BlockPos(101,44,-76),new BlockPos(109,44,-88),
        new BlockPos(108,42,-124),new BlockPos(102,54,-152),new BlockPos(128,53,-136),new BlockPos(118,72,-120),new BlockPos(149,70,-145),
        new BlockPos(133,73,-173),new BlockPos(113,90,-163),new BlockPos(103,76,-158),new BlockPos(134,85,-166),new BlockPos(132,70,-51),
        new BlockPos(115,86,-35),new BlockPos(109,86,-25),new BlockPos(96,72,2),new BlockPos(107,73,24),new BlockPos(58,92,43),
        new BlockPos(45,73,25),new BlockPos(18,87,64),new BlockPos(34,70,99),new BlockPos(15,83,98),new BlockPos(13,99,92),
        new BlockPos(-13,81,92),new BlockPos(-36,72,107),new BlockPos(-51,89,104),new BlockPos(-82,99,115),new BlockPos(-103,74,119),
        new BlockPos(-82,97,86),new BlockPos(-100,80,92),new BlockPos(-106,72,84),new BlockPos(-77,73,69),new BlockPos(-63,82,82),
        new BlockPos(-56,92,65),new BlockPos(-37,73,63),new BlockPos(-12,86,64),new BlockPos(4,72,35),new BlockPos(0,73,3),
        new BlockPos(36,71,0),new BlockPos(69,79,-19),new BlockPos(83,89,-35),new BlockPos(94,103,-53),new BlockPos(95,83,-71),
        new BlockPos(96,73,-83),new BlockPos(81,81,-84),new BlockPos(58,78,-76),new BlockPos(35,77,-78),new BlockPos(53,74,-106),
        new BlockPos(28,80,-133),new BlockPos(-6,75,-131),new BlockPos(10,70,-126),new BlockPos(16,77,-96),new BlockPos(6,76,-97),
        new BlockPos(5,76,-81),new BlockPos(-30,72,-91),new BlockPos(-44,81,-69),new BlockPos(-43,86,-37),new BlockPos(-72,77,-55),
        new BlockPos(-86,70,-48),new BlockPos(-65,72,-67),new BlockPos(-115,72,-99),new BlockPos(-108,99,-93),new BlockPos(-138,75,-77),
        new BlockPos(-161,73,-86),new BlockPos(-130,70,-128),new BlockPos(-128,72,-81),new BlockPos(-109,71,-61),new BlockPos(-128,76,-42),
        new BlockPos(-136,87,-43),new BlockPos(-151,70,-28),new BlockPos(-141,75,-5),new BlockPos(-126,72,21),new BlockPos(-92,72,39),
        new BlockPos(-90,74,28),new BlockPos(-79,78,-3),new BlockPos(-86,74,-19),new BlockPos(-110,77,-21),new BlockPos(-113,78,-9)
    ));

    private KeyMapping removeNearestKey;
    private KeyMapping toggleHudKey;
    private boolean hudVisible = true;
    private String status = "100 waypoints loaded";
    private int statusTicks;
    private static final KeyMapping.Category KEY_CATEGORY = KeyMapping.Category.register(ResourceLocation.fromNamespaceAndPath("waypointer_halloween", "controls"));

    @Override
    public void onInitializeClient() {
        removeNearestKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "key.waypointer_halloween.remove_nearest", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_BACKSPACE,
            KEY_CATEGORY));
        toggleHudKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.waypointer_halloween.toggle_hud", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H,
            KEY_CATEGORY));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (removeNearestKey.consumeClick()) removeNearest(client);
            while (toggleHudKey.consumeClick()) {
                hudVisible = !hudVisible;
                setStatus(client, hudVisible ? "Waypoint HUD enabled" : "Waypoint HUD hidden");
            }
            if (statusTicks > 0) statusTicks--;
        });
    }

    private void removeNearest(Minecraft client) {
        if (client.player == null) return;
        BlockPos playerPos = client.player.blockPosition();
        int nearestIndex = -1;
        double nearestDistance = Double.MAX_VALUE;
        for (int i = 0; i < WAYPOINTS.size(); i++) {
            BlockPos point = WAYPOINTS.get(i);
            double distance = Math.sqrt(playerPos.distSqr(point));
            if (distance < nearestDistance) { nearestDistance = distance; nearestIndex = i; }
        }
        if (nearestIndex >= 0 && nearestDistance <= 1.0) {
            BlockPos removed = WAYPOINTS.remove(nearestIndex);
            setStatus(client, "Removed waypoint " + coords(removed) + " | " + WAYPOINTS.size() + " left");
        } else {
            setStatus(client, "No waypoint within 1 block. Nearest: " + (nearestIndex < 0 ? "none" : coords(WAYPOINTS.get(nearestIndex))));
        }
    }

    private void setStatus(Minecraft client, String message) {
        status = message;
        statusTicks = 100;
        if (client.player != null) client.player.displayClientMessage(Component.literal(message), true);
    }

    private static String coords(BlockPos p) { return p.getX() + " " + p.getY() + " " + p.getZ(); }
}
