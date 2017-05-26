package com.tfr.microbrew.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.tfr.microbrew.model.Context;
import com.tfr.microbrew.model.ContextSummary;
import org.apache.commons.io.FilenameUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Created by Erik Hage on 5/13/2017.
 */
public class ContextHelper {

    private static final Logger logger = LoggerFactory.getLogger(ContextHelper.class);

    private static final String CONTEXT_DIRECTORY = "logs/context";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    public static List<String> getContextIds() {
        List<String> contextIds;
        File[] directories = new File(CONTEXT_DIRECTORY).listFiles(File::isDirectory);

        if(directories == null || directories.length == 0) {
            logger.warn("No context ids found");
            return new ArrayList<>();
        }

        contextIds = Arrays.stream(directories)
                .map(File::getName)
                .collect(Collectors.toList());

        logger.debug(String.format("Found %s context ids", contextIds.size()));
        return contextIds;
    }

    public static Context getInitialContextData(String contextId) throws FileNotFoundException {
        Context context;
        try {
            List<File> files = Arrays.asList(new File(CONTEXT_DIRECTORY + "/" + contextId).listFiles());
            Collections.sort(files);
            String filename = files.get(0).getName();
            logger.debug("Found file: " + filename);

            URL url = Paths.get("logs","context", contextId, filename).toUri().toURL();
            String json = Resources.toString(url, Charsets.UTF_8);
            context = objectMapper.readValue(json, Context.class);
        } catch (IOException e) {
            logger.error(String.format("Error getting path to initial context [%s]", contextId), e);
            throw new FileNotFoundException(String.format("Error getting path to initial context [%s]", contextId));
        }
        return context;
    }

    public static Context getContextData(String contextId, String filename) throws FileNotFoundException {
        Context context;
        try {
            URL url = Paths.get("logs","context", contextId, filename + ".json").toUri().toURL();
            String json = Resources.toString(url, Charsets.UTF_8);
            context = objectMapper.readValue(json, Context.class);
        } catch (IOException e) {
            logger.error(String.format("Error getting path to context [%s] file [%s]", contextId, filename), e);
            throw new FileNotFoundException(String.format("Error getting path to context [%s] file [%s]", contextId, filename));
        }
        return context;
    }

    public static ContextSummary getContextSummary(String contextId) throws FileNotFoundException {
        ContextSummary contextSummary;
        try {
            List<File> files = Arrays.asList(new File(CONTEXT_DIRECTORY + "/" + contextId).listFiles());
            Collections.sort(files);

            contextSummary = new ContextSummary();
            contextSummary.setStartDate(FilenameUtils.getBaseName(files.get(0).getName()));

            for(int i=0; i<files.size(); i++) {
                URL url = Paths.get("logs","context", contextId, files.get(i).getName()).toUri().toURL();
                String json = Resources.toString(url, Charsets.UTF_8);
                Context context = objectMapper.readValue(json, Context.class);

                contextSummary.addContext(context);

                if(i == files.size()-1) {
                    contextSummary.setEndDate(FilenameUtils.getBaseName(files.get(i).getName()));
                }
            }

        } catch (IOException e) {
            logger.error(String.format("Error getting path to context [%s]", contextId), e);
            throw new FileNotFoundException(String.format("Error getting path to context [%s]", contextId));
        }
        return contextSummary;
    }


}
