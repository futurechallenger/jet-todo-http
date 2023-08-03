package com.example.jet_todo.model

data class ConfigOption(
    var name: String,
    var code: Int,
    var subConfigs: List<ConfigOption>?,
    var parentCode: Int?
)