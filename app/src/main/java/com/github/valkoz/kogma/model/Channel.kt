package com.github.valkoz.kogma.model

import org.simpleframework.xml.*

@Root(name = "channel", strict = false)
data class Channel(
        @field:Path("title") @field:Text(required = false) var title: String? = "",
        @field:Path("link") @field:Text(required = false) var link: String? = "",
        @field:Element(name = "description", required = false) var description: String? = "",
        @field:Element(name = "language", required = false) var language: String? = "",
        @field:Element(name = "managingEditor", required = false) var managingEditor: String? = "",
        @field:Element(name = "generator", required = false) var generator: String? = "",
        @field:Element(name = "pubDate", required = false) var pubDate: String? = "",
        @field:Element(name = "image", required = false) var image: String? = "",
        @field:ElementList(name = "item", inline = true) var items: List<Item>? = null)