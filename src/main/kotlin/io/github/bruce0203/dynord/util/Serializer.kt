@file:Suppress("unused")
package io.github.bruce0203.dynord.util

import java.io.*

fun <T> serialize(e: T, file: File) {
    val fileOut = FileOutputStream(file)
    val out = ObjectOutputStream(fileOut)
    out.writeObject(e)
    out.close()
    fileOut.close()
}

@Suppress("UNCHECKED_CAST")
fun <T> deserialize(file: File): T {
    val e: T?
    val fileIn = FileInputStream(file)
        val `in` = ObjectInputStream(fileIn)
        e = `in`.readObject() as T
        `in`.close()
        fileIn.close()
    return e
}
