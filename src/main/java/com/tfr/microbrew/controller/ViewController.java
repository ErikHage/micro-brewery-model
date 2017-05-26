package com.tfr.microbrew.controller;

import com.tfr.microbrew.config.Routes;
import com.tfr.microbrew.config.Views;
import com.tfr.microbrew.helper.ContextHelper;
import com.tfr.microbrew.model.Context;
import com.tfr.microbrew.model.ContextSummary;
import com.tfr.microbrew.model.ViewContext;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 * Created by Erik Hage on 5/13/2017.
 */
@Controller
public class ViewController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    @RequestMapping(value = Routes.INDEX,
                    method = RequestMethod.GET)
    public String index(Model model) {
        logger.debug("Request to " + Routes.INDEX);

        List<String> contextIds = ContextHelper.getContextIds();

        model.addAttribute("contextIds", contextIds);

        return Views.INDEX_TEMPLATE;
    }

    @RequestMapping(value = Routes.CONTEXT_SUMMARY,
            method = RequestMethod.GET)
    public String contextSummary(Model model,
                               @PathVariable("contextId") String contextId) {
        ContextSummary contextSummary;

        try {
            contextSummary = ContextHelper.getContextSummary(contextId);
        } catch (FileNotFoundException e) {
            String message = "Invalid Context: " + contextId;
            model.addAttribute("message", message);
            return Views.ERROR_PAGE;
        }

        model.addAttribute("contextId", contextId);
        model.addAttribute("contextSummary", contextSummary);

        return Views.CONTEXT_SUMMARY_TEMPLATE;
    }

    @RequestMapping(value = Routes.DAY_VIEW_START,
                    method = RequestMethod.GET)
    public String dayViewStart(Model model,
                               @PathVariable("contextId") String contextId) {
        Context context = null;
        try {
            context = ContextHelper.getInitialContextData(contextId);
        } catch (FileNotFoundException e) {
            String message = "Invalid Context: " + contextId;
            model.addAttribute("message", message);
            return Views.ERROR_PAGE;
        }

        LocalDate date = context.getDate();
        LocalDate previousDate = date.minusDays(1);
        LocalDate nextDate = date.plusDays(1);

        model.addAttribute("hidePreviousArrow", true);
        model.addAttribute("hideNextArrow", false);
        model.addAttribute("contextId", contextId);
        model.addAttribute("currentDate", date.toString(dateFormatter));
        model.addAttribute("previousDate", previousDate.toString(dateFormatter));
        model.addAttribute("nextDate", nextDate.toString(dateFormatter));
        model.addAttribute("viewContext", new ViewContext(context));

        return Views.DAY_VIEW_TEMPLATE;
    }

    @RequestMapping(value = Routes.DAY_VIEW,
                    method = RequestMethod.GET)
    public String dayView(Model model,
                          @PathVariable("contextId") String contextId,
                          @PathVariable("date") String dateString) {
        Context context;
        try {
            context = ContextHelper.getContextData(contextId, dateString);
        } catch (FileNotFoundException e) {
            String message = "Date outside of Context range: " + contextId;
            model.addAttribute("message", message);
            return Views.ERROR_PAGE;
        }

        LocalDate date = LocalDate.parse(dateString, dateFormatter);
        LocalDate previousDate = date.minusDays(1);
        LocalDate nextDate = date.plusDays(1);
        ViewContext viewContext = new ViewContext(context);

        boolean hidePreviousArrow = !isValidFile(contextId, previousDate);
        boolean hideNextArrow = !isValidFile(contextId, nextDate);

        model.addAttribute("hidePreviousArrow", hidePreviousArrow);
        model.addAttribute("hideNextArrow", hideNextArrow);
        model.addAttribute("contextId", contextId);
        model.addAttribute("currentDate", dateString);
        model.addAttribute("previousDate", previousDate.toString(dateFormatter));
        model.addAttribute("nextDate", nextDate.toString(dateFormatter));
        model.addAttribute("viewContext", viewContext);

        return Views.DAY_VIEW_TEMPLATE;
    }

    private boolean isValidFile(String context, LocalDate date) {
        try {
            ContextHelper.getContextData(context, date.toString(dateFormatter));
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }


}
