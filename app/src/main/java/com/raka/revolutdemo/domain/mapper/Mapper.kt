package com.raka.revolutdemo.domain.mapper

abstract class Mapper<in InputModel, out DomainModel> {

    abstract fun toDomain(value: InputModel): DomainModel

    fun toDomain(values: List<InputModel>) = values.map { toDomain(it) }
}