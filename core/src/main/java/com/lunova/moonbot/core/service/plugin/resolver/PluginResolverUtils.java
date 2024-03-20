package com.lunova.moonbot.core.service.plugin.resolver;

import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.supplier.RepositorySystemSupplier;
import org.eclipse.aether.supplier.SessionBuilderSupplier;

public class PluginResolverUtils {

    //TODO: Evaluate usefulness of class, at minimum ensure local repository is reused upon restarts.


    public static PluginResolver createDefaultResolver() {
        RepositorySystem system = new RepositorySystemSupplier().get();
        RepositorySystemSession.SessionBuilder sessionBuilder = new SessionBuilderSupplier(system).get();
        LocalRepository localRepository = new LocalRepository("core/data/plugins/");
        return new PluginResolver(system, sessionBuilder.withLocalRepositories(localRepository));
    }
}
