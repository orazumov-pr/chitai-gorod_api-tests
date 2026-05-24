package helpers;

import io.qameta.allure.Allure;
import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.TestResult;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.slf4j.Logger;


import java.util.concurrent.atomic.AtomicLong;

public class CustomApiListener implements Filter, TestLifecycleListener {
    private static final AtomicLong counter = new AtomicLong(0);


    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {
        long requestNumber = counter.incrementAndGet();
        Response response = ctx.next(requestSpec, responseSpec);

        Allure.addAttachment(
                String.format("API Request #%d: %s %s", requestNumber, requestSpec.getMethod(), requestSpec.getURI()),
                "text/plain",
                String.format("Request Headers:\n%s\n\nRequest Body:\n%s",
                        requestSpec.getHeaders(),
                        requestSpec.getBody() != null ? requestSpec.getBody().toString() : "Empty")
        );

        Allure.addAttachment(
                String.format("API Response #%d", requestNumber),
                "text/plain",
                String.format("Status Code: %d\n\nResponse Body:\n%s",
                        response.getStatusCode(),
                        response.getBody().asPrettyString())
        );

        return response;
    }

//    @Override
//    public void beforeTestStop(TestResult result) {
//        log.info("Завершен тест: {} - Статус: {}", result.getName(), result.getStatus());
//    }
}

