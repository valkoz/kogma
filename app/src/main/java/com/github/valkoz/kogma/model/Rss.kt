package com.github.valkoz.kogma.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "Rss")
data class Rss(@field:Element(name="channel") var channel: Channel? = null)