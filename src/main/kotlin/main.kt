import kotlinext.js.asJsObject
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.dom.isText
import net.kyori.adventure.text.minimessage.MiniMessage
import org.w3c.dom.Element
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.asList

fun main() {
    document.addEventListener("DOMContentLoaded", {
        document.getElementById("input")!!.addEventListener("keyup", {
            doStuff(it.target?.asJsObject().unsafeCast<HTMLInputElement>().value)
        })
        document.getElementById("input")!!.addEventListener("change", {
            doStuff(it.target?.asJsObject().unsafeCast<HTMLInputElement>().value)
        })

        window.setInterval( { obfuscateAll() }, 10)
    })
}

fun obfuscateAll() {
    document.getElementsByClassName("obfuscated").asList().forEach {
        obfuscate(it)
    }
}

fun obfuscate(input: Element) {
    if (input.childElementCount > 0) {
        input.children.asList().forEach {
            obfuscate(it)
        }
    } else if (input.textContent != null){
        input.textContent = obfuscate(input.textContent!!)
    }
}

fun obfuscate(input: String): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..input.length)
        .map { allowedChars.random() }
        .joinToString("")
}

fun doStuff(input: String) {
    val output = document.getElementById("output")!!
    output.textContent = ""
    output.append(MiniMessage.get().parse(input).buildOutChildren())
}

fun escapeHtml(str: String): String {
    return str.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#039;")
}