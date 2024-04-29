import kotlinx.io.*

sealed class GGUFSectionEmitter {
    data class Tensor(val data: ByteArray) : GGUFSectionEmitter()
    data class Metadata(val data: ByteArray) : GGUFSectionEmitter()
}

class GGUFReader(private val source: Source) {

    private fun readHeader(): GGUFHeader {
        val magicNumber = source.readByteString(4)
        val version = source.readUByte()
        val tensorsCount = source.readULong()
        val metadataKvCount = source.readULong()

        return GGUFHeader(magicNumber.toString(), version, tensorsCount, metadataKvCount)
    }

    fun readSection(): GGUFSectionEmitter {
        val type = source.readByte()
        val length = source.readIntLe()
        val data: ByteArray = source.readByteArray(length)
        return when (type) {
            0x01.toByte() -> GGUFSectionEmitter.Tensor(data)
            0x02.toByte() -> GGUFSectionEmitter.Metadata(data)
            else -> throw IllegalArgumentException("Unknown section type: $type")
        }
    }

    fun parseGGUFModel(): GGUFModel {
        val header = readHeader()

        return GGUFModel(header)

        /*
        // read meta
        0uL.rangeTo(metadata_kv_count).forEach {
            source
        }


        val sections = mutableListOf<Section>()
        while (!source.exhausted()) {
            val type = source.readByte()
            val length = source.readIntLe()
            val data: ByteArray = source.readByteArray(length)
            sections.add(Section(type, data))
        }

        return GGUFModel(magicNumber.toString(), sections)

         */
    }
}