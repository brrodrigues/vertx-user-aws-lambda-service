package rio.brunorodrigues.service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import rio.brunorodrigues.repository.UserRepository;

import javax.inject.Inject;

public class UserServiceVerticle extends AbstractVerticle {

    @Inject
    private UserRepository userRepository;

    @Override
    public void start() throws Exception {
        EventBus eventBus = vertx.eventBus();

        eventBus.consumer("GET:/users", message -> message.reply(userRepository.findAll()));

    }
}
