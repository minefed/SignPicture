package com.kamesuta.mc.signpic.entry;

import com.kamesuta.mc.signpic.entry.content.ContentId;
import com.kamesuta.mc.signpic.image.meta.MetaBuilder;

public class EntryIdBuilder {
	private MetaBuilder meta;
	private String uri;

	public EntryIdBuilder() {
	}

	public EntryIdBuilder load(final EntryId source) {
		if (source!=null) {
			this.meta = source.getMetaBuilder();
			if (source.hasContentId())
				this.uri = source.getContentId().getURI();
		}
		return this;
	}

	public void setMeta(final MetaBuilder meta) {
		this.meta = meta;
	}

	public MetaBuilder getMeta() {
		if (this.meta==null)
			return this.meta = new MetaBuilder();
		else
			return this.meta;
	}

	public void setURI(final String uri) {
		this.uri = uri;
	}

	public String getURI() {
		if (this.uri==null)
			return this.uri = "";
		else
			return this.uri;
	}

	public EntryId build() {
		return new EntryId("#"+new ContentId(getURI()).getID()+getMeta().compose());
	}
}
