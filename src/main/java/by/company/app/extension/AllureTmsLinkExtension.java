package by.company.app.extension;

import by.company.app.annotation.TestCaseId;
import io.qameta.allure.TmsLink;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AllureTmsLinkExtension extends TestListenerAdapter {

    @Override
    public void onTestStart(ITestResult result) {
        Method method = result.getMethod().getConstructorOrMethod().getMethod();

        int caseId = method.isAnnotationPresent(TestCaseId.class) ? method.getAnnotation(TestCaseId.class).value() : 0;

        if (caseId != 0) {

            TmsLink tmsLink = new TmsLink() {

                @Override
                public Class<? extends Annotation> annotationType() {
                    return TmsLink.class;
                }

                @Override
                public String value() {
                    return String.valueOf(caseId);
                }

            };

            Class<?> superclass = method.getClass().getSuperclass();

            Field annotations = null;

            try {
                annotations = superclass.getDeclaredField("declaredAnnotations");
                annotations.setAccessible(true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

            if (annotations != null) {
                try {
                    Map<Class<? extends Annotation>, Annotation> map =
                            (Map<Class<? extends Annotation>, Annotation>) annotations.get(method);

                    if (map.getClass() == Collections.EMPTY_MAP.getClass()) {
                        map = new HashMap<>();
                        annotations.set(method, map);
                    }

                    map.put(TmsLink.class, tmsLink);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
