package github.thelawf.gensokyoontology.client.renderer.entity.misc;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.client.GSKORenderTypes;
import github.thelawf.gensokyoontology.common.entity.misc.LaserSourceEntity;
import github.thelawf.gensokyoontology.common.util.Vec3fConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// 种子：-8902379324748255918 坐标：1519 76 441 维度：幻想乡 群系：博丽神社周边
@OnlyIn(Dist.CLIENT)
public class LaserEntityRenderer extends EntityRenderer<LaserSourceEntity> {

    public static final ResourceLocation LASER_SOURCE_TEX = GensokyoOntology.withRL("textures/entity/laser_source.png");
    public static final ResourceLocation LASER_BEAM_TEX = GensokyoOntology.withRL("textures/entity/laser_beam_1.png");
    public static final RenderType LASER_BEAM = RenderType.getEntityTranslucent(LASER_BEAM_TEX);

    public LaserEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    @NotNull
    public ResourceLocation getEntityTexture(@NotNull LaserSourceEntity entity) {
        return LASER_SOURCE_TEX;
    }

    private static void drawLaser(IVertexBuilder builder, Matrix4f matrix4f, int r, int g, int b) {
        builder.pos(matrix4f, 0F,0.5F,0F).color(r, g, b, 255).endVertex();
        builder.pos(matrix4f, 0F,2F,0F).color(r, g, b, 255).endVertex();
    }

    private static void drawSprite(IVertexBuilder builder, Matrix4f matrix4f, TextureAtlasSprite sprite) {
        builder.pos(matrix4f, 0,0,1).color(255, 255, 255, 255).tex(sprite.getMinU(), sprite.getMinV()).lightmap(0, 240).endVertex();
        builder.pos(matrix4f, 1,0,1).color(255, 255, 255, 255).tex(sprite.getMaxU(), sprite.getMinV()).lightmap(0, 240).endVertex();
        builder.pos(matrix4f, 1,1,1).color(255, 255, 255, 255).tex(sprite.getMaxU(), sprite.getMaxV()).lightmap(0, 240).endVertex();
        builder.pos(matrix4f, 0,1,1).color(255, 255, 255, 255).tex(sprite.getMinU(), sprite.getMaxV()).lightmap(0, 240).endVertex();
    }

    private static void drawLaser(IVertexBuilder builder, Matrix4f matrix4f, Vector3f start, Vector3f end, int r, int g, int b, int alpha) {
        builder.pos(matrix4f, start.getX(), start.getY(), start.getZ()).color(r, g, b, alpha).endVertex();
        builder.pos(matrix4f, end.getX(), end.getY(), end.getZ()).color(r, g, b, alpha).endVertex();
    }

    private static void drawLaser(IVertexBuilder builder, Matrix4f matrix4f, Matrix3f matrix3f,
                                  float dx1, float dy1, float dz1, int r, int g, int b, float u, float v) {
        builder.pos(matrix4f, dx1, dy1, dz1).color(r, g, b, 255).tex(u, v).overlay(OverlayTexture.NO_OVERLAY).lightmap(15728880).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
    }

    private static void drawLaser(IVertexBuilder builder, Matrix4f matrix4f, Matrix3f matrix3f,
                                  float dx1, float dy1, float dz1, int r, int g, int b, int a, float u, float v) {
        builder.pos(matrix4f, dx1, dy1, dz1).color(r, g, b, a).tex(u, v).overlay(OverlayTexture.NO_OVERLAY).lightmap(15728880).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void render(@NotNull LaserSourceEntity entityIn, float entityYaw, float partialTicks, @NotNull MatrixStack matrixStackIn, @NotNull IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        // TODO: 实现激光源头的贴图渲染
        TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(LASER_SOURCE_TEX);
        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        IVertexBuilder builder = buffer.getBuffer(GSKORenderTypes.LASER_LINE);

        // if (entityIn.ticksExisted <= entityIn.getPreparation()) {
        //     Vector3f start = new Vector3f(0F, 0.5F, 0F);
        //     Vector3f end = toVector3f(entityIn.getLookVec().scale(entityIn.getRange()));
        //
        //     matrixStackIn.push();
        //     Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
        //     drawLaser(builder, matrix4f, start, end, entityIn.getRed(), entityIn.getGreen(), entityIn.getBlue(), entityIn.getAlpha());
        //     matrixStackIn.pop();
        // }
        // else {
        //     renderLaserUsingMojangsShit(entityIn, null, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        // }
        renderLaserUsingMojangsShit(entityIn, null, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    private static Vector3f toVector3f(Vector3d vector3d) {
        return new Vector3f((float) vector3d.x, (float) vector3d.y, (float) vector3d.z);
    }

    /** Render laser using Mojang's shit. <br>
     * Copy from {@link net.minecraft.client.renderer.entity.GuardianRenderer}. Mojang official uses these codes below only for rendering
     * Guardian Entity's laser.
     */
    private void renderLaserUsingMojangsShit(LaserSourceEntity entityIn, @Nullable Vector3d lookVec, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn){
        //LivingEntity livingentity = entityIn.getTargetedEntity();

        float scale = 0.5f;
        float f1 = (float)entityIn.world.getGameTime() + partialTicks;
        float f2 = f1 * 0.5F % 1.0F;
        float eyeHeight = entityIn.getEyeHeight();
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, eyeHeight, 0.0D);
        Vector3d vector3d = entityIn.getPositionVec();

        // Using this.getPosition(entityIn, eyeHeight, partialTicks);
        Vector3d vector3d1 = entityIn.getLookVec().scale(entityIn.getRange());
        float f4 = (float)(vector3d1.length() + 1.0D);

        // 获取激光光束的发射方向，根据这个方向旋转matrixStack到相应的位置进行渲染
        Vector3d vector3d2 = entityIn.getLookVec();
        float f5 = (float)Math.acos(vector3d2.y);
        float f6 = (float)Math.atan2(vector3d2.z, vector3d2.x);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(((float)Math.PI / 2 - f6) * (180 / (float)Math.PI)));
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(f5 * (180 / (float)Math.PI)));

        float f7 = f1 * 0.05F * -1.5F;
        float f8 = scale * scale;

        // 这里是获取渲染颜色数值
        // int j = 64 + (int)(f8 * 191.0F); // Red: 111.75 -> 111
        // int k = 32 + (int)(f8 * 191.0F); // Green: 79.75 -> 79
        // int l = 128 - (int)(f8 * 64.0F); // Blue: 80.25 -> 80

        // entityIn.setARGB(0xFFFF0000);
        int j = 255;
        int k = 255;
        int l = 255;

        int r = entityIn.getRed();
        int g = entityIn.getGreen();
        int b = entityIn.getBlue();
        int a = entityIn.getAlpha();

        float f11 = MathHelper.cos(f7 + 2.3561945F) * 0.282F;
        float f12 = MathHelper.sin(f7 + 2.3561945F) * 0.282F;
        float f13 = MathHelper.cos(f7 + ((float)Math.PI / 4F)) * 0.282F;
        float f14 = MathHelper.sin(f7 + ((float)Math.PI / 4F)) * 0.282F;
        float f15 = MathHelper.cos(f7 + 3.926991F) * 0.282F;
        float f16 = MathHelper.sin(f7 + 3.926991F) * 0.282F;
        float f17 = MathHelper.cos(f7 + 5.4977875F) * 0.282F;
        float f18 = MathHelper.sin(f7 + 5.4977875F) * 0.282F;
        float f19 = MathHelper.cos(f7 + (float)Math.PI) * 0.2F;
        float f20 = MathHelper.sin(f7 + (float)Math.PI) * 0.2F;
        float f21 = MathHelper.cos(f7 + 0.0F) * 0.2F;
        float f22 = MathHelper.sin(f7 + 0.0F) * 0.2F;
        float f23 = MathHelper.cos(f7 + ((float)Math.PI / 2F)) * 0.2F;
        float f24 = MathHelper.sin(f7 + ((float)Math.PI / 2F)) * 0.2F;
        float f25 = MathHelper.cos(f7 + ((float)Math.PI * 1.5F)) * 0.2F;
        float f26 = MathHelper.sin(f7 + ((float)Math.PI * 1.5F)) * 0.2F;
        float f29 = -1.0F + f2;
        float f30 = f4 * 2.5F + f29;
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(LASER_BEAM);
        MatrixStack.Entry matrixstack$entry = matrixStackIn.getLast();
        Matrix4f matrix4f = matrixstack$entry.getMatrix();
        Matrix3f matrix3f = matrixstack$entry.getNormal();

        // draw(f4, j, k, l, f19, f20, f21, f22, f29, f30, ivertexbuilder, matrix4f, matrix3f);
        // draw(f4, j, k, l, f23, f24, f25, f26, f29, f30, ivertexbuilder, matrix4f, matrix3f);

        draw(f4, r, g, b, f19, f20, f21, f22, f29, f30, ivertexbuilder, matrix4f, matrix3f);
        draw(f4, r, g, b, f23, f24, f25, f26, f29, f30, ivertexbuilder, matrix4f, matrix3f);
        float f31 = 0.0F;
        if (entityIn.ticksExisted % 2 == 0) {
            f31 = 0.5F;
        }

        // drawLaser(ivertexbuilder, matrix4f, matrix3f, f11, f4, f12, j, k, l, 0.5F, f31 + 0.5F);
        // drawLaser(ivertexbuilder, matrix4f, matrix3f, f13, f4, f14, j, k, l, 1.0F, f31 + 0.5F);
        // drawLaser(ivertexbuilder, matrix4f, matrix3f, f17, f4, f18, j, k, l, 1.0F, f31);
        // drawLaser(ivertexbuilder, matrix4f, matrix3f, f15, f4, f16, j, k, l, 0.5F, f31);

        drawLaser(ivertexbuilder, matrix4f, matrix3f, f11, f4, f12, r, g, b, 0.5F, f31 + 0.5F);
        drawLaser(ivertexbuilder, matrix4f, matrix3f, f13, f4, f14, r, g, b, 1.0F, f31 + 0.5F);
        drawLaser(ivertexbuilder, matrix4f, matrix3f, f17, f4, f18, r, g, b, 1.0F, f31);
        drawLaser(ivertexbuilder, matrix4f, matrix3f, f15, f4, f16, r, g, b, 0.5F, f31);
        matrixStackIn.pop();
    }

    @Override
    public boolean shouldRender(LaserSourceEntity livingEntityIn, ClippingHelper camera, double camX, double camY, double camZ) {
        return super.shouldRender(livingEntityIn, camera, camX, camY, camZ);
    }

    private void draw(float f4, int j, int k, int l, float f19, float f20, float f21, float f22, float f29, float f30, IVertexBuilder ivertexbuilder, Matrix4f matrix4f, Matrix3f matrix3f) {
        drawLaser(ivertexbuilder, matrix4f, matrix3f, f19, f4, f20, j, k, l, 0.4999F, f30);
        drawLaser(ivertexbuilder, matrix4f, matrix3f, f19, 0.0F, f20, j, k, l, 0.4999F, f29);
        drawLaser(ivertexbuilder, matrix4f, matrix3f, f21, 0.0F, f22, j, k, l, 0.0F, f29);
        drawLaser(ivertexbuilder, matrix4f, matrix3f, f21, f4, f22, j, k, l, 0.0F, f30);
    }
}
