package net.gigabit101.quantumstorage.client.layer;

import com.google.gson.*;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.gigabit101.quantumstorage.init.QSBlocks;
import net.gigabit101.quantumstorage.util.WebUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ContributorLayer extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>
{
    private static volatile List<String> contributorNames = new ArrayList<>();
    private static boolean startedLoading = false;
    private static final ItemStack itemStack = new ItemStack(QSBlocks.QSU.get());


    public ContributorLayer(PlayerRenderer playerRenderer)
    {
        super(playerRenderer);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, AbstractClientPlayerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
    {
        firstStart();

        String name = entitylivingbaseIn.getGameProfile().getName();

        if(isContributor(name))
        {
            matrixStackIn.push();

            getEntityModel().bipedHead.translateRotate(matrixStackIn);

            matrixStackIn.translate(0D, -0.65D, 0D);
            matrixStackIn.scale(0.3F, 0.3F, 0.3F);
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(180f));
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180f));

            BackpackLayer.renderItem(itemStack, matrixStackIn, bufferIn, 0xF000F0);

            matrixStackIn.pop();
        }
    }

    public boolean isContributor(String name)
    {
        for (String contributorName : contributorNames)
        {
            if(contributorName.equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    public static void firstStart()
    {
        if (!startedLoading)
        {
            CompletableFuture.runAsync(ContributorLayer::load);
            startedLoading = true;
        }
    }

    public static void load()
    {
        String resp = WebUtils.getWebResponse("https://files.gigabit101.net/contributors.json");

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(resp);
        List<String> tempList = new ArrayList<>();
        if (element.isJsonObject())
        {
            JsonObject obj = element.getAsJsonObject();
            JsonArray jsonArray = obj.getAsJsonArray("contributors").getAsJsonArray();
            for (JsonElement jsonElement : jsonArray)
            {
                tempList.add(jsonElement.getAsString());
            }
            contributorNames = tempList;
        }
    }
}
