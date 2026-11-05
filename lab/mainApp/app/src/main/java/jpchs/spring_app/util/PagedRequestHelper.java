package jpchs.spring_app.util;

import jakarta.validation.Validator;
import jpchs.dto.inner.ErrorItem;
import jpchs.dto.paging.Filter;
import jpchs.dto.paging.PagedRequest;
import jpchs.dto.paging.PagedRequestValidated;
import jpchs.dto.paging.SortOrder;
import jpchs.enm.Errors;
import jpchs.spring_app.converter.StringConverter;
import jpchs.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class PagedRequestHelper {

    private final StringConverter<Filter> filterConverter;
    private final StringConverter<SortOrder> sortConverter;
    private final Validator validator;

    public PagedRequestValidated convertAndValidate(PagedRequest req) {
        var errors = new ArrayList<ErrorItem>();
        var filters = new ArrayList<Filter>();
        var sort = new ArrayList<SortOrder>();

        var constrainsViolations = validator.validate(req);
        constrainsViolations
                .forEach(c ->
                    errors.add(new ErrorItem(
                            Errors.INVALID_ARGUMENT,
                            c.getMessage()
                    ))
                );

        if (nonNull(req.filter()))  {
            for (var filterString : req.filter()) {
                try {
                    filters.add(filterConverter.convert(filterString));
                } catch (IllegalArgumentException e) {
                    errors.add(new ErrorItem(
                            Errors.INVALID_FILTER,
                            "Invalid filter: " + filterString
                    ));
                }
            }
        }

        if (nonNull(req.sort())) {
            for (var sortString : req.sort()) {
                try {
                    sort.add(sortConverter.convert(sortString));
                } catch (IllegalArgumentException e) {
                    errors.add(new ErrorItem(
                            Errors.INVALID_SORT,
                            "Invalid filter: " + sortString
                    ));
                }
            }
        }

        if (!errors.isEmpty()) {
            throw new ApplicationException(errors);
        }

        return new PagedRequestValidated(
                filters,
                sort,
                req.page(),
                req.pageSize()
        );
    }
}
