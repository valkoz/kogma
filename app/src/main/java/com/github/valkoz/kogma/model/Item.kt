package com.github.valkoz.kogma.model

import org.simpleframework.xml.*

@Root(name = "item", strict = false)
data class Item(
        @field:Path("title") @field:Text var title: String? = "",
        @field:Element(name = "guid", required = false) var guid: String? = "",
        @field:Path("link") @field:Text(required = false) var link: String? = "",
        @field:Element(name = "description", required = false) var description: String? = "",
        @field:Element(name = "pubDate", required = false) var pubDate: String? = "",
        @field:Namespace(prefix = "dc") @field:Element(name = "creator", required = false) var creator: String? = "",
        @field:ElementList(name = "category", inline = true) var categories: List<Category>? = null
)