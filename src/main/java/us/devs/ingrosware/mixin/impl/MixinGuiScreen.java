package us.devs.ingrosware.mixin.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import us.devs.ingrosware.IngrosWare;
import us.devs.ingrosware.event.impl.render.RenderToolTipEvent;

@Mixin(GuiScreen.class)
public class MixinGuiScreen {

    @Inject(method = "renderToolTip", at = @At("HEAD"), cancellable = true)
    public void renderToolTip(ItemStack stack, int x, int y, CallbackInfo info) {
        if (Minecraft.getMinecraft().ingameGUI != null && Minecraft.getMinecraft().currentScreen != null && stack != null) {
            final RenderToolTipEvent event = new RenderToolTipEvent(stack, x, y);
            IngrosWare.INSTANCE.getBus().post(event);
            if (event.isCancelled()) {
                info.cancel();
            }
        }
    }
}
