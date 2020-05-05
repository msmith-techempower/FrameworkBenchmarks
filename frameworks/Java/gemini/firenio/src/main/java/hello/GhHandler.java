package hello;

import com.firenio.codec.http11.HttpContentType;
import com.firenio.codec.http11.HttpFrame;
import com.techempower.gemini.Context;
import com.techempower.gemini.firenio.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GhHandler {
    private Logger log = LoggerFactory.getLogger(GhHandler.class);
    
    static final String PLAINTEXT = "Hello, World!";

    public void plaintext(Context context) throws IOException {
        // NOTE: This is WIP Hacky Stuff - do not rely on ANY version of Gemini
        //       working this way until an official release. This is a proof of
        //       concept build to measure performance ONLY.
        ((HttpFrame)((HttpRequest)context.getRequest()).getFrame()).setContentType(HttpContentType.text_plain);
        context.getRequest().print(PLAINTEXT);
    }
    
    public void json(Context context) throws IOException {
        // NOTE: This is WIP Hacky Stuff - do not rely on ANY version of Gemini
        //       working this way until an official release. This is a proof of
        //       concept build to measure performance ONLY.
        Json json = new Json();
        json.message = PLAINTEXT;
        ((HttpFrame)((HttpRequest)context.getRequest()).getFrame()).setContentType(HttpContentType.application_json);
        context.getRequest().print(context.getApplication().getJavaScriptWriter().write(json));
    }
}
