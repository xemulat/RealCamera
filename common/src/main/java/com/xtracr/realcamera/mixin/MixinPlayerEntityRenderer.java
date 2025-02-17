package com.xtracr.realcamera.mixin;

import com.xtracr.realcamera.api.VirtualRenderer;
import com.xtracr.realcamera.util.Flag;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class MixinPlayerEntityRenderer
        extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public MixinPlayerEntityRenderer(Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model,
            float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "render*", at = @At("HEAD"))
    private void realCamera$onRenderHEAD(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g,
            MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo cInfo) {
        if (abstractClientPlayerEntity instanceof ClientPlayerEntity) Flag.isRenderingClientPlayer = true;
    }

    @Inject(method = "render*", at = @At("RETURN"))
    private void realCamera$onRenderRETURN(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g,
            MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo cInfo) {
        Flag.isRenderingClientPlayer = false;
    }

    @Inject(method = "setModelPose", at = @At("RETURN"))
    private void realCamera$onSetModelPoseRETURN(AbstractClientPlayerEntity player, CallbackInfo cInfo) {
        if (VirtualRenderer.shouldDisableRender("head")) model.head.visible = false;
        if (VirtualRenderer.shouldDisableRender("hat")) model.hat.visible = false;
        if (VirtualRenderer.shouldDisableRender("body")) model.body.visible = false;
        if (VirtualRenderer.shouldDisableRender("rightArm")) model.rightArm.visible = false;
        if (VirtualRenderer.shouldDisableRender("leftArm")) model.leftArm.visible = false;
        if (VirtualRenderer.shouldDisableRender("rightLeg")) model.rightLeg.visible = false;
        if (VirtualRenderer.shouldDisableRender("leftLeg")) model.leftLeg.visible = false;
        if (VirtualRenderer.shouldDisableRender("leftSleeve")) model.leftSleeve.visible = false;
        if (VirtualRenderer.shouldDisableRender("rightSleeve")) model.rightSleeve.visible = false;
        if (VirtualRenderer.shouldDisableRender("leftPants")) model.leftPants.visible = false;
        if (VirtualRenderer.shouldDisableRender("rightPants")) model.rightPants.visible = false;
        if (VirtualRenderer.shouldDisableRender("jacket")) model.jacket.visible = false;
    }
}
