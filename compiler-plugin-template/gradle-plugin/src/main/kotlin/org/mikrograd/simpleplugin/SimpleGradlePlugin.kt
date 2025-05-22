package org.mikrograd.simpleplugin

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption
import javax.inject.Inject
import org.gradle.api.provider.Provider

class SimpleGradlePlugin @Inject constructor() : KotlinCompilerPluginSupportPlugin {
    override fun apply(target: Project) {}

    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean = true

    override fun getCompilerPluginId(): String = "org.mikrograd.simpleplugin"

    override fun getPluginArtifact(): SubpluginArtifact =
        SubpluginArtifact("org.mikrograd", "plugin", "0.1-SNAPSHOT")

    override fun getPluginArtifactForNative(): SubpluginArtifact? = null

    override fun getOptions(project: Project, kotlinCompilation: KotlinCompilation<*>): Provider<Iterable<SubpluginOption>> =
        project.provider { emptyList() }
}
