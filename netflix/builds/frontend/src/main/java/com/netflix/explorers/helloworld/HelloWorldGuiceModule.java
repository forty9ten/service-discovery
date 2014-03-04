package com.netflix.explorers.helloworld;

import com.google.inject.AbstractModule;
import com.netflix.explorers.annotations.ExplorerGuiceModule;
import com.netflix.explorers.helloworld.resources.HelloWorldAppResource;

@ExplorerGuiceModule(jerseyPackagePath = "com.netflix.explorers.helloworld.resources")
public class HelloWorldGuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(HelloWorldAppResource.class).asEagerSingleton();
    }
}
