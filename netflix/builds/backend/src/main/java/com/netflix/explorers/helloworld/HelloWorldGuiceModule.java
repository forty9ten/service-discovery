package com.netflix.explorers.helloworld;

import com.google.inject.AbstractModule;
import com.netflix.explorers.annotations.ExplorerGuiceModule;

@ExplorerGuiceModule(jerseyPackagePath = "com.netflix.explorers.helloworld.resources")
public class HelloWorldGuiceModule extends AbstractModule {
    @Override
    protected void configure() {
    }
}
