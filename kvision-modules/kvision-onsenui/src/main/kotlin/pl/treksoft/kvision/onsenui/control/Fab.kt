/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package pl.treksoft.kvision.onsenui.control

import com.github.snabbdom.VNode
import org.w3c.dom.events.MouseEvent
import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.html.CustomTag
import pl.treksoft.kvision.onsenui.FloatPosition
import pl.treksoft.kvision.onsenui.visual.Icon
import pl.treksoft.kvision.utils.set

/**
 * An Onsen UI fab component.
 *
 * @constructor Creates a floating action button component.
 * @param icon the name of the icon placed on the button
 * @param floatPosition a position of the button
 * @param content the content the button.
 * @param rich whether [content] can contain HTML code
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
@Suppress("LeakingThis")
open class Fab(
    icon: String? = null,
    floatPosition: FloatPosition? = null,
    content: String? = null,
    rich: Boolean = false,
    classes: Set<String> = setOf(),
    init: (Fab.() -> Unit)? = null
) : CustomTag("ons-fab", content, rich, null, classes) {

    /**
     * The name of the icon placed on the button.
     */
    var icon: String?
        get() = iconWidget?.icon
        set(value) {
            if (value != null) {
                if (iconWidget != null) {
                    iconWidget?.icon = value
                } else {
                    iconWidget = Icon(value).apply { this.parent = this@Fab }
                }
            } else {
                iconWidget?.dispose()
                iconWidget = null
            }
        }

    /**
     * A position of the floating button.
     */
    var floatPosition: FloatPosition? by refreshOnUpdate(floatPosition)

    /**
     * Whether the button will have a ripple effect when tapped.
     */
    var ripple: Boolean? by refreshOnUpdate()

    /**
     * A modifier attribute to specify custom styles.
     */
    var modifier: String? by refreshOnUpdate()

    /**
     * Whether the button is disabled.
     */
    var disabled: Boolean? by refreshOnUpdate()

    /**
     * A dynamic property returning whether the button is visible.
     */
    @Suppress("UnsafeCastFromDynamic")
    val isVisible: Boolean?
        get() = getElement()?.asDynamic()?.visible

    /**
     * An internal icon widget.
     */
    protected var iconWidget: Icon? = icon?.let { Icon(it).apply { this.parent = this@Fab } }

    init {
        init?.invoke(this)
    }

    override fun childrenVNodes(): Array<VNode> {
        val iconArr = iconWidget?.let { arrayOf(it.renderVNode()) } ?: emptyArray()
        return iconArr + super.childrenVNodes()
    }

    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        floatPosition?.let {
            sn.add("position" to it.type)
        }
        if (ripple == true) {
            sn.add("ripple" to "ripple")
        }
        modifier?.let {
            sn.add("modifier" to it)
        }
        if (disabled == true) {
            sn.add("disabled" to "disabled")
        }
        return sn
    }

    /**
     * Shows the button.
     */
    open fun showFab() {
        getElement()?.asDynamic()?.show()
    }

    /**
     * Hides the button.
     */
    open fun hideFab() {
        getElement()?.asDynamic()?.hide()
    }

    /**
     * Toggles the visibility of the button.
     */
    open fun toggleFab() {
        getElement()?.asDynamic()?.toggle()
    }

    /**
     * A convenient helper for easy setting onClick event handler.
     */
    open fun onClick(handler: Fab.(MouseEvent) -> Unit): Fab {
        this.setEventListener<Fab> {
            click = { e ->
                self.handler(e)
            }
        }
        return this
    }

    override fun dispose() {
        super.dispose()
        iconWidget?.dispose()
    }
}

/**
 * DSL builder extension function.
 *
 * It takes the same parameters as the constructor of the built component.
 */
fun Container.fab(
    icon: String? = null,
    floatPosition: FloatPosition? = null,
    content: String? = null,
    rich: Boolean = false,
    classes: Set<String>? = null,
    className: String? = null,
    init: (Fab.() -> Unit)? = null
): Fab {
    val fab = Fab(icon, floatPosition, content, rich, classes ?: className.set, init)
    this.add(fab)
    return fab
}
