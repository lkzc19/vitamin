package structural.facede

import java.io.File


class VideoFile(filename: String) {
    val codecType = filename.substringAfterLast('.')

    var _name = filename.substringBeforeLast('.')
    var _ext = codecType

    override fun toString(): String {
        return "${_name}.${_ext}"
    }
}

/**
 * 对外暴露的简单接口
 */
interface Codec {
    val type: String
}

/**
 * 封装子系统1
 */
class OggCompressionCodec : Codec {
    override val type = "ogg"
}

/**
 * 封装子系统2
 */
class MPEG4CompressionCodec : Codec {
    override val type = "mp4"
}

/**
 * 子系统对象生成工厂
 */
class CodecFactory {
    companion object fun extract(file: VideoFile): Codec {
        val codecType = file.codecType
        if (codecType == "mp4") {
            println("CodecFactory: extracting mpeg audio...")
            return MPEG4CompressionCodec()
        } else {
            println("CodecFactory: extracting ogg audio...")
            return OggCompressionCodec()
        }
    }
}

/**
 * 调用子系统的统一封装方法
 */
class BitrateReader {
    companion object {
        fun read(file: VideoFile, sourceCodec: Codec): VideoFile {
            println("BitrateReader: reading file...")
            // ... 一些操作
            if (file.codecType != sourceCodec.type) {
                throw IllegalArgumentException("Codec 不匹配")
            }
            return file
        }

        fun convert(file: VideoFile, destCodec: Codec): VideoFile {
            println("BitrateReader: writing file...")
            // ... 一些操作
            file._ext = destCodec.type
            return file
        }
    }
}

/**
 * 各个子系统的一些共有功能封装
 */
class AudioMixer {
    fun fix(result: VideoFile): VideoFile {
        println("AudioMixer: fixing audio...")
        return result
    }
}

/**
 * 为了将框架的复杂性隐藏在一个简单接口背后，我们创建了一个外观类。它是在功能性和简洁性之间做出的权衡。
 */
class VideoConversionFacade {

    fun convert(filename: String, format: String): VideoFile {
        val file = VideoFile(filename)
        // 获取解码方式
        val sourceCodec = CodecFactory().extract(file)
        // 获取编码方式
        val destCodec = if (format == "mp4") {
            MPEG4CompressionCodec()
        } else {
            OggCompressionCodec()
        }
        // 解码
        val buffer = BitrateReader.read(file, sourceCodec)
        // 编码
        val result = BitrateReader.convert(buffer, destCodec)
        // 其他的一些操作
        return AudioMixer().fix(result)
    }
}

