package table

import util.FastElementNotFoundException
import util.fastForEach

open class ComponentTable : NodeTable<CollectionTable>() {
    override fun get(key: Any): CollectionTable {
        return try { super.get(key) } catch (_: FastElementNotFoundException) { CollectionTable().also { set(key, it) } }
    }

    operator fun set(key: Any, newValue: TableVisitor<CollectionTable>) {
        super.set(key, newValue.table)
    }

}

open class CollectionTable(vararg parents: CollectionTable) : NodeTable<RowTable>() {

    init {
        parents.fastForEach(::addChild)
    }

    override fun get(key: Any): RowTable {
        return try { super.get(key) } catch (_: FastElementNotFoundException) { RowTable().also { set(key, it) } }
    }

    operator fun set(key: Any, newValue: TableVisitor<RowTable>) {
        super.set(key, newValue.table)
    }
}

open class RowTable : NodeTable<Any>() {

    infix fun <T> to(code: (RowTable) -> T): T = code(this)

}