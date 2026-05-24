package steps.search;

import data.EndpointsData;
import models.search.SearchResponseRecord;
import steps.BaseSteps;

public class SearchSteps extends BaseSteps {

    public SearchResponseRecord searchItem(String query, String token) {
        return executeGet(EndpointsData.PRODUCT_SEARCH + "?" + EndpointsData.SEARCH_PARAM + "=" + query)
                .as(SearchResponseRecord.class);
    }

    public SearchResponseRecord searchWithPagination(String query, int page, int size) {
        String endpoint = EndpointsData.PRODUCT_SEARCH +
                "?" + EndpointsData.SEARCH_PARAM + "=" + query +
                "&page=" + page + "&size=" + size;
        return executeGet(endpoint)
                .as(SearchResponseRecord.class);
    }

    public SearchResponseRecord searchAndValidate(String query) {
        var response = searchItem(query, token);
        if (!response.hasResults()) {
            throw new AssertionError("Поиск по запросу '" + query + "' не вернул результатов");
        }
        return response;
    }
}
