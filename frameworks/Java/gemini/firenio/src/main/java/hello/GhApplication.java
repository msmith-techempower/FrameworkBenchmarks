package hello;

import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.firenio.FirenioGeminiApplication;
import com.techempower.js.JavaScriptWriter;

public class GhApplication extends FirenioGeminiApplication {
    public static final GhApplication INSTANCE = new GhApplication();

    public static void main(String[] args) throws Exception {
        INSTANCE.start("TestServer", 8080, 1024 * 8);
    }

    @Override
    protected Dispatcher constructDispatcher() {
        return new FunctionalInterfaceRouter()
                .get("/", context -> context.setStatus(200))
                .get("/plaintext", new GhHandler()::plaintext)
                .get("/json", new GhHandler()::json)
                .defaultRoute(context -> context.setStatus(404));
    }
}
