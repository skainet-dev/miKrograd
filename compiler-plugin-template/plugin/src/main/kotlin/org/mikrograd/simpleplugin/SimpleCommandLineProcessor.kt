package org.mikrograd.simpleplugin

import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.config.CompilerConfiguration

class SimpleCommandLineProcessor : CommandLineProcessor {
    override val pluginId: String = "org.mikrograd.simpleplugin"
    override val pluginOptions: Collection<CliOption> = emptyList()

    override fun processOption(option: CliOption, value: String, configuration: CompilerConfiguration) {}
}
