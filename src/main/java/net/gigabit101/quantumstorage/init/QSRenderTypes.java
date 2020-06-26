package net.gigabit101.quantumstorage.init;

import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class QSRenderTypes extends RenderState
{
    public QSRenderTypes(String p_i225973_1_, Runnable p_i225973_2_, Runnable p_i225973_3_)
    {
        super(p_i225973_1_, p_i225973_2_, p_i225973_3_);
    }
    
//    public static final RenderType TANK_RENDER = RenderType.get("myrcraft_bucket", DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP, GL11.GL_QUADS, 256, true, false,
//            RenderType.State.getBuilder()
//                    .texture(BLOCK_SHEET)
//                    .cull(CULL_ENABLED)
//                    .transparency(TRANSLUCENT_TRANSPARENCY)
//                    .lightmap(LIGHTMAP_ENABLED)
//                    .shadeModel(SHADE_ENABLED)
//                    .build(true));
}
