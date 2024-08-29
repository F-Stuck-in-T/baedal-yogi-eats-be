package com.fstuckint.baedalyogieats.core.api.product.support.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class SliceResult<S> {

    private final S result;

    @JsonProperty
    private final boolean hasNext;

    private final String nextCursor;

    private SliceResult(S result, boolean hasNext, String nextCursor) {
        this.result = result;
        this.hasNext = hasNext;
        this.nextCursor = nextCursor;
    }

    public S getResult() {
        return result;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public String getNextCursor() {
        return nextCursor;
    }

    public static <S> SliceResult<List<S>> of(List<S> result, long limit, String nextCursor) {
        boolean hasNext = result.size() > limit;
        if (hasNext) {
            return new SliceResult<>(result.subList(0, (int) limit - 1), true, nextCursor);
        }
        return new SliceResult<>(result, false, null);
    }

}
