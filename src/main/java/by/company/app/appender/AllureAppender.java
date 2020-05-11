package by.company.app.appender;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllureAppender extends AppenderSkeleton {

    private static final Map<Thread, List<String>> threadLogMap = new HashMap<>();

    @Override
    protected void append(LoggingEvent loggingEvent) {

        if (!threadLogMap.containsKey(Thread.currentThread()))
            threadLogMap.put(Thread.currentThread(), new ArrayList<>());

        String message = this.layout.format(loggingEvent);

        threadLogMap.get(Thread.currentThread()).add(message);

    }

    public static String reset() {

        if (threadLogMap.containsKey(Thread.currentThread())) {

            StringBuilder sb = new StringBuilder();

            threadLogMap
                    .get(Thread.currentThread())
                    .forEach(f -> sb.append(f).append("\t"));

            threadLogMap.remove(Thread.currentThread());

            return sb.toString();

        } else return Thread.currentThread().getName() + " is not contain in a thread log map.";
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return true;
    }

}
