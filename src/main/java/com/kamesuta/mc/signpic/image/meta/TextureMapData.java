package com.kamesuta.mc.signpic.image.meta;

import com.kamesuta.mc.bnnwidget.ShortestFloatFormatter;

public class TextureMapData implements IMotionFrame<TextureMapData>, IComposable {
	public static final float defaultUV = 0f;
	// Width Height
	public static final float defaultWH = 1f;
	// Crossing Slitting
	public static final float defaultCS = 1f;
	public static final float defaultOpacity = 10f;
	public static final boolean defaultRepeat = true;
	public static final boolean defaultMipMap = true;

	public final float u;
	public final float v;
	public final float w;
	public final float h;
	public final float c;
	public final float s;
	public final float o;
	public final boolean r;
	public final boolean m;

	public TextureMapData(final float u, final float v, final float w, final float h, final float c, final float s, final float o, final boolean r, final boolean m) {
		this.u = u;
		this.v = v;
		this.w = w;
		this.h = h;
		this.c = c;
		this.s = s;
		this.o = o;
		this.r = r;
		this.m = m;
	}

	public TextureMapData scale(final float scale) {
		return new TextureMapData(this.u*scale, this.v*scale, this.w*scale, this.h*scale, this.c*scale, this.s*scale, this.o*scale, this.r, this.m);
	}

	@Override
	public TextureMapData per() {
		return this;
	}

	@Override
	public TextureMapData per(final float per, final TextureMapData before) {
		return new TextureMapData(
				this.u*per+before.u*(1f-per),
				this.v*per+before.v*(1f-per),
				this.w*per+before.w*(1f-per),
				this.h*per+before.h*(1f-per),
				this.c*per+before.c*(1f-per),
				this.s*per+before.s*(1f-per),
				this.o*per+before.o*(1f-per),
				this.r, this.m);
	}

	@Override
	public String compose() {
		final StringBuilder stb = new StringBuilder();
		if (this.u!=defaultUV)
			stb.append("u").append(ShortestFloatFormatter.format(this.u));
		if (this.v!=defaultUV)
			stb.append("v").append(ShortestFloatFormatter.format(this.v));
		if (this.w!=defaultWH)
			stb.append("w").append(ShortestFloatFormatter.format(this.w));
		if (this.h!=defaultWH)
			stb.append("h").append(ShortestFloatFormatter.format(this.h));
		if (this.c!=defaultCS)
			stb.append("c").append(ShortestFloatFormatter.format(this.c));
		if (this.s!=defaultCS)
			stb.append("s").append(ShortestFloatFormatter.format(this.s));
		if (this.o!=defaultOpacity)
			stb.append("o").append(ShortestFloatFormatter.format(this.o));
		if (this.r!=defaultRepeat)
			stb.append("r");
		if (this.m!=defaultMipMap)
			stb.append("m");
		return stb.toString();
	}

	@Override
	public String toString() {
		return "TextureMapData [u="+this.u+", v="+this.v+", w="+this.w+", h="+this.h+", c="+this.c+", s="+this.s+", o="+this.o+", r="+this.r+", m="+this.m+"]";
	}
}
