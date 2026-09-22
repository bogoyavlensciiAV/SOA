package jpchs.spring_app.util;

import jakarta.validation.Validator;
import jpchs.spring_app.converter.StringConverter;
import jpchs.spring_app.dto.inner.ErrorItem;
import jpchs.spring_app.dto.paging.Filter;
import jpchs.spring_app.dto.paging.PagedRequest;
import jpchs.spring_app.dto.paging.PagedRequestValidated;
import jpchs.spring_app.enm.Errors;
import jpchs.spring_app.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class PagedRequestHelper {

    private final StringConverter<Filter> filterConverter;
    private final StringConverter<Sort> sortConverter;
    private final Validator validator;

    public PagedRequestValidated convertAndValidate(PagedRequest req) {
        var errors = new ArrayList<ErrorItem>();
        var filters = new ArrayList<Filter>();
        var sort = new ArrayList<Sort>();

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
