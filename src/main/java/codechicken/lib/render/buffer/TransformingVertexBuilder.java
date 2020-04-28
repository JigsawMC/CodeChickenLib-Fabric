package codechicken.lib.render.buffer;

import codechicken.lib.vec.Matrix4;
import codechicken.lib.vec.Transformation;
import codechicken.lib.vec.Vector3;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

/**
 * Created by covers1624 on 4/24/20.
 */
public class TransformingVertexBuilder implements ISpriteAwareVertexBuilder {

    private final IVertexBuilder delegate;
    private final Transformation transform;
    private final Vector3 storage = new Vector3();

    public TransformingVertexBuilder(IVertexBuilder delegate, MatrixStack stack) {
        this(delegate, new Matrix4(stack));
    }

    public TransformingVertexBuilder(IVertexBuilder delegate, Transformation transform) {
        this.delegate = delegate;
        this.transform = transform;
    }

    @Override
    public IVertexBuilder pos(double x, double y, double z) {
        storage.set(x, y, z);
        transform.apply(storage);
        delegate.pos(storage.x, storage.y, storage.z);
        return this;
    }

    @Override
    public IVertexBuilder color(int red, int green, int blue, int alpha) {
        delegate.color(red, green, blue, alpha);
        return this;
    }

    @Override
    public IVertexBuilder tex(float u, float v) {
        delegate.tex(u, v);
        return this;
    }

    @Override
    public IVertexBuilder overlay(int u, int v) {
        delegate.overlay(u, v);
        return this;
    }

    @Override
    public IVertexBuilder lightmap(int u, int v) {
        delegate.lightmap(u, v);
        return this;
    }

    @Override
    public IVertexBuilder normal(float x, float y, float z) {
        storage.set(x, y, z);
        transform.applyN(storage);
        delegate.normal((float) storage.x, (float) storage.y, (float) storage.z);
        return this;
    }

    @Override
    public void endVertex() {
        delegate.endVertex();
    }

    @Override
    public void sprite(TextureAtlasSprite sprite) {
        if (delegate instanceof ISpriteAwareVertexBuilder) {
            ((ISpriteAwareVertexBuilder) delegate).sprite(sprite);
        }
    }
}
