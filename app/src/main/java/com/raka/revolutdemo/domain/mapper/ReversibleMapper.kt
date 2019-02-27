package com.raka.revolutdemo.domain.mapper

abstract class ReversibleMapper<InputModel, DomainModel> : Mapper<InputModel, DomainModel>() {

    abstract fun fromDomain(value: DomainModel): InputModel

    fun fromDomain(values: List<DomainModel>) = values.map { fromDomain(it) }
}