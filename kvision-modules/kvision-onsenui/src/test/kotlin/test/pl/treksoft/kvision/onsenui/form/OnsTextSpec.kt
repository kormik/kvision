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
package test.pl.treksoft.kvision.onsenui.form

import pl.treksoft.kvision.form.text.TextInputType
import pl.treksoft.kvision.onsenui.form.onsText
import pl.treksoft.kvision.panel.ContainerType
import pl.treksoft.kvision.panel.Root
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class OnsTextSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = ContainerType.FIXED)
            val text = root.onsText(TextInputType.EMAIL, placeholder = "password", label = "Label") {
                this.autofocus = true
            }
            val id = text.input.inputId
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div class=\"kv-ons-form-group form-group\"><label class=\"control-label\" for=\"${id}\">Label</label><ons-input class=\"kv-ons-form-control form-control\" placeholder=\"password\" autofocus=\"autofocus\" type=\"email\" input-id=\"${id}\" modifier=\"underbar\"></ons-input></div>",
                element?.innerHTML,
                "Should render Onsen UI text form component"
            )
        }
    }
}
