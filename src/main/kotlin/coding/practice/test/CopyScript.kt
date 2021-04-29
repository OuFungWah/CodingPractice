package coding.practice.test

import java.io.File
import java.io.FileFilter
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

fun main() {
    val copyString = CopyScript()
    val scanner: Scanner = Scanner(System.`in`)
    println("输入 JPEG 所在文件夹:")
    val aDirPath = scanner.nextLine()
    println("输入 RAW 所在文件夹:")
    val bDirPath = scanner.nextLine()
    copyString.execute(File(aDirPath), File(bDirPath))
}

class CopyScript {

    companion object {
        const val BUFFERING_SIZE = 10 * 1024
    }

    fun execute(aDir: File, bDir: File) {
        if (!aDir.exists() || !aDir.isDirectory) {
            println("A is not exists or a directory.")
            return
        }
        if (!bDir.exists() || !bDir.isDirectory) {
            println("B is not exists or a directory.")
            return
        }
        val arwDir = File(aDir, "GENERATE_DIR")
        arwDir.mkdirs()
        findSameName(aDir, bDir).forEach {
            println("Try to cpy ${it.name} to ${aDir.absolutePath}")
            copyFile(it, arwDir)
        }
    }

    private fun findSameName(aDir: File, bDir: File): Array<File> {
        val fromDirFileList = aDir.listFiles(FileFilter { '.' in it.name }).map { it.name.split('.').first() }
        return bDir.listFiles(FileFilter { '.' in it.name && (it.name.split('.').first() in fromDirFileList) })
                ?: emptyArray()
    }

    private fun copyFile(sourceFile: File, targetDir: File) {
        var inputStream: FileInputStream? = null
        var outputStream: FileOutputStream? = null
        try {
//            val result = sourceFile.copyTo(targetDir, false, BUFFERING_SIZE)
            inputStream = sourceFile.inputStream()
            val bytes = ByteArray(BUFFERING_SIZE)
            val newFile = File(targetDir, sourceFile.name)
            outputStream = newFile.outputStream()
            while (inputStream.read(bytes) > 0) {
                outputStream.write(bytes)
            }
            if (newFile.exists()) {
                println("Copy Success: ${newFile.absolutePath}")
            } else {
                println("Copy Failed: ${sourceFile.absolutePath}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
            outputStream?.close()
        }
    }

}