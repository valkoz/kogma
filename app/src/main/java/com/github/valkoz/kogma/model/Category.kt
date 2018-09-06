package com.github.valkoz.kogma.model

import org.simpleframework.xml.Text

data class Category(@field:Text var name: String = "") {
        override fun toString(): String {
                return name
        }
}