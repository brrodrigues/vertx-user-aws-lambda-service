package rio.brunorodrigues.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import io.vertx.core.AsyncResult;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@RequestScoped
public class VertxHandler implements RequestHandler<Map, Object> {

    @Inject
    private Vertx vertx;

    @Override
    public Object handleRequest(Map input, Context context) {
        CompletableFuture<Object> future = new CompletableFuture<>();

        // Send message to event bus using httpmethod:resource as dynamic channel
        final String eventBusAddress = "${input.httpMethod}:${input.resource}";

        Object o = new Object();

        vertx.eventBus().send(eventBusAddress, input, (AsyncResult<Message<Object>> messageAsyncResult) -> {
            if (messageAsyncResult.succeeded()){
                future.complete(messageAsyncResult.result().body());
            }else {
                future.exceptionally(Throwable::getCause);
            }
        } );


        try {
            o = future.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return o;
    }
}
