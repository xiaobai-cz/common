package io.github.xiaobaicz.common.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream
import java.io.Reader
import java.io.Writer

suspend fun OutputStream.readFrom(input: InputStream) {
    withContext(Dispatchers.IO) {
        val buff = ByteArray(64 * 1024)
        var len = input.read(buff)
        while (len > 0) {
            write(buff, 0, len)
            len = input.read(buff)
        }
        flush()
    }
}

suspend fun InputStream.writeTo(output: OutputStream) {
    output.readFrom(this)
}

suspend fun Writer.readFrom(read: Reader) {
    withContext(Dispatchers.IO) {
        val buff = CharArray(64 * 1024)
        var len = read.read(buff)
        while (len > 0) {
            write(buff, 0, len)
            len = read.read(buff)
        }
        flush()
    }
}

suspend fun Reader.writeTo(writer: Writer) {
    writer.readFrom(this)
}
