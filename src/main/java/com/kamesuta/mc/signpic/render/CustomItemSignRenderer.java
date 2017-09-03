package com.kamesuta.mc.signpic.render;

import static org.lwjgl.opengl.GL11.*;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.ImmutableList;
import com.kamesuta.mc.bnnwidget.render.OpenGL;
import com.kamesuta.mc.signpic.Log;
import com.kamesuta.mc.bnnwidget.render.RenderOption;
import com.kamesuta.mc.signpic.attr.AttrReaders;
import com.kamesuta.mc.signpic.attr.prop.OffsetData;
import com.kamesuta.mc.signpic.attr.prop.RotationData.RotationGL;
import com.kamesuta.mc.signpic.attr.prop.SizeData;
import com.kamesuta.mc.signpic.attr.prop.SizeData.ImageSizes;
import com.kamesuta.mc.signpic.entry.Entry;
import com.kamesuta.mc.signpic.entry.EntryId;
import com.kamesuta.mc.signpic.entry.content.Content;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.Attributes;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.ISmartItemModel;

@SuppressWarnings("deprecation")
public class CustomItemSignRenderer extends IFlexibleBakedModel.Wrapper implements ISmartItemModel, IPerspectiveAwareModel {
	public static final @Nonnull ModelResourceLocation modelResourceLocation = new ModelResourceLocation("minecraft:sign", "inventory");
	private final @Nonnull IBakedModel baseModel;
	private @Nullable ItemStack itemStack;

	@SuppressWarnings("deprecation")
	public CustomItemSignRenderer(final @Nonnull IBakedModel model) {
		super(model, Attributes.DEFAULT_BAKED_FORMAT);
		this.baseModel = model;
	}

	@Override
	@SuppressWarnings("deprecation")
	public @Nonnull IBakedModel handleItemState(final @Nullable ItemStack stack) {
		this.itemStack = stack;
		if (stack!=null&&stack.getItem()==Items.sign&&EntryId.fromItemStack(stack).entry().isValid())
			return this;
		else
			return this.baseModel;
	}

	@Override
	@SuppressWarnings("deprecation")
	public @Nullable Pair<IBakedModel, Matrix4f> handlePerspective(final @Nullable TransformType cameraTransformType) {
		final ItemStack itemStack = this.itemStack;
		if (itemStack!=null&&cameraTransformType!=null) {
			OpenGL.glPushMatrix();
			if (this.baseModel instanceof IPerspectiveAwareModel) {
				final Pair<? extends IBakedModel, Matrix4f> pair = ((IPerspectiveAwareModel) this.baseModel).handlePerspective(cameraTransformType);
				if (pair.getRight()!=null)
					ForgeHooksClient.multiplyCurrentGlMatrix(pair.getRight());
			}
			OpenGL.glDisable(GL11.GL_CULL_FACE);
			renderItem(cameraTransformType, itemStack);
			OpenGL.glEnable(GL11.GL_LIGHTING);
			OpenGL.glEnable(GL11.GL_BLEND);
			OpenGL.glEnable(GL11.GL_TEXTURE_2D);
			OpenGL.glEnable(GL11.GL_CULL_FACE);
			OpenGL.glPopMatrix();
		}
		return Pair.of((IBakedModel) this, null);
	}

	@SuppressWarnings("deprecation")
	public void renderItem(final @Nonnull TransformType type, final @Nullable ItemStack item) {
		OpenGL.glPushMatrix();
		OpenGL.glPushAttrib();
		OpenGL.glDisable(GL_CULL_FACE);
		final Entry entry = EntryId.fromItemStack(item).entry();
		final AttrReaders attr = entry.getMeta();
		final Content content = entry.getContent();
		// Size
		final SizeData size01 = content!=null ? content.image.getSize() : SizeData.DefaultSize;
		final SizeData size = attr.sizes.getMovie().get().aspectSize(size01);
		if (type==net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.GUI) {
			OpenGL.glScalef(1f, -1f, 1f);
			final float slot = 1f;
			final SizeData size2 = ImageSizes.INNER.defineSize(size, slot, slot);
			OpenGL.glScalef(.5f, .5f, 1f);
			OpenGL.glTranslatef((slot-size2.getWidth())/2f, (slot-size2.getHeight())/2f, 0f);
			OpenGL.glTranslatef(-.5f, -.5f, 0f);
			OpenGL.glScalef(slot, slot, 1f);
			entry.getGui().drawScreen(0, 0, 0f, 1f, size2.getWidth()/slot, size2.getHeight()/slot, new RenderOption());
		} else {
			OpenGL.glScalef(1f, -1f, 1f);
			/*if (type==net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.GROUND)
				OpenGL.glTranslatef(-size.getWidth()/2f, .25f, 0f);
			else*/ if (type==net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.NONE) {
				final float f = 0.0078125F; // vanilla map offset
				OpenGL.glTranslatef(-size.getWidth()/2f, .5f, f);
				Log.log.info("NONE");
			} else if (type==net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.FIRST_PERSON)
				OpenGL.glTranslatef(-.25f, .25f, 0f);
			else if (type==net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.THIRD_PERSON) {
				OpenGL.glTranslatef(.25f, .25f, 0f);
				OpenGL.glTranslatef(-size.getWidth(), 0f, 0f);
			} else if (type==net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.HEAD)
				;// Minecraft 1.8.x doesn't support Item Head.
			OpenGL.glTranslatef(0f, -size.getHeight(), 0f);
			final OffsetData offset = attr.offsets.getMovie().get();
			OpenGL.glTranslatef(offset.x.offset, offset.y.offset, offset.z.offset);
			RotationGL.glRotate(attr.rotations.getMovie().get().getRotate());
			entry.getGui().drawScreen(0, 0, 0f, 1f, size.getWidth(), size.getHeight(), new RenderOption());
		}
		OpenGL.glPopAttrib();
		OpenGL.glPopMatrix();

	}

	@Override
	public @Nullable List<BakedQuad> getGeneralQuads() {
		//		return super.getGeneralQuads();
		return ImmutableList.of();
	}
}