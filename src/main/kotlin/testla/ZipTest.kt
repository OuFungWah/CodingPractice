package testla

import java.io.*
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

fun main() {
//    val zipFile: File = File("/Users/oufenghua/Documents/Personal/Kotlin/Deom/CodingPractice/src/main/kotlin/testla/web-mobile.zip")
//    val inputStream = FileInputStream(zipFile)
//    val zipInputStream = ZipInputStream(inputStream)
//    var zipEntity: ZipEntry? = zipInputStream.nextEntry
//    while (zipEntity != null){
//        println("${zipEntity.name}")
//        zipEntity = zipInputStream.nextEntry
//    }
//    zipInputStream.close()
//    inputStream.close()
    ZipTest().unZip(File("/Users/oufenghua/Documents/Personal/Kotlin/Deom/CodingPractice/src/main/kotlin/testla/web-mobile.zip"))
}


class ZipTest {

    /**
     * @param targetFile test1.txt
     */
    fun zip(targetFile: String) {
        // 打开输出流。锚定压缩后输出的 zip 文件
        val fos = FileOutputStream("compressed.zip")
        // 用 Zip 的输出流包装输出流
        val zipOut = ZipOutputStream(fos)
        // 打开源文件
        val fileToZip = File(targetFile)
        // 打开源文件的输入流
        val fis = FileInputStream(fileToZip)
        // 使用 ZipEntity 包装源文件
        val zipEntry = ZipEntry(fileToZip.name)
        // 将 zipEntity 放入 Zip 的输出流
        zipOut.putNextEntry(zipEntry)
        // 创建缓冲区
        val bytes = ByteArray(1024)
        var length: Int
        while (fis.read(bytes).also { length = it } >= 0) {
            zipOut.write(bytes, 0, length)
        }
        zipOut.close()
        fis.close()
        fos.close()
    }

    fun zipMultiFile() {
        val srcFiles = Arrays.asList("test1.txt", "test2.txt")
        val fos = FileOutputStream("multiCompressed.zip")
        val zipOut = ZipOutputStream(fos)
        for (srcFile in srcFiles) {
            val fileToZip = File(srcFile)
            val fis = FileInputStream(fileToZip)
            val zipEntry = ZipEntry(fileToZip.name)
            zipOut.putNextEntry(zipEntry)
            val bytes = ByteArray(1024)
            var length: Int
            while (fis.read(bytes).also { length = it } >= 0) {
                zipOut.write(bytes, 0, length)
            }
            fis.close()
        }
        zipOut.close()
        fos.close()
    }

    fun unZip(zipFile: File, targetDir: File? = null) {
        val targetParentFile = targetDir?: zipFile.parentFile
        val totalSize = zipFile.length()
        var targetSize = swipeTargetSize(zipFile)
        val zipInputStream = ZipInputStream(FileInputStream(zipFile))
        var entity = zipInputStream.nextEntry
        val buffer = ByteArray(1024)
        var sum = 0
        while (entity != null) {
            val file = File(targetParentFile, entity.name)
            if (entity.isDirectory) {
                file.mkdirs()
            } else {
                file.parentFile.mkdirs()
                val fileOutPutStream = file.outputStream()
                var len: Int
                while (zipInputStream.read(buffer).also { len = it } > 0) {
                    fileOutPutStream.write(buffer, 0, len)
                    sum += len
                    println("total = $targetSize, cur = $sum, progress = ${(sum.toFloat() / targetSize.toFloat() * 100).toInt()}%")
                }
                fileOutPutStream.close()
            }
            entity = zipInputStream.nextEntry
        }
        zipInputStream.close()
    }

    fun swipeTargetSize(file: File): Long {
        val zipFile = ZipFile(file)
        var sum = 0L
        for (element in zipFile.entries()) {
            if (element.isDirectory) continue
            sum += element.size
        }
        println("targetSize = $sum")
        return sum
    }

//    fun swipeTargetSize(zipFile: File): Long {
//        val zipInputStream = ZipInputStream(FileInputStream(zipFile))
//        var zipEntity: ZipEntry? = zipInputStream.nextEntry
//        var targetSize = 0L
//        while (zipEntity != null) {
//            targetSize += zipEntity.size
//            zipEntity = zipInputStream.nextEntry
//        }
//        zipInputStream.close()
//        println("targetSize = $targetSize")
//        return targetSize
//    }

//    fun splitPureName()

//    fun unZip(path: String, fileName: String) {
//        val fileZip = "$path/$fileName"
//        val destDir = File(path)
//        val buffer = ByteArray(1024)
//        val zis = ZipInputStream(FileInputStream(fileZip))
//        var zipEntry = zis.nextEntry
//        while (zipEntry != null) {
//            val newFile: File = newFile(destDir, zipEntry)
//            val fos = FileOutputStream(newFile)
//            var len: Int
//            while (zis.read(buffer).also { len = it } > 0) {
//                fos.write(buffer, 0, len)
//            }
//            fos.close()
//            zipEntry = zis.nextEntry
//        }
//        zis.closeEntry()
//        zis.close()
//    }
//
//    @Throws(IOException::class)
//    fun newFile(destinationDir: File, zipEntry: ZipEntry): File {
//        val destFile = File(destinationDir, zipEntry.name)
//        println("destfile ${destFile.exists()}")
//        println("parent: ${destFile.parentFile} & ${destFile.parentFile.exists()}")
//        val destDirPath = destinationDir.canonicalPath
//        val destFilePath = destFile.canonicalPath
//        if (!destFilePath.startsWith(destDirPath + File.separator)) {
//            throw IOException("Entry is outside of the target dir: " + zipEntry.name)
//        }
//        return destFile
//    }

}