package rio.brunorodrigues.launcher;

import io.vertx.core.Launcher;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rio.brunorodrigues.service.UserServiceVerticle;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class VertxLauncher {

    private final Logger LOG = LoggerFactory.getLogger(VertxLauncher.class);

    public static void main(String... args){
        Launcher.executeCommand("run", UserServiceVerticle.class.getName());
    }

    private Vertx vertx;

    public void initVertx(@Observes @Initialized(ApplicationScoped.class) Object obj) {
        System.setProperty("vertx.disableFileCPResolving", "true");
        vertx = Vertx.vertx();
        vertx.deployVerticle(new UserServiceVerticle());
    }

    @Produces
    @ApplicationScoped
    public Vertx vertx(){
        LOG.info("Recuperando vertx....");
        return vertx;
    }

    @PreDestroy
    public void shutdown() {
        LOG.info("Finalizando vertx....");
        this.vertx.close();
    }
}
