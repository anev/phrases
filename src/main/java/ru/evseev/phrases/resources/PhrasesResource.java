package ru.evseev.phrases.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Multiset;
import ru.evseev.phrases.FilesProcessor;
import ru.evseev.phrases.core.SentenceProcessor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by anev on 16/05/16.
 */
@Path("/phrases")
@Produces(MediaType.APPLICATION_JSON)
public class PhrasesResource {

    private final FilesProcessor filesProcessor;
    private final SentenceProcessor sp;

    public PhrasesResource(FilesProcessor filesProcessor, SentenceProcessor sp) {
        this.filesProcessor = filesProcessor;
        this.sp = sp;
    }

    private static final String RESP = "most popular phrase (%s repetitions) is '%s'";

    @Path("")
    @GET
    @Timed
    public String mostPopularPhrase() throws IOException {
        Multiset.Entry<String> en = sp.mostPopularPhrase();
        return String.format(RESP, en.getCount(), en.getElement());
    }

    @Path("start")
    @GET
    @Timed
    public String startWork() throws IOException {
        sp.clear();
        filesProcessor.process();
        Multiset.Entry<String> en = sp.mostPopularPhrase();
        return String.format(RESP, en.getCount(), en.getElement());
    }


}
