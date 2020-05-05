package hello;

import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class FunctionalInterfaceRouter implements Dispatcher {
    private static final Logger log = LoggerFactory.getLogger(FunctionalInterfaceRouter.class);
    private static final Map<String, ContextConsumer> GET_ROUTES = new HashMap<>();
    private static final Map<String, ContextConsumer> POST_ROUTES = new HashMap<>();
    
    private ContextConsumer _default = null;

    /**
     * Takes the given string URI and ContextConsumer and associates them for
     * routing. This method should only be called during an application's
     * constructDispatcher method.
     * @param route
     * @param consumer
     * @return This FunctionalInterfaceRouter
     */
    public FunctionalInterfaceRouter get(String route, ContextConsumer consumer) {
        GET_ROUTES.put(route, consumer);
        return this;
    }
    
    public FunctionalInterfaceRouter post(String route, ContextConsumer consumer) {
        POST_ROUTES.put(route, consumer);
        return this;
    }

    @Override
    public String[] getRoutes() {
        Set<String> routes = new HashSet<>();
        for (String route : GET_ROUTES.keySet()) {
            routes.add(route);
        }
        for (String route : POST_ROUTES.keySet()) {
            routes.add(route);
        }
        return routes.toArray(new String[routes.size()]);
    }

    /**
     * Takes the given ContextConsumer and uses it as the default action for
     * a string URI that is not mapped.
     * This differs from non-default routes in that it will respond to all
     * HttpMethod types (GET, POST, etc).
     * @param consumer
     * @return This FunctionalInterfaceRouter
     */
    public FunctionalInterfaceRouter defaultRoute(ContextConsumer consumer) {
        this._default = consumer;
        return this;
    }

    @Override
    public boolean dispatch(Context context) {
        final String uri = context.getRequestUri();
        final ContextConsumer method;

        switch(context.getRequestMethod()) {
            case GET:
                method = GET_ROUTES.get(uri);
                break;
            case POST:
                method = POST_ROUTES.get(uri);
                break;
            default:
                // This is likely an error
                method = null;
        }

        if (method != null) {
            try {
                method.accept(context);
            } catch (Throwable throwable) {
                dispatchException(context, throwable, "Error thrown by handler.");
            }
        } else if (_default != null) {
            try {
                _default.accept(context);
            } catch (Throwable throwable) {
                dispatchException(context, throwable, "Error thrown by default handler.");
            }
        } else {
            /*
            There is nothing to be done - the dispatcher found nothing to do.
             */
            context.setStatus(400);
        }

        return true;
    }

    @Override
    public void dispatchComplete(Context context) {

    }

    @Override
    public void renderStarting(Context context, String renderingName) {

    }

    @Override
    public void renderComplete(Context context) {

    }

    @Override
    public void dispatchException(Context context, Throwable exception, String description) {
        log.error(description, exception);
    }
}
